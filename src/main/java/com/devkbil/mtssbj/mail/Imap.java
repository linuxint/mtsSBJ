package com.devkbil.mtssbj.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import com.devkbil.mtssbj.common.util.DateUtil;
import com.devkbil.mtssbj.common.util.FileUtil;
import com.devkbil.mtssbj.common.util.FileVO;

import com.devkbil.mtssbj.mail.MailVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Imap {

    private final String protocol = "imap";
    private final String mbox = "INBOX";
    private final boolean debug = true;
    private String filePath;

    private Store store;
    private Folder folder;
    private Message[] msgs;

    public static void main(String[] args) throws Exception {
        Imap a = new Imap();
        a.connect("", "", "");
        a.patchMessage(null);

        int cnt = 0;

        while (cnt < a.msgs.length) {
            ArrayList<MailVO> msgList = a.getMail(0);
            cnt += msgList.size();
            System.out.println(cnt);
            break;
        }
        a.disconnect();
    }

    public void connect(String host, final String user, final String password) {
        Properties props = System.getProperties();
        props.put("mail.store.protocol", "imap");
        props.put("mail.imap.host", host);
        props.put("mail.imap.socketFactory.port", "993");
        props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //props.put("mail.debug", debug);

        // Get a Session object
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        session.setDebug(debug);

        // Get a Store object
        try {
            store = session.getStore(protocol);
            store.connect(host, user, password);
        } catch (MessagingException e) {
        }
    }

    public void disconnect() {
        try {
            if (folder != null) {
                folder.close(false);
            }
            store.close();
        } catch (MessagingException e) {
        }
    }

    public Integer patchMessage(String chgdate) {
        filePath = System.getProperty("user.dir") + "/fileupload/"; //localeMessage.getMessage("info.filePath");

        try {
            folder = store.getDefaultFolder();
            if (folder == null) {
                System.out.println("Cant find default namespace");
                return 0;
            }

            folder = folder.getFolder(mbox);
            if (folder == null) {
                System.out.println("Invalid folder");
                return 0;
            }

            folder.open(Folder.READ_ONLY);
            SearchTerm dateTerm = null;

            if (chgdate != null) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                c.set(Calendar.AM_PM, Calendar.AM);
                c.add(Calendar.DATE, 1);    // next day
                ReceivedDateTerm endDateTerm = new ReceivedDateTerm(ComparisonTerm.LT, c.getTime());

                c.setTime(DateUtil.str2Date(chgdate));
                ReceivedDateTerm startDateTerm = new ReceivedDateTerm(ComparisonTerm.GE, c.getTime());

                dateTerm = new AndTerm(startDateTerm, endDateTerm);
            }

            if (dateTerm == null) {
                msgs = folder.getMessages();
            } else {
                msgs = folder.search(dateTerm);
                FetchProfile fp = new FetchProfile();
                fp.add(FetchProfile.Item.ENVELOPE);
                folder.fetch(msgs, fp);
            }
        } catch (MessagingException e) {
        }
        return msgs.length;
    }

    public ArrayList<MailVO> getMail(Integer sIndex) {
        ArrayList<MailVO> msgList = new ArrayList<MailVO>();

        try {
            for (int i = sIndex; i < msgs.length; i++) {
                MailVO mailInfo = new MailVO();
                dumpPart(msgs[i], mailInfo);
                msgList.add(mailInfo);
                if (msgList.size() == 100) {
                    break; // commit by 100
                }
            }
        } catch (Exception e) {
        }
        return msgList;
    }

    public void dumpPart(Part p, MailVO mailInfo) throws Exception {
        if(p instanceof Message) {
            Message message = (Message) p;
            Address[] address;
            // FROM
            if ((address = message.getFrom()) != null) {
                mailInfo.setEmfrom(address[0].toString());
            }

            // TO
            if ((address = message.getRecipients(Message.RecipientType.TO)) != null) {
                for (int j = 0; j < address.length; j++) {
                    mailInfo.getEmto().add(MimeUtility.decodeText(address[j].toString()));
                }
            }

            Address[] recipients = message.getRecipients(Message.RecipientType.CC);
            if (recipients != null) {
                for (Address recipient : recipients) {
                    mailInfo.getEmcc().add(MimeUtility.decodeText(recipient.toString()));
                }
            }

            mailInfo.setEmsubject(message.getSubject());
            mailInfo.setRegdate(DateUtil.date2Str(message.getSentDate()));
        }

        Object o = p.getContent();
        if (o instanceof String) {
            mailInfo.setEmcontents((String)o);
        } else if(o instanceof Multipart) {
            Multipart mp = (Multipart) o;
            int count = mp.getCount();
            for (int i = 0; i < count; i++) {
                dumpPart(mp.getBodyPart(i), mailInfo);
            }
        } else if(o instanceof InputStream) {
            String filename = p.getFileName();
            if (filename == null) {
                return;
            }

            String newName = FileUtil.getNewName();

            File file = new File(filePath + newName);
            OutputStream out = new FileOutputStream(file);
            InputStream is = (InputStream) o;
            int c;
            while ((c = is.read()) != -1) {
                out.write(c);
            }

            FileVO filedo = new FileVO();
            filedo.setFilename(MimeUtility.decodeText(filename));
            filedo.setRealname(newName);
            filedo.setFilesize(file.length());
            mailInfo.getFiles().add(filedo);
        }
    }
}

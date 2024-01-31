package com.devkbil.mtssbj.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * SFTP Util
 *
 */
public class SftpUtil {

    //private Log log = LogFactory.getLog(SFTPUtil.class);

    public Session session = null;
    public Channel channel = null;
    public ChannelSftp channelSftp = null;

    /**
     * SFTP 접속
     *
     * @param ip
     * @param port
     * @param id
     * @param pw
     * @param privateKey
     */
    public void sftpInit(String ip, int port, String id, String pw, String privateKey) throws Exception {
        String connIp = ip;		//접속 SFTP 서버 IP
        int connPort = port;	//접속 PORT
        String connId = id;		//접속 ID
        String connPw = pw;		//접속 PW
        int timeout = 10000; 	//타임아웃 10초

        JSch jsch = new JSch();
        try {
            InetAddress local;
            local = InetAddress.getLocalHost();

            //key 인증방식일경우
            if(null != privateKey && !"".equals(privateKey)) {
                jsch.addIdentity(privateKey);
            }

            //세션객체 생성
            session = jsch.getSession(connId, connIp, connPort);

            if(null == privateKey || "".equals(privateKey)) {
                session.setPassword(connPw); //password 설정
            }

            //세션관련 설정정보 설정
            java.util.Properties config = new java.util.Properties();

            //호스트 정보 검사하지 않는다.
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setTimeout(timeout); //타임아웃 설정

            //log.info("connect.. " + connIp);
            System.out.println("connect.. " + connIp);
            session.connect();	//접속

            channel = session.openChannel("sftp");	//sftp 채널 접속
            channel.connect();

        } catch (JSchException e) {
            //log.error(e);
            System.out.println(e);
            throw e;
        } catch (Exception e) {
            //log.error(e);
            System.out.println(e);
            throw e;
        }
        channelSftp = (ChannelSftp) channel;
    }

    /**
     * SFTP 서버 접속 종료
     */
    public void disconnect() {
        if(channelSftp != null) {
            channelSftp.quit();
        }
        if(channel != null) {
            channel.disconnect();
        }
        if(session != null) {
            session.disconnect();
        }
    }

    /**
     * SFTP 서버 파일 업로드
     * @param uploadPath
     * @param localPath
     * @param uploadFileNm
     */
    public void sftpFileUpload(String uploadPath, String localPath, String uploadFileNm) throws Exception {
        FileInputStream in = null;

        try{
            //파일을 가져와서 inputStream에 넣고 저장경로를 찾아 업로드
            in = new FileInputStream(localPath+uploadFileNm);
            channelSftp.cd(uploadPath);
            channelSftp.put(in,uploadFileNm);
            //log.info("sftpFileUpload success.. ");
            System.out.println("sftpFileUpload success.. ");
        }catch(SftpException se){
            //log.error(se);
            System.out.println(se);
            throw se;
        }catch(FileNotFoundException fe){
            //log.error(fe);
            System.out.println(fe);
            throw fe;
        } catch (Exception e) {
            //log.error(e);
            System.out.println(e);
            throw e;
        }finally{
            try{
                in.close();
            } catch(IOException ioe){
                //log.error(ioe);
                System.out.println(ioe);
            }
        }
    }

    /**
     * SFTP 서버 여러 파일 업로드
     * @param uploadPath
     * @param localPath
     * @param uploadFiles
     */
    public void sftpMultiFileUpload(String uploadPath, String localPath, ArrayList<String> uploadFiles) throws Exception {
        FileInputStream in = null;

        channelSftp.cd(uploadPath);
        for(int i=0; i<uploadFiles.size();i++) {
            try{
                //파일을 가져와서 inputStream에 넣고 저장경로를 찾아 업로드
                in = new FileInputStream(localPath+String.valueOf(uploadFiles.get(i)));
                channelSftp.put(in,String.valueOf(uploadFiles.get(i)));
                //log.info(">>>>>>>>>>>>>>> "+ String.valueOf(uploadFiles.get(i)) +" file success..");
                System.out.println(">>>>>>>>>>>>>>> "+ String.valueOf(uploadFiles.get(i)) +" file success..");
                in.close();
            }catch(SftpException se){
                //log.error(se);
                System.out.println(se);
                throw se;
            }catch(FileNotFoundException fe){
                //log.error(fe);
                System.out.println(fe);
                throw fe;
            } catch (Exception e) {
                //log.error(e);
                System.out.println(e);
                throw e;
            }
        }
    }

    /**
     * SFTP 서버 파일 다운로드
     * @param downloadPath
     * @param localFilePath
     */
    public void sftpFileDownload(String downloadPath, String localFilePath) throws Exception {
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;

        try {
            //SFTP 서버 파일 다운로드 경로
            String cdDir = downloadPath.substring(0, downloadPath.lastIndexOf("/") + 1);
            //파일명
            String fileName = downloadPath.substring(downloadPath.lastIndexOf("/") + 1, downloadPath.length());

            channelSftp.cd(cdDir);

            File file = new File(downloadPath);
            bis = new BufferedInputStream(channelSftp.get(fileName));

            //파일 다운로드 SFTP 서버 -> 다운로드 서버
            File newFile = new File(localFilePath+fileName);
            os = new FileOutputStream(newFile);
            bos = new BufferedOutputStream(os);

            int readCount;

            while ((readCount = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, readCount);
            }

            //log.debug("sftpFileDownload success.. ");
            System.out.println("sftpFileDownload success.. ");
        } catch (Exception e) {
            //log.error(e);
            System.out.println(e);
            throw e;
        } finally {
            try {
                bis.close();
                bos.close();
                os.close();
            } catch (IOException e) {
                //log.error(e);
                System.out.println(e);
            }
        }
    }

    /**
     * SFTP 서버 파일 찾기, 파일목록보기
     * @param downloadPath
     * @return findFileName
     * @throws Exception
     */
    public String sftpSearchFile(String downloadPath) throws Exception {
        try {
            //SFTP 서버 파일 다운로드 경로
            String cdDir = downloadPath.substring(0, downloadPath.lastIndexOf("/") + 1);
            //파일명
            String fileName = downloadPath.substring(downloadPath.lastIndexOf("/") + 1, downloadPath.length());
            String findFileName = "";

            channelSftp.cd(cdDir);

            Vector fileList = channelSftp.ls(cdDir);

            for(int i=0; i<fileList.size();i++) {
                LsEntry files = (LsEntry) fileList.get(i);
                //log.info("file >>>>> "+ files);
                System.out.println("file >>>>> "+ files);

                //파일 찾는부분
                files.getFilename().matches(fileName+(".*"));
                if(files.getFilename().matches(fileName+(".*"))) {
                    findFileName = files.getFilename();
                    //log.debug("find file : " + fileName);
                    System.out.println("find file : " + fileName);
                    break;
                }
            }

            return findFileName;

        } catch (Exception e) {
            //log.error(e);
            System.out.println(e);
            throw e;
        }
    }
}

package com.devkbil.mtssbj.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class FileUtil {
    static final Integer IMG_WIDTH = 100;
    static final Integer IMG_HEIGHT = 100;
    public static int nSUCCESS = 1;
    public static int nException = 0;
    public static int nFAILER = -1;
    static int bufferSize = 1024; /** fileCopy bufferSize **/

    /**
     * 실제 파일 저장.
     */
    public static String saveFileOne(MultipartFile file, String basePath, String fileName) {
        if (file == null || file.getName().equals("") || file.getSize() < 1) {
            return null;
        }

        makeBasePath(basePath);
        String serverFullPath = basePath + fileName;

        File file1 = new File(serverFullPath);
        try {
            file.transferTo(file1);
        } catch (IOException ex) {
            log.error("IOException");
        }

        return serverFullPath;
    }

    private static BufferedImage resizeImage(BufferedImage srcImage, int type) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.drawImage(srcImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g2.dispose();

        return resizedImage;
    }

    /**
     * 파일 저장 경로 생성.
     */
    public static void makeBasePath(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 날짜로 새로운 파일명 부여.
     */
    public static String getNewName() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        return ft.format(new Date()) + (int) (Math.random() * 10);
    }

    public static String getFileExtension(String filename) {
        Integer mid = filename.lastIndexOf(".");
        return filename.substring(mid + 1);
    }

    /**
     * 파일 업로드.
     */
    public FileVO saveFile(MultipartFile uploadfile) {
        if (uploadfile == null || uploadfile.getSize() == 0) {
            return null;
        }

        String filePath = System.getProperty("user.dir") + "/fileupload/"; // localeMessage.getMessage("info.filePath");
        String newName = getNewName();
        filePath = getRealPath(filePath, newName);

        saveFileOne(uploadfile, filePath, newName);

        FileVO filedo = new FileVO();
        filedo.setFilename(uploadfile.getOriginalFilename());
        filedo.setRealname(newName);
        filedo.setFilesize(uploadfile.getSize());

        return filedo;
    }

    /**
     * 멀티 파일 업로드.
     */
    public List<FileVO> saveAllFiles(List<MultipartFile> upfiles) {
        List<FileVO> filelist = new ArrayList<FileVO>();
        String filePath = System.getProperty("user.dir") + "/fileupload/"; // localeMessage.getMessage("info.filePath");

        for (MultipartFile uploadfile : upfiles) {
            if (uploadfile.getSize() == 0) {
                continue;
            }

            String newName = getNewName();

            saveFileOne(uploadfile, getRealPath(filePath, newName), newName);

            FileVO filedo = new FileVO();
            filedo.setFilename(uploadfile.getOriginalFilename());
            filedo.setRealname(newName);
            filedo.setFilesize(uploadfile.getSize());
            filelist.add(filedo);
        }
        return filelist;
    }

    /**
     * 이미지 파일 업로드 및 resize.
     */
    public FileVO saveImage(MultipartFile file) {
        if (file == null || file.getName().equals("") || file.getSize() < 1) {
            return null;
        }

        String filePath = System.getProperty("user.dir") + "/fileupload/"; // localeMessage.getMessage("info.filePath");
        String newName = getNewName();
        String basePath = getRealPath(filePath, newName);
        String serverFullPath = basePath + newName;
        String ext = getFileExtension(file.getOriginalFilename());
        makeBasePath(basePath);

        File file1 = new File(serverFullPath);
        try {
            file.transferTo(file1);
            BufferedImage srcImage = ImageIO.read(file1);
            int type = srcImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : srcImage.getType();
            if (srcImage.getWidth() > IMG_WIDTH && srcImage.getHeight() > IMG_HEIGHT) {
                BufferedImage resizeImageJpg = resizeImage(srcImage, type);
                ImageIO.write(resizeImageJpg, ext, new File(serverFullPath + "1"));
                newName += "1";
                file1.delete();
            }
        } catch (IOException ex) {
            log.error("IOException:saveImage");
        }

        FileVO filedo = new FileVO();
        filedo.setFilename(file.getOriginalFilename());
        filedo.setRealname(newName);
        filedo.setFilesize(file.getSize());

        return filedo;
    }

    public String getRealPath(String path, String filename) {
        return path + filename.substring(0, 4) + "/";
    }


    /**
     * 파일존재여부 체크
     *
     * @param srcFilePath 파일패스
     * @return 존배여부
     */
    public static boolean fileExist(String srcFilePath) {
        File temp = null;
        boolean retValue = false;

        try {
            temp = new File(srcFilePath);

            if (temp.isFile()) {
                retValue = true;
            } else {
                retValue = false;
            }
        } catch (Exception e) {
            retValue = false;
        }

        return retValue;
    }

    /**
     * 파일명변경
     *
     * @param srcFilePath  원본파일패스
     * @param destFilePath 타켓파일패스
     * @return 성공여부
     */
    public static int fileRename(String srcFilePath, String destFilePath) {

        int nRtn;

        if (srcFilePath == null || "".equals(srcFilePath)) return nFAILER;

        //  파일명변경: ".bak" 추가
        File srcFile = new File(srcFilePath);
        File destFile = new File(destFilePath);

        if (!srcFile.renameTo(destFile)) {
            return nFAILER;
        } else {
            return nSUCCESS;
        }

    }

    /** //////////////////////////////////// FILE COPY ///////////////////////////////////////////////////// **/

    /**
     * 파일이동
     *
     * @param source 원본파일위치
     * @param target 타켓파일위치
     * @return 성공여부
     */
    public static boolean fileMove(String source, String target) {
        boolean retValue = true;
        if (source == null || target == null) {
            return false;
        }

        File srcFile = new File(source);
        File destFile = new File(target);
        try {
            if (srcFile.renameTo(destFile)) {
                retValue = true;
            } else {
                retValue = false;
            }
        } catch (Exception e) {
            retValue = false;
        } finally {
            try {
                if (srcFile != null) srcFile = null;
            } catch (Exception e) {
            }
            try {
                if (destFile != null) destFile = null;
            } catch (Exception e) {
            }
        }
        return retValue;
    }

    /**
     * file copy (alias - channelFileCopy)
     *
     * @param source source file
     * @param target destination file
     * @return true/false
     */
    public static int copyFile(String source, String target) {
        return channelFileCopy(source, target);
    }

    /**
     * file copy (method : buffer)
     *
     * @param source source file
     * @param target destination file
     * @return true/false
     */
    public static int bufferFileCopy(String source, String target) {
        int retValue = nSUCCESS;
        File sourceFile = new File(source);

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;

        int bytesRead;
        byte[] buffer = new byte[bufferSize];

        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(target);

            bin = new BufferedInputStream(inputStream);
            bout = new BufferedOutputStream(outputStream);

            while ((bytesRead = bin.read(buffer, 0, bufferSize)) != -1) {
                bout.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            retValue = nException;
        } finally {
            try {
                outputStream.close();
            } catch (IOException ioe) {
            }
            try {
                inputStream.close();
            } catch (IOException ioe) {
            }
            try {
                bin.close();
            } catch (IOException ioe) {
            }
            try {
                bout.close();
            } catch (IOException ioe) {
            }
        }
        return retValue;
    }

    /**
     * file copy (method : channel)
     *
     * @param source source file
     * @param target destination file
     * @return true/false
     */
    public static int channelFileCopy(String source, String target) {
        int retValue = nSUCCESS;
        long retSize;
        /** source file Define **/
        File sourceFile = new File(source);
        /** Stream, Channel Define **/
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel fcin = null;
        FileChannel fcout = null;

        try {
            /** stream create **/
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(target);
            /** channel create **/
            fcin = inputStream.getChannel();
            fcout = outputStream.getChannel();

            /** Stream Transfer for channel **/
            long size = fcin.size();
            retSize = fcin.transferTo(0, size, fcout);
            if ((retSize - Integer.MAX_VALUE) > 0) {
                retValue = Integer.MAX_VALUE;
            }

        } catch (Exception e) {
            retValue = nException;
        } finally {
            try {
                fcout.close();
            } catch (IOException ioe) {
            }
            try {
                fcin.close();
            } catch (IOException ioe) {
            }
            try {
                outputStream.close();
            } catch (IOException ioe) {
            }
            try {
                inputStream.close();
            } catch (IOException ioe) {
            }

        }
        return retValue;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * file copy (method : stream)
     *
     * @param source source file
     * @param target destination file
     * @return true/false
     */
    public int streamFileCopy(String source, String target) {
        if (source == null || target == null) {
            return nFAILER;
        }
        int retValue = nSUCCESS;

        File sourceFile = new File(source);

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;

        int bytesRead;
        byte[] buffer = new byte[bufferSize];

        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(target);

            while ((bytesRead = inputStream.read(buffer, 0, bufferSize)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            retValue = nException;
        } finally {
            try {
                outputStream.close();
            } catch (IOException ioe) {
            }
            try {
                inputStream.close();
            } catch (IOException ioe) {
            }
        }
        return retValue;
    }
}

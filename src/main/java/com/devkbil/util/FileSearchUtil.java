package com.devkbil.util;

import com.devkbil.common.FileVO;
import com.devkbil.common.LocaleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

public class FileSearchUtil extends SimpleFileVisitor<Path> {
    
    static final Logger LOGGER = LoggerFactory.getLogger(FileSearchUtil.class);
    static final Integer IMG_WIDTH = 100;
    static final Integer IMG_HEIGHT = 100;
    
    @Autowired
    LocaleMessage localeMessage;
    
    /**
     * 파일 리스트 출력 (Recursive, listFiles)
     * ex) System.out.println("2");
     *     showFilesInDIr("/home/mjs/test/test");
     * @param dirPath
     */
    public static void showFilesInDIr(String dirPath) {
        File dir = new File(dirPath);
        File files[] = dir.listFiles();
        
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                showFilesInDIr(file.getPath());
            } else {
                System.out.println("file: " + file);
            }
        }
    }
    
    /**
     * 파일 리스트 출력(Recursive, Filtering)
     * @param dirPath
     */
    public static void showFilesInDIr(String dirPath, String fileExt) {
        File dir = new File(dirPath);
        File files[] = dir.listFiles();
        
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                showFilesInDIr(file.getPath(), fileExt);
            } else if (file.getName().endsWith(fileExt)){
                System.out.println("file: " + file);
            }
        }
    }
    
    public static List<FileVO> showFIlesInDir3(String dirPath) {
        List<FileVO> filelist = new ArrayList<FileVO>();
    
        Path path = Paths.get(dirPath);
        DirectoryStream<Path> dir = null;
        BasicFileAttributes attrs;
        FileVO filedo;
        FileChannel fileChannel;
        try {
            dir = Files.newDirectoryStream(path);
            for (Path file : dir) {
                attrs = Files.readAttributes(file, BasicFileAttributes.class);
                if(attrs.isDirectory()) {
                    System.out.println("dir : " + file);
                    showFIlesInDir3(file.toString());
                //} else if(file.getFileName().endsWith(".txt")) {
                //    System.out.println("1");
                } else {
                    filedo = new FileVO();
                    filedo.setFilename(file.getFileName().toString());
                    filedo.setRealname(file.getFileName().toString());
                    
                    fileChannel = FileChannel.open(path);
                    filedo.setFilesize(fileChannel.size()); // Kbyte
                    
                    filedo.setUri(file.toUri().toString());
                    filedo.setFilepath(file.getParent().toString());
                    filelist.add(filedo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dir != null) {
                    dir.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return filelist;
        }
    }
    
    // 방문한 파일 정보
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
        if (attr.isSymbolicLink()) {
            System.out.format("Symbolic link: %s ", file);
        } else if (attr.isRegularFile()) {
            System.out.format("Regular file: %s ", file);
        } else {
            System.out.format("Other: %s ", file);
        }
        System.out.println("(" + attr.size() + "bytes)");
        return CONTINUE;
    }
    
    // 방문한 디렉토리 정보
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        System.out.format("Directory: %s%n", dir);
        return CONTINUE;
    }
    
    // 파일을 접근하는 중에 에러가 존재하면, 사용자에게 에러와 예외로 알려줌
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }
    
}

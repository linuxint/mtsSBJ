package com.devkbil.mtssbj;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfFIleMergeTest {
    public static void main(String[] args) throws IOException {

        Path resourceDirectory = Paths.get("src", "test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath() + "/";
        System.out.println(absolutePath);

        RandomAccessReadBufferedFile file = null;
        //Create PDFMergerUtility class object
        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        //Setting the destination file path
        PDFmerger.setDestinationFileName(absolutePath + "merged.pdf");
        PDDocument doc = null;
        String[] pdfFileList = {"Demo1.pdf", "Demo2.pdf"};

        try {
            for (String pdfFile : pdfFileList) {

                file = new RandomAccessReadBufferedFile(absolutePath + pdfFile);

                doc = Loader.loadPDF(file);
                int pageCount = doc.getNumberOfPages();
                doc.close();

                System.out.println(pdfFile + " page " + pageCount);
                //adding the source files
                PDFmerger.addSource(file);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        file = null;
        //Merging the documents
        PDFmerger.mergeDocuments(null);
        PDFmerger = null;
        System.out.println("PDF Documents merged to a single file successfully");

    }

}

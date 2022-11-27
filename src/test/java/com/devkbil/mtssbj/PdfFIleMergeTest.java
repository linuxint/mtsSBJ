package com.devkbil.mtssbj;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
public class PdfFIleMergeTest {
    public static void main(String[] args) throws IOException {

        String[] destFiles = { "Demo1.pdf","Demo2.pdf" };

        File fileIO;
        int pageCount = 0;

        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        for(String pdfFile: destFiles) {
            fileIO = new File(pdfFile);
            pageCount = PDDocument.load(fileIO).getNumberOfPages();
            System.out.println(pdfFile + " pageCount : "+ pageCount);

            //Instantiating PDFMergerUtility class
            //adding the source files
            PDFmerger.addSource(fileIO);
        }

        PDFmerger.setDestinationFileName("merged.pdf");

        //Merging the two documents
        PDFmerger.mergeDocuments();
        System.out.println("Documents merged");

        int mergeFileCount = PDDocument.load(new File("merged.pdf")).getNumberOfPages();
        System.out.println("mergeFileCount : " + mergeFileCount);

    }
}

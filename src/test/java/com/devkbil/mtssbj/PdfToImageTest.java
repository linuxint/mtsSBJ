package com.devkbil.mtssbj;

import static com.devkbil.mtssbj.common.util.PdfUtil.*;

import java.io.File;

public class PdfToImageTest {


    public static void main(String[] args) throws Exception {
        File pdf = new File("test.pdf");
        convertToSeparateImageFiles(pdf, "png");
        convertToSeparateImageFilesWithCompression(pdf, "jpg");

        //tiff
        convertToSinglePageTiffs(pdf);
        convertToMultipageTiff(pdf, new File(pdf.getAbsoluteFile().getParent() +
                File.separator + pdf.getName() + "multi-page.tif"));

    }

}

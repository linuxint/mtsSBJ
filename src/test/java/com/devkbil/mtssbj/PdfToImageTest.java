package com.devkbil.mtssbj;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.devkbil.mtssbj.common.util.PdfUtil.*;

public class PdfToImageTest {


    public static void main(String[] args) throws Exception {

        Path resourceDirectory = Paths.get("src", "test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath() + "/";
        System.out.println(absolutePath);

        File pdf = new File(absolutePath + "Demo1.pdf");
        convertToSeparateImageFiles(pdf, "png");
        convertToSeparateImageFilesWithCompression(pdf, "jpg");

        //tiff
        convertToSinglePageTiffs(pdf);
        convertToMultipageTiff(pdf, new File(pdf.getAbsoluteFile().getParent() +
                File.separator + pdf.getName() + "multi-page.tif"));

    }

}

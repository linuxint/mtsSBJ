package com.devkbil.mtssbj;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleTextExtractorTest {
    public static void main1(String[] args) throws Exception {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath() + "/";

        String fileName = "인천둘레길스템프북.pptx";
        String fileName1 = "Demo1.pdf";
        // Create a Tika instance with the default configuration
        Tika tika = new Tika();

        // Parse all given files and print out the extracted
        // text content
        String text = tika.parseToString(new File(absolutePath + fileName));
        System.out.print(text);
    }

    public static void main(String[] args) throws Exception {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath() + "/";
        String fileName1 = "Demo1.pdf";

        SimpleTextExtractorTest tika = new SimpleTextExtractorTest();
        tika.convertPdf(absolutePath + fileName1);
    }

    public void convertPdf(String fileName){
        InputStream stream = null;
        try {
            stream = new FileInputStream(fileName);
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            parser.parse(stream, handler, metadata, new
                    ParseContext());
            System.out.println(handler.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                    System.out.println("Error closing stream");
                }
        }
    }
}

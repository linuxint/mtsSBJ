package com.devkbil.mtssbj;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
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
    public static void main(String[] args) throws Exception {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath() + "/";

        String[] fileArr = { "인천둘레길스템프북.pptx" , "Demo1.pdf"};
        // Create a Tika instance with the default configuration

        for(String fileName : fileArr) {
            System.out.println(convertSimple(absolutePath + fileName));
            System.out.println(convert(absolutePath + fileName));
        }
    }

    public static String convertSimple(String fileName) throws TikaException, IOException {
        Tika tika = new Tika();
        String text = tika.parseToString(new File(fileName));
        return text;
    }
    public static String convert(String fileName){
        InputStream stream = null;
        String text = null;
        try {
            stream = new FileInputStream(fileName);
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            parser.parse(stream, handler, metadata, new ParseContext());
            text = handler.toString();
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
        return text;
    }
}

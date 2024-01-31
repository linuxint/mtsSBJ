package com.devkbil.mtssbj.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PdfUtil {

    /**
     * Merges PDF files.
     *
     * @param inputPdfFiles array of input files
     * @param outputPdfFile output file
     */
    public static void mergePdf(File[] inputPdfFiles, File outputPdfFile) {
        try {
            PDFMergerUtility mergerUtility = new PDFMergerUtility();
            mergerUtility.setDestinationFileName(outputPdfFile.getPath());
            for (File inputPdfFile : inputPdfFiles) {
                mergerUtility.addSource(inputPdfFile);
            }
            mergerUtility.mergeDocuments(null);
        } catch (IOException ioe) {
        }
    }

    /**
     * pdf to images
     * @param pdf
     * @param type
     * @throws Exception
     */
    public static void convertToSeparateImageFiles(File pdf, String type) throws Exception {
        int DPI = 300;
        ImageType IMAGE_TYPE = ImageType.RGB;//This can be GRAY,ARGB,BINARY, BGR

//        try (PDDocument document = PDDocument.load(pdf)) { // pdfbox 2.x
        try (PDDocument document = Loader.loadPDF(pdf)) { // pdfbox 3.x
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, DPI, IMAGE_TYPE);

                File outputFile = new File(pdf.getAbsoluteFile().getParent() +
                        File.separator + pdf.getName() + "-P-" + page + "." + type);
                ImageIO.write(bim, type, outputFile);
            }
        }
    }

    /**
     * pdf to image with compression
     * @param pdf
     * @param type
     * @throws Exception
     */
    public static void convertToSeparateImageFilesWithCompression(File pdf, String type) throws Exception {
        int DPI = 300;
        ImageType IMAGE_TYPE = ImageType.RGB;//This can be GRAY,ARGB,BINARY, BGR

//        try (PDDocument document = PDDocument.load(pdf)) { // pdfbox 2.x
        try (PDDocument document = Loader.loadPDF(pdf)) { // pdfbox 3.x
            ImageWriter writer = null;
            try {
                writer = ImageIO.getImageWritersByFormatName(type).next();

                ImageWriteParam params = writer.getDefaultWriteParam();
                params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                params.setCompressionQuality(0.6f);

                PDFRenderer pdfRenderer = new PDFRenderer(document);

                for (int page = 0; page < document.getNumberOfPages(); page++) {
                    BufferedImage bim = pdfRenderer.renderImageWithDPI(page, DPI, IMAGE_TYPE);

                    File outputFile = new File(pdf.getAbsoluteFile().getParent() +
                            File.separator + pdf.getName() + "-compressed-P-" + page + "." + type);
                    ImageOutputStream outputStream = new FileImageOutputStream(outputFile);
                    writer.setOutput(outputStream);

                    writer.write(null, new IIOImage(bim, null, null), params);
                }
            } finally {
                if (writer != null) {
                    writer.dispose();
                }
            }
        }
    }

    /**
     * PDF to single page tiff files
     * @param pdf
     * @throws Exception
     */
    public static void convertToSinglePageTiffs(File pdf) throws Exception {
        int DPI = 300;
        ImageType IMAGE_TYPE = ImageType.BINARY;//This can be GRAY,ARGB,BINARY, BGR

//        try (PDDocument document = PDDocument.load(pdf)) { // pdfbox 2.x
        try (PDDocument document = Loader.loadPDF(pdf)) { // pdfbox 3.x

            ImageWriter writer = null;
            try {
                writer = ImageIO.getImageWritersByFormatName("tiff").next();

                ImageWriteParam params = writer.getDefaultWriteParam();
                params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                // Compression Types: None, PackBits, ZLib, Deflate, LZW, JPEG and CCITT
                params.setCompressionType("LZW");
                params.setCompressionQuality(0.8f);

                PDFRenderer pdfRenderer = new PDFRenderer(document);

                for (int page = 0; page < document.getNumberOfPages(); page++) {
                    BufferedImage bim = pdfRenderer.renderImageWithDPI(page, DPI, IMAGE_TYPE);

                    File outputFile = new File(pdf.getAbsoluteFile().getParent() +
                            File.separator + pdf.getName() + "-compressed-P-" + page + ".tif");
                    ImageOutputStream outputStream = new FileImageOutputStream(outputFile);
                    writer.setOutput(outputStream);
                    writer.write(null, new IIOImage(bim, null, null), params);
                }
            } finally {
                if (writer != null) {
                    writer.dispose();
                }
            }
        }
    }

    /**
     * PDF to a multi-page tiff file
     * @param pdf
     * @param outputTiff
     * @throws Exception
     */
    public static void convertToMultipageTiff(File pdf, File outputTiff) throws Exception {
        int DPI = 300;
        ImageType IMAGE_TYPE = ImageType.GRAY;//This can be GRAY,ARGB,BINARY, BGR

//        try (PDDocument document = PDDocument.load(pdf)) { // pdfbox 2.x
        try (PDDocument document = Loader.loadPDF(pdf)) { // pdfbox 3.x
            try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputTiff)) {

                ImageWriter writer = null;
                try {
                    writer = ImageIO.getImageWritersByFormatName("tiff").next();
                    writer.setOutput(ios);
                    writer.prepareWriteSequence(null);

                    ImageWriteParam params = writer.getDefaultWriteParam();
                    params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    // Compression Types: None, PackBits, ZLib, Deflate, LZW, JPEG and CCITT
                    params.setCompressionType("LZW");
                    params.setCompressionQuality(0.8f);

                    PDFRenderer pdfRenderer = new PDFRenderer(document);

                    for (int page = 0; page < document.getNumberOfPages(); page++) {
                        BufferedImage bim = pdfRenderer.renderImageWithDPI(page, DPI, IMAGE_TYPE);

                        IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(bim), params);
                        writer.writeToSequence(new IIOImage(bim, null, metadata), params);
                    }
                } finally {
                    if (writer != null) {
                        writer.dispose();
                    }
                }
            }
        }
    }
}

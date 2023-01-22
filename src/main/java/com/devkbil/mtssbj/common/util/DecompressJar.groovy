package com.devkbil.mtssbj.common.util

import java.util.jar.JarEntry
import java.util.jar.JarFile

class DecompressJar {

    void unjar(String sourcePath, String destPath) {
        println ' - Start Unjar'
        byte[] buffer = new byte[2048]
        try {
            /** Ready **/
            JarFile jar = new JarFile(sourcePath)
            java.util.Enumeration enumEntries = jar.entries()
            /** Read the jar entries using the nextElement method **/
            while (enumEntries.hasMoreElements()) {
                JarEntry entry = (JarEntry) enumEntries.nextElement()
                java.io.File file = new java.io.File(destPath + java.io.File.separator + entry.getName())
                println "Extracting: ${file.getAbsolutePath()}"
                if (entry.isDirectory()) {
                    file.mkdirs()
                } else {
                    file.parentFile.mkdirs()
                    java.io.InputStream is = jar.getInputStream(entry)
                    java.io.FileOutputStream fos = new java.io.FileOutputStream(file)
                    int len
                    while ((len = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, len)
                    }
                    fos.close()
                    is.close()
                }
            }
            /** Close the input stream **/
            jar.close()
            println "Unjar Completed Successfully!!"

        } catch (IOException ex) {
            ex.printStackTrace()
        }

    }

}

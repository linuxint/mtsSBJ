package com.devkbil.mtssbj.common.util

import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream

class DecompressTar {

    public void untar(String filePath, String dest){
        println ' - Start Untar'
        byte[] buffer = new byte[2048]
        try{
            /** Ready **/
            FileInputStream fin = new FileInputStream(filePath)
            BufferedInputStream bis = new BufferedInputStream(fin)
            GzipCompressorInputStream gzIn = new GzipCompressorInputStream(bis)
            TarArchiveInputStream tarIn = new TarArchiveInputStream(gzIn)
            TarArchiveEntry entry
            /** Read the tar entries using the getNextEntry method **/
            while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null){
                File file = new File(dest, entry.getName())
                println "Extracting: ${file.getAbsolutePath()}"
                if (entry.isDirectory()){
                    file.mkdirs()
                }else{
                    file.parentFile.mkdirs()
                    int len
                    FileOutputStream fos = new FileOutputStream(file)
                    BufferedOutputStream destOs = new BufferedOutputStream(fos, BUFFER)
                    while ((len = tarIn.read(buffer, 0, BUFFER)) != -1){
                        destOs.write(buffer, 0, len)
                    }
                    destOs.close()
                }
            }
            /** Close the input stream **/
            tarIn.close()
            println "Untar Completed Successfully!!"

        }catch(IOException ex){
            ex.printStackTrace()
        }
    }

}

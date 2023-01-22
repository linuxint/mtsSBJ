package com.devkbil.mtssbj.common.util;

public class HostUtil {

    public HostUtil() {
    }

    public static String getHostName() {
        String hostName = "";
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("hostname");
            int inp;
            while ((inp = proc.getInputStream().read()) != -1) {
                hostName += (char)inp;
            }
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hostName.trim();
    }

}

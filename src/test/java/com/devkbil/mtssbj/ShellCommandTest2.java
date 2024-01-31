package com.devkbil.mtssbj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellCommandTest2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] cmd = new String[]{"sh", "-c", "ifconfig"};
        String OS = "Mac";
        String output = "";
        if(cmd != null) {
            String s = null;
            try {
                Process p = null;
                if(OS.startsWith("Windows"))  {
                    p = Runtime.getRuntime().exec(cmd);
                } else {
                    p = Runtime.getRuntime().exec(cmd);
                }
                BufferedReader sI = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while((s = sI.readLine()) != null) {
                    output += s;
                }
            }
            catch(IOException e) {e.printStackTrace();}
        }
    }
}

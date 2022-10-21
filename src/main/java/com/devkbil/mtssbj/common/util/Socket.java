package com.devkbil.mtssbj.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Socket {
    private java.net.Socket socket;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    public Socket(InetAddress byName, int port) {
        socket = null;
        bis = null;
        bos = null;
    }
    public int connect (String host, int port) throws UnknownHostException, IOException {
        socket = new java.net.Socket(InetAddress.getByName(host), port);
        return 0;
    }
}

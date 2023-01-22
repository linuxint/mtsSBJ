package com.devkbil.mtssbj.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;

public class Socket {
    private java.net.Socket socket;
    private final BufferedInputStream bis;
    private final BufferedOutputStream bos;

    public Socket(InetAddress byName, int port) {
        socket = null;
        bis = null;
        bos = null;
    }

    public int connect(String host, int port) throws IOException {
        socket = new java.net.Socket(InetAddress.getByName(host), port);
        return 0;
    }
}

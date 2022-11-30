package com.devkbil.mtssbj;

public class StringUtilTest {
    public static void main(String[] args) {
        String[] page = "1".split(",");
        System.out.println(page.length);
        System.out.println(Integer.parseInt(page[0]));
    }
}

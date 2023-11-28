package com.devkbil.mtssbj;

public class StringIsEmptyTest {

    public static void main(String[] args) {
        String text = "";

        int loopCount = 10000000;
        long startTime, endTime, duration1, duration2;

        startTime = System.nanoTime();
        for (int i = 0; i < loopCount; i++) {
            text.equals("");
        }
        endTime = System.nanoTime();
        duration1 = endTime - startTime;
        System.out.println(".equals(\"\") duration " + ": \t" + duration1);

        startTime = System.nanoTime();
        for (int i = 0; i < loopCount; i++) {
            text.isEmpty();
        }
        endTime = System.nanoTime();
        duration2 = endTime - startTime;
        System.out.println(".isEmpty() duration " + ": \t\t" + duration2);

        System.out.println("isEmpty() to equals(\"\") ratio: " + ((float)duration2 / (float)duration1));

    }
}

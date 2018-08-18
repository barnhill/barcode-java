package com.pnuema.java.barcode.utils;

public class CharUtils {
    public static String getChar(int i) {
        return String.valueOf(Character.forDigit(i, 10));
    }
}

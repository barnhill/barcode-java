package com.pnuema.java.barcode.utils;

public final class Utils2of5 {
    public static int CalculateMod10CheckDigit(String data) {
        int sum = 0;
        boolean even = true;
        for (int i = data.length() - 1; i >= 0; --i)
        {
            sum += data.charAt(i) * (even ? 3 : 1);
            even = !even;
        }

        return (10 - sum % 10) % 10;
    }
}

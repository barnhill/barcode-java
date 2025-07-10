package com.pnuema.java.barcode.utils;

/**
 * Utility class for operations related to the Interleaved 2 of 5 (ITF) barcode
 * symbology, including calculations for check digits. This class is designed
 * to provide core computational functionality for barcode systems and is not
 * intended to be instantiated.
 */
public final class Utils2of5 {
    /**
     * Private constructor to prevent instantiation of the Utils2of5 class.
     * This utility class is designed solely to provide static utility methods
     * related to Interleaved 2 of 5 (ITF) barcode symbology. As such, it
     * must not be instantiated. Attempts to instantiate this class will
     * result in a compilation error.
     */
    private Utils2of5() {
    }

    /**
     * Calculates the Mod-10 check digit for the provided numeric string.
     * The Mod-10 algorithm, also called Luhn algorithm, is commonly used
     * in various numbering schemes, including credit card numbers and barcodes.
     * The algorithm works by iterating through the digits of the input string
     * in reverse order, applying a weight of 3 or 1 alternately to the digits,
     * summing the results, and calculating the check digit to ensure the total
     * modulo 10 equals 0.
     *
     * @param data The numeric string for which the Mod-10 check digit needs to be calculated.
     *             This should only contain numeric characters.
     * @return The calculated Mod-10 check digit as an integer.
     *         If the input is invalid or non-numeric, the result may be undefined.
     */
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

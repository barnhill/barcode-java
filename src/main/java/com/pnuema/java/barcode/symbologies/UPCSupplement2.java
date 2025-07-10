package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

/**
 * Encodes a 2-digit UPC Supplemental barcode. The supplemental barcode is often
 * added to the primary UPC barcode to encode additional information such as pricing
 * or issue numbers for magazines.
 * This class extends the BarcodeCommon class, which provides foundational methods
 * for barcode encoding and validation. The UPC Supplemental 2-digit (UPC-E Add-On)
 * uses a parity-based system for digit encoding and requires a numeric string of length 2.
 * The encoding process follows these steps:
 * 1. Validate the input data for length and numeric content.
 * 2. Generate a parity pattern based on the modulus-4 sum of the input digits.
 * 3. Encode each digit using a pre-defined set of patterns for odd ("a") or even ("b") parity.
 */
public class UPCSupplement2 extends BarcodeCommon {
    private final String[] EAN_CodeA = {"0001101", "0011001", "0010011", "0111101", "0100011", "0110001", "0101111", "0111011", "0110111", "0001011"};
    private final String[] EAN_CodeB = {"0100111", "0110011", "0011011", "0100001", "0011101", "0111001", "0000101", "0010001", "0001001", "0010111"};
    private final String[] UPC_SUPP_2 = {"aa", "ab", "ba", "bb"};

    /**
     * Constructs a UPCSupplement2 object with the specified input string.
     * This constructor initializes the object by setting the raw data for the
     * 2-digit UPC Supplemental barcode encoding process.
     *
     * @param input the string input representing the 2-digit data for the UPC Supplemental barcode.
     *              The input must be a numeric string with a length of 2 characters.
     */
    public UPCSupplement2(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the UPC Supplemental 2-digit algorithm.
     * @return Encoded value
     */
    private String encodeUPCSupplemental2() {
        if (getRawData().length() != 2) {
            error("EUPC-SUP2-1: Invalid data length. (Length = 2 required)");
        }

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EUPC-SUP2-2: Numeric Data Only");
        }

        String pattern = "";

        try {
            pattern = this.UPC_SUPP_2[Integer.parseInt(getRawData().trim()) % 4];
        } catch (Exception ex) {
            error("EUPC-SUP2-3: Invalid Data. (Numeric only)");
        }

        return doEncoding(pattern);
    }

    private String doEncoding(String pattern) {
        StringBuilder result = new StringBuilder("1011");

        int pos = 0;
        for (char c : pattern.toCharArray()) {
            if (c == 'a') {
                //encode using odd parity
                result.append(EAN_CodeA[Integer.parseInt(String.valueOf(getRawData().toCharArray()[pos]))]);
            } else if (c == 'b') {
                //encode using even parity
                result.append(EAN_CodeB[Integer.parseInt(String.valueOf(getRawData().toCharArray()[pos]))]);
            }

            if (pos++ == 0) {
                result.append("01"); //Inter-character separator
            }
        }
        return result.toString();
    }

    public String getEncodedValue() {
        return encodeUPCSupplemental2();
    }
}

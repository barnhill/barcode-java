package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

/**
 * This class represents the UPC Supplemental 5-digit extension barcode encoding model.
 * It encodes a 5-digit supplemental code used in conjunction with a primary UPC barcode.
 * The primary purpose of the 5-digit supplemental is to encode additional information,
 * such as price data or other identifiers, for certain product categories like books or periodicals.
 * This class extends {@code BarcodeCommon} and implements the necessary behavior to validate,
 * encode, and retrieve the encoded representation of the 5-digit supplemental barcode.
 */
public class UPCSupplement5 extends BarcodeCommon {
    private final String[] EAN_CodeA = {"0001101", "0011001", "0010011", "0111101", "0100011", "0110001", "0101111", "0111011", "0110111", "0001011"};
    private final String[] EAN_CodeB = {"0100111", "0110011", "0011011", "0100001", "0011101", "0111001", "0000101", "0010001", "0001001", "0010111"};
    private final String[] UPC_SUPP_5 = {"bbaaa", "babaa", "baaba", "baaab", "abbaa", "aabba", "aaabb", "ababa", "abaab", "aabab"};

    /**
     * Constructs a new UPCSupplement5 instance and initializes it with the given input data.
     * The input data represents the raw 5-digit supplemental information for encoding.
     *
     * @param input the raw 5-digit supplemental string used for UPC encoding
     */
    public UPCSupplement5(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the UPC Supplemental 5-digit algorithm.
     *
     * @return Encoded value
     */
    private String encodeUPCSupplemental5() {
        if (getRawData().length() != 5) {
            error("EUPC-SUP5-1: Invalid data length. (Length = 5 required)");
        }

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EUPC-SUP5-2: Numeric Data Only");
        }

        //calculate the checksum digit
        int even = 0;
        int odd = 0;

        //odd
        for (int i = 0; i <= 4; i += 2) {
            odd += Integer.parseInt(getRawData().substring(i, i + 1)) * 3;
        }

        //even
        for (int i = 1; i < 4; i += 2) {
            even += Integer.parseInt(getRawData().substring(i, i + 1)) * 9;
        }

        int total = even + odd;
        int cs = total % 10;

        String pattern = UPC_SUPP_5[cs];

        return doEncoding(pattern);
    }

    private String doEncoding(String pattern) {
        StringBuilder result = new StringBuilder();

        int pos = 0;
        for (char c : pattern.toCharArray()) {
            //Inter-character separator
            if (pos == 0) {
                result.append("1011");
            } else {
                result.append("01");
            }

            if (c == 'a') {
                //encode using odd parity
                result.append(EAN_CodeA[Integer.parseInt(String.valueOf(getRawData().toCharArray()[pos]))]);
            } else if (c == 'b') {
                //encode using even parity
                result.append(EAN_CodeB[Integer.parseInt(String.valueOf(getRawData().toCharArray()[pos]))]);
            }
            pos++;
        }
        return result.toString();
    }

    public String getEncodedValue() {
        return encodeUPCSupplemental5();
    }
}
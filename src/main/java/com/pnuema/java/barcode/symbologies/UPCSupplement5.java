package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

/**
 * UPC Supplement-5 encoding
 */
public class UPCSupplement5 extends BarcodeCommon implements IBarcode {
    private String[] EAN_CodeA = {"0001101", "0011001", "0010011", "0111101", "0100011", "0110001", "0101111", "0111011", "0110111", "0001011"};
    private String[] EAN_CodeB = {"0100111", "0110011", "0011011", "0100001", "0011101", "0111001", "0000101", "0010001", "0001001", "0010111"};
    private String[] UPC_SUPP_5 = {"bbaaa", "babaa", "baaba", "baaab", "abbaa", "aabba", "aaabb", "ababa", "abaab", "aabab"};

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
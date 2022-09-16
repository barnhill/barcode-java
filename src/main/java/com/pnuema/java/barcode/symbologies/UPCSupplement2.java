package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

/**
 * UPC Supplement-2 encoding
 */
public class UPCSupplement2 extends BarcodeCommon {
    private final String[] EAN_CodeA = {"0001101", "0011001", "0010011", "0111101", "0100011", "0110001", "0101111", "0111011", "0110111", "0001011"};
    private final String[] EAN_CodeB = {"0100111", "0110011", "0011011", "0100001", "0011101", "0111001", "0000101", "0010001", "0001001", "0010111"};
    private final String[] UPC_SUPP_2 = {"aa", "ab", "ba", "bb"};

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

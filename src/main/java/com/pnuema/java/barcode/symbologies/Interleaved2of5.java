package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

/**
 * Interleaved 2 of 5 encoding
 */
public class Interleaved2of5 extends BarcodeCommon implements IBarcode {
    private String[] I25_Code = {"NNWWN", "WNNNW", "NWNNW", "WWNNN", "NNWNW", "WNWNN", "NWWNN", "NNNWW", "WNNWN", "NWNWN"};

    public Interleaved2of5(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the Interleaved 2 of 5 algorithm.
     *
     * @return Encoded value
     */
    private String encodeInterleaved2Of5() {
        //check length of input
        if (getRawData().length() % 2 != 0)
            error("EI25-1: Data length invalid.");

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EI25-2: Numeric Data Only");
        }

        StringBuilder result = new StringBuilder("1010");

        for (int i = 0; i < getRawData().length(); i += 2) {
            boolean bars = true;
            String patternbars = I25_Code[Integer.parseInt(String.valueOf(getRawData().toCharArray()[i]))];
            String patternspaces = I25_Code[Integer.parseInt(String.valueOf(getRawData().toCharArray()[i + 1]))];
            StringBuilder patternmixed = new StringBuilder();

            //interleave
            while (!patternbars.isEmpty()) {
                patternmixed.append(String.valueOf(patternbars.toCharArray()[0])).append(String.valueOf(patternspaces.toCharArray()[0]));
                patternbars = patternbars.substring(1);
                patternspaces = patternspaces.substring(1);
            }

            for (char c1 : patternmixed.toString().toCharArray()) {
                if (bars) {
                    result.append(c1 == 'N' ? "1" : "11");
                } else {
                    result.append(c1 == 'N' ? "0" : "00");
                }
                bars = !bars;
            }
        }

        //add ending bars
        result.append("1101");
        return result.toString();
    }

    public String getEncodedValue() {
        return encodeInterleaved2Of5();
    }
}

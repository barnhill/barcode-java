package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

/**
 * ITF-14 encoding
 */
public class ITF14 extends BarcodeCommon {
    private final String[] ITF14_Code = {"NNWWN", "WNNNW", "NWNNW", "WWNNN", "NNWNW", "WNWNN", "NWWNN", "NNNWW", "WNNWN", "NWNWN"};

    public ITF14(String input) {
        setRawData(input);
        checkDigit();
    }

    /**
     * Encode the raw data using the ITF-14 algorithm.
     *
     * @return Encoded value
     */
    private String encodeITF14() {
        //check length of input
        if (getRawData().length() > 14 || getRawData().length() < 13) {
            error("EITF14-1: Data length invalid. (Length must be 13 or 14)");
        }

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EITF14-2: Numeric Data Only");
        }

        StringBuilder result = new StringBuilder("1010");

        for (int i = 0; i < getRawData().length(); i += 2) {
            boolean bars = true;
            String patternbars = ITF14_Code[Integer.parseInt(String.valueOf(getRawData().toCharArray()[i]))];
            String patternspaces = ITF14_Code[Integer.parseInt(String.valueOf(getRawData().toCharArray()[i + 1]))];
            StringBuilder patternmixed = new StringBuilder();

            //interleave
            while (patternbars.length() > 0) {
                patternmixed.append(String.valueOf(patternbars.toCharArray()[0])).append(String.valueOf(patternspaces.toCharArray()[0]));
                patternbars = patternbars.substring(1);
                patternspaces = patternspaces.substring(1);
            }

            for (char c1 : patternmixed.toString().toCharArray()) {
                if (bars) {
                    if (c1 == 'N') {
                        result.append("1");
                    } else {
                        result.append("11");
                    }
                } else {
                    if (c1 == 'N') {
                        result.append("0");
                    } else {
                        result.append("00");
                    }
                }
                bars = !bars;
            }
        }

        //add ending bars
        result.append("1101");

        return result.toString();
    }

    private void checkDigit() {
        //calculate and include checksum if it is necessary
        if (getRawData().length() == 13) {
            int total = 0;

            for (int i = 0; i <= getRawData().length() - 1; i++) {
                int temp = Integer.parseInt(getRawData().substring(i, i + 1));
                total += temp * ((i == 0 || i % 2 == 0) ? 3 : 1);
            }//for

            int cs = total % 10;
            cs = 10 - cs;
            if (cs == 10) {
                cs = 0;
            }

            setRawData(getRawData() + cs);
        }
    }

    public String getEncodedValue() {
        return encodeITF14();
    }
}

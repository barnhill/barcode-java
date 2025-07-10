package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

/**
 * Represents the ITF-14 barcode symbology, which is a subset of the Interleaved 2 of 5 (ITF) format.
 * ITF-14 is commonly used to encode Global Trade Item Numbers (GTIN) for shipping and logistics.
 * This class provides functionality to validate, encode, and generate barcodes compliant with the ITF-14 standard.
 */
public class ITF14 extends BarcodeCommon {
    private final String[] ITF14_Code = {"NNWWN", "WNNNW", "NWNNW", "WWNNN", "NNWNW", "WNWNN", "NWWNN", "NNNWW", "WNNWN", "NWNWN"};

    /**
     * Constructs an ITF14 barcode object and initializes the raw data and check digit.
     * The constructor sets the provided input string as raw data and calculates the check digit,
     * ensuring that the ITF-14 barcode is valid and ready for encoding.
     *
     * @param input the raw data string used to generate the ITF-14 barcode. This string must meet
     *              the requirements for ITF-14 barcodes, including proper length and content. The
     *              constructor validates the input and modifies it by appending a calculated check
     *              digit if necessary.
     */
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
            while (!patternbars.isEmpty()) {
                patternmixed.append(patternbars.toCharArray()[0]).append(patternspaces.toCharArray()[0]);
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
                total += temp * (i % 2 == 0 ? 3 : 1);
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

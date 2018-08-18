package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

/**
 * Standard 2 of 5 encoding
 */
public class Standard2of5 extends BarcodeCommon implements IBarcode {
    private String[] S25_Code = {"11101010101110", "10111010101110", "11101110101010", "10101110101110", "11101011101010", "10111011101010", "10101011101110", "10101110111010", "11101010111010", "10111010111010"};

    public Standard2of5(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the Standard 2 of 5 algorithm.
     *
     * @return Encoded value
     */
    private String encodeStandard2Of5() {
        //check numeric only
        if (checkNumericOnly(getRawData())) {
            error("ES25-2: Numeric Data Only");
        }

        StringBuilder result = new StringBuilder("11011010");

        for (char c : getRawData().toCharArray()) {
            result.append(S25_Code[Integer.parseInt(String.valueOf(c))]);
        }

        //add ending bars
        result.append("1101011");
        return result.toString();
    }

    public String getEncodedValue() {
        return encodeStandard2Of5();
    }
}

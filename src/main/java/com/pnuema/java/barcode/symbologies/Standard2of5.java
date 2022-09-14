package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.Barcode;
import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.EncodingType;
import com.pnuema.java.barcode.IBarcode;
import com.pnuema.java.barcode.utils.Utils2of5;

/**
 * Standard 2 of 5 encoding
 */
public class Standard2of5 extends BarcodeCommon {
    private final String[] S25_Code = {"10101110111010", "11101010101110", "10111010101110", "11101110101010", "10101110101110", "11101011101010", "10111011101010", "10101011101110", "11101010111010", "10111010111010"};
    private final EncodingType type;

    public Standard2of5(String input, EncodingType encodingType) {
        setRawData(input);
        type = encodingType;
    }

    /**
     * Encode the raw data using the Standard 2 of 5 algorithm.
     *
     * @return Encoded value
     */
    private String encodeStandard2Of5() {
        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("ES25-1: Numeric Data Only");
        }

        StringBuilder result = new StringBuilder("11011010");
        String data = getRawData() + (type == EncodingType.Standard2of5_Mod10 ? Utils2of5.CalculateMod10CheckDigit(getRawData()) : "");

        for (char c : data.toCharArray()) {
            result.append(S25_Code[Integer.parseInt(String.valueOf(c))]);
        }

        result.append(type == EncodingType.Standard2of5_Mod10 ? S25_Code[Utils2of5.CalculateMod10CheckDigit(getRawData())] : "");

        //add ending bars
        result.append("1101011");
        return result.toString();
    }

    public String getEncodedValue() {
        return encodeStandard2Of5();
    }
}

package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.utils.Utils2of5;

/**
 * IATA 2 of 5 encoding
 */
public class IATA2of5 extends BarcodeCommon {
    private final String[] IATA2of5_Code = {"10101110111010", "11101010101110", "10111010101110", "11101110101010", "10101110101110", "11101011101010", "10111011101010", "10101011101110", "11101010111010", "10111010111010"};

    public IATA2of5(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the Standard 2 of 5 algorithm.
     *
     * @return Encoded value
     */
    private String encodeIATA2Of5() {
        if (getRawData().length() > 17 || getRawData().length() < 16)
            error("EIATA25-1: Data length invalid. (Length must be 16 or 17)");

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EIATA25-2: Numeric Data Only");
        }

        StringBuilder result = new StringBuilder("1010");

        String data = getRawData();
        if (getRawData().length() == 17) {
            data = getRawData().substring(0, 16);
        }

        //add check digit
        data += Utils2of5.CalculateMod10CheckDigit(data);

        //encode
        for (char c : data.toCharArray()) {
            result.append(IATA2of5_Code[Integer.parseInt(String.valueOf(c))]);
        }

        //add ending bars
        result.append("111010");
        return result.toString();
    }

    public String getEncodedValue() {
        return encodeIATA2Of5();
    }
}

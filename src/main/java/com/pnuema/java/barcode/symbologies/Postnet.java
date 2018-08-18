package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

/**
 * Postnet encoding
 */
public class Postnet extends BarcodeCommon implements IBarcode {
    private String[] POSTNET_Code = {"11000", "00011", "00101", "00110", "01001", "01010", "01100", "10001", "10010", "10100"};

    public Postnet(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the PostNet algorithm.
     * @return Encoded value
     */
    private String encodePostnet() {
        //remove dashes if present
        setRawData(getRawData().replace("-", ""));

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EPOSTNET-1: Numeric Data Only");
            return "";
        }

        switch (getRawData().length()) {
            case 5:
            case 6:
            case 9:
            case 11:
                break;
            default:
                error("EPOSTNET-2: Invalid data length. (5, 6, 9, or 11 digits only)");
                break;
        }

        //Note: 0 = half bar and 1 = full bar
        //initialize the result with the starting bar
        StringBuilder result = new StringBuilder("1");
        int checkdigitsum = 0;

        for (char c : getRawData().toCharArray()) {
            int index = Integer.parseInt(String.valueOf(c));
            result.append(POSTNET_Code[index]);
            checkdigitsum += index;
        }

        //calculate and add check digit
        int temp = checkdigitsum % 10;
        int checkdigit = 10 - (temp == 0 ? 10 : temp);

        result.append(POSTNET_Code[checkdigit]);

        //ending bar
        result.append("1");

        return result.toString();
    }

    public String getEncodedValue() {
        return encodePostnet();
    }
}

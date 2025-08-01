package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

/**
 * The Postnet class provides functionality to encode numeric data into
 * the POSTNET barcode symbology. The POSTNET barcode is a form of postal
 * barcode used by the United States Postal Service for faster and more
 * accurate sorting of mail. This class extends the BarcodeCommon class to
 * leverage common validation and error handling mechanisms.
 */
public class Postnet extends BarcodeCommon {
    private final String[] POSTNET_Code = {"11000", "00011", "00101", "00110", "01001", "01010", "01100", "10001", "10010", "10100"};

    /**
     * Constructs a Postnet object with the specified input string.
     *
     * @param input the raw data string that represents numeric data to be encoded into
     *              the POSTNET barcode. The input is processed and validated to ensure
     *              it conforms to POSTNET requirements for encoding. Only numeric data
     *              is valid for this parameter.
     */
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

        return doEncoding();
    }

    private String doEncoding() {
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

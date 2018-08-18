package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

/**
 * UPC-E encoding
 */
public class UPCE extends BarcodeCommon implements IBarcode {
    private String[] EAN_CodeA = {"0001101", "0011001", "0010011", "0111101", "0100011", "0110001", "0101111", "0111011", "0110111", "0001011"};
    private String[] EAN_CodeB = {"0100111", "0110011", "0011011", "0100001", "0011101", "0111001", "0000101", "0010001", "0001001", "0010111"};
    private String[] UPCE_Code_0 = {"bbbaaa", "bbabaa", "bbaaba", "bbaaab", "babbaa", "baabba", "baaabb", "bababa", "babaab", "baabab"};
    private String[] UPCE_Code_1 = {"aaabbb", "aababb", "aabbab", "aabbba", "abaabb", "abbaab", "abbbaa", "ababab", "ababba", "abbaba"};

    /**
     * Encodes a UPC-E symbol.
     *
     * @param input Raw data to encode
     */
    public UPCE(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the UPC-E algorithm.
     *
     * @return Encoded value
     */
    private String encodeUPCE() {
        if (getRawData().length() != 6 && getRawData().length() != 8 && getRawData().length() != 12) {
            error("EUPCE-1: Invalid data length. (8 or 12 numbers only)");
        }

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EUPCE-2: Numeric Data Only");
        }

        //check for a valid number system
        int numberSystem = Integer.parseInt(String.valueOf(getRawData().toCharArray()[0]));
        if (numberSystem != 0 && numberSystem != 1) {
            error("EUPCE-3: Invalid Number System (only 0 & 1 are valid)");
        }

        int checkDigit = Integer.parseInt(String.valueOf(getRawData().toCharArray()[getRawData().length() - 1]));

        //Convert to UPC-E from UPC-A if necessary
        if (getRawData().length() == 12) {
            String UPCECode = "";

            //break apart into components
            String manufacturer = getRawData().substring(1, 6);
            String productCode = getRawData().substring(6, 11);

            if (manufacturer.endsWith("000") || manufacturer.endsWith("100") || manufacturer.endsWith("200") && Integer.parseInt(productCode) <= 999) {
                //rule 1
                UPCECode += manufacturer.substring(0, 2); //first two of manufacturer
                UPCECode += productCode.substring(2, 5); //last three of product
                UPCECode += String.valueOf(manufacturer.toCharArray()[2]); //third of manufacturer
            } else if (manufacturer.endsWith("00") && Integer.parseInt(productCode) <= 99) {
                //rule 2
                UPCECode += manufacturer.substring(0, 3); //first three of manufacturer
                UPCECode += productCode.substring(3, 5); //last two of product
                UPCECode += "3"; //number 3
            } else if (manufacturer.endsWith("0") && Integer.parseInt(productCode) <= 9) {
                //rule 3
                UPCECode += manufacturer.substring(0, 4); //first four of manufacturer
                UPCECode += productCode.toCharArray()[4]; //last digit of product
                UPCECode += "4"; //number 4
            } else if (!manufacturer.endsWith("0") && Integer.parseInt(productCode) <= 9 && Integer.parseInt(productCode) >= 5) {
                //rule 4
                UPCECode += manufacturer; //manufacturer
                UPCECode += productCode.toCharArray()[4]; //last digit of product
            } else {
                error("EUPCE-4: Illegal UPC-A entered for conversion.  Unable to convert.");
            }

            setRawData(UPCECode);
        }

        //get encoding pattern 
        String pattern;

        if (numberSystem == 0) {
            pattern = UPCE_Code_0[checkDigit];
        } else {
            pattern = UPCE_Code_1[checkDigit];
        }

        //encode the data
        StringBuilder result = new StringBuilder("101");

        int pos = 0;
        for (char c : pattern.toCharArray()) {
            int i = Integer.parseInt(String.valueOf(getRawData().toCharArray()[pos++]));
            if (c == 'a') {
                result.append(EAN_CodeA[i]);
            } else if (c == 'b') {
                result.append(EAN_CodeB[i]);
            }
        }

        //guard bars
        result.append("01010");

        //end bars
        result.append("1");

        return result.toString();
    }

    public String getEncodedValue() {
        return encodeUPCE();
    }
}
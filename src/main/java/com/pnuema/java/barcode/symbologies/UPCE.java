package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

/**
 * UPC-E encoding
 */
public class UPCE extends BarcodeCommon {
    private final String[] EAN_CodeA = {"0001101", "0011001", "0010011", "0111101", "0100011", "0110001", "0101111", "0111011", "0110111", "0001011"};
    private final String[] EAN_CodeB = {"0100111", "0110011", "0011011", "0100001", "0011101", "0111001", "0000101", "0010001", "0001001", "0010111"};
    private final String[] UPCE_Code_0 = {"bbbaaa", "bbabaa", "bbaaba", "bbaaab", "babbaa", "baabba", "baaabb", "bababa", "babaab", "baabab"};
    private final String[] UPCE_Code_1 = {"aaabbb", "aababb", "aabbab", "aabbba", "abaabb", "abbaab", "abbbaa", "ababab", "ababba", "abbaba"};

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
            error("EUPCE-1: Invalid data length. (6, 8 or 12 numbers only)");
        }

        int numberSystem = 0;
        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EUPCE-2: Numeric Data Only");
        }

        if (getRawData().length() == 8) {
            //strip check digit off to recalculate
            setRawData(getRawData().substring(1, 7));
        }

        //Convert to UPC-E from UPC-A if necessary
        if (getRawData().length() == 12) {
            numberSystem = Integer.parseInt(String.valueOf(getRawData().charAt(0)));
            setRawData(convertUPCAToUPCE());
        }

        int checkDigit = Integer.parseInt(calculateCheckDigit(convertUPCEToUPCA(getRawData())));

        //get encoding pattern 
        String pattern = getPattern(checkDigit, numberSystem);

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

        //end-guard bars
        result.append("010101");

        return result.toString();
    }

    private String getPattern(int checkDigit, int numberSystem) {
        if (numberSystem != 0 && numberSystem != 1) {
            error("EUPCE-3: Invalid Number System (only 0 & 1 are valid)");
        }

        String pattern;

        if (numberSystem == 0) {
            pattern = UPCE_Code_0[checkDigit];
        } else {
            pattern = UPCE_Code_1[checkDigit];
        }

        return pattern;
    }

    private String convertUPCAToUPCE() {
        String UPCECode = "";

        //break apart into components
        String manufacturer = getRawData().substring(1, 6);
        String productCode = getRawData().substring(6, 11);

        int numericProductCode = Integer.parseInt(productCode);

        if ((manufacturer.endsWith("000") || manufacturer.endsWith("100") || manufacturer.endsWith("200")) && numericProductCode <= 999) {
            //rule 1
            UPCECode += manufacturer.substring(0, 2); //first two of manufacturer
            UPCECode += productCode.substring(2, 5); //last three of product
            UPCECode += String.valueOf(manufacturer.toCharArray()[2]); //third of manufacturer
        } else if (manufacturer.endsWith("00") && numericProductCode <= 99) {
            //rule 2
            UPCECode += manufacturer.substring(0, 3); //first three of manufacturer
            UPCECode += productCode.substring(3, 5); //last two of product
            UPCECode += "3"; //number 3
        } else if (manufacturer.endsWith("0") && numericProductCode <= 9) {
            //rule 3
            UPCECode += manufacturer.substring(0, 4); //first four of manufacturer
            UPCECode += productCode.toCharArray()[4]; //last digit of product
            UPCECode += "4"; //number 4
        } else if (!manufacturer.endsWith("0") && numericProductCode <= 9 && numericProductCode >= 5) {
            //rule 4
            UPCECode += manufacturer; //manufacturer
            UPCECode += productCode.toCharArray()[4]; //last digit of product
        } else {
            error("EUPCE-4: Illegal UPC-A entered for conversion.  Unable to convert.");
        }

        return UPCECode;
    }

    private String convertUPCEToUPCA(String UPCECode) {
        String UPCACode = "0";
        if (UPCECode.endsWith("0") || UPCECode.endsWith("1") || UPCECode.endsWith("2")) {
            //rule 1
            UPCACode += UPCECode.substring(0, 2) + UPCECode.charAt(5) + "00"; //manufacturer
            UPCACode += "00" + UPCECode.substring(2, 5); //product
        } else if (UPCECode.endsWith("3")) {
            //rule 2
            UPCACode += UPCECode.substring(0, 3) + "00"; //manufacturer
            UPCACode += "000" + UPCECode.substring(3, 5); //product
        } else if (UPCECode.endsWith("4")) {
            //rule 3
            UPCACode += UPCECode.substring(0, 4) + "0"; //manufacturer
            UPCACode += "0000" + UPCECode.charAt(4); //product
        } else {
            //rule 4
            UPCACode += UPCECode.substring(0, 5); //manufacturer
            UPCACode += "0000" + UPCECode.charAt(5); //product
        }

        return UPCACode;
    }

    @SuppressWarnings("DuplicatedCode")
    private String calculateCheckDigit(String upcA) {
        int cs = 0;
        try {
            //calculate check digit
            int sum = 0;

            for (int i = 0; i < upcA.length(); i++) {
                int parseInt = Integer.parseInt(upcA.substring(i, i + 1));
                if (i % 2 == 0) {
                    sum += parseInt * 3;
                } else {
                    sum += parseInt;
                }
            }

            cs = (10 - sum % 10) % 10;
        } catch (Exception ex) {
            error("EUPCE-5: Error calculating check digit.");
        }

        return String.valueOf(cs);
    }

    public String getEncodedValue() {
        return encodeUPCE();
    }
}
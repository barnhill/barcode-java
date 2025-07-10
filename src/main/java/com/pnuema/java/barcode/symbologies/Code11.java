package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

/**
 * The Code11 class provides functionality for generating Code 11 barcodes.
 * Code 11 is a barcode symbology that supports numeric data and the "-" character.
 * The class derives from BarcodeCommon to use common barcode validation and processing mechanisms.
 */
public class Code11 extends BarcodeCommon {
    private final String[] C11_Code = { "101011", "1101011", "1001011", "1100101", "1011011", "1101101", "1001101", "1010011", "1101001", "110101", "101101", "1011001" };

    /**
     * Constructor for the Code11 class. This constructor initializes a new instance of
     * the Code11 barcode using the provided input string.
     *
     * @param input the raw data string to be encoded using the Code 11 algorithm.
     *              This string will be internally set as the raw data for processing
     *              and later encoding into a Code 11 barcode.
     */
    public Code11(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the Code 11 algorithm.
     * @return Code 11 encoding
     */
    private String encodeCode11() {
        if (!checkNumericOnly(getRawData().replace("-", ""))) {
            error("EC11-1: Numeric data and '-' Only");
        }

        //calculate the checksums
        int weight = 1;
        int CTotal = 0;
        String dataToEncodeWithChecksums = getRawData();

        //figure the C checksum
        for (int i = getRawData().length() - 1; i >= 0; i--) {
            //C checksum weights go 1-10
            if (weight == 10) {
                weight = 1;
            }

            if (getRawData().charAt(i) != '-') {
                CTotal += Integer.parseInt(String.valueOf(getRawData().charAt(i))) * weight++;
            } else {
                CTotal += 10 * weight++;
            }
        }

        int checksumC = CTotal % 11;

        dataToEncodeWithChecksums += String.valueOf(checksumC);

        //K checksums are recommended on any message length greater than or equal to 10
        if (getRawData().length() >= 10) {
            weight = 1;
            int KTotal = 0;

            //calculate K checksum
            for (int i = dataToEncodeWithChecksums.length() - 1; i >= 0; i--) {
                //K checksum weights go 1-9
                if (weight == 9) {
                    weight = 1;
                }

                if (dataToEncodeWithChecksums.charAt(i) != '-') {
                    KTotal += Integer.parseInt(String.valueOf(dataToEncodeWithChecksums.charAt(i))) * weight++;
                } else {
                    KTotal += 10 * weight++;
                }
            }

            int checksumK = KTotal % 9;
            dataToEncodeWithChecksums += checksumK;
        }

        //encode data
        return doEncoding(dataToEncodeWithChecksums);
    }

    private String doEncoding(String dataToEncodeWithChecksums) {
        String space = "0";
        StringBuilder builder = new StringBuilder();
        builder.append(C11_Code[11]);//start-stop char
        builder.append(space); //inter-character space

        for (char c : dataToEncodeWithChecksums.toCharArray()) {
            int index = (c == '-' ? 10 : Integer.parseInt(String.valueOf(c)));
            builder.append(C11_Code[index]);

            //inter-character space
            builder.append(space);
        }//foreach

        //stop bars
        builder.append(C11_Code[11]);
        return builder.toString();
    }

    @Override
    public String getEncodedValue() {
        return encodeCode11();
    }
}

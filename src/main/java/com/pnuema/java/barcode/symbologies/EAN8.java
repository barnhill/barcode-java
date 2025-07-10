package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

/**
 * The EAN8 class provides functionality for encoding raw data
 * into the EAN-8 barcode format. The EAN-8 is a compressed version
 * of the EAN-13 barcode, used for smaller products or packaging where
 * space is limited. This class handles validation, check digit calculation,
 * and encoding of EAN-8 barcodes.
 */
public class EAN8 extends BarcodeCommon {
    private final String[] EAN_CodeA = { "0001101", "0011001", "0010011", "0111101", "0100011", "0110001", "0101111", "0111011", "0110111", "0001011" };
    private final String[] EAN_CodeC = { "1110010", "1100110", "1101100", "1000010", "1011100", "1001110", "1010000", "1000100", "1001000", "1110100" };

    /**
     * Constructs an EAN8 instance with the specified input data.
     * This constructor initializes the raw data for the barcode
     * and calculates the check digit if necessary.
     *
     * @param input the raw data to be encoded into the EAN-8 barcode format
     */
    public EAN8(String input) {
        setRawData(input);
        calculateCheckDigit();
    }

    /**
     * Encode the raw data using the EAN-8 algorithm.
     * @return Encoded value
     */
    private String encodeEAN8() {
        //check length
        if (getRawData().length() != 8 && getRawData().length() != 7) {
            error("EEAN8-1: Invalid data length. (7 or 8 numbers only)");
        }

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EEAN8-2: Numeric Data Only");
        }

        //encode the data
        StringBuilder result = new StringBuilder("101");

        //first half (Encoded using left hand / odd parity)
        for (int i = 0; i < getRawData().length() / 2; i++) {
            result.append(EAN_CodeA[Integer.parseInt(String.valueOf(getRawData().toCharArray()[i]))]);
        }

        //center guard bars
        result.append("01010");

        //second half (Encoded using right hand / even parity)
        for (int i = getRawData().length() / 2; i < getRawData().length(); i++) {
            result.append(EAN_CodeC[Integer.parseInt(String.valueOf(getRawData().toCharArray()[i]))]);
        }

        result.append("101");

        return result.toString();
    }

    private void calculateCheckDigit() {
        //calculate the checksum digit if necessary
        if (getRawData().length() == 7) {
            //calculate the checksum digit
            int even = 0;
            int odd = 0;

            //odd
            for (int i = 0; i <= 6; i += 2) {
                odd += Integer.parseInt(getRawData().substring(i, i + 1)) * 3;
            }

            //even
            for (int i = 1; i <= 5; i += 2) {
                even += Integer.parseInt(getRawData().substring(i, i + 1));
            }

            int total = even + odd;
            int checksum = total % 10;
            checksum = 10 - checksum;
            if (checksum == 10) {
                checksum = 0;
            }

            //add the checksum to the end of the
            setRawData(getRawData() + checksum);
        }
    }

    public String getEncodedValue(){
        return encodeEAN8();
    }
}

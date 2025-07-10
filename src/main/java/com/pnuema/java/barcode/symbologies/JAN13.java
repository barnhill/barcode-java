package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

/**
 * Represents a JAN-13 barcode, a variation of the EAN-13 barcode format specific to Japan.
 * This class inherits from {@code BarcodeCommon} and provides encoding functionality.
 * The JAN-13 barcode requires a country code of "49" as a prefix in the raw data.
 */
public class JAN13 extends BarcodeCommon {
    /**
     * Constructs a new JAN-13 barcode object using the specified input string.
     * This constructor initializes the barcode with the raw data to be encoded
     * into the JAN-13 (Japanese Article Number) format. The provided input
     * must adhere to the JAN-13 standard, including a "49" prefix as the country code.
     *
     * @param input the raw data string to be encoded as a JAN-13 barcode. This input
     *              must be a 13-digit numeric string that starts with the country
     *              identification code "49" to comply with the JAN-13 standard.
     *              Any invalid input, including non-numeric or improperly formatted
     *              data, may result in errors when attempting to encode or retrieve
     *              the encoded value.
     */
    public JAN13(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the JAN-13 algorithm.
     *
     * @return Encoded value
     */
    private String encodeJAN13() {
        if (!getRawData().startsWith("49")) {
            error("EJAN13-1: Invalid Country Code for JAN13 (49 required)");
        }

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EJAN13-2: Numeric Data Only");
        }

        return new EAN13(getRawData()).getEncodedValue();
    }

    public String getEncodedValue() {
        return encodeJAN13();
    }
}
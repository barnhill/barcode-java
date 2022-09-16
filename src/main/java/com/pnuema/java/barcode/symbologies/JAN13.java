package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

/**
 * JAN-13 encoding
 */
public class JAN13 extends BarcodeCommon {
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
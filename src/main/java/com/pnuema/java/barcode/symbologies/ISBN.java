package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

/**
 * ISBN encoding
 */
public class ISBN extends BarcodeCommon implements IBarcode {
    public ISBN(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the Bookland/ISBN algorithm.
     * @return Encoded Value
     */
    private String encodeISBNBookland() {
        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EBOOKLANDISBN-1: Numeric Data Only");
        }

        String type = "UNKNOWN";
        if (getRawData().length() == 10 || getRawData().length() == 9) {
            if (getRawData().length() == 10) {
                setRawData(getRawData().substring(0, 9));
            }
            setRawData("978" + getRawData());
            type = "ISBN";
        } else if (getRawData().length() == 12 && getRawData().startsWith("978")) {
            type = "BOOKLAND-NOCHECKDIGIT";
        } else if (getRawData().length() == 13 && getRawData().startsWith("978")) {
            type = "BOOKLAND-CHECKDIGIT";
            setRawData(getRawData().substring(0, 12));
        }

        //check to see if its an unknown type
        if ("UNKNOWN".equals(type)) {
            error("EBOOKLANDISBN-2: Invalid input.  Must start with 978 and be length must be 9, 10, 12, 13 characters.");
        }

        return new EAN13(getRawData()).getEncodedValue();
    }

    public String getEncodedValue() {
        return encodeISBNBookland();
    }
}

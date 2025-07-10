package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

/**
 * The ISBN class is responsible for encoding input data into a valid ISBN or Bookland barcode format.
 * It extends BarcodeCommon and provides a specific implementation for the ISBN encoding algorithm.
 */
public class ISBN extends BarcodeCommon {
    /**
     * Constructs an ISBN object using the given input string.
     * The input data is set to be encoded into a valid ISBN or Bookland barcode format.
     *
     * @param input the raw data string to encode. It should represent a numeric sequence
     *              that adheres to the ISBN or Bookland format rules. The input is validated
     *              and processed to meet the encoding requirements for these barcode types.
     */
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

        String type = null;
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

        //check to see if it is an unknown type
        if (type == null) {
            error("EBOOKLANDISBN-2: Invalid input.  Must start with 978 and be length must be 9, 10, 12, 13 characters.");
        }

        return new EAN13(getRawData()).getEncodedValue();
    }

    public String getEncodedValue() {
        return encodeISBNBookland();
    }
}

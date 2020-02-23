package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

/**
 * Pharmacode encoding
 */
public class Pharmacode extends BarcodeCommon implements IBarcode {
    /**
     * Encodes with Pharmacode.
     *
     * @param input Data to encode
     */
    public Pharmacode(String input) {
        setRawData(input);

        if (getRawData().length() > 6) {
            error("EPHARM-2: Data too long (invalid data input length).");
        }//if

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EPHARM-1: Numeric Data Only");
        }
    }

    /**
     * Encode the raw data using the Pharmacode algorithm.
     *
     * @return Encoded value
     */
    private String encodePharmacode() {
        if (!getErrors().isEmpty()) {
            return "";
        }

        int num = Integer.parseInt(getRawData());

        if (num < 3 || num > 131070) {
            error("EPHARM-4: Data contains invalid  characters (invalid numeric range).");
        }

        String thickBar = "111";
        String thinBar = "1";
        String gap = "00";
        StringBuilder result = new StringBuilder();

        do
        {
            if ((num & 1) == 0)
            {
                result.insert(0, thickBar);
                num = (num - 2) / 2;
            }
            else
            {
                result.insert(0, thinBar);
                num = (num - 1) / 2;
            }

            if (num != 0)
            {
                result.insert(0, gap);
            }
        } while (num != 0);

        return result.toString();
    }

    public String getEncodedValue() {
        return encodePharmacode();
    }
}

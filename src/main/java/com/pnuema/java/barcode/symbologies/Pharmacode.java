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

        int startIndex = 0;

        //find start index
        for (int index = 15; index >= 0; index--) {
            if (Math.pow(2, index) < num / 2) {
                startIndex = index;
                break;
            }
        }

        double sum = Math.pow(2, startIndex + 1) - 2;
        String[] encoded = new String[startIndex + 1];
        int i = 0;

        String thickBar = "111";
        String thinBar = "1";
        for (int index = startIndex; index >= 0; index--) {
            double power = Math.pow(2, index);
            double diff = num - sum;
            if (diff > power) {
                encoded[i++] = thickBar;
                sum += power;
            } else {
                encoded[i++] = thinBar;
            }
        }

        String gap = "00";
        StringBuilder result = new StringBuilder();
        for (String s : encoded) {
            if (result.length() > 0) {
                result.append(gap);
            }

            result.append(s);
        }

        return result.toString();
    }

    public String getEncodedValue() {
        return encodePharmacode();
    }
}

package com.pnuema.java.barcode;
import java.util.ArrayList;
import java.util.List;

public abstract class BarcodeCommon {
    private String rawData = "";
    private List<String> errors = new ArrayList<>();

    protected void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getRawData() {
        return rawData;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void clearErrors() {
        errors.clear();
    }

    protected void error(String ErrorMessage) {
        errors.add(ErrorMessage);
        throw new RuntimeException(ErrorMessage);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected static boolean checkNumericOnly(String data) {
        //This function takes a String of data and breaks it into parts and trys to do a parse
        //This will verify that only numeric data is contained in the String passed in.  The complexity below
        //was done to ensure that the minimum number of iterations and checks could be performed.

        //early check to see if the whole number can be parsed to improve efficiency of this method
        if (data != null) {
            try {
                Long.parseLong(data);
                return true;
            } catch (NumberFormatException ignored) {
            }
        } else {
            return false;
        }

        //9223372036854775808 is the largest number a 64bit number(signed) can hold so ... make sure its less than that by one place
        int STRING_LENGTHS = 18;

        String temp = data;

        String piece;
        while (!temp.isEmpty()) {
            if (temp.length() >= STRING_LENGTHS) {
                piece = temp.substring(0, STRING_LENGTHS);
                temp = temp.substring(STRING_LENGTHS);
            } else {
                piece = temp;
            }

            try {
                Long.parseLong(piece);
            } catch (NumberFormatException ignored) {
                return false;
            }
        }

        return true;
    }
}
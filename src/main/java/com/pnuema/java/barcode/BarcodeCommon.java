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

    @SuppressWarnings("WeakerAccess")
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

    protected static boolean checkNumericOnly(String data) {
        return data.matches("^\\d+$");
    }
}
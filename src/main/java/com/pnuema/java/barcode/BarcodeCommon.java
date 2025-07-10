package com.pnuema.java.barcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for barcode generation.
 * Provides common properties and methods used across various barcode symbologies.
 * This class handles the validation of raw data, storage of errors, and provides
 * utility methods to facilitate encoding processes.
 */
public abstract class BarcodeCommon implements IBarcode {
    private String rawData = "";
    private final List<String> errors = new ArrayList<>();

    /**
     * Default constructor for the BarcodeCommon class.
     * Initializes a BarcodeCommon instance, providing base functionality
     * for barcode generation. This constructor is protected, as it is intended
     * to be used only within the context of subclassing or instantiation of
     * derived barcode classes.
     */
    public BarcodeCommon() {
    }

    public abstract String getEncodedValue() throws BarcodeException;

    /**
     * Sets the raw data to be used by the barcode instance.
     * This method updates the internal raw data field with the given input.
     *
     * @param rawData the raw data string to set for the barcode instance
     */
    protected void setRawData(String rawData) {
        this.rawData = rawData;
    }

    /**
     * Retrieves the raw data stored in the barcode instance.
     *
     * @return the raw data as a string
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * Retrieves the list of currently stored error messages.
     *
     * @return a list of error messages encountered during processing
     */
    @SuppressWarnings("WeakerAccess")
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Clears all error messages from the internal error list.
     * This method is used to reset the error state, removing
     * any previously stored error messages.
     */
    public void clearErrors() {
        errors.clear();
    }

    /**
     * Adds an error message to the error list and throws a {@code BarcodeException} with the specified message.
     *
     * @param ErrorMessage the error message to add and propagate as an exception
     * @throws BarcodeException if an error occurs in barcode processing
     */
    protected void error(String ErrorMessage) throws BarcodeException {
        errors.add(ErrorMessage);
        throw new BarcodeException(ErrorMessage);
    }

    /**
     * Validates whether the input string contains only numeric characters.
     *
     * @param data the string to be checked for numeric content
     * @return {@code true} if the input string consists solely of digits (0-9); {@code false} otherwise
     */
    protected static boolean checkNumericOnly(String data) {
        return data.matches("^\\d+$");
    }
}
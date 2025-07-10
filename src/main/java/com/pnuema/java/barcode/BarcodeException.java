package com.pnuema.java.barcode;

/**
 * Represents an exception thrown when an error related to barcode processing occurs.
 * This exception extends the {@code RuntimeException}, allowing it to be used for
 * unchecked exceptions.
 * The {@code BarcodeException} can be instantiated with a specific error message
 * to provide details about the cause of the exception.
 */
public class BarcodeException extends RuntimeException {
    /**
     * Constructs a new {@code BarcodeException} with the specified error message.
     *
     * @param errorMessage the detail message explaining the reason for the exception
     */
    public BarcodeException(String errorMessage) {
        super(errorMessage);
    }
}

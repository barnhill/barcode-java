package com.pnuema.java.barcode;

import java.util.List;

/**
 * Barcode interface for symbology layout
 */
public interface IBarcode {
    /**
     * Triggers the encoding process
     * @return Encoded value
     */
    String getEncodedValue();

    /**
     * Gets the raw data prior to encoding
     * @return Raw data
     */
    String getRawData();

    /**
     * Gets errors encountered during the encoding process
     * @return Errors encountered during the encoding
     */
    List<String> getErrors();

    /**
     * Clears the errors
     */
    void clearErrors();
}
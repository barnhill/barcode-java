package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.utils.Utils2of5;

/**
 * Represents the IATA 2 of 5 barcode symbology, which is a self-checking,
 * numeric-only encoding schema used commonly in industrial and logistics
 * applications. This class extends {@code BarcodeCommon} and provides
 * functionality for encoding numeric data into the IATA 2 of 5 barcode format.
 * The barcode encoding includes:
 * - A start sequence ("1010").
 * - Encoded numeric characters using a pre-defined set of bar patterns.
 * - An optional Mod-10 check digit appended to the data.
 * - A stop sequence ("111010").
 * This implementation validates the input data to ensure it adheres to the
 * constraints of the IATA 2 of 5 barcode format, such as numeric-only content and
 * length limitations, before performing the encoding.
 */
public class IATA2of5 extends BarcodeCommon {
    private final String[] IATA2of5_Code = {"10101110111010", "11101010101110", "10111010101110", "11101110101010", "10101110101110", "11101011101010", "10111011101010", "10101011101110", "11101010111010", "10111010111010"};

    /**
     * Constructs an instance of the IATA2of5 barcode class with the provided input.
     * The input string is set as the raw data for this barcode instance. The raw data
     * is expected to be numeric-only and adheres to the constraints of the IATA 2 of 5
     * barcode symbology.
     *
     * @param input the raw data to be encoded as an IATA 2 of 5 barcode. The input must be
     *              numeric-only and typically consists of 16 or 17 digits, where the 17th
     *              digit may represent a Mod-10 check digit if included.
     */
    public IATA2of5(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the Standard 2 of 5 algorithm.
     *
     * @return Encoded value
     */
    private String encodeIATA2Of5() {
        if (getRawData().length() > 17 || getRawData().length() < 16)
            error("EIATA25-1: Data length invalid. (Length must be 16 or 17)");

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EIATA25-2: Numeric Data Only");
        }

        StringBuilder result = new StringBuilder("1010");

        String data = getRawData();
        if (getRawData().length() == 17) {
            data = getRawData().substring(0, 16);
        }

        //add check digit
        data += Utils2of5.CalculateMod10CheckDigit(data);

        //encode
        for (char c : data.toCharArray()) {
            result.append(IATA2of5_Code[Integer.parseInt(String.valueOf(c))]);
        }

        //add ending bars
        result.append("111010");
        return result.toString();
    }

    public String getEncodedValue() {
        return encodeIATA2Of5();
    }
}

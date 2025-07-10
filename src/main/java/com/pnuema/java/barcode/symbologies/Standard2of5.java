package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.EncodingType;
import com.pnuema.java.barcode.utils.Utils2of5;

/**
 * The Standard2of5 class implements the Standard 2 of 5 barcode symbology.
 * This symbology encodes numeric-only input and optionally supports Mod10
 * checksum validation based on the EncodingType provided.
 * The encoding process for Standard 2 of 5 uses interleaved patterns of bars
 * and spaces, specifically matching the S25_Code mapping array for digits 0-9.
 * An optional Mod10 checksum can be appended to the data based on the selected
 * encoding type.
 */
public class Standard2of5 extends BarcodeCommon {
    private final String[] S25_Code = { "10101110111010", "11101010101110", "10111010101110", "11101110101010", "10101110101110", "11101011101010", "10111011101010", "10101011101110", "11101010111010", "10111010111010" };
    private final EncodingType type;

    /**
     * Constructs a new Standard2of5 object with the specified input data and encoding type.
     *
     * @param input        The input data to be encoded. The input must consist of numeric characters only.
     * @param encodingType The encoding type to be used for Standard 2 of 5. This determines whether
     *                     a Mod10 checksum is appended to the encoded value.
     */
    public Standard2of5(String input, EncodingType encodingType) {
        setRawData(input);
        type = encodingType;
    }

    /**
     * Encode the raw data using the Standard 2 of 5 algorithm.
     *
     * @return Encoded value
     */
    private String encodeStandard2Of5() {
        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("ES25-1: Numeric Data Only");
        }

        StringBuilder result = new StringBuilder("1110111010");

        for (char c : getRawData().toCharArray()) {
            result.append(S25_Code[Integer.parseInt(String.valueOf(c))]);
        }

        result.append(type == EncodingType.Standard2of5_Mod10 ? S25_Code[Utils2of5.CalculateMod10CheckDigit(getRawData())] : "");

        //add ending bars
        result.append("1110101110");
        return result.toString();
    }

    public String getEncodedValue() {
        return encodeStandard2Of5();
    }
}

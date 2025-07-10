package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.EncodingType;
import com.pnuema.java.barcode.utils.Utils2of5;

/**
 * Represents a barcode type using the Interleaved 2 of 5 (I2/5) symbology.
 * This barcode symbology is a continuous two-width code where digits are encoded in pairs.
 * Implementation supports optional Mod10 checksum calculation for additional data integrity.
 * This class extends {@code BarcodeCommon} to use common barcode functionalities, such as
 * data validation and error handling, and adds specific encoding for the Interleaved 2 of 5 symbology.
 */
public class Interleaved2of5 extends BarcodeCommon {
    private final String[] I25_Code = {"NNWWN", "WNNNW", "NWNNW", "WWNNN", "NNWNW", "WNWNN", "NWWNN", "NNNWW", "WNNWN", "NWNWN"};
    private final EncodingType type;

    /**
     * Constructs an instance of the Interleaved2of5 class, initializing it with the input data and
     * the chosen encoding type. The input data represents the string to be encoded, and the type
     * specifies the encoding format, such as standard or Mod10 checksum.
     *
     * @param input The data to be encoded using the Interleaved 2 of 5 symbology. Should be numeric.
     * @param encodingType The specific encoding type, either standard Interleaved 2 of 5 or with Mod10.
     */
    public Interleaved2of5(String input, EncodingType encodingType) {
        setRawData(input);
        type = encodingType;
    }

    /**
     * Encode the raw data using the Interleaved 2 of 5 algorithm.
     *
     * @return Encoded value
     */
    private String encodeInterleaved2Of5() {
        //check length of input
        if (getRawData().length() % 2 != (type == EncodingType.Interleaved2of5_Mod10 ? 1 : 0))
            error("EI25-1: Data length invalid.");

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EI25-2: Numeric Data Only");
        }

        StringBuilder result = new StringBuilder("1010");
        String data = getRawData() + (type == EncodingType.Interleaved2of5_Mod10 ? Utils2of5.CalculateMod10CheckDigit(getRawData()) : "");

        for (int i = 0; i < data.length(); i += 2) {
            boolean bars = true;
            String patternbars = I25_Code[Integer.parseInt(String.valueOf(data.charAt(i)))];
            String patternspaces = I25_Code[Integer.parseInt(String.valueOf(data.charAt(i + 1)))];
            StringBuilder patternmixed = new StringBuilder();

            //interleave
            while (!patternbars.isEmpty()) {
                patternmixed.append(patternbars.charAt(0)).append(patternspaces.toCharArray()[0]);
                patternbars = patternbars.substring(1);
                patternspaces = patternspaces.substring(1);
            }

            for (char c1 : patternmixed.toString().toCharArray()) {
                if (bars) {
                    result.append(c1 == 'N' ? "1" : "11");
                } else {
                    result.append(c1 == 'N' ? "0" : "00");
                }
                bars = !bars;
            }
        }

        //add ending bars
        result.append("1101");
        return result.toString();
    }

    public String getEncodedValue() {
        return encodeInterleaved2Of5();
    }
}

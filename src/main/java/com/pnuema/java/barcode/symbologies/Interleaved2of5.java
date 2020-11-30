package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.Barcode;
import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;
import com.pnuema.java.barcode.utils.Utils2of5;

/**
 * Interleaved 2 of 5 encoding
 */
public class Interleaved2of5 extends BarcodeCommon implements IBarcode {
    private final String[] I25_Code = {"NNWWN", "WNNNW", "NWNNW", "WWNNN", "NNWNW", "WNWNN", "NWWNN", "NNNWW", "WNNWN", "NWNWN"};
    private final Barcode.TYPE type;

    public Interleaved2of5(String input, Barcode.TYPE encodingType) {
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
        if (getRawData().length() % 2 != (type == Barcode.TYPE.Interleaved2of5_Mod10 ? 1 : 0))
            error("EI25-1: Data length invalid.");

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EI25-2: Numeric Data Only");
        }

        StringBuilder result = new StringBuilder("1010");
        String data = getRawData() + (type == Barcode.TYPE.Interleaved2of5_Mod10 ? Utils2of5.CalculateMod10CheckDigit(getRawData()) : "");

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

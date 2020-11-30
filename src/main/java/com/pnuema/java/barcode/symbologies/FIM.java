package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

/**
 * FIM encoding
 */
public class FIM extends BarcodeCommon implements IBarcode {
    public enum FIMTypes {FIM_A, FIM_B, FIM_C, FIM_D, FIM_E}

    public FIM(String input) {
        input = input.trim();

        String[] FIM_Codes = {"110010011", "101101101", "110101011", "111010111", "101000101"};
        switch (input) {
            case "A":
            case "a":
                setRawData(FIM_Codes[FIMTypes.FIM_A.ordinal()]);
                break;
            case "B":
            case "b":
                setRawData(FIM_Codes[FIMTypes.FIM_B.ordinal()]);
                break;
            case "C":
            case "c":
                setRawData(FIM_Codes[FIMTypes.FIM_C.ordinal()]);
                break;
            case "D":
            case "d":
                setRawData(FIM_Codes[FIMTypes.FIM_D.ordinal()]);
                break;
            case "E":
            case "e":
                setRawData(FIM_Codes[FIMTypes.FIM_E.ordinal()]);
                break;
            default:
                error("EFIM-1: Could not determine encoding type. (Only pass in A, B, C, D, or E)");
                break;
        }
    }

    private String encodeFIM() {
        StringBuilder encoded = new StringBuilder();
        for (char c : getRawData().toCharArray()) {
            encoded.append(c).append("0");
        }

        encoded = new StringBuilder(encoded.substring(0, encoded.length() - 1));

        return encoded.toString();
    }

    public String getEncodedValue() {
        return encodeFIM();
    }
}
package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

import java.util.HashMap;

/**
 * Codabar encoding
 */
public class Codabar extends BarcodeCommon {
    private final HashMap<Character, String> codabarCode = new HashMap<>();

    public Codabar(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the Codabar algorithm.
     * @return Encoded string
     */
    private String encodeCodabar() {
        if (getRawData().length() < 2) error("ECODABAR-1: Data format invalid. (Invalid length)");

        //check first char to make sure its a start/stop char
        switch (String.valueOf(getRawData().charAt(0)).toUpperCase().trim()) {
            case "A":
            case "B":
            case "C":
            case "D":
                break;
            default:
                error("ECODABAR-2: Data format invalid. (Invalid START character)");
                break;
        }

        //check the ending char to make sure its a start/stop char
        switch (String.valueOf(getRawData().charAt(getRawData().trim().length() - 1)).trim().toUpperCase()) {
            case "A":
            case "B":
            case "C":
            case "D":
                break;
            default:
                error("ECODABAR-3: Data format invalid. (Invalid STOP character)");
                break;
        }

        //populate the hashtable to begin the process
        initCodabar();

        //replace non-numeric VALID chars with empty strings before checking for all numerics
        String temp = getRawData();

        for (char c : codabarCode.keySet()) {
            if (!checkNumericOnly(String.valueOf(c))) {
                temp = temp.replace(c, '1');
            }
        }

        //now that all the valid non-numeric chars have been replaced with a number check if all numeric exist
        if (!checkNumericOnly(temp)) {
            error("ECODABAR-4: Data contains invalid  characters.");
        }

        StringBuilder result = new StringBuilder();
        for (char c : getRawData().toCharArray()) {
            result.append(codabarCode.get(c));
            result.append("0"); //inter-character space
        }

        //remove the extra 0 at the end of the result
        result.deleteCharAt(result.length() - 1);

        //clears the hashtable so it no longer takes up memory
        codabarCode.clear();

        //change the Raw_Data to strip out the start stop chars for label purposes
        setRawData(getRawData().trim().substring(1, getRawData().trim().length() - 2));

        return result.toString();
    }

    private void initCodabar() {
        codabarCode.clear();
        codabarCode.put('0', "101010011");//"101001101101");
        codabarCode.put('1', "101011001");//"110100101011");
        codabarCode.put('2', "101001011");//"101100101011");
        codabarCode.put('3', "110010101");//"110110010101");
        codabarCode.put('4', "101101001");//"101001101011");
        codabarCode.put('5', "110101001");//"110100110101");
        codabarCode.put('6', "100101011");//"101100110101");
        codabarCode.put('7', "100101101");//"101001011011");
        codabarCode.put('8', "100110101");//"110100101101");
        codabarCode.put('9', "110100101");//"101100101101");
        codabarCode.put('-', "101001101");//"110101001011");
        codabarCode.put('$', "101100101");//"101101001011");
        codabarCode.put(':', "1101011011");//"110110100101");
        codabarCode.put('/', "1101101011");//"101011001011");
        codabarCode.put('.', "1101101101");//"110101100101");
        codabarCode.put('+', "101100110011");//"101101100101");
        codabarCode.put('A', "1011001001");//"110110100101");
        codabarCode.put('B', "1010010011");//"101011001011");
        codabarCode.put('C', "1001001011");//"110101100101");
        codabarCode.put('D', "1010011001");//"101101100101");
        codabarCode.put('a', "1011001001");//"110110100101");
        codabarCode.put('b', "1010010011");//"101011001011");
        codabarCode.put('c', "1001001011");//"110101100101");
        codabarCode.put('d', "1010011001");//"101101100101");
    }

    @Override
    public String getEncodedValue() {
        return encodeCodabar();
    }
}

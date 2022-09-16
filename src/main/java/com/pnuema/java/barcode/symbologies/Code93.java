package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Code 93 encoding
 */
public class Code93 extends BarcodeCommon {
    private final List<Entry> C93_Code = new ArrayList<>();

    private static class Entry {
        private final String value;
        private final String character;
        private final String encoding;

        Entry(String value, String character, String encoding) {
            this.value = value;
            this.character = character;
            this.encoding = encoding;
        }

        String getValue() {
            return value;
        }

        String getCharacter() {
            return character;
        }

        String getEncoding() {
            return encoding;
        }
    }

    /**
     * Encodes with Code93
     * @param input Data to encode
     */
    public Code93(String input) {
        setRawData(input);
    }

    private Entry findRowByValue(String match) {
        Entry returnValue = null;
        for (Entry entry : C93_Code) {
            if (entry.getValue().equals(match)) {
                returnValue = entry;
            }
        }

        return Objects.requireNonNull(returnValue);
    }

    private Entry findRowByCharacter(String match) {
        Entry returnValue = null;
        for (Entry entry : C93_Code) {
            if (entry.getCharacter().equals(match)) {
                returnValue = entry;
            }
        }

        return Objects.requireNonNull(returnValue);
    }

    /**
     * Encode the raw data using the Code 93 algorithm
     */
    private String encodeCode93() {
        this.initCode93();

        String FormattedData = addCheckDigits(getRawData());
        StringBuilder result = new StringBuilder(findRowByCharacter("*").getEncoding());
        for (char c : FormattedData.toCharArray()) {
            try {
                result.append(findRowByCharacter(String.valueOf(c)).getEncoding());
            } catch(Exception ex) {
                error("EC93-1: Invalid data.");
            }
        }

        result.append(findRowByCharacter("*").getEncoding());

        //termination bar
        result.append("1");

        //clear the hashtable so it no longer takes up memory
        C93_Code.clear();

        return result.toString();
    }
    private void initCode93() {
        C93_Code.clear();
        C93_Code.add(new Entry( "0",  "0", "100010100" ));
        C93_Code.add(new Entry( "1",  "1", "101001000" ));
        C93_Code.add(new Entry( "2",  "2", "101000100" ));
        C93_Code.add(new Entry( "3",  "3", "101000010" ));
        C93_Code.add(new Entry( "4",  "4", "100101000" ));
        C93_Code.add(new Entry( "5",  "5", "100100100" ));
        C93_Code.add(new Entry( "6",  "6", "100100010" ));
        C93_Code.add(new Entry( "7",  "7", "101010000" ));
        C93_Code.add(new Entry( "8",  "8", "100010010" ));
        C93_Code.add(new Entry( "9",  "9", "100001010" ));
        C93_Code.add(new Entry( "10", "A", "110101000" ));
        C93_Code.add(new Entry( "11", "B", "110100100" ));
        C93_Code.add(new Entry( "12", "C", "110100010" ));
        C93_Code.add(new Entry( "13", "D", "110010100" ));
        C93_Code.add(new Entry( "14", "E", "110010010" ));
        C93_Code.add(new Entry( "15", "F", "110001010" ));
        C93_Code.add(new Entry( "16", "G", "101101000" ));
        C93_Code.add(new Entry( "17", "H", "101100100" ));
        C93_Code.add(new Entry( "18", "I", "101100010" ));
        C93_Code.add(new Entry( "19", "J", "100110100" ));
        C93_Code.add(new Entry( "20", "K", "100011010" ));
        C93_Code.add(new Entry( "21", "L", "101011000" ));
        C93_Code.add(new Entry( "22", "M", "101001100" ));
        C93_Code.add(new Entry( "23", "N", "101000110" ));
        C93_Code.add(new Entry( "24", "O", "100101100" ));
        C93_Code.add(new Entry( "25", "P", "100010110" ));
        C93_Code.add(new Entry( "26", "Q", "110110100" ));
        C93_Code.add(new Entry( "27", "R", "110110010" ));
        C93_Code.add(new Entry( "28", "S", "110101100" ));
        C93_Code.add(new Entry( "29", "T", "110100110" ));
        C93_Code.add(new Entry( "30", "U", "110010110" ));
        C93_Code.add(new Entry( "31", "V", "110011010" ));
        C93_Code.add(new Entry( "32", "W", "101101100" ));
        C93_Code.add(new Entry( "33", "X", "101100110" ));
        C93_Code.add(new Entry( "34", "Y", "100110110" ));
        C93_Code.add(new Entry( "35", "Z", "100111010" ));
        C93_Code.add(new Entry( "36", "-", "100101110" ));
        C93_Code.add(new Entry( "37", ".", "111010100" ));
        C93_Code.add(new Entry( "38", " ", "111010010" ));
        C93_Code.add(new Entry( "39", "$", "111001010" ));
        C93_Code.add(new Entry( "40", "/", "101101110" ));
        C93_Code.add(new Entry( "41", "+", "101110110" ));
        C93_Code.add(new Entry( "42", "%", "110101110" ));
        C93_Code.add(new Entry( "43", "(", "100100110" ));//dont know what character actually goes here
        C93_Code.add(new Entry( "44", ")", "111011010" ));//dont know what character actually goes here
        C93_Code.add(new Entry( "45", "#", "111010110" ));//dont know what character actually goes here
        C93_Code.add(new Entry( "46", "@", "100110010" ));//dont know what character actually goes here
        C93_Code.add(new Entry( "-",  "*", "101011110" ));
    }

    private String addCheckDigits(String input) {
        //populate the C weights
        int[] aryCWeights = new int[input.length()];
        int curweight = 1;
        for (int i = input.length() - 1; i >= 0; i--) {
            if (curweight > 20) {
                curweight = 1;
            }
            aryCWeights[i] = curweight;
            curweight++;
        }

        //populate the K weights
        int[] aryKWeights = new int[input.length() + 1];
        curweight = 1;
        for (int i = input.length(); i >= 0; i--) {
            if (curweight > 15) {
                curweight = 1;
            }
            aryKWeights[i] = curweight;
            curweight++;
        }

        //calculate C checksum
        int sum = 0;
        for (int i = 0; i < input.length(); i++) {
            sum += aryCWeights[i] * Integer.parseInt(findRowByCharacter(String.valueOf(input.toCharArray()[i])).getValue());
        }

        int checksumValue = sum % 47;

        input += findRowByValue(String.valueOf(checksumValue)).getCharacter();

        //calculate K checksum
        sum = 0;
        for (int i = 0; i < input.length(); i++) {
            sum += aryKWeights[i] * Integer.parseInt(findRowByCharacter(String.valueOf(input.toCharArray()[i])).getValue());
        }
        checksumValue = sum % 47;

        input += findRowByValue(String.valueOf(checksumValue)).getCharacter();

        return input;
    }

    public String getEncodedValue() {
        return encodeCode93();
    }
}
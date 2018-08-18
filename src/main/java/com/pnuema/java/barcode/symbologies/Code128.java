package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

import java.util.ArrayList;
import java.util.List;

import static com.pnuema.java.barcode.utils.CharUtils.getChar;

/**
 * Code 128 encoding
 */
public class Code128 extends BarcodeCommon implements IBarcode {
    public enum TYPES { DYNAMIC, A, B, C };
    private List<Entry> C128_Code = new ArrayList<>();
    private List<String> formattedData = new ArrayList<>();
    private List<String> encodedData = new ArrayList<>();
    private Entry startCharacter = null;
    private TYPES type = TYPES.DYNAMIC;

    private static class Entry {
        private String id;
        private String A;
        private String B;
        private String C;
        private String encoding;

        Entry(String id, String a, String b, String c, String encoding) {
            this.id = id;
            A = a;
            B = b;
            C = c;
            this.encoding = encoding;
        }

        String getId() {
            return id;
        }

        String getA() {
            return A;
        }

        String getB() {
            return B;
        }

        String getC() {
            return C;
        }

        public String getEncoding() {
            return encoding;
        }
    }
    
    /**
     * Encodes data in Code128 format
     * @param input Data to encode
     */
    public Code128(String input) {
        setRawData(input);
    }

    /**
     * Encodes data in Code128 format
     * @param input Data to encode
     * @param type Type of encoding to lock to. (Code 128A, Code 128B, Code 128C)
     */
    public Code128(String input, TYPES type) {
        this.type = type;
        setRawData(input);
    }

    private String encodeCode128() {
        //initialize datastructure to hold encoding information
        initCode128();

        return getEncoding();
    }

    private void initCode128() {
        //populate data
        this.C128_Code.add(new Entry ( "0", " ", " ", "00", "11011001100" ));
        this.C128_Code.add(new Entry ( "1", "!", "!", "01", "11001101100" ));
        this.C128_Code.add(new Entry ( "2", "\"", "\"", "02", "11001100110" ));
        this.C128_Code.add(new Entry ( "3", "#", "#", "03", "10010011000" ));
        this.C128_Code.add(new Entry ( "4", "$", "$", "04", "10010001100" ));
        this.C128_Code.add(new Entry ( "5", "%", "%", "05", "10001001100" ));
        this.C128_Code.add(new Entry ( "6", "&", "&", "06", "10011001000" ));
        this.C128_Code.add(new Entry ( "7", "'", "'", "07", "10011000100" ));
        this.C128_Code.add(new Entry ( "8", "(", "(", "08", "10001100100" ));
        this.C128_Code.add(new Entry ( "9", ")", ")", "09", "11001001000" ));
        this.C128_Code.add(new Entry ( "10", "*", "*", "10", "11001000100" ));
        this.C128_Code.add(new Entry ( "11", "+", "+", "11", "11000100100" ));
        this.C128_Code.add(new Entry ( "12", ",", ",", "12", "10110011100" ));
        this.C128_Code.add(new Entry ( "13", "-", "-", "13", "10011011100" ));
        this.C128_Code.add(new Entry ( "14", ".", ".", "14", "10011001110" ));
        this.C128_Code.add(new Entry ( "15", "/", "/", "15", "10111001100" ));
        this.C128_Code.add(new Entry ( "16", "0", "0", "16", "10011101100" ));
        this.C128_Code.add(new Entry ( "17", "1", "1", "17", "10011100110" ));
        this.C128_Code.add(new Entry ( "18", "2", "2", "18", "11001110010" ));
        this.C128_Code.add(new Entry ( "19", "3", "3", "19", "11001011100" ));
        this.C128_Code.add(new Entry ( "20", "4", "4", "20", "11001001110" ));
        this.C128_Code.add(new Entry ( "21", "5", "5", "21", "11011100100" ));
        this.C128_Code.add(new Entry ( "22", "6", "6", "22", "11001110100" ));
        this.C128_Code.add(new Entry ( "23", "7", "7", "23", "11101101110" ));
        this.C128_Code.add(new Entry ( "24", "8", "8", "24", "11101001100" ));
        this.C128_Code.add(new Entry ( "25", "9", "9", "25", "11100101100" ));
        this.C128_Code.add(new Entry ( "26", ":", ":", "26", "11100100110" ));
        this.C128_Code.add(new Entry ( "27", ";", ";", "27", "11101100100" ));
        this.C128_Code.add(new Entry ( "28", "<", "<", "28", "11100110100" ));
        this.C128_Code.add(new Entry ( "29", "=", "=", "29", "11100110010" ));
        this.C128_Code.add(new Entry ( "30", ">", ">", "30", "11011011000" ));
        this.C128_Code.add(new Entry ( "31", "?", "?", "31", "11011000110" ));
        this.C128_Code.add(new Entry ( "32", "@", "@", "32", "11000110110" ));
        this.C128_Code.add(new Entry ( "33", "A", "A", "33", "10100011000" ));
        this.C128_Code.add(new Entry ( "34", "B", "B", "34", "10001011000" ));
        this.C128_Code.add(new Entry ( "35", "C", "C", "35", "10001000110" ));
        this.C128_Code.add(new Entry ( "36", "D", "D", "36", "10110001000" ));
        this.C128_Code.add(new Entry ( "37", "E", "E", "37", "10001101000" ));
        this.C128_Code.add(new Entry ( "38", "F", "F", "38", "10001100010" ));
        this.C128_Code.add(new Entry ( "39", "G", "G", "39", "11010001000" ));
        this.C128_Code.add(new Entry ( "40", "H", "H", "40", "11000101000" ));
        this.C128_Code.add(new Entry ( "41", "I", "I", "41", "11000100010" ));
        this.C128_Code.add(new Entry ( "42", "J", "J", "42", "10110111000" ));
        this.C128_Code.add(new Entry ( "43", "K", "K", "43", "10110001110" ));
        this.C128_Code.add(new Entry ( "44", "L", "L", "44", "10001101110" ));
        this.C128_Code.add(new Entry ( "45", "M", "M", "45", "10111011000" ));
        this.C128_Code.add(new Entry ( "46", "N", "N", "46", "10111000110" ));
        this.C128_Code.add(new Entry ( "47", "O", "O", "47", "10001110110" ));
        this.C128_Code.add(new Entry ( "48", "P", "P", "48", "11101110110" ));
        this.C128_Code.add(new Entry ( "49", "Q", "Q", "49", "11010001110" ));
        this.C128_Code.add(new Entry ( "50", "R", "R", "50", "11000101110" ));
        this.C128_Code.add(new Entry ( "51", "S", "S", "51", "11011101000" ));
        this.C128_Code.add(new Entry ( "52", "T", "T", "52", "11011100010" ));
        this.C128_Code.add(new Entry ( "53", "U", "U", "53", "11011101110" ));
        this.C128_Code.add(new Entry ( "54", "V", "V", "54", "11101011000" ));
        this.C128_Code.add(new Entry ( "55", "W", "W", "55", "11101000110" ));
        this.C128_Code.add(new Entry ( "56", "X", "X", "56", "11100010110" ));
        this.C128_Code.add(new Entry ( "57", "Y", "Y", "57", "11101101000" ));
        this.C128_Code.add(new Entry ( "58", "Z", "Z", "58", "11101100010" ));
        this.C128_Code.add(new Entry ( "59", "[", "[", "59", "11100011010" ));
        this.C128_Code.add(new Entry ( "60","\\", "\\", "60", "11101111010" ));
        this.C128_Code.add(new Entry ( "61", "]", "]", "61", "11001000010" ));
        this.C128_Code.add(new Entry ( "62", "^", "^", "62", "11110001010" ));
        this.C128_Code.add(new Entry ( "63", "_", "_", "63", "10100110000" ));
        this.C128_Code.add(new Entry ( "64", "\0", "`", "64", "10100001100" ));
        this.C128_Code.add(new Entry ( "65", getChar(1), "a", "65", "10010110000" ));
        this.C128_Code.add(new Entry ( "66", getChar(2), "b", "66", "10010000110" ));
        this.C128_Code.add(new Entry ( "67", getChar(3), "c", "67", "10000101100" ));
        this.C128_Code.add(new Entry ( "68", getChar(4), "d", "68", "10000100110" ));
        this.C128_Code.add(new Entry ( "69", getChar(5), "e", "69", "10110010000" ));
        this.C128_Code.add(new Entry ( "70", getChar(6), "f", "70", "10110000100" ));
        this.C128_Code.add(new Entry ( "71", getChar(7), "g", "71", "10011010000" ));
        this.C128_Code.add(new Entry ( "72", getChar(8), "h", "72", "10011000010" ));
        this.C128_Code.add(new Entry ( "73", getChar(9), "i", "73", "10000110100" ));
        this.C128_Code.add(new Entry ( "74", getChar(10), "j", "74", "10000110010" ));
        this.C128_Code.add(new Entry ( "75", getChar(11), "k", "75", "11000010010" ));
        this.C128_Code.add(new Entry ( "76", getChar(12), "l", "76", "11001010000" ));
        this.C128_Code.add(new Entry ( "77", getChar(13), "m", "77", "11110111010" ));
        this.C128_Code.add(new Entry ( "78", getChar(14), "n", "78", "11000010100" ));
        this.C128_Code.add(new Entry ( "79", getChar(15), "o", "79", "10001111010" ));
        this.C128_Code.add(new Entry ( "80", getChar(16), "p", "80", "10100111100" ));
        this.C128_Code.add(new Entry ( "81", getChar(17), "q", "81", "10010111100" ));
        this.C128_Code.add(new Entry ( "82", getChar(18), "r", "82", "10010011110" ));
        this.C128_Code.add(new Entry ( "83", getChar(19), "s", "83", "10111100100" ));
        this.C128_Code.add(new Entry ( "84", getChar(20), "t", "84", "10011110100" ));
        this.C128_Code.add(new Entry ( "85", getChar(21), "u", "85", "10011110010" ));
        this.C128_Code.add(new Entry ( "86", getChar(22), "v", "86", "11110100100" ));
        this.C128_Code.add(new Entry ( "87", getChar(23), "w", "87", "11110010100" ));
        this.C128_Code.add(new Entry ( "88", getChar(24), "x", "88", "11110010010" ));
        this.C128_Code.add(new Entry ( "89", getChar(25), "y", "89", "11011011110" ));
        this.C128_Code.add(new Entry ( "90", getChar(26), "z", "90", "11011110110" ));
        this.C128_Code.add(new Entry ( "91", getChar(27), "{", "91", "11110110110" ));
        this.C128_Code.add(new Entry ( "92", getChar(28), "|", "92", "10101111000" ));
        this.C128_Code.add(new Entry ( "93", getChar(29), "}", "93", "10100011110" ));
        this.C128_Code.add(new Entry ( "94", getChar(30), "~", "94", "10001011110" ));

        this.C128_Code.add(new Entry ( "95", getChar(31), getChar(127), "95", "10111101000" ));
        this.C128_Code.add(new Entry ( "96", getChar(202)/*FNC3*/, getChar(202)/*FNC3*/, "96", "10111100010" ));
        this.C128_Code.add(new Entry ( "97", getChar(201)/*FNC2*/, getChar(201)/*FNC2*/, "97", "11110101000" ));
        this.C128_Code.add(new Entry ( "98", "SHIFT", "SHIFT", "98", "11110100010" ));
        this.C128_Code.add(new Entry ( "99", "CODE_C", "CODE_C", "99", "10111011110" ));
        this.C128_Code.add(new Entry ( "100", "CODE_B", getChar(203)/*FNC4*/, "CODE_B", "10111101110" ));
        this.C128_Code.add(new Entry ( "101", getChar(203)/*FNC4*/, "CODE_A", "CODE_A", "11101011110" ));
        this.C128_Code.add(new Entry ( "102", getChar(200)/*FNC1*/, getChar(200)/*FNC1*/, getChar(200)/*FNC1*/, "11110101110" ));
        this.C128_Code.add(new Entry ( "103", "START_A", "START_A", "START_A", "11010000100" ));
        this.C128_Code.add(new Entry ( "104", "START_B", "START_B", "START_B", "11010010000" ));
        this.C128_Code.add(new Entry ( "105", "START_C", "START_C", "START_C", "11010011100" ));
        this.C128_Code.add(new Entry ( "", "STOP", "STOP", "STOP", "11000111010" ));
     }

    private Entry findRow(String column, String match) {
        for (Entry entry : C128_Code) {
            if (column.equalsIgnoreCase("A") && entry.getA().equals(match)) {
                return entry;
            } else if (column.equalsIgnoreCase("B") && entry.getB().equals(match)) {
                return entry;
            } else if (column.equalsIgnoreCase("C") && entry.getC().equals(match)) {
                return entry;
            } else if (column.equalsIgnoreCase("encoding") && entry.encoding.equals(match)) {
                return entry;
            } else if (column.equalsIgnoreCase("id") && entry.getId().equals(match)) {
                return entry;
            }
        }

        return null;
    }
    private List<Entry> findStartorCodeCharacter(String s) {
        List<Entry> rows = new ArrayList<>();

        //if two chars are numbers (or FNC1) then START_C or CODE_C
        if (s.length() > 1 && (Character.isDigit(s.toCharArray()[0]) || String.valueOf(s.toCharArray()[0]).equals(getChar(200)) && (Character.isDigit(s.toCharArray()[1]) || String.valueOf(s.toCharArray()[1]).equals(getChar(200))))) {
            if (startCharacter == null) {
                startCharacter = findRow("A", "START_C");
                rows.add(startCharacter);
            } else {
                rows.add(findRow("A", "CODE_C"));
            }
        } else {
            boolean AFound = false;
            boolean BFound = false;
            for (Entry row : this.C128_Code) {
                try {
                    if (!AFound && s.equals(row.A)) {
                        AFound = true;

                        if (startCharacter == null) {
                            startCharacter = findRow("A",  "START_A");
                            rows.add(startCharacter);
                        } else {
                            rows.add(findRow("B", "CODE_A"));//first column is FNC4 so use B
                        }
                    } else if (!BFound && s.equals(row.B)) {
                        BFound = true;

                        if (startCharacter == null) {
                            startCharacter = findRow("A", "START_B");
                            rows.add(startCharacter);
                        } else {
                            rows.add(findRow("A", "CODE_B"));
                        }
                    } else if (AFound && BFound) {
                        break;
                    }
                } catch (Exception ex) {
                    error("EC128-1: " + ex.getMessage());
                }
            }

            if (rows.isEmpty()) {
                error("EC128-2: Could not determine start character.");
            }
        }

        return rows;
    }

    private String CalculateCheckDigit() {
        int checkSum = 0;

        for (int i = 0; i < formattedData.size(); i++) {
            //replace apostrophes with double apostrophes for escape chars
            String s = formattedData.get(i).replace("'", "''");

            //try to find value in the A column
            Entry row = findRow("A",  s);

            //try to find value in the B column
            if (row == null) {
                row = findRow("B", s);
            }

            //try to find value in the C column
            if (row == null) {
                row = findRow("C", s);
            }

            if (row == null) {
                error("EC128-3: No value found in encoding table");
                return null;
            }

            int value = Integer.parseInt(row.getId());
            int addition = value * ((i == 0) ? 1 : i);
            checkSum +=  addition;
        }

        int Remainder = (checkSum % 103);
        Entry retRows = findRow("id", String.valueOf(Remainder));
        if (retRows != null) {
            return retRows.encoding;
        } else {
            return "";
        }
    }
        
    private void breakUpDataForEncoding() {
        StringBuilder temp = new StringBuilder();
        String tempRawData = getRawData();

        //breaking the raw data up for code A and code B will mess up the encoding
        if (this.type == TYPES.A || this.type == TYPES.B) {
            for (char c : getRawData().toCharArray()){
                formattedData.add(String.valueOf(c));
            }
            return;
        }

        if (this.type == TYPES.C) {
            if (!checkNumericOnly(getRawData())) {
                error("EC128-6: Only numeric values can be encoded with C128-C.");
            }

            //CODE C: adds a 0 to the front of the Raw_Data if the length is not divisible by 2
            if (getRawData().length() % 2 > 0) {
                tempRawData = "0" + getRawData();
            }
        }

        for (char c : tempRawData.toCharArray()) {
            if (checkNumericOnly(String.valueOf(c))) {
                if (temp.length() == 0) {
                    temp.append(c);
                } else {
                    temp.append(c);
                    formattedData.add(temp.toString());
                    temp = new StringBuilder();
                }
            } else {
                if (temp.length() > 0) {
                    formattedData.add(temp.toString());
                    temp = new StringBuilder();
                }
                formattedData.add(String.valueOf(c));
            }
        }

        //if something is still in temp go ahead and push it onto the queue
        if (temp.length() > 0) {
            formattedData.add(temp.toString());
        }
    }

    private void insertStartandCodeCharacters() {
        Entry currentCodeSet;
        String currentCodeString;

        if (this.type != TYPES.DYNAMIC) {
            switch (this.type) {
                case A:
                    formattedData.add(0, "START_A");
                    break;
                case B:
                    formattedData.add(0, "START_B");
                    break;
                case C:
                    formattedData.add(0, "START_C");
                    break;
                default:
                    error("EC128-4: Unknown start type in fixed type encoding.");
                    break;
            }
        } else {
            try {
                List<Entry> tempStartChars = findStartorCodeCharacter(formattedData.get(0));

                //initial char with start char
                currentCodeSet = tempStartChars.get(0);
                currentCodeString = currentCodeSet.getA().substring(currentCodeSet.getA().length() - 1);
                formattedData.add(0, currentCodeSet.getA());

                for (int i = 0; i < formattedData.size(); i++) {
                    tempStartChars = findStartorCodeCharacter(formattedData.get(i));

                    //check all the start characters and see if we need to stay with the same codeset or if a change of sets is required
                    boolean sameCodeSet = false;
                    for (Entry row : tempStartChars) {
                        if (row.getA().endsWith(currentCodeString) || row.getB().endsWith(currentCodeString) || row.getC().endsWith(currentCodeString)) {
                            sameCodeSet = true;
                            break;
                        }
                    }

                    //change curent code string if not the same as the current code set
                    if (!sameCodeSet) {
                        currentCodeSet = tempStartChars.get(0);

                        switch (currentCodeString) {
                            case "A":
                                currentCodeString = currentCodeSet.getB().substring(currentCodeSet.getB().length() - 1);
                                formattedData.add(i++, currentCodeSet.getA());
                                break;
                            case "B":
                                currentCodeString = currentCodeSet.getC().substring(currentCodeSet.getC().length() - 1);
                                formattedData.add(i++, currentCodeSet.getB());
                                break;
                            case "C":
                                currentCodeString = currentCodeSet.getA().substring(currentCodeSet.getA().length() - 1);
                                formattedData.add(i++, currentCodeSet.getC());
                                break;
                        }
                    }
                }
            } catch (Exception ex) {
                error("EC128-3: Could not insert start and code characters.\n Message: " + ex.getMessage());
            }
        }
    }
    private String getEncoding() {
        //break up data for encoding
        breakUpDataForEncoding();

        //insert the start characters
        insertStartandCodeCharacters();

        StringBuilder encodedData = new StringBuilder();
        for (String s : formattedData) {
            //handle exception with apostrophes in select statements
            String s1 = s.replace("'", "''");
            Entry E_Row;

            //select encoding only for type selected
            switch (this.type) {
                case A:
                    E_Row = findRow("A",  s1);
                    break;
                case B:
                    E_Row = findRow("B",  s1);
                    break;
                case C:
                    E_Row = findRow("C",  s1);
                    break;
                case DYNAMIC:
                    E_Row = findRow("A",  s1);
                    if (E_Row == null) {
                        E_Row = findRow("B", s1);

                        if (E_Row == null) {
                            E_Row = findRow("C", s1);
                        }
                    }
                    break;
                default:
                    E_Row = null;
                    break;
            }

            if (E_Row == null) {
                error("EC128-5: Could not find encoding of a value( " + s1 + " ) in C128 type " + this.type);
                return null;
            }

            encodedData.append(E_Row.encoding);
            this.encodedData.add(E_Row.encoding);
        }

        //add the check digit
        encodedData.append(CalculateCheckDigit());
        this.encodedData.add(CalculateCheckDigit());

        //add the stop character
        Entry stopChar = findRow("A", "STOP");
        if (stopChar != null) {
            encodedData.append(stopChar.encoding);
            this.encodedData.add(stopChar.encoding);
        }

        //add the termination bars
        encodedData.append("11");
        this.encodedData.add("11");

        return encodedData.toString();
    }

    @Override
    public String getEncodedValue() {
        return encodeCode128();
    }
}

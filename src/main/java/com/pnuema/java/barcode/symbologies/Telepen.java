package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

import java.util.HashMap;

/**
 * Telepen encoding
 */
public class Telepen extends BarcodeCommon implements IBarcode {
    private static HashMap<Character, String> Telepen_Code = new HashMap<>();

    public enum StartStopCode {
        START1(Character.forDigit(0, 10)),
        STOP1(Character.forDigit(1, 10)),
        START2(Character.forDigit(2, 10)),
        STOP2(Character.forDigit(3, 10)),
        START3(Character.forDigit(4, 10)),
        STOP3(Character.forDigit(5, 10)),;

        private char asChar() {
            return asChar;
        }

        private final char asChar;

        StartStopCode(char asChar) {
            this.asChar = asChar;
        }
    }

    private StartStopCode startCode = StartStopCode.START1;
    private StartStopCode stopCode = StartStopCode.STOP1;
    private int switchModeIndex = 0;
    private int iCheckSum = 0;

    /**
     * Encodes data using the Telepen algorithm.
     *
     * @param input Raw data to encode
     */
    public Telepen(String input) {
        setRawData(input);
    }

    /**
     * Encode the raw data using the Telepen algorithm.
     * @return Encoded value
     */
    private String encodeTelepen() {
        //only init if needed
        if (Telepen_Code.isEmpty()) {
            initTelepen();
        }

        iCheckSum = 0;
        String result;

        setEncodingSequence();

        //include the Start sequence pattern
        result = Telepen_Code.get(startCode.asChar());

        switch (startCode) {
            //numeric --> ascii
            case START2:
                encodeNumeric(getRawData().substring(0, switchModeIndex), result);

                if (switchModeIndex < getRawData().length()) {
                    encodeSwitchMode(result);
                    encodeASCII(getRawData().substring(switchModeIndex), result);
                }//if
                break;
            //ascii --> numeric
            case START3:
                encodeASCII(getRawData().substring(0, switchModeIndex), result);
                encodeSwitchMode(result);
                encodeNumeric(getRawData().substring(switchModeIndex), result);
                break;
            //full ascii
            default:
                encodeASCII(getRawData(), result);
                break;
        }

        //checksum
        result += Telepen_Code.get(Calculate_Checksum(iCheckSum));

        //stop character
        result += Telepen_Code.get(stopCode.asChar());

        return result;
    }

    @SuppressWarnings("StringConcatenationInLoop")
    private void encodeASCII(String input, String output) {
        try {
            for (char c : input.toCharArray()) {
                output += (Telepen_Code.get(c));
                iCheckSum += c;
            }
        } catch (Exception ex) {
            error("ETELEPEN-1: Invalid data when encoding ASCII");
        }
    }

    @SuppressWarnings("StringConcatenationInLoop")
    private void encodeNumeric(String input, String output) {
        try {
            if (input.length() % 2 > 0) {
                error("ETELEPEN-3: Numeric encoding attempted on odd number of characters");
            }
            for (int i = 0; i < input.length(); i += 2) {
                output += (Telepen_Code.get(Character.forDigit(Integer.parseInt(input.substring(i, i + 2)) + 27, 10)));
                iCheckSum += Integer.parseInt(input.substring(i, i + 2)) + 27;
            }
        } catch (Exception ex) {
            error("ETELEPEN-2: Numeric encoding failed");
        }
    }

    private void encodeSwitchMode(String output) {
        //ASCII code DLE is used to switch modes
        iCheckSum += 16;
        //noinspection UnusedAssignment
        output += Telepen_Code.get(Character.forDigit(16, 10));
    }

    private char Calculate_Checksum(int iCheckSum) {
        return Character.forDigit(127 - (iCheckSum % 127), 10);
    }

    private void setEncodingSequence() {
        //reset to full ascii
        startCode = StartStopCode.START1;
        stopCode = StartStopCode.STOP1;
        switchModeIndex = getRawData().length();

        //starting number of 'numbers'
        int StartNumerics = 0;
        for (char c : getRawData().toCharArray()) {
            if (Character.isDigit(c)) {
                StartNumerics++;
            } else {
                break;
            }
        }

        if (StartNumerics == getRawData().length()) {
            //Numeric only mode due to only numbers being present
            startCode = StartStopCode.START2;
            stopCode = StartStopCode.STOP2;

            if ((getRawData().length() % 2) > 0)
                switchModeIndex = getRawData().length() - 1;
        }//if
        else {
            //ending number of numbers
            int EndNumerics = 0;
            for (int i = getRawData().length() - 1; i >= 0; i--) {
                if (Character.isDigit(getRawData().toCharArray()[i])) {
                    EndNumerics++;
                } else {
                    break;
                }
            }

            if (StartNumerics >= 4 || EndNumerics >= 4) {
                //hybrid mode will be used
                if (StartNumerics > EndNumerics) {
                    //start in numeric switching to ascii
                    startCode = StartStopCode.START2;
                    stopCode = StartStopCode.STOP2;
                    switchModeIndex = (StartNumerics % 2) == 1 ? StartNumerics - 1 : StartNumerics;
                } else {
                    //start in ascii switching to numeric
                    startCode = StartStopCode.START3;
                    stopCode = StartStopCode.STOP3;
                    switchModeIndex = (EndNumerics % 2) == 1 ? getRawData().length() - EndNumerics + 1 : getRawData().length() - EndNumerics;
                }
            }
        }
    }

    private void initTelepen() {
        Telepen_Code.put(Character.forDigit(0, 10), "1110111011101110");
        Telepen_Code.put(Character.forDigit(1, 10), "1011101110111010");
        Telepen_Code.put(Character.forDigit(2, 10), "1110001110111010");
        Telepen_Code.put(Character.forDigit(3, 10), "1010111011101110");
        Telepen_Code.put(Character.forDigit(4, 10), "1110101110111010");
        Telepen_Code.put(Character.forDigit(5, 10), "1011100011101110");
        Telepen_Code.put(Character.forDigit(6, 10), "1000100011101110");
        Telepen_Code.put(Character.forDigit(7, 10), "1010101110111010");
        Telepen_Code.put(Character.forDigit(8, 10), "1110111000111010");
        Telepen_Code.put(Character.forDigit(9, 10), "1011101011101110");
        Telepen_Code.put(Character.forDigit(10, 10), "1110001011101110");
        Telepen_Code.put(Character.forDigit(11, 10), "1010111000111010");
        Telepen_Code.put(Character.forDigit(12, 10), "1110101011101110");
        Telepen_Code.put(Character.forDigit(13, 10), "1010001000111010");
        Telepen_Code.put(Character.forDigit(14, 10), "1000101000111010");
        Telepen_Code.put(Character.forDigit(15, 10), "1010101011101110");
        Telepen_Code.put(Character.forDigit(16, 10), "1110111010111010");
        Telepen_Code.put(Character.forDigit(17, 10), "1011101110001110");
        Telepen_Code.put(Character.forDigit(18, 10), "1110001110001110");
        Telepen_Code.put(Character.forDigit(19, 10), "1010111010111010");
        Telepen_Code.put(Character.forDigit(20, 10), "1110101110001110");
        Telepen_Code.put(Character.forDigit(21, 10), "1011100010111010");
        Telepen_Code.put(Character.forDigit(22, 10), "1000100010111010");
        Telepen_Code.put(Character.forDigit(23, 10), "1010101110001110");
        Telepen_Code.put(Character.forDigit(24, 10), "1110100010001110");
        Telepen_Code.put(Character.forDigit(25, 10), "1011101010111010");
        Telepen_Code.put(Character.forDigit(26, 10), "1110001010111010");
        Telepen_Code.put(Character.forDigit(27, 10), "1010100010001110");
        Telepen_Code.put(Character.forDigit(28, 10), "1110101010111010");
        Telepen_Code.put(Character.forDigit(29, 10), "1010001010001110");
        Telepen_Code.put(Character.forDigit(30, 10), "1000101010001110");
        Telepen_Code.put(Character.forDigit(31, 10), "1010101010111010");
        Telepen_Code.put(' ', "1110111011100010");
        Telepen_Code.put('!', "1011101110101110");
        Telepen_Code.put('"', "1110001110101110");
        Telepen_Code.put('#', "1010111011100010");
        Telepen_Code.put('$', "1110101110101110");
        Telepen_Code.put('%', "1011100011100010");
        Telepen_Code.put('&', "1000100011100010");
        Telepen_Code.put('\'', "1010101110101110");
        Telepen_Code.put('(', "1110111000101110");
        Telepen_Code.put(')', "1011101011100010");
        Telepen_Code.put('*', "1110001011100010");
        Telepen_Code.put('+', "1010111000101110");
        Telepen_Code.put(',', "1110101011100010");
        Telepen_Code.put('-', "1010001000101110");
        Telepen_Code.put('.', "1000101000101110");
        Telepen_Code.put('/', "1010101011100010");
        Telepen_Code.put('0', "1110111010101110");
        Telepen_Code.put('1', "1011101000100010");
        Telepen_Code.put('2', "1110001000100010");
        Telepen_Code.put('3', "1010111010101110");
        Telepen_Code.put('4', "1110101000100010");
        Telepen_Code.put('5', "1011100010101110");
        Telepen_Code.put('6', "1000100010101110");
        Telepen_Code.put('7', "1010101000100010");
        Telepen_Code.put('8', "1110100010100010");
        Telepen_Code.put('9', "1011101010101110");
        Telepen_Code.put(':', "1110001010101110");
        Telepen_Code.put(';', "1010100010100010");
        Telepen_Code.put('<', "1110101010101110");
        Telepen_Code.put('=', "1010001010100010");
        Telepen_Code.put('>', "1000101010100010");
        Telepen_Code.put('?', "1010101010101110");
        Telepen_Code.put('@', "1110111011101010");
        Telepen_Code.put('A', "1011101110111000");
        Telepen_Code.put('B', "1110001110111000");
        Telepen_Code.put('C', "1010111011101010");
        Telepen_Code.put('D', "1110101110111000");
        Telepen_Code.put('E', "1011100011101010");
        Telepen_Code.put('F', "1000100011101010");
        Telepen_Code.put('G', "1010101110111000");
        Telepen_Code.put('H', "1110111000111000");
        Telepen_Code.put('I', "1011101011101010");
        Telepen_Code.put('J', "1110001011101010");
        Telepen_Code.put('K', "1010111000111000");
        Telepen_Code.put('L', "1110101011101010");
        Telepen_Code.put('M', "1010001000111000");
        Telepen_Code.put('N', "1000101000111000");
        Telepen_Code.put('O', "1010101011101010");
        Telepen_Code.put('P', "1110111010111000");
        Telepen_Code.put('Q', "1011101110001010");
        Telepen_Code.put('R', "1110001110001010");
        Telepen_Code.put('S', "1010111010111000");
        Telepen_Code.put('T', "1110101110001010");
        Telepen_Code.put('U', "1011100010111000");
        Telepen_Code.put('V', "1000100010111000");
        Telepen_Code.put('W', "1010101110001010");
        Telepen_Code.put('X', "1110100010001010");
        Telepen_Code.put('Y', "1011101010111000");
        Telepen_Code.put('Z', "1110001010111000");
        Telepen_Code.put('[', "1010100010001010");
        Telepen_Code.put('\\', "1110101010111000");
        Telepen_Code.put(']', "1010001010001010");
        Telepen_Code.put('^', "1000101010001010");
        Telepen_Code.put('_', "1010101010111000");
        Telepen_Code.put('`', "1110111010001000");
        Telepen_Code.put('a', "1011101110101010");
        Telepen_Code.put('b', "1110001110101010");
        Telepen_Code.put('c', "1010111010001000");
        Telepen_Code.put('d', "1110101110101010");
        Telepen_Code.put('e', "1011100010001000");
        Telepen_Code.put('f', "1000100010001000");
        Telepen_Code.put('g', "1010101110101010");
        Telepen_Code.put('h', "1110111000101010");
        Telepen_Code.put('i', "1011101010001000");
        Telepen_Code.put('j', "1110001010001000");
        Telepen_Code.put('k', "1010111000101010");
        Telepen_Code.put('l', "1110101010001000");
        Telepen_Code.put('m', "1010001000101010");
        Telepen_Code.put('n', "1000101000101010");
        Telepen_Code.put('o', "1010101010001000");
        Telepen_Code.put('p', "1110111010101010");
        Telepen_Code.put('q', "1011101000101000");
        Telepen_Code.put('r', "1110001000101000");
        Telepen_Code.put('s', "1010111010101010");
        Telepen_Code.put('t', "1110101000101000");
        Telepen_Code.put('u', "1011100010101010");
        Telepen_Code.put('v', "1000100010101010");
        Telepen_Code.put('w', "1010101000101000");
        Telepen_Code.put('x', "1110100010101000");
        Telepen_Code.put('y', "1011101010101010");
        Telepen_Code.put('z', "1110001010101010");
        Telepen_Code.put('{', "1010100010101000");
        Telepen_Code.put('|', "1110101010101010");
        Telepen_Code.put('}', "1010001010101000");
        Telepen_Code.put('~', "1000101010101000");
        Telepen_Code.put(Character.forDigit(127, 10), "1010101010101010");
        Telepen_Code.put(StartStopCode.START1.asChar(), "1010101010111000");
        Telepen_Code.put(StartStopCode.STOP1.asChar(), "1110001010101010");
        Telepen_Code.put(StartStopCode.START2.asChar(), "1010101011101000");
        Telepen_Code.put(StartStopCode.STOP2.asChar(), "1110100010101010");
        Telepen_Code.put(StartStopCode.START3.asChar(), "1010101110101000");
        Telepen_Code.put(StartStopCode.STOP3.asChar(), "1110101000101010");
    }

    public String getEncodedValue() {
        return encodeTelepen();
    }
}

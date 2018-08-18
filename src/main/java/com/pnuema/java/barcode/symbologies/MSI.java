package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.Barcode;
import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.IBarcode;

/**
 * MSI encoding
 */
@SuppressWarnings("StringConcatenationInLoop")
public class MSI extends BarcodeCommon implements IBarcode {
    private String[] MSI_Code = {"100100100100", "100100100110", "100100110100", "100100110110", "100110100100", "100110100110", "100110110100", "100110110110", "110100100100", "110100100110"};
    private Barcode.TYPE encodedType;

    public MSI(String input, Barcode.TYPE EncodedType) {
        encodedType = EncodedType;
        setRawData(input);
    }

    /**
     * Encode the raw data using the MSI algorithm.
     *
     * @return Encoded value
     */
    private String encodeMSI() {
        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EMSI-1: Numeric Data Only");
        }

        String PreEncoded = getRawData();

        //get checksum
        if (encodedType == Barcode.TYPE.MSI_Mod10 || encodedType == Barcode.TYPE.MSI_2Mod10) {
            String odds = "";
            String evens = "";
            for (int i = PreEncoded.length() - 1; i >= 0; i -= 2) {
                odds = String.valueOf(PreEncoded.toCharArray()[i]) + odds;
                if (i - 1 >= 0)
                    evens = String.valueOf(PreEncoded.toCharArray()[i - 1]) + evens;
            }

            //multiply odds by 2
            odds = String.valueOf(Integer.parseInt(odds) * 2);

            int evensum = 0;
            int oddsum = 0;
            for (Character c : evens.toCharArray()) {
                evensum += Integer.parseInt(c.toString());
            }
            for (Character c : odds.toCharArray()) {
                oddsum += Integer.parseInt(c.toString());
            }
            int mod = (oddsum + evensum) % 10;
            int checksum = mod == 0 ? 0 : 10 - mod;
            PreEncoded += String.valueOf(checksum);
        }

        if (encodedType == Barcode.TYPE.MSI_Mod11 || encodedType == Barcode.TYPE.MSI_Mod11_Mod10) {
            int sum = 0;
            int weight = 2;
            for (int i = PreEncoded.length() - 1; i >= 0; i--) {
                if (weight > 7) {
                    weight = 2;
                }
                sum += Integer.parseInt(String.valueOf(PreEncoded.toCharArray()[i])) * weight++;
            }
            int mod = sum % 11;
            int checksum = mod == 0 ? 0 : 11 - mod;

            PreEncoded += String.valueOf(checksum);
        }

        if (encodedType == Barcode.TYPE.MSI_2Mod10 || encodedType == Barcode.TYPE.MSI_Mod11_Mod10) {
            //get second check digit if 2 mod 10 was selected or Mod11/Mod10
            String odds = "";
            String evens = "";
            for (int i = PreEncoded.length() - 1; i >= 0; i -= 2) {
                odds = String.valueOf(PreEncoded.toCharArray()[i]) + odds;
                if (i - 1 >= 0) {
                    evens = String.valueOf(PreEncoded.toCharArray()[i - 1]) + evens;
                }
            }

            //multiply odds by 2
            odds = String.valueOf(Integer.parseInt(odds) * 2);

            int evensum = 0;
            int oddsum = 0;
            for (Character c : evens.toCharArray()) {
                evensum += Integer.parseInt(c.toString());
            }
            for (Character c : odds.toCharArray()) {
                oddsum += Integer.parseInt(c.toString());
            }
            int checksum = 10 - ((oddsum + evensum) % 10);
            PreEncoded += String.valueOf(checksum);
        }

        String result = "110";
        for (Character c : PreEncoded.toCharArray()) {
            result += MSI_Code[Integer.parseInt(c.toString())];
        }

        //add stop character
        result += "1001";

        return result;
    }

    public String getEncodedValue() {
        return encodeMSI();
    }
}

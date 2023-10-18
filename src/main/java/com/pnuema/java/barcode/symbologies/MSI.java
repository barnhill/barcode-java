package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;
import com.pnuema.java.barcode.EncodingType;

/**
 * MSI encoding
 */
@SuppressWarnings("StringConcatenationInLoop")
public class MSI extends BarcodeCommon {
    private final String[] MSI_Code = {"100100100100", "100100100110", "100100110100", "100100110110", "100110100100", "100110100110", "100110110100", "100110110110", "110100100100", "110100100110"};
    private final EncodingType encodedType;

    public MSI(String input, EncodingType EncodedType) {
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

        //Add Checksum
        String withChecksum = "";

        if (encodedType == EncodingType.MSI_Mod10) {
            withChecksum = Mod10(getRawData());
        } else if (encodedType == EncodingType.MSI_Mod11) {
            withChecksum = Mod11(getRawData());
        } else if (encodedType == EncodingType.MSI_2Mod10) {
            withChecksum = Mod10(Mod10(getRawData()));
        } else if (encodedType == EncodingType.MSI_Mod11_Mod10) {
            withChecksum = Mod10(Mod11(getRawData()));
        }

        if (withChecksum.isEmpty())
            error("EMSI-2: Invalid MSI encoding type");

        //add start character
        String result = "110";

        //add encoding
        for (Character c : withChecksum.toCharArray()) {
            result += MSI_Code[Integer.parseInt(c.toString())];
        }

        //add stop character
        result += "1001";

        return result;
    }

    private String Mod10(String code)
    {
        var odds = "";
        var evens = "";
        var chars = code.toCharArray();
        for (var i = code.length() - 1; i >= 0; i -= 2)
        {
            odds = chars[i] + odds;
            if (i - 1 >= 0)
                evens = chars[i - 1] + evens;
        }//for

        //multiply odds by 2
        odds = String.valueOf(Integer.parseInt(odds) * 2);

        var evensum = 0;
        var oddsum = 0;
        for (Character c : evens.toCharArray())
            evensum += Integer.parseInt(c.toString());
        for (Character c : odds.toCharArray())
            oddsum += Integer.parseInt(c.toString());
        var mod = (oddsum + evensum) % 10;
        var checksum = mod == 0 ? 0 : 10 - mod;
        return code + checksum;
    }

    private String Mod11(String code)
    {
        var sum = 0;
        var weight = 2;
        var chars = code.toCharArray();
        for (var i = code.length() - 1; i >= 0; i--)
        {
            if (weight > 7) weight = 2;
            sum += Integer.parseInt(String.valueOf(chars[i])) * weight++;
        }//foreach
        var mod = sum % 11;
        var checksum = mod == 0 ? 0 : 11 - mod;

        return code + checksum;
    }

    public String getEncodedValue() {
        return encodeMSI();
    }
}

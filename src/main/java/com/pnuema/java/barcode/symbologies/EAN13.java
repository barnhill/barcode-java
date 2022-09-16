package com.pnuema.java.barcode.symbologies;

import com.pnuema.java.barcode.BarcodeCommon;

import java.util.TreeMap;

/**
 * EAN-13 encoding
 */
public class EAN13 extends BarcodeCommon {
    private final String[] EAN_CodeA = { "0001101", "0011001", "0010011", "0111101", "0100011", "0110001", "0101111", "0111011", "0110111", "0001011" };
    private final String[] EAN_CodeB = { "0100111", "0110011", "0011011", "0100001", "0011101", "0111001", "0000101", "0010001", "0001001", "0010111" };
    private final String[] EAN_CodeC = { "1110010", "1100110", "1101100", "1000010", "1011100", "1001110", "1010000", "1000100", "1001000", "1110100" };
    private final String[] EAN_Pattern = { "aaaaaa", "aababb", "aabbab", "aabbba", "abaabb", "abbaab", "abbbaa", "ababab", "ababba", "abbaba" };
    private final TreeMap<Integer, String> countryCodes = new TreeMap<>(); //is initialized by initCountryCodes()
    private String assigningCountry = "";
    private boolean disableCountryCodeParsing = false;

    public EAN13(String input) {
        setRawData(input);
        calculateCheckDigit();
    }

    public EAN13(String input, boolean disableCountryCodeParsing) {
        this(input);
        this.disableCountryCodeParsing = disableCountryCodeParsing;
    }

    public String getAssigningCountry() {
        return assigningCountry;
    }

    /**
     * Encode the raw data using the EAN-13 algorithm. (Can include the checksum already.  If it doesnt exist in the data then it will calculate it for you.  Accepted data lengths are 12 + 1 checksum or just the 12 data digits)
     * @return Encoded value
     */
    private String encodeEAN13()
        {
        //check length of input
        if (getRawData().length() < 12 || getRawData().length() > 13) {
            error("EEAN13-1: Data length invalid. (Length must be 12 or 13)");
        }

        //check numeric only
        if (!checkNumericOnly(getRawData())) {
            error("EEAN13-2: Numeric Data Only");
        }

        String patterncode = EAN_Pattern[Integer.parseInt(String.valueOf(getRawData().toCharArray()[0]))];
        StringBuilder result = new StringBuilder("101");

        //second
        int pos = 0;
        while (pos < 6) {
            if (patterncode.toCharArray()[pos] == 'a') {
                result.append(EAN_CodeA[Integer.parseInt(String.valueOf(getRawData().toCharArray()[pos + 1]))]);
            } else if (patterncode.toCharArray()[pos] == 'b') {
                result.append(EAN_CodeB[Integer.parseInt(String.valueOf(getRawData().toCharArray()[pos + 1]))]);
            }
            pos++;
        }

        //add divider bars
        result.append("01010");

        //get the third
        pos = 1;
        while (pos <= 5) {
            result.append(EAN_CodeC[Integer.parseInt(String.valueOf(getRawData().toCharArray()[(pos++) + 6]))]);
        }

        //checksum digit
        int cs = Integer.parseInt(String.valueOf(getRawData().toCharArray()[getRawData().length() - 1]));

        //add checksum
        result.append(EAN_CodeC[cs]);

        //add ending bars
        result.append("101");

        if (!disableCountryCodeParsing) {
            assigningCountry = parseCountryCode();
        }

        return result.toString();
    }

    private String parseCountryCode() {
        //get the manufacturer assigning country
        initCountryCodes();
        String countryAssigningManufacturerCode = "N/A";
        int twodigitCode = Integer.parseInt(getRawData().substring(0, 2));
        int threedigitCode = Integer.parseInt(getRawData().substring(0, 3));
        try {
            countryAssigningManufacturerCode = countryCodes.get(threedigitCode);
        } catch (Exception ex) {
            try {
                countryAssigningManufacturerCode = countryCodes.get(twodigitCode);
            } catch (Exception exInner) {
                error("EEAN13-3: Country assigning manufacturer code not found.");
            }
        } finally {
            countryCodes.clear();
        }

        return countryAssigningManufacturerCode;
    }

    private void createCountryCodeRange(int startingNumber, int endingNumber, String countryDescription) {
        for (int i = startingNumber; i <= endingNumber; i++) {
            countryCodes.put(i, countryDescription);
        }
    }

    private void initCountryCodes() {
        countryCodes.clear();

        // Source: https://en.wikipedia.org/wiki/List_of_GS1_country_codes
        createCountryCodeRange(0, 19, "US / CANADA");
        createCountryCodeRange(20, 29, "IN STORE");
        createCountryCodeRange(30, 39, "US DRUGS");
        createCountryCodeRange(40, 49, "Used to issue restricted circulation numbers within a geographic region (MO defined)");
        createCountryCodeRange(50, 59, "GS1 US reserved for future use");
        createCountryCodeRange(60, 99, "US / CANADA");
        createCountryCodeRange(100, 139, "UNITED STATES");
        createCountryCodeRange(200, 299, "Used to issue GS1 restricted circulation number within a geographic region (MO defined)");
        createCountryCodeRange(300, 379, "FRANCE AND MONACO");

        createCountryCodeRange(380, 380, "BULGARIA");
        createCountryCodeRange(383, 383, "SLOVENIA");
        createCountryCodeRange(385, 385, "CROATIA");
        createCountryCodeRange(387, 387, "BOSNIA AND HERZEGOVINA");
        createCountryCodeRange(389, 389, "MONTENEGRO");
        createCountryCodeRange(400, 440, "GERMANY");
        createCountryCodeRange(450, 459, "JAPAN");
        createCountryCodeRange(460, 469, "RUSSIA");
        createCountryCodeRange(470, 470, "KYRGYZSTAN");
        createCountryCodeRange(471, 471, "TAIWAN");
        createCountryCodeRange(474, 474, "ESTONIA");
        createCountryCodeRange(475, 475, "LATVIA");
        createCountryCodeRange(476, 476, "AZERBAIJAN");
        createCountryCodeRange(477, 477, "LITHUANIA");
        createCountryCodeRange(478, 478, "UZBEKISTAN");
        createCountryCodeRange(479, 479, "SRI LANKA");
        createCountryCodeRange(480, 480, "PHILIPPINES");
        createCountryCodeRange(481, 481, "BELARUS");
        createCountryCodeRange(482, 482, "UKRAINE");
        createCountryCodeRange(483, 483, "TURKMENISTAN");
        createCountryCodeRange(484, 484, "MOLDOVA");
        createCountryCodeRange(485, 485, "ARMENIA");
        createCountryCodeRange(486, 486, "GEORGIA");
        createCountryCodeRange(487, 487, "KAZAKHSTAN");
        createCountryCodeRange(488, 488, "TAJIKISTAN");
        createCountryCodeRange(489, 489, "HONG KONG");
        createCountryCodeRange(490, 499, "JAPAN");
        createCountryCodeRange(500, 509, "UNITED KINGDOM");
        createCountryCodeRange(520, 521, "GREECE");
        createCountryCodeRange(528, 528, "LEBANON");
        createCountryCodeRange(529, 529, "CYPRUS");
        createCountryCodeRange(530, 530, "ALBANIA");
        createCountryCodeRange(531, 531, "MACEDONIA");
        createCountryCodeRange(535, 535, "MALTA");
        createCountryCodeRange(539, 539, "REPUBLIC OF IRELAND");
        createCountryCodeRange(540, 549, "BELGIUM AND LUXEMBOURG");
        createCountryCodeRange(560, 560, "PORTUGAL");
        createCountryCodeRange(569, 569, "ICELAND");
        createCountryCodeRange(570, 579, "DENMARK, FAROE ISLANDS AND GREENLAND");
        createCountryCodeRange(590, 590, "POLAND");
        createCountryCodeRange(594, 594, "ROMANIA");
        createCountryCodeRange(599, 599, "HUNGARY");
        createCountryCodeRange(600, 601, "SOUTH AFRICA");
        createCountryCodeRange(603, 603, "GHANA");
        createCountryCodeRange(604, 604, "SENEGAL");
        createCountryCodeRange(608, 608, "BAHRAIN");
        createCountryCodeRange(609, 609, "MAURITIUS");
        createCountryCodeRange(611, 611, "MOROCCO");
        createCountryCodeRange(613, 613, "ALGERIA");
        createCountryCodeRange(615, 615, "NIGERIA");
        createCountryCodeRange(616, 616, "KENYA");
        createCountryCodeRange(618, 618, "IVORY COAST");
        createCountryCodeRange(619, 619, "TUNISIA");
        createCountryCodeRange(620, 620, "TANZANIA");
        createCountryCodeRange(621, 621, "SYRIA");
        createCountryCodeRange(622, 622, "EGYPT");
        createCountryCodeRange(623, 623, "BRUNEI");
        createCountryCodeRange(624, 624, "LIBYA");
        createCountryCodeRange(625, 625, "JORDAN");
        createCountryCodeRange(626, 626, "IRAN");
        createCountryCodeRange(627, 627, "KUWAIT");
        createCountryCodeRange(628, 628, "SAUDI ARABIA");
        createCountryCodeRange(629, 629, "UNITED ARAB EMIRATES");
        createCountryCodeRange(640, 649, "FINLAND");
        createCountryCodeRange(690, 699, "CHINA");
        createCountryCodeRange(700, 709, "NORWAY");
        createCountryCodeRange(729, 729, "ISRAEL");
        createCountryCodeRange(730, 739, "SWEDEN");
        createCountryCodeRange(740, 740, "GUATEMALA");
        createCountryCodeRange(741, 741, "EL SALVADOR");
        createCountryCodeRange(742, 742, "HONDURAS");
        createCountryCodeRange(743, 743, "NICARAGUA");
        createCountryCodeRange(744, 744, "COSTA RICA");
        createCountryCodeRange(745, 745, "PANAMA");
        createCountryCodeRange(746, 746, "DOMINICAN REPUBLIC");
        createCountryCodeRange(750, 750, "MEXICO");
        createCountryCodeRange(754, 755, "CANADA");
        createCountryCodeRange(759, 759, "VENEZUELA");
        createCountryCodeRange(760, 769, "SWITZERLAND AND LIECHTENSTEIN");
        createCountryCodeRange(770, 771, "COLOMBIA");
        createCountryCodeRange(773, 773, "URUGUAY");
        createCountryCodeRange(775, 775, "PERU");
        createCountryCodeRange(777, 777, "BOLIVIA");
        createCountryCodeRange(778, 779, "ARGENTINA");
        createCountryCodeRange(780, 780, "CHILE");
        createCountryCodeRange(784, 784, "PARAGUAY");
        createCountryCodeRange(786, 786, "ECUADOR");
        createCountryCodeRange(789, 790, "BRAZIL");
        createCountryCodeRange(800, 839, "ITALY, SAN MARINO AND VATICAN CITY");
        createCountryCodeRange(840, 849, "SPAIN AND ANDORRA");
        createCountryCodeRange(850, 850, "CUBA");
        createCountryCodeRange(858, 858, "SLOVAKIA");
        createCountryCodeRange(859, 859, "CZECH REPUBLIC");
        createCountryCodeRange(860, 860, "SERBIA");
        createCountryCodeRange(865, 865, "MONGOLIA");
        createCountryCodeRange(867, 867, "NORTH KOREA");
        createCountryCodeRange(868, 869, "TURKEY");
        createCountryCodeRange(870, 879, "NETHERLANDS");
        createCountryCodeRange(880, 880, "SOUTH KOREA");
        createCountryCodeRange(884, 884, "CAMBODIA");
        createCountryCodeRange(885, 885, "THAILAND");
        createCountryCodeRange(888, 888, "SINGAPORE");
        createCountryCodeRange(890, 890, "INDIA");
        createCountryCodeRange(893, 893, "VIETNAM");
        createCountryCodeRange(896, 896, "PAKISTAN");
        createCountryCodeRange(899, 899, "INDONESIA");
        createCountryCodeRange(900, 919, "AUSTRIA");
        createCountryCodeRange(930, 939, "AUSTRALIA");
        createCountryCodeRange(940, 949, "NEW ZEALAND");
        createCountryCodeRange(950, 950, "GS1 GLOBAL OFFICE SPECIAL APPLICATIONS");
        createCountryCodeRange(951, 951, "EPC GLOBAL SPECIAL APPLICATIONS");
        createCountryCodeRange(955, 955, "MALAYSIA");
        createCountryCodeRange(958, 958, "MACAU");
        createCountryCodeRange(960, 961, "GS1 UK OFFICE: GTIN-8 ALLOCATIONS");
        createCountryCodeRange(962, 969, "GS1 GLOBAL OFFICE: GTIN-8 ALLOCATIONS");
        createCountryCodeRange(977, 977, "SERIAL PUBLICATIONS (ISSN)");
        createCountryCodeRange(978, 979, "BOOKLAND (ISBN) 979-0 USED FOR SHEET MUSIC (ISMN-13, REPLACES DEPRECATED ISMN M- NUMBERS)");
        createCountryCodeRange(980, 980, "REFUND RECEIPTS");
        createCountryCodeRange(981, 984, "GS1 COUPON IDENTIFICATION FOR COMMON CURRENCY AREAS");
        createCountryCodeRange(990, 999, "GS1 COUPON IDENTIFICATION");
    }

    private void calculateCheckDigit() {
        try {
            String rawDataHolder = getRawData().substring(0, 12);

            int even = 0;
            int odd = 0;

            for (int i = 0; i < rawDataHolder.length(); i++) {
                if (i % 2 == 0) {
                    odd += Integer.parseInt(rawDataHolder.substring(i, i + 1));
                } else {
                    even += Integer.parseInt(rawDataHolder.substring(i, i + 1)) * 3;
                }
            }

            int total = even + odd;
            int cs = total % 10;
            cs = 10 - cs;
            if (cs == 10) {
                cs = 0;
            }

            setRawData(rawDataHolder + String.valueOf(cs).toCharArray()[0]);
        } catch (Exception ex) {
            error("EEAN13-4: Error calculating check digit.");
        }
    }

    public String getEncodedValue() {
        return encodeEAN13();
    }
}

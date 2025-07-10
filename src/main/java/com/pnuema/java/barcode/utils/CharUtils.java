package com.pnuema.java.barcode.utils;

/**
 * Utility class containing methods for handling character-related operations.
 * The CharUtils class provides methods to work with characters, including retrieving
 * their string representation. This class is designed as a utility and cannot be
 * instantiated.
 */
public class CharUtils {
    /**
     * Private constructor to prevent instantiation of the CharUtils class.
     * This class is designed solely to provide static utility methods and
     * should not be instantiated. Attempts to instantiate this class will
     * result in a compilation error.
     */
    private CharUtils() {
    }
    /**
     * Retrieves the string representation of a single-digit integer.
     * Converts the provided integer to its corresponding character
     * representation using base 10 and returns it as a string.
     *
     * @param i the integer to be converted to its character representation.
     *          Must be between 0 and 9 inclusive; otherwise, the result is undefined.
     * @return the string representation of the integer, or an empty string
     *         if the integer cannot be converted.
     */
    public static String getChar(int i) {
        return String.valueOf(Character.forDigit(i, 10));
    }
}

package com.pnuema.java.barcode;

/**
 * An enumeration that defines various barcode encoding types.
 * The EncodingType enum provides a comprehensive set of constants
 * representing different types of barcode symbologies, each with specific
 * features and intended use cases. These encoding types can be used
 * in applications requiring barcode generation and recognition, such as
 * inventory management, logistics, retail, and publishing.
 * Each constant encapsulates the characteristics and standards of a particular
 * barcode type, including its structure, usage, and any relevant validation features.
 */
public enum EncodingType {
    /**
     * The {@code UPCA} constant represents the Universal Product Code (UPC) Version A encoding standard.
     * UPC-A is a widely used barcode format commonly seen on retail products for globally unique
     * item identification. It consists of 12 numeric digits, which include a number system prefix,
     * a manufacturer code, a product code, and a check digit for validation.
     * This barcode format is primarily used in the retail industry for inventory management,
     * point-of-sale scanning, and logistics purposes.
     */
    UPCA,
    /**
     * The {@code UPCE} constant represents the Universal Product Code (UPC) Version E encoding standard.
     * UPC-E is a compact version of the UPC barcode format that compresses the data by omitting
     * unnecessary zeroes, creating a smaller barcode for use on products with limited space.
     * This format consists of 6 numeric digits, along with additional components derived
     * from its UPC-A equivalent such as a check digit. It is commonly used for smaller
     * packaging, including convenience goods and space-restricted items.
     */
    UPCE,
    /**
     * The {@code UPC_SUPPLEMENTAL_2DIGIT} constant represents a barcode encoding format
     * that supplements a primary UPC barcode with an additional 2-digit code. This format
     * is used in specific applications, such as publishing or periodicals, to encode
     * additional information like issue numbers or other supplementary data.
     * It is appended to a base UPC code to provide extended detail for specialized use cases.
     */
    UPC_SUPPLEMENTAL_2DIGIT,
    /**
     * The {@code UPC_SUPPLEMENTAL_5DIGIT} constant represents a barcode encoding format
     * that supplements a primary UPC barcode with an additional 5-digit code. This format
     * is often used in specific applications, such as in the book or publishing industries,
     * to convey additional information like prices, product variations, or other supplementary data.
     * It is appended to a base UPC code to provide extended details or context
     * for specialized use cases.
     */
    UPC_SUPPLEMENTAL_5DIGIT,
    /**
     * Represents the EAN-13 barcode encoding type.
     * EAN-13 (European Article Number) is a widely used standard for barcodes,
     * encoding a 13-digit number typically used for product identification.
     */
    EAN13,
    /**
     * Represents the EAN-8 barcode encoding type.
     * EAN-8 is a small, 8-digit European Article Number designed for use on small packages
     * where space is limited, like candy or cigarettes.
     */
    EAN8,
    /**
     * Interleaved2of5 is a barcode symbology encoded as a compact numeric-only format.
     * It is often used in logistics and inventory management, as well as ITF-14 barcodes.
     * Each digit is encoded using alternating patterns of black bars and white spaces.
     * This symbology supports high-density numeric data representation.
     */
    Interleaved2of5,
    /**
     * Represents the encoding type for the Interleaved 2 of 5 barcode with a Modulo 10 checksum.
     * Interleaved 2 of 5 is a numeric-only, high-density symbology commonly used in
     * industries such as distribution and warehouse management.
     * It uses pairs of numbers, with one number represented in the bars
     * and the other in the spaces.
     * The Modulo 10 checksum adds a layer of validation for data integrity.
     */
    Interleaved2of5_Mod10,
    /**
     * Represents the "Standard 2 of 5" barcode encoding type.
     * Standard 2 of 5 is a numeric-only, fixed-width symbology traditionally used in
     * industrial and warehouse environments. It encodes data using parallel bars
     * with two bars out of five representing each digit.
     */
    Standard2of5,
    /**
     * Represents the Standard 2 of 5 barcode encoding type with a Modulo 10 checksum.
     * This encoding type is a variation of the Standard 2 of 5 barcode that includes
     * a checksum digit for enhanced data validation. It is commonly used in
     * applications where accuracy and data integrity are critical, such as
     * inventory tracking, logistics, and labeling.
     * This encoding type is defined as part of the EncodingType enum.
     */
    Standard2of5_Mod10,
    /**
     * Represents the IATA 2 of 5 barcode encoding type.
     * This is used for the IATA (International Air Transport Association) 2 of 5 barcode standard,
     * which is a numeric-only barcode format often used in logistics and transportation industries,
     * such as cargo handling and airline operations.
     */
    IATA2of5,
    /**
     * Represents the Industrial 2 of 5 barcode encoding type.
     * Industrial 2 of 5 is a linear numerical-only barcode, primarily used in
     * industries such as warehouse and distribution for internal purposes.
     * It uses a two-of-five coding scheme where two spaces out of every five
     * ones are wide, making it a discrete (non-continuous) barcode format.
     */
    Industrial2of5,
    /**
     * Represents the Industrial 2 of 5 barcode symbology with a Modulo-10 checksum.
     * Industrial 2 of 5 is a numeric-only, discrete barcode symbology used in
     * specific industrial applications. This variant includes a Modulo-10
     * checksum to enhance data integrity by detecting input errors.
     */
    Industrial2of5_Mod10,
    /**
     * Represents the encoding type for the CODE39 barcode standard.
     * CODE39 is a discrete, variable-length barcode symbology, widely used for
     * alphanumeric data encoding. It supports encoding of letters (A-Z), digits (0-9),
     * and a limited set of special characters (-, ., $, /, +, %, and space).
     * This encoding type is commonly used in inventory management,
     * identification cards, and industrial applications.
     */
    CODE39,
    /**
     * Represents the CODE39Extended encoding type, which is an extended version of the CODE39 barcode standard.
     * This encoding type supports the full 128 ASCII character set, including lower-case letters,
     * special characters, and control characters, by using combinations of existing CODE39 symbols.
     */
    CODE39Extended,
    /**
     * Represents the CODE39 with Mod 43 checksum encoding type.
     * This encoding type uses the standard CODE39 barcode symbology
     * with an additional Mod 43 checksum character for error detection.
     */
    CODE39_Mod43,
    /**
     * Represents the Codabar encoding type, which is a linear barcode symbology
     * commonly used in libraries, blood banks, and airbills. Codabar allows
     * encoding numeric data and a limited set of additional characters.
     */
    Codabar,
    /**
     * PostNet is a predefined value within the EncodingType enumeration class, representing
     * a type of barcode encoding specific to the POSTNET barcode standard used by postal
     * services for sorting and routing mail.
     */
    PostNet,
    /**
     * Represents the BOOKLAND encoding type used in barcode standards.
     * BOOKLAND is a specific encoding format used primarily for encoding
     * ISBNs for books into barcodes. Typically, it is an EAN-13
     * encoding with a specific prefix to identify it as a book-related
     * product.
     * This encoding type is commonly used in publishing and retail industries
     * for the unique identification of books.
     */
    BOOKLAND,
    /**
     * Represents the International Standard Book Number (ISBN) encoding type.
     * This encoding type is used specifically for barcodes assigned to books and similar publications
     * to uniquely identify them worldwide.
     */
    ISBN,
    /**
     * Represents the JAN (Japanese Article Number) 13 barcode encoding type.
     * It is primarily used for labeling retail products in Japan and is a subset
     * of the EAN13 standard. This encoding is commonly used in product
     * identification for inventory and point-of-sale systems.
     */
    JAN13,
    /**
     * Represents the MSI Mod 10 encoding type, a variant of the MSI (Modified Plessey)
     * barcode format that uses a modulo 10 checksum for error detection.
     * This encoding type is commonly used for inventory control and various
     * warehouse management applications.
     */
    MSI_Mod10,
    /**
     * Represents the MSI Plessey barcode symbology with two Mod 10 checksum digits appended.
     * MSI (Modified Plessey) is a numeric-only symbology often used for inventory control, marking storage containers,
     * and other applications where numeric data encoding is required.
     */
    MSI_2Mod10,
    /**
     * MSI_Mod11 is a barcode encoding type following the Modified Plessey barcode standard
     * with a Modulo 11 check digit scheme. This encoding method is commonly used in
     * applications such as inventory management and industrial tracking. The Modulo 11
     * check digit is incorporated to ensure data integrity during transmission or scanning.
     */
    MSI_Mod11,
    /**
     * Represents the MSI barcode symbology that uses both Modulo 11 and Modulo 10 checksum algorithms.
     * This encoding provides an additional level of error detection by combining two checksum calculations.
     */
    MSI_Mod11_Mod10,
    /**
     * Represents the Modified Plessey encoding type, which is a variation of the Plessey barcode symbology.
     * It is used primarily for inventory and library systems, offering a unique method of encoding numeric data.
     */
    Modified_Plessey,
    /**
     * CODE11 is an enumeration constant within the EncodingType class.
     * Represents the CODE11 barcode symbology, which is used for labeling telecommunication equipment.
     * It supports encoding of numeric digits (0-9) and the dash symbol (-).
     */
    CODE11,
    /**
     * Represents the USD-8 encoding type used in barcode standards.
     * USD-8 is a symbology commonly used for specific kinds of barcodes,
     * typically involving numeric-only data encoding.
     * This encoding type is part of the supported barcode symbologies
     * available within the EncodingType enumeration.
     */
    USD8,
    /**
     * Represents the UCC12 barcode encoding type.
     * UCC12 (Uniform Code Council 12) is primarily used for UPC (Universal Product Code) standard barcodes
     * with 12 digits, commonly seen in retail and logistics industries.
     */
    UCC12,
    /**
     * Represents the UCC13 encoding type, part of the Universal Product Code (UPC) standards.
     * UCC13 is synonymous with EAN-13, used to identify products globally and is typically
     * used for retail barcoding systems.
     */
    UCC13,
    /**
     * LOGMARS is a predefined encoding type used for generating barcodes in the Labeling of Government
     * Managed Articles (LOGMARS) system. It adheres to military standards for inventory and tracking
     * purposes, often used for logistics and supply chain within government entities.
     */
    LOGMARS,
    /**
     * Represents the CODE128 barcode encoding type.
     * CODE128 is a high-density barcode symbology that can encode
     * all 128 ASCII characters, making it suitable for a wide variety
     * of data representation including alphanumeric and numeric data.
     * Generally used in logistics, shipping, and product labeling
     * due to its ability to encode large amounts of data in a compact format.
     */
    CODE128,
    /**
     * Represents the Code 128 Subset A encoding type used in barcodes.
     * Subset A encodes upper-case letters, numeric digits, and control
     * characters, along with special symbols and function codes.
     */
    CODE128A,
    /**
     * Represents the encoding scheme for Code 128 Barcode standard, specifically subset B.
     * Code 128 is a high-density barcode symbology that supports alphanumeric characters.
     * Subset B includes upper and lower case letters, numbers, and a range of special characters.
     * It is commonly used in logistics, shipping, and product identification.
     */
    CODE128B,
    /**
     * Represents the CODE128C barcode encoding type.
     * CODE128C is a subset of the CODE128 symbology.
     * It is optimized for numeric-only data and encodes data in pairs of digits,
     * providing a high data density.
     */
    CODE128C,
    /**
     * ITF14 represents the specific encoding type for International
     * Trade Item Number (ITIN) barcodes encoded in the ITF-14 format.
     * ITF-14 barcodes are used to mark packaging levels of a product
     * and are typically applied to logistics and shipping labels.
     * The ITF-14 format uses an Interleaved 2 of 5 symbology with
     * additional specifications for format and length.
     */
    ITF14,
    /**
     * Represents the CODE93 encoding type.
     * CODE93 is a continuous, variable-length symbology that encodes alphanumeric data.
     * It is designed to provide a higher density and data security compared to CODE39.
     */
    CODE93,
    /**
     * Represents the TELEPEN encoding type used in barcode generation or recognition.
     * TELEPEN is a symbology designed to encode full ASCII character sets compactly,
     * often used for alphanumeric data encoding in specialized use cases.
     */
    TELEPEN,
    /**
     * Represents the FIM (Facing Identification Mark) encoding type.
     * FIM is a barcode symbology used for mail automation in the postal services industry.
     */
    FIM,
    /**
     * PHARMACODE is a constant representing the Pharmacode symbology,
     * a barcode standard primarily used in the pharmaceutical industry
     * for the encoding and identification of medication packages.
     * This symbology is designed to enable fast and reliable packaging
     * processes, supporting applications such as inventory management
     * and product tracking.
     */
    PHARMACODE
}

package com.pnuema.java.barcode;

import com.pnuema.java.barcode.symbologies.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * Generates a barcode image of a specified symbology from a string of data.
 */
@SuppressWarnings({"WeakerAccess", "unused", "DuplicatedCode"})
public class Barcode {
    /**
     * Represents the types of file formats that can be used for saving files.
     * This enumeration includes common image file formats as well as an unspecified option.
     * Enum Constants:
     * - JPG: Represents the JPEG image format.
     * - BMP: Represents the BMP (Bitmap) image format.
     * - PNG: Represents the PNG (Portable Network Graphics) image format.
     * - GIF: Represents the GIF (Graphics Interchange Format) image format.
     * - TIFF: Represents the TIFF (Tagged Image File Format) image format.
     * - UNSPECIFIED: Represents an undefined or unspecified file format.
     */
    public enum SaveTypes {
        /**
         * Represents the JPEG image file format.
         * Commonly used for storing and compressing digital images, particularly for photographs.
         */
        JPG,
        /**
         * Represents the BMP (Bitmap) image file format.
         * BMP is a raster graphics image file format used to store bitmap digital images.
         * It is commonly utilized for simple and uncompressed images.
         */
        BMP,
        /**
         * Represents the PNG (Portable Network Graphics) image file format.
         * PNG is a raster-graphics file format that supports lossless data compression.
         * It is widely used for high-quality images with support for transparency.
         */
        PNG,
        /**
         * Represents the GIF (Graphics Interchange Format) image file format.
         * GIF is widely used for animated images and simple graphics with limited color palettes.
         */
        GIF,
        /**
         * Represents the TIFF (Tagged Image File Format) image file format.
         * TIFF is a flexible and adaptable file format used for storing high-quality raster graphics and images.
         * It is commonly used in applications requiring detailed image data, such as desktop publishing and professional photography.
         */
        TIFF,
        /**
         * Represents an undefined or unspecified file format.
         * This constant is used when no specific file format is selected or applicable.
         */
        UNSPECIFIED
    }

    /**
     * The AlignmentPositions enum represents the alignment positions
     * that can be used to specify the layout or positioning of elements.
     * It defines the following constants:
     * - CENTER: Represents a centered alignment.
     * - LEFT: Represents a left-aligned position.
     * - RIGHT: Represents a right-aligned position.
     */
    public enum AlignmentPositions {
        /**
         * Represents a centered alignment position.
         * Typically used to specify that an element should be positioned
         * or aligned at the center within its container or layout.
         */
        CENTER,
        /**
         * Represents a left-aligned position.
         * Typically used to specify that an element should be positioned
         * or aligned to the left within its container or layout.
         */
        LEFT,
        /**
         * Represents a right-aligned position.
         * Typically used to specify that an element should be positioned
         * or aligned to the right within its container or layout.
         */
        RIGHT
    }

    private IBarcode ibarcode;
    private String rawData = "";
    private String encodedValue = "";
    private String countryAssigningManufacturerCode = "N/A";
    private EncodingType encodedType = null;
    private Color foreColor = Color.BLACK;
    private Color backColor = Color.WHITE;
    private int width = 300;
    private int height = 150;
    private Font labelFont = new Font("Serif", Font.BOLD, 10);
    private Labels.LabelPositions labelPositions = Labels.LabelPositions.BOTTOM;

    //Properties
    private AlignmentPositions alignmentPosition = AlignmentPositions.CENTER;
    private String alternateLabel;
    private boolean includeLabel;
    private boolean standardizeLabel = true;
    private double encodingTime;
    private double drawTime;
    private Integer barWidth;
    private Double aspectRatio;

    /**
     * Default constructor.  Does not populate the raw data.  MUST be done via the RawData property before encoding.
     */
    @SuppressWarnings("WeakerAccess")
    public Barcode() {
    }

    /**
     * Constructor. Populates the raw data. No whitespace will be added before or after the barcode.
     *
     * @param data String to be encoded
     */
    @SuppressWarnings("WeakerAccess")
    public Barcode(String data) {
        setRawData(data);
    }

    /**
     * Constructor. Populates the raw data. No whitespace will be added before or after the barcode.
     *
     * @param data  String to be encoded
     * @param iType Type to encode
     */
    public Barcode(String data, EncodingType iType) {
        this(data);
        encodedType = iType;
    }

    /**
     * Get the Barcode libraries name
     * @return Name string
     */
    public String getTitle() {
        return getClass().getPackage().getImplementationTitle();
    }

    /**
     * Get the Barcode libraries version
     * @return Version string
     */
    public String getVersion() {
        return getClass().getPackage().getImplementationVersion();
    }

    /**
     * Gets the raw data to encode
     *
     * @return String representation of the data to encode
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * Sets the raw data to encode
     *
     * @param rawData String representation of the data to encode
     */
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    /**
     * Gets the encoded value
     *
     * @return Encoded value
     */
    public String getEncodedValue() {
        return encodedValue;
    }

    /**
     * Gets the Country that assigned the Manufacturer Code
     *
     * @return Country that assigned the manufacturer code
     */
    public String getCountryAssigningManufacturerCode() {
        return countryAssigningManufacturerCode;
    }

    /**
     * Gets the Encoded Type (ex. UPC-A, EAN-13 ... etc.)
     *
     * @return encoded type
     */
    public EncodingType getEncodedType() {
        return encodedType;
    }

    /**
     * Sets the Encoded Type (ex. UPC-A, EAN-13 ... etc.)
     *
     * @param encoded_Type encoded type
     */
    public void setEncodedType(EncodingType encoded_Type) {
        encodedType = encoded_Type;
    }

    /**
     * Gets the color of the bars. (Default is black)
     *
     * @return Color of the bars
     */
    public Color getForeColor() {
        return foreColor;
    }

    /**
     * Sets the color of the bars. (Default is black)
     *
     * @param _ForeColor Color of the bars
     */
    public void setForeColor(Color _ForeColor) {
        this.foreColor = _ForeColor;
    }

    /**
     * Gets the background color. (Default is white)
     *
     * @return Color of the background
     */
    public Color getBackColor() {
        return backColor;
    }

    /**
     * Sets the background color. (Default is white)
     *
     * @param _BackColor Color of the background
     */
    public void setBackColor(Color _BackColor) {
        this.backColor = _BackColor;
    }

    /**
     * Gets the label font. (Default is Serif, 10pt, Bold)
     *
     * @return Label font
     */
    public Font getLabelFont() {
        return labelFont;
    }

    /**
     * Sets the label font. (Default is Serif, 10pt, Bold)
     *
     * @param _LabelFont Label font
     */
    public void setLabelFont(Font _LabelFont) {
        this.labelFont = _LabelFont;
    }

    /**
     * Gets the location of the label in relation to the barcode. (BOTTOMCENTER is default)
     *
     * @return Label location in relation to the barcode orientation
     */
    public Labels.LabelPositions getLabelPosition() {
        return labelPositions;
    }

    /**
     * Sets the location of the label in relation to the barcode. (BOTTOMCENTER is default)
     *
     * @param _LabelPosition Label location in relation to the barcode orientation
     */
    public void setLabelPosition(Labels.LabelPositions _LabelPosition) {
        this.labelPositions = _LabelPosition;
    }

    /**
     * Gets the width of the image to be drawn. (Default is 300 pixels)
     *
     * @return Width of the barcode image
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the image to be drawn. (Default is 300 pixels)
     *
     * @param width Width to draw the image
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height of the image to be drawn. (Default is 150 pixels)
     *
     * @return Height fo the image
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the image to be drawn. (Default is 150 pixels)
     *
     * @param height Height to draw the image
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets alternate label to be displayed.  (IncludeLabel must be set to true as well)
     *
     * @return Alternate label text
     */
    public String getAlternateLabel() {
        return alternateLabel;
    }

    /**
     * Sets alternate label to be displayed.  (IncludeLabel must be set to true as well)
     *
     * @param alternateLabel Alternate label text
     */
    public void setAlternateLabel(String alternateLabel) {
        this.alternateLabel = alternateLabel;
    }

    /**
     * Gets whether a label should be drawn below the image. (Default is false)
     *
     * @return True if a label should be drawn below the image, false otherwise
     */
    public boolean isIncludeLabel() {
        return includeLabel;
    }

    /**
     * Sets whether a label should be drawn below the image. (Default is false)
     *
     * @param includeLabel True if a label should be drawn below the image, false otherwise
     */
    public void setIncludeLabel(boolean includeLabel) {
        this.includeLabel = includeLabel;
    }

    /**
     * Get if supposed to try to standardize the label format. (Valid only for EAN13 and empty AlternateLabel, default is true)
     *
     * @return True if standardization of the label should be attempted, false otherwise
     */
    public boolean isStandardizeLabel() {
        return standardizeLabel;
    }

    /**
     * Set to standardize the label format. (Valid only for EAN13 and empty AlternateLabel, default is true)
     *
     * @param standardizeLabel True if standardization of the label should be attempted, false otherwise
     */
    public void setStandardizeLabel(boolean standardizeLabel) {
        this.standardizeLabel = standardizeLabel;
    }

    /**
     * Gets the amount of time in milliseconds that it took to encode and draw the barcode.
     *
     * @return The number of milliseconds elapsed since encoding started
     */
    public double getEncodingTime() {
        return encodingTime;
    }

    /**
     * Sets the amount of time in milliseconds that it took to encode and draw the barcode.
     *
     * @param encodingTime The number of milliseconds elapsed since encoding started
     */
    private void setEncodingTime(double encodingTime) {
        this.encodingTime = encodingTime;
    }

    /**
     * Gets the amount of time in milliseconds that it took to draw the barcode.
     *
     * @return The number of milliseconds elapsed since encoding started
     */
    public double getDrawTime() {
        return drawTime;
    }

    /**
     * Sets the amount of time in milliseconds that it took to encode and draw the barcode.
     *
     * @param drawTime The number of milliseconds elapsed since drawing started
     */
    private void setDrawTime(double drawTime) {
        this.drawTime = drawTime;
    }

    /**
     * Get the bar width if set, otherwise null
     * @return Width of the bars if manually set, null otherwise
     */
    public Integer getBarWidth() {
        return barWidth;
    }

    /**
     * Sets the width of a bar. If non-null width is ignored and calculated automatically.
     * @param barWidth Fixed width of the bars are set and width is recalculated
     */
    public void setBarWidth(Integer barWidth) {
        this.barWidth = barWidth;
    }

    /**
     * Gets the aspect ratio of the image if set, otherwise null
     * @return Aspect ratio of the image, otherwise null
     */
    public Double getAspectRatio() {
        return aspectRatio;
    }

    /**
     * If non-null, Height is ignored and set to Width divided by this value rounded down.
     * <p>
     * Note: As longer barcodes may be more difficult to align a scanner gun with,
     * growing the height based on the width automatically allows the gun to be rotated the
     * same amount regardless of how wide the barcode is. A recommended value is 2.
     * <p>
     * This value is applied to Height after Width has been
     * calculated. So it is safe to use in conjunction with {@link Barcode#setBarWidth(Integer)}
     *
     * @param aspectRatio Aspect ratio of height to width to set the image to
     */
    public void setAspectRatio(Double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    /**
     * Gets the list of errors encountered
     *
     * @return List of errors encountered during encoding
     */
    public List<String> getErrors() {
        return ibarcode.getErrors();
    }

    /**
     * Gets the alignment of the barcode inside the image. (Not for Postnet or ITF-14)
     *
     * @return alignment
     */
    public AlignmentPositions getAlignmentPosition() {
        return alignmentPosition;
    }

    /**
     * Sets the alignment of the barcode inside the image. (Not for Postnet or ITF-14)
     *
     * @param alignment alignment
     */
    public void setAlignmentPosition(AlignmentPositions alignment) {
        alignmentPosition = alignment;
    }

    /**
     * Encodes the raw data into binary form representing bars and spaces.  Also generates an Image of the barcode.
     *
     * @param iType          Type of encoding to use
     * @param stringToEncode Raw data to encode
     * @param width          Width of the resulting barcode.(pixels)
     * @param height         Height of the resulting barcode.(pixels)
     * @return Image representing the barcode
     */
    public Image encode(EncodingType iType, String stringToEncode, int width, int height) {
        setWidth(width);
        setHeight(height);
        return encode(iType, stringToEncode);
    }

    /**
     * Encodes the raw data into binary form representing bars and spaces.  Also generates an Image of the barcode.
     *
     * @param iType          Type of encoding to use
     * @param stringToEncode Raw data to encode
     * @param foreColor      Foreground color
     * @param backColor      Background color
     * @param width          Width of the resulting barcode (pixels)
     * @param height         Height of the resulting barcode (pixels)
     * @return Image representing the barcode
     */
    public Image encode(EncodingType iType, String stringToEncode, Color foreColor, Color backColor, int width, int height) {
        setWidth(width);
        setHeight(height);
        return encode(iType, stringToEncode, foreColor, backColor);
    }

    /**
     * Encodes the raw data into binary form representing bars and spaces.  Also generates an Image of the barcode
     *
     * @param iType          Type of encoding to use
     * @param stringToEncode Raw data to encode
     * @param foreColor      Foreground color
     * @param backColor      Background color
     * @return Image representing the barcode
     */
    public Image encode(EncodingType iType, String stringToEncode, Color foreColor, Color backColor) {
        setBackColor(backColor);
        setForeColor(foreColor);
        return encode(iType, stringToEncode);
    }

    /**
     * Encodes the raw data into binary form representing bars and spaces.  Also generates an Image of the barcode.
     *
     * @param iType          Type of encoding to use
     * @param stringToEncode Raw data to encode
     * @return Image representing the barcode
     */
    public Image encode(EncodingType iType, String stringToEncode) {
        rawData = stringToEncode;
        return encode(iType);
    }

    /**
     * Encodes the raw data into binary form representing bars and spaces.  Also generates an Image of the barcode.
     *
     * @param iType Type of encoding to use
     * @return Image representation of the encoded value
     */
    private Image encode(EncodingType iType) {
        encodedType = iType;
        return encode();
    }

    /**
     * Encodes the raw data into binary form representing bars and spaces.
     *
     * @return Image representation of the encoded value
     */
    private Image encode() {
        long dtStartTime = System.nanoTime();

        //make sure there is something to encode
        if (rawData.trim().isEmpty()) {
            throw new IllegalArgumentException("EENCODE-1: Input data not allowed to be blank.");
        }

        if (getEncodedType() == null) {
            throw new IllegalArgumentException("EENCODE-2: Symbology type not allowed to be unspecified.");
        }

        encodedValue = "";
        countryAssigningManufacturerCode = "N/A";

        switch (this.encodedType) {
            case UCC12:
            case UPCA:
                ibarcode = new UPCA(rawData);
                break;
            case Industrial2of5_Mod10:
            case Industrial2of5:
            case Standard2of5_Mod10:
            case Standard2of5:
                ibarcode = new Standard2of5(rawData, getEncodedType());
                break;
            case IATA2of5:
                ibarcode = new IATA2of5(rawData);
                break;
            case Interleaved2of5_Mod10:
            case Interleaved2of5:
                ibarcode = new Interleaved2of5(rawData, getEncodedType());
                break;
            case UCC13:
            case EAN13:
                ibarcode = new EAN13(rawData);
                break;
            case LOGMARS:
            case CODE39:
                ibarcode = new Code39(rawData);
                break;
            case CODE39Extended:
                ibarcode = new Code39(rawData, true);
                break;
            case CODE39_Mod43:
                ibarcode = new Code39(rawData, false, true);
                break;
            case Codabar:
                ibarcode = new Codabar(rawData);
                break;
            case ISBN:
            case BOOKLAND:
                ibarcode = new ISBN(rawData);
                break;
            case JAN13:
                ibarcode = new JAN13(rawData);
                break;
            case MSI_Mod10:
            case MSI_2Mod10:
            case MSI_Mod11:
            case MSI_Mod11_Mod10:
            case Modified_Plessey:
                ibarcode = new MSI(rawData, encodedType);
                break;
            case UPC_SUPPLEMENTAL_2DIGIT:
                ibarcode = new UPCSupplement2(rawData);
                break;
            case UPC_SUPPLEMENTAL_5DIGIT:
                ibarcode = new UPCSupplement5(rawData);
                break;
            case UPCE:
                ibarcode = new UPCE(rawData);
                break;
            case PostNet:
                ibarcode = new Postnet(rawData);
                break;
            case EAN8:
                ibarcode = new EAN8(rawData);
                break;
            case USD8:
            case CODE11:
                ibarcode = new Code11(rawData);
                break;
            case CODE128:
                ibarcode = new Code128(rawData);
                break;
            case CODE128A:
                ibarcode = new Code128(rawData, Code128.TYPES.A);
                break;
            case CODE128B:
                ibarcode = new Code128(rawData, Code128.TYPES.B);
                break;
            case CODE128C:
                ibarcode = new Code128(rawData, Code128.TYPES.C);
                break;
            case CODE93:
                ibarcode = new Code93(rawData);
                break;
            case FIM:
                ibarcode = new FIM(rawData);
                break;
            case ITF14:
                ibarcode = new ITF14(rawData);
                break;
            case TELEPEN:
                ibarcode = new Telepen(rawData);
                break;
            case PHARMACODE:
                ibarcode = new Pharmacode(rawData);
                break;

            default:
                throw new IllegalArgumentException("EENCODE-2: Unsupported encoding type specified.");
        }

        ibarcode.clearErrors();

        encodedValue = ibarcode.getEncodedValue();
        rawData = ibarcode.getRawData();

        Image image = generateImage();

        setEncodingTime((System.nanoTime() - dtStartTime) / 1000000.0);

        return image;
    }

    /**
     * Gets a bitmap representation of the encoded data
     *
     * @return Bitmap of encoded value
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private Image generateImage() {
        if (encodedValue.isEmpty()) {
            throw new IllegalArgumentException("EGENERATE_IMAGE-1: Must be encoded first.");
        }

        BufferedImage bitmap;
        long dtStartTime = System.nanoTime();

        switch (this.encodedType) {
            case ITF14: {
                // Automatically calculate the Width if applicable. Quite confusing with this
                // barcode type, and it seems this method overestimates the minimum width. But
                // at least it's deterministic and doesn't produce too small of a value.
                if (getBarWidth() != null) {
                    setWidth((int) (241 / 176.9 * encodedValue.length() * getBarWidth() + 1));
                }

                if (getAspectRatio() != null) {
                    setHeight((int)(getWidth() / getAspectRatio()));
                }

                int ILHeight = getHeight();
                if (isIncludeLabel()) {
                    ILHeight -= getLabelFont().getSize();
                }

                bitmap = new BufferedImage(getWidth(), getHeight(), TYPE_INT_ARGB);

                int bearerwidth = (int) ((getWidth()) / 12.05);
                int iquietzone = (int) Math.round(getWidth() * 0.05);
                int iBarWidth = (getWidth() - (bearerwidth * 2) - (iquietzone * 2)) / encodedValue.length();
                int shiftAdjustment = ((getWidth() - (bearerwidth * 2) - (iquietzone * 2)) % encodedValue.length()) / 2;

                if (iBarWidth <= 0 || iquietzone <= 0) {
                    throw new IllegalArgumentException("EGENERATE_IMAGE-3: Image size specified not large enough to draw image. (Bar size determined to be less than 1 pixel or quiet zone determined to be less than 1 pixel)");
                }

                //draw image
                int pos = 0;

                Graphics g = bitmap.createGraphics();
                try {
                    //fill background
                    g.setColor(getBackColor());
                    g.fillRect(0, 0, bitmap.getWidth(), bitmap.getHeight());

                    //lines are fBarWidth wide so draw the appropriate color line vertically
                    g.setColor(getForeColor());

                    while (pos < getEncodedValue().length()) {
                        //draw the appropriate color line vertically
                        if (getEncodedValue().charAt(pos) == '1') {
                            g.fillRect((pos * iBarWidth) + shiftAdjustment + bearerwidth + iquietzone, 0, iBarWidth, getHeight());
                        }

                        pos++;
                    }

                    //bearer bars
                    int bearerBarWidth = ILHeight / 8;

                    g.fillRect(0, 0, getWidth(), bearerBarWidth);//top
                    g.fillRect(0, ILHeight - bearerBarWidth, getWidth(), bearerBarWidth);//bottom
                    g.fillRect(0, 0, bearerBarWidth, ILHeight);//left
                    g.fillRect(getWidth() - bearerBarWidth, bearerBarWidth, getWidth(), ILHeight);//right
                } finally {
                    g.dispose();
                }

                if (isIncludeLabel()) {
                    Labels.labelGeneric(this, bitmap);
                    //Labels.Label_ITF14(this, bitmap);
                }

                break;
            }
            case UPCA: {
                // Automatically calculate Width if applicable.
                if (getBarWidth() != null && !getEncodedValue().isEmpty()) {
                    setWidth(getBarWidth() * encodedValue.length());
                }

                // Automatically calculate Height if applicable.
                if (getAspectRatio() != null) {
                    setHeight((int) (getWidth() / getAspectRatio()));
                }

                int ILHeight = getHeight();
                int topLabelAdjustment = 0;

                int iBarWidth = getWidth() / encodedValue.length();

                //set alignment
                int shiftAdjustment = getShiftAdjustment();

                if (isIncludeLabel()) {
                    if ((getAlternateLabel() == null || getRawData().startsWith(getAlternateLabel())) && isStandardizeLabel()) {
                        // UPCA standardized label
                        String defTxt = getRawData();
                        String labTxt = defTxt.charAt(0) + "--" + defTxt.substring(1, 6) + "--" + defTxt.substring(7);

                        Font font = getLabelFont();
                        Font labFont = new Font(font != null ? font.getFamily() : "Serif", Font.PLAIN, Labels.getFontsize(getWidth(), getHeight(), labTxt));
                        setLabelFont(labFont);

                        ILHeight -= (labFont.getSize() / 2);

                        iBarWidth = getWidth() / encodedValue.length();
                    } else {
                        // Shift drawing down if top label
                        if (getLabelPosition() == Labels.LabelPositions.TOP)
                            topLabelAdjustment = getLabelFont().getSize();

                        ILHeight -= getLabelFont().getSize();
                    }
                }

                bitmap = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                if (iBarWidth <= 0) {
                    throw new IllegalArgumentException("EGENERATE_IMAGE-2: Image size specified not large enough to draw image. (Bar size determined to be less than 1 pixel)");
                }

                //draw image
                int pos = 0;

                Graphics g = bitmap.createGraphics();
                try {
                    //clears the image and colors the entire background

                    g.setColor(getBackColor());
                    g.fillRect(0, 0, bitmap.getWidth(), bitmap.getHeight());

                    g.setColor(getForeColor());

                    //lines are fBarWidth wide so draw the appropriate color line vertically
                    while (pos < getEncodedValue().length()) {
                        if (getEncodedValue().charAt(pos) == '1') {
                            g.fillRect(pos * iBarWidth + shiftAdjustment, topLabelAdjustment, iBarWidth, ILHeight + topLabelAdjustment);
                        }

                        pos++;
                    }
                } finally {
                    g.dispose();
                }

                if (isIncludeLabel()) {
                    Labels.labelGeneric(this, bitmap);
                    /*if ((getAlternateLabel() == null || getRawData().startsWith(getAlternateLabel())) && isStandardizeLabel()) {
                        Labels.Label_UPCA(this, bitmap);
                    } else {
                        Labels.labelGeneric(this, bitmap);
                    }*/
                }

                break;
            }
            case EAN13: {
                // Automatically calculate Width if applicable.
                if (getBarWidth() != null) {
                    setWidth(getBarWidth() * getEncodedValue().length());
                }

                // Automatically calculate Height if applicable.
                if (getAspectRatio() != null) {
                    setHeight((int) (getWidth() / getAspectRatio()));
                }

                int ILHeight = getHeight();
                int topLabelAdjustment = 0;

                int shiftAdjustment = getShiftAdjustment();

                if (isIncludeLabel()) {
                    if ((getAlternateLabel() == null || getRawData().startsWith(getAlternateLabel())) && isStandardizeLabel()) {
                        // EAN13 standardized label
                        String defTxt = getRawData();
                        String labTxt = defTxt.charAt(0) + "--" + defTxt.substring(1, 6) + "--" + defTxt.substring(7);

                        Font font = getLabelFont();
                        Font labFont = new Font(font != null ? font.getFamily() : "Serif", Font.PLAIN, Labels.getFontsize(getWidth(), getHeight(), labTxt));
                        setLabelFont(labFont);

                        ILHeight -= (labFont.getSize() / 2);
                    } else {
                        // Shift drawing down if top label.
                        if (getLabelPosition() == Labels.LabelPositions.TOP) {
                            topLabelAdjustment = getLabelFont().getSize();
                        }

                        ILHeight -= getLabelFont().getSize();
                    }
                }

                bitmap = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                int iBarWidth = getWidth() / getEncodedValue().length();
                if (iBarWidth <= 0)
                    throw new IllegalArgumentException("EGENERATE_IMAGE-2: Image size specified not large enough to draw image. (Bar size determined to be less than 1 pixel)");

                //draw image
                int pos = 0;

                Graphics g = bitmap.createGraphics();

                try {
                    //clears the image and colors the entire background
                    g.setColor(getBackColor());
                    g.fillRect(0, 0, getWidth(), getHeight());

                    g.setColor(getForeColor());

                    while (pos < getEncodedValue().length()) {
                        if (getEncodedValue().charAt(pos) == '1') {
                            g.fillRect(pos * iBarWidth + shiftAdjustment, topLabelAdjustment, iBarWidth, ILHeight + topLabelAdjustment);
                        }
                        pos++;
                    }
                } finally {
                    g.dispose();
                }

                if (isIncludeLabel()) {
                    Labels.labelGeneric(this, bitmap);
                    /*if ((getAlternateLabel() == null || getRawData().startsWith(getAlternateLabel())) && isStandardizeLabel()) {
                        Labels.Label_EAN13(this, bitmap);
                    } else {
                        Labels.labelGeneric(this, bitmap);
                    }*/
                }

                break;
            }
            default: {
                // Automatically calculate Width if applicable.
                if (getBarWidth() != null) {
                    setWidth(getBarWidth() * getEncodedValue().length());
                }

                // Automatically calculate Height if applicable.
                if (getAspectRatio() != null) {
                    setHeight((int) (getWidth() / getAspectRatio()));
                }

                int ILHeight = getHeight();
                int topLabelAdjustment = 0;

                if (isIncludeLabel()) {
                    // Shift drawing down if top label.
                    if (getLabelPosition() == Labels.LabelPositions.TOP)
                        topLabelAdjustment = getLabelFont().getSize();

                    ILHeight -= getLabelFont().getSize();
                }


                bitmap = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                int iBarWidth = getWidth() / getEncodedValue().length();
                int shiftAdjustment;

                //set alignment
                shiftAdjustment = getShiftAdjustment();

                if (iBarWidth <= 0) {
                    throw new IllegalArgumentException("EGENERATE_IMAGE-2: Image size specified not large enough to draw image. (Bar size determined to be less than 1 pixel)");
                }

                //draw image
                int pos = 0;

                Graphics g = bitmap.createGraphics();

                try {
                    //clears the image and colors the entire background
                    g.setColor(getBackColor());
                    g.fillRect(0, 0, getWidth(), getHeight());

                    g.setColor(getForeColor());

                    while (pos < getEncodedValue().length()) {
                        if (getEncodedType() == EncodingType.PostNet) {
                            //draw half bars in postnet
                            if (getEncodedValue().charAt(pos) == '0') {
                                g.fillRect(pos * iBarWidth + shiftAdjustment, (int) (ILHeight - ILHeight * 0.4f) + topLabelAdjustment, iBarWidth / 2, (int) (ILHeight - ILHeight * 0.4f) + topLabelAdjustment);
                            } else {
                                g.fillRect(pos * iBarWidth + shiftAdjustment, topLabelAdjustment, iBarWidth / 2, ILHeight + topLabelAdjustment);
                            }
                        } else {
                            if (getEncodedValue().charAt(pos) == '1') {
                                g.fillRect(pos * iBarWidth + shiftAdjustment, topLabelAdjustment, iBarWidth, ILHeight + topLabelAdjustment);
                            }
                        }
                        pos++;
                    }
                } finally {
                    g.dispose();
                }

                if (isIncludeLabel()) {
                    Labels.labelGeneric(this, bitmap);
                }

                break;
            }
        }

        setDrawTime((System.nanoTime() - dtStartTime) / 1000000.0);

        return bitmap;
    }

    /**
     * Gets the bytes that represent the image
     * @param savetype File type to put the data in before returning the bytes
     * @return Bytes representing the encoded image
     */
    public byte[] getImageData(SaveTypes savetype) {
        byte[] imageData = null;

        try {
            if (encode() != null) {
                //Save the image to a memory stream so that we can get a byte array!
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                saveImage(stream, savetype);
                imageData = stream.toByteArray();
                stream.flush();
                stream.close();
            }
        } catch (Exception ex) {
            throw new IllegalStateException("EGETIMAGEDATA-1: Could not retrieve image data. " + ex.getMessage());
        }
        return imageData;
    }

    /**
     * Saves an encoded image to a specified file and type
     * @param filename Filename to save to
     * @param fileType Format to use
     * @throws IOException throws this if writing the image to the file causes an Exception
     */
    public void saveImage(String filename, SaveTypes fileType) throws IOException {
        try {
            Image encodedImage = encode();
            if (encodedImage != null) {
                String imageformat = getImageFormatFromFileType(fileType);
                ImageIO.write((RenderedImage) encodedImage, imageformat, new File(filename));
            }
        } catch (IOException ex) {
            throw new IOException("ESAVEIMAGE-1: Could not save image.\n\n=======================\n\n" + ex.getMessage());
        }
    }

    /**
     * Saves an encoded image to a specified stream
     *
     * @param stream   Stream to write image to
     * @param fileType Format to use
     * @throws IOException throws this if writing the image to the stream causes an Exception
     */
    public void saveImage(OutputStream stream, SaveTypes fileType) throws IOException {
        try {
            Image encodedImage = encode();
            if (encodedImage != null) {
                String imageformat = getImageFormatFromFileType(fileType);
                ImageIO.write((RenderedImage) encodedImage, imageformat, stream);
            }
        } catch (Exception ex) {
            throw new IOException("ESAVEIMAGE-2: Could not save image.\n\n=======================\n\n" + ex.getMessage());
        }
    }

    private String getImageFormatFromFileType(SaveTypes saveType) {
        switch (saveType) {
            case BMP:
                return "bmp";
            case GIF:
                return "gif";
            case PNG:
                return "png";
            case TIFF:
                return "tif";
            case JPG:
            default:
                return "jpg";
        }
    }

    private int getShiftAdjustment() {
        int shiftAdjustment;
        switch (getAlignmentPosition()) {
            case LEFT:
                shiftAdjustment = 0;
                break;
            case RIGHT:
                shiftAdjustment = (getWidth() % getEncodedValue().length());
                break;
            case CENTER:
            default:
                shiftAdjustment = (getWidth() % getEncodedValue().length()) / 2;
                break;
        }

        return shiftAdjustment;
    }

}

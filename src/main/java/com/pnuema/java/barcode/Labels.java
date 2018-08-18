package com.pnuema.java.barcode;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class to draw labels for differing barcode types
 */
class Labels {
    /**
     * Draws Label for ITF-14 barcodes
     * @param Barcode Barcode to draw the label for
     * @param img Image representation of the barcode without the labels
     * @return Image representation of the barcode with labels applied
     */
    public static Image Label_ITF14(Barcode Barcode, BufferedImage img) {
        try
        {
            Font font = Barcode.getLabelFont();

            Graphics2D g = img.createGraphics();
            g.drawImage(img, 0, 0, null);

            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHints(rh);

            g.setColor(Barcode.getBackColor());
            //color a background color box at the bottom of the barcode to hold the string of data
            Rectangle rect = new Rectangle(0, img.getHeight() - (font.getSize() - 2), img.getWidth(), font.getSize());
            g.drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());

            //draw datastring under the barcode image
            g.setColor(Barcode.getForeColor());
            String text = Barcode.getAlternateLabel() == null ? Barcode.getRawData() : Barcode.getAlternateLabel();
            drawCenteredString(g, text, rect, font);

            float lineThickness = (float)img.getHeight() / 16;
            g.drawRect(0, img.getHeight() - font.getSize() - 2 - ((int)lineThickness / 2), img.getWidth(), img.getHeight() - font.getSize() - 2 + ((int)lineThickness/2));

            g.dispose();

            return img;
        }
        catch (Exception ex) {
            throw new RuntimeException("ELABEL_ITF14-1: " + ex.getMessage());
        }
    }

    /**
     * Draws Label for Generic barcodes
     * @param Barcode Barcode to draw the label for
     * @param img Image representation of the barcode without the labels
     * @return Image representation of the barcode with labels applied
     */
    public static Image labelGeneric(Barcode Barcode, BufferedImage img) {
        try {
            Font font = Barcode.getLabelFont();

            Graphics2D g = img.createGraphics();
            g.drawImage(img, 0, 0, null);

            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHints(rh);

            int LabelY = 0;

            switch (Barcode.getLabelPosition()) {
                case BOTTOM:
                    LabelY = img.getHeight() - (font.getSize());
                    break;
            }//switch

            //color a background color box at the bottom of the barcode to hold the string of data
            g.setColor(Barcode.getBackColor());
            g.drawRect(0, LabelY, img.getWidth(), font.getSize());

            //draw datastring under the barcode image
            Rectangle rect = new Rectangle(0, LabelY, img.getWidth(), font.getSize());
            drawCenteredString(g, Barcode.getAlternateLabel() == null ? Barcode.getRawData() : Barcode.getAlternateLabel(), rect, font);

            g.dispose();
            return img;
        } catch (Exception ex) {
            throw new RuntimeException("ELABEL_GENERIC-1: " + ex.getMessage());
        }
    }

    /**
     * Draws Label for EAN-13 barcodes
     * @param Barcode Barcode to draw the label for
     * @param img Image representation of the barcode without the labels
     * @return Image representation of the barcode with labels applied
     */
    public static Image Label_EAN13(Barcode Barcode, BufferedImage img) {
        try {
            int iBarWidth = Barcode.getWidth() / Barcode.getEncodedValue().length();
            String defTxt = Barcode.getRawData();

            int fontSize = getFontsize(Barcode.getWidth() - Barcode.getWidth() % Barcode.getEncodedValue().length(), img.getHeight(), defTxt);
            Font labFont = new Font("Serif", Font.PLAIN, fontSize);
            Font smallFont = new Font(labFont.getFamily(), labFont.getStyle(), (int)(fontSize * 0.5f));

            int shiftAdjustment = getShiftAdjustment(Barcode);

            Graphics2D g = img.createGraphics();
            g.drawImage(img, 0, 0, null);

            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHints(rh);

            int LabelY;

            //Default alignment for EAN13
            LabelY = img.getHeight() - labFont.getSize();

            float w1 = iBarWidth * 4; //Width of first block
            float w2 = iBarWidth * 42; //Width of second block
            float w3 = iBarWidth * 42; //Width of third block

            float s1 = shiftAdjustment - iBarWidth;
            float s2 = s1 + (iBarWidth * 4); //Start position of block 2
            float s3 = s2 + w2 + (iBarWidth * 5); //Start position of block 3

            //Draw the background rectangles for each block
            g.setColor(Barcode.getBackColor());
            g.drawRect((int)s2, LabelY, (int) w2, labFont.getSize());
            g.drawRect((int)s3, LabelY, (int) w3, labFont.getSize());

            //draw datastring under the barcode image
            g.setColor(Barcode.getForeColor());
            g.drawString(defTxt.substring(0, 1), s1, (float)img.getHeight() - (float)(smallFont.getSize() * 0.9));
            g.drawString(defTxt.substring(1, 6), s2, (float)LabelY);
            g.drawString(defTxt.substring(7), s3 - iBarWidth, (float)LabelY);

            g.dispose();
            return img;
        } catch (Exception ex) {
            throw new IllegalArgumentException("ELABEL_EAN13-1: " + ex.getMessage());
        }
    }

    /**
     * Draws Label for UPC-A barcodes
     * @param Barcode Barcode to draw the label for
     * @param img Image representation of the barcode without the labels
     * @return Image representation of the barcode with labels applied
     */
    public static Image Label_UPCA(Barcode Barcode, BufferedImage img) {
        try {
            int iBarWidth = Barcode.getWidth() / Barcode.getEncodedValue().length();
            int halfBarWidth = (int)(iBarWidth * 0.5);
            String defTxt = Barcode.getRawData();

            int fontSize = getFontsize((int)((Barcode.getWidth() - Barcode.getWidth() % Barcode.getEncodedValue().length()) * 0.9f), img.getHeight(), defTxt);
            Font labFont = new Font("Serif", Font.PLAIN, fontSize);
            Font smallFont = new Font(labFont.getFamily(), labFont.getStyle(), (int)(fontSize * 0.5f));

            int shiftAdjustment = getShiftAdjustment(Barcode);

            Graphics2D g = img.createGraphics();
            g.drawImage(img, 0, 0, null);

            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHints(rh);

            int LabelY;

            //Default alignment for UPCA
            LabelY = img.getHeight() - labFont.getSize();

            float w1 = iBarWidth * 4; //Width of first block
            float w2 = iBarWidth * 34; //Width of second block
            float w3 = iBarWidth * 34; //Width of third block

            float s1 = shiftAdjustment - iBarWidth;
            float s2 = s1 + (iBarWidth * 12); //Start position of block 2
            float s3 = s2 + w2 + (iBarWidth * 5); //Start position of block 3
            float s4 = s3 + w3 + (iBarWidth * 8) - halfBarWidth;

            //Draw the background rectangles for each block
            g.setColor(Barcode.getBackColor());
            g.drawRect((int)s2, LabelY, (int) w2, labFont.getSize());
            g.drawRect((int)s3, LabelY, (int) w3, labFont.getSize());

            //draw data string under the barcode image
            g.setColor(Barcode.getForeColor());
            g.drawString(defTxt.substring(0, 1), s1, (float)img.getHeight() - smallFont.getSize());
            g.drawString(defTxt.substring(1, 5), s2 - iBarWidth, (float)LabelY);
            g.drawString(defTxt.substring(6, 11), s3 - iBarWidth, (float)LabelY);
            g.drawString(defTxt.substring(11), s4, img.getHeight() - smallFont.getSize());

            g.dispose();
            return img;
        } catch (Exception ex) {
            throw new RuntimeException("ELABEL_UPCA-1: " + ex.getMessage());
        }
    }

    public static int getFontsize(int wid, int hgt, String lbl) {
        //Returns the optimal font size for the specified dimensions
        int fontSize = 10;

        if (lbl.length() > 0) {
            BufferedImage fakeImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); //As we cannot use CreateGraphics() in a class library, so the fake image is used to load the Graphics.

            Graphics g = fakeImage.createGraphics();

            for (int i = 1; i <= 100; i++) {
                // See how much space the text would
                // need, specifying a maximum width.

                int text_size = g.getFontMetrics().stringWidth(lbl);
                if ((text_size > wid) || (text_size > hgt)) {
                    fontSize = i - 1;
                    break;
                }
            }

            g.dispose();
        }

        return fontSize;
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     */
    public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    private static int getShiftAdjustment(Barcode barcode) {
        switch (barcode.getAlignmentPosition()) {
            case LEFT:
                return 0;
            case RIGHT:
                return (barcode.getWidth() % barcode.getEncodedValue().length());
            case CENTER:
            default:
                return (barcode.getWidth() % barcode.getEncodedValue().length()) / 2;
        }
    }
}

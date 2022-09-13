package com.pnuema.java.barcode;

/**
 * Represents the size of an image in real world coordinates (millimeters or inches).
 */
class ImageSize {
    public ImageSize(double width, double height, boolean metric) {
        setWidth(width);
        setHeight(height);
        setMetric(metric);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isMetric() {
        return metric;
    }

    public void setMetric(boolean metric) {
        this.metric = metric;
    }

    private double width;
    private double height;
    private boolean metric;
}

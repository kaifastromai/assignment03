package com.storm;

import java.nio.ByteBuffer;

public class Pixel {
    static final int R_BITMASK = 0x00FF0000;
    static final int G_BITMASK = 0x0000FF00;
    static final int B_BITMASK = 0x000000FF;
    private int rgb;
    public Pixel() {
        rgb = 0;
    }

    public Pixel(int r, int g, int b) {
        rgb=0;
        setR(r);
        setG(g);
        setB(b);
    }
    public Pixel(int rgb){
        this.rgb=rgb;
    }

    public int getR() {
        // return this.r;
        return (rgb & R_BITMASK) >> 2 * 8;

    }

    public int getG() {
        return (rgb & G_BITMASK) >> 8;

    }

    public int getB() {
        return (rgb & B_BITMASK);

    }

    /**
     * Returns copy of this pixel
     * @return
     * Pixel
     */
    public Pixel getPixel() {
        return new Pixel(getR(), getG(), getB());
    }

    /**
     * Return mutable reference to this pixel
     * @return
     * Returns Pixel
     */
    public Pixel getPixelMut() {
        return this;
    }

    /**
     * Set this pixels rgb values
     * @param r color in red channel
     * @param g color in green channel
     * @param b color in blue channel
     *
     */
    public void setRGB(int r, int g, int b) {
        setR(r);
        setG(g);
        setB(b);
    }

    public void setR(int val) {
        if (0 <= val && val <= 255) {
            val = val << 2 * 8;

            rgb = (rgb & ~R_BITMASK) | val;// t;

        }
    }

    public void setG(int val) {
        if (0 <= val && val <= 255) {
            //Make sure the  relevant section is properly displaced
            val = val << 8;

            rgb = (rgb & ~G_BITMASK) | val;

        }
    }

    public void setB(int val) {
        if (0 <= val && val <= 255) {
            //This zeroes out the section we want to change (while leaving the rest unchanged), and then sets to the new value
            rgb = (rgb & ~B_BITMASK) | val;

        }
    }

    @Override
    public String toString() {
        return "[ " + getR() + ", " + getG() + ", " + getB() + "]";
    }

    public String toOpenString() {
        return getR() + " " + getG() + " " +getB() ;

    }

    public String toStringHex() {
        return String.format("%06X",rgb);
    }

}

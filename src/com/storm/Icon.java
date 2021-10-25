package com.storm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Icon {

    private ArrayList<ArrayList<Pixel>> pixar;

    private int width;
    private int height;

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    Icon(int w, int h) {
        //Initialize pixel array to given dimensions.
        width = w;
        height = h;
        if (w > 0 && h > 0) {
            pixar = new ArrayList<ArrayList<Pixel>>(h);
            for (int i = 0; i < h; i++) {
                pixar.add(new ArrayList<Pixel>(w));

                for (int j = 0; j < w; j++) {
                    pixar.get(i).add(new Pixel());

                }
            }
        }
    }

    public Icon() {
        //Default to size of 5X5
        pixar = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            pixar.add(new ArrayList<Pixel>(5));

            for (int j = 0; j < 5; j++) {
                pixar.get(i).add(new Pixel());

            }
        }
    }

    /**
     * Set pixel at (row, col) to the value of p (does not change p)
     */
    public void setPixel(int row, int col, Pixel p) {

        if (0 <= row && row < height && 0 <= col && col < width) {
            pixar.get(row).get(col).setRGB(p.getR(),p.getG(),p.getB());
        }


    }

    /**
     * Set pixel at (row, col) to value have color value of r, g, b
     */
    public void setRGB(int row, int col, int r, int g, int b) {
        if (0 <= row && row < height && 0 <= col && col < width) {
            pixar.get(row).get(col).setRGB(r, g, b);
        }
    }
    /**
     * Return pixel referenced at (row, col)
     */
    public Pixel getPixel(int row, int col) {
        return pixar.get(row).get(col);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<Pixel> a : pixar) {

            for (Pixel p : a) {

                sb.append(p.toStringHex()+" ");
            }
            sb.append("\n");

        }
        return sb.toString();
    }

    /**
     * Generates text based ppm file representing this icon
     */
    public void genPPM() {
        try {
            FileWriter file = new FileWriter("out.ppm");

            BufferedWriter bw = new BufferedWriter(file);
            bw.write("P3\n" + width + " " + height + "\n" + 255 + "\n");

            for (var a : pixar) {
                for (var b : a) {
                    bw.write(b.toOpenString() + "\n");
                }
            }

            bw.close();

        } catch (Exception e) {
            System.out.println(e.toString());
            e.getStackTrace();
        }
    }

    /**
     * Generates binary bitmap file representing this icon
     */
    public void writeBMP() {


        try {

            //get size of total file
            int size = 54 + (int) Math.ceil(24 * width / 32.0) * height*4;

            byte[] br = new byte[size];
            ByteBuffer bf = ByteBuffer.wrap(br);

            //------ Signature--------
            bf.putChar((char) 0x424D); //Magic number
            bf.order(ByteOrder.LITTLE_ENDIAN); //The rest is little endian
            bf.putInt(size); //Size of bmp
            bf.putInt(0x0); //Unused
            bf.putInt(0x36); //Where the pixel array can be found


            //------- DIB Header--------
            bf.putInt(0x28); //Number of bytes in DIB header
            bf.putInt(width); // Width in pixels
            bf.putInt(height);//Height in pixels
            bf.putShort((short) 1); //Number of color planes (must be 1)
            bf.putShort((short) 24); //Bitdepth (24)
            bf.putInt(0x0); //Compression used (none)
            bf.putInt(size - 54); //Size of raw bitmap data + padding
            bf.putInt(2835); //Print resolution horizontal
            bf.putInt(2835);//Print resolution vertical
            bf.putInt(0x0); //Number of colors in palette--leave 0
            bf.putInt(0x0); //Number important colors--leave 0
            File file =new File("out.bmp");
            ArrayList<Byte> byteArrayList=new ArrayList<>();
            List<Byte> l=Arrays.asList((byte)0xff,(byte)0xff,(byte)0xff);





            //Start of pixel array
            for(int r=height-1;r>=0;r--){
                int extra_bits=(4-(width*3)%4)%4;
                byte[] extrab=new byte[extra_bits];
                for(int c=0;c<width;c++){

                    bf.put((byte)pixar.get(r).get(c).getB());
                    bf.put((byte)pixar.get(r).get(c).getG());
                    bf.put((byte)pixar.get(r).get(c).getR());
                }
                bf.put(extrab);

            }
            Files.write(Paths.get("test.bmp"),bf.array());

        } catch (Exception e) {
            System.out.println("OH NO!");
            System.out.println(e.toString());
            e.getStackTrace();
        }

    }

}

package com.storm;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public  class CSUtils {
    static int clamp(int a, int min, int max){
        return Math.max(min,Math.min(a,max));
    }
    static float clamp(float a, float min, float max){
        return Math.max(min,Math.min(a,max));
    }
    static Color invertColor(Color color){
        return new Color(0xFF-color.getRed(),0xFF-color.getGreen(),0xFF-color.getBlue(),color.getAlpha());
   }
   static class HSB{
        float h;
        float s;
        float b;
        HSB(float h, float s , float b){
            this.h=h;
            this.s=s;
            this.b=b;
        }
        HSB(Color color){
         var c= Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),null);
         h=c[0];
         s=c[1];
         b=c[2];
        }
        static Color toColor(HSB hsb){
            return new Color(Color.HSBtoRGB(hsb.h,hsb.s,hsb.b));
        }
        Color getColor(){
            return new Color(Color.HSBtoRGB(h,s,b));
        }
   }
   static void toIconAndWrite(BufferedImage bufferedImage){
        Icon ico=new Icon(bufferedImage.getWidth(),bufferedImage.getHeight());
        for(int h=0;h<ico.getHeight();h++){
            for(int w=0;w<ico.getWidth();w++){
              Pixel pixel=new Pixel(bufferedImage.getRGB(w,h));
              ico.setPixel(h,w,pixel);
            }
        }
        ico.writeBMP("testout");
   }
   static void toIconAndWrite(ArrayList<PixelButton> pixelButtons, int width, int height){
        Icon ico=new Icon(width,height);

        for(int h=0;h<ico.getHeight();h++){
            for(int w=0;w<ico.getWidth();w++){
                Pixel pxl=new Pixel(pixelButtons.get(width*h+w).getBackground().getRGB());
                ico.setPixel(h,w,pxl);
            }
       }
        ico.writeBMP("bitmapOutput");
   }
   static void nativeWriteToFile(BufferedImage bufferedImage){
       try {
           //Have to write to a png instead because my buffers have transparency...
           if(!ImageIO.write(bufferedImage,"png", new FileImageOutputStream(Path.of("n.bmp").toFile()))){
               throw new IOException();
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

}

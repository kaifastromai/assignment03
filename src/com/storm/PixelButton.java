package com.storm;

import javax.swing.*;
import java.awt.*;

public class PixelButton extends JButton {
   public int x;
   public int y;
   static Color highlightColor=Color.WHITE;
   public Color internalHighlight=Color.WHITE;
   static float Ticker=0;
   public boolean isSelectionCandidate=false;


   PixelButton(String name, int x, int y){
       super(name);
       this.x=x;
       this.y=y;
   }
    static public Color getHighlightColor(){
        return highlightColor;
    }

    static public void setHighlightColor(Color highlightColor) {
       PixelButton.highlightColor = highlightColor;
    }

    public static float getTicker() {
        return Ticker;
    }

    public static void setTicker(float ticker) {
        Ticker = ticker;
    }
}

package com.storm;

import javax.swing.*;
import java.awt.*;

public class MainPaint extends JPanel {

    private PixelImage pixelImage;
    private JScrollPane scrollPane;
    private float currentZoom;
/**
 * Manages an instance of  <code>PixelImage</code> and handles things like zoom and other things
 * @param width the width of the image
 * @param height represents height of image
 * /
 */
    MainPaint(int width, int height){


    }
    void setImageScale(float scale){
       // pixelImage.setScale(scale);
    }
    void initImage(int width, int height){
        currentZoom=this.getPreferredSize().height/height;
        pixelImage=new PixelImage(width, height);scrollPane=new JScrollPane(pixelImage);
        pixelImage.setBackground(Color.DARK_GRAY);
        this.setLayout(new GridLayout(1,1));
        this.add(scrollPane);
        setZoom(0);
    }
    /**
     * Set zoom to percent
     *
     * @param percent The amount to zoom, must be greater than 0
     * /
     */
    void setZoom(float percent){
    }
}

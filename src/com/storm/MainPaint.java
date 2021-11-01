package com.storm;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;

public class MainPaint extends JPanel implements KeyListener {

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
        addKeyListener(this);
        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK);
// bind the keystroke to an object\
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
    public void setColor(Color color){
        pixelImage.currentBrushColor=color;
    }
    /**
     * Set zoom to percent
     *
     * @param delta The amount to zoom, must be greater than 0
     * /
     */
    void setZoom(float delta){
        pixelImage.scale=CSUtils.clamp(pixelImage.scale+delta,0.01f,10f);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Events!!");
        if(e.isAltDown() ){
            if(e.getKeyCode()==KeyEvent.VK_PLUS) {
                pixelImage.scale += 0.2;
            }else if(e.getKeyCode()==KeyEvent.VK_MINUS){
                pixelImage.scale-=0.2;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

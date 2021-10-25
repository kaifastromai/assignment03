package com.storm;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class CSImage extends JPanel  {
    Image image;

    CSImage(Image img){
        image=img;

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(image==null){
            g.drawRect(0,0,400,400);
        }else {
            g.drawImage(image, 0, 0, this);
        }

    }

    @Override
    public Dimension getPreferredSize() {
        var d=new Dimension(400,400);
        if(image!=null) {
          d.width=image.getWidth(this);
          d.height=image.getHeight(this);

        }
        return d;
    }
}

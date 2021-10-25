package com.storm;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class PixelButtonUI extends BasicButtonUI {
    @Override
    public void paint(Graphics g, JComponent c){

        PixelButton b = (PixelButton) c;
        b.setRolloverEnabled(true);
        ButtonModel model = b.getModel();

        g.setColor(b.getBackground());
        if(b.isSelectionCandidate){
            g.setColor(b.internalHighlight);
        }
        g.fillRect(0,0,b.getWidth(),b.getHeight());
        Polygon p=new Polygon();
        p.addPoint(0,0);
        p.addPoint(0,b.getHeight());
        p.addPoint(b.getWidth(),b.getHeight());
        p.addPoint(b.getWidth(),0);
        g.setColor(Color.GRAY);
        g.drawPolygon(p);
    }


}

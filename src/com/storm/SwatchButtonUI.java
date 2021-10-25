package com.storm;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class SwatchButtonUI extends BasicButtonUI {
    @Override
    public void paint(Graphics g, JComponent c){
        var g2=(Graphics2D)g;
        JButton b = (JButton) c;
        b.setRolloverEnabled(true);
        ButtonModel model = b.getModel();
        g.setColor(b.getBackground());
        g.fillRect(0,0,b.getWidth(),b.getHeight());
        g2.setStroke(new BasicStroke(2));
        g2.setPaint(b.getForeground());
        g2.drawRect(0,0,b.getWidth(),b.getHeight());
    }
}

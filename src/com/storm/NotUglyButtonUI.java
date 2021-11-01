package com.storm;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class NotUglyButtonUI extends BasicButtonUI {
    @Override
    public void paint(Graphics g, JComponent c){
        JButton b = (JButton) c;
        g.setColor(b.getBackground());
        g.fillRoundRect(0,0,b.getWidth()/2,b.getHeight()/2,b.getWidth()/3,b.getHeight()/3);

    }
}

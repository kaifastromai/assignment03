package com.storm;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI {
    static int FillRows=1;
    static int FillCols=1;
    JPanel mainPanel;
    boolean isAdvancedMode=false;
    MainUI(int pixel_width, int pixel_height, Frame frame){

        JCheckBox advancedToggle=new JCheckBox("Advanced");
        advancedToggle.setBackground(Color.BLUE);
        AdvancedDialog ad=new AdvancedDialog(frame,"Advanced Options");
        ad.pack();
        advancedToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox b=(JCheckBox)e.getSource();
               isAdvancedMode= b.getModel().isSelected();
                BitmapUI.isAdvanced=isAdvancedMode;
               ad.setVisible(isAdvancedMode);
            }
        });
        mainPanel=new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(0,0,0,0));
        GridBagLayout gridBagLayout=new GridBagLayout();
        BitmapUI bitmapUI=new BitmapUI(pixel_width,pixel_height);

        JColorChooser colorChooser=new JColorChooser();
        colorChooser.setForeground(Color.BLACK);
        colorChooser.setBackground(Color.BLACK);
        var choosers= ColorChooserComponentFactory.getDefaultChooserPanels();
        colorChooser.setChooserPanels(new AbstractColorChooserPanel[]{choosers[1]});

        colorChooser.setPreviewPanel(new ColorPreview());
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                var mdl= ((ColorSelectionModel)e.getSource()).getSelectedColor();
                BitmapUI.currentColor=mdl;
                PixelButton.setHighlightColor(mdl);
            }


        });
        colorChooser.setSize(100,100);
        GridBagConstraints c=new GridBagConstraints();

        JPanel iconPanel=new JPanel();

        iconPanel.setLayout(new GridLayout(pixel_width,pixel_height,0,0));
        iconPanel.setPreferredSize(new Dimension(400,400));

        c.fill=GridBagConstraints.BOTH;
        for(var b: bitmapUI.buttons){
            iconPanel.add(b);
        }
        c.ipadx=3;
        c.ipady=3;
        c.gridwidth=3;
        c.gridheight=3;
        iconPanel.setBackground(Color.DARK_GRAY);
        c.weightx=0.0;
        c.weighty=0.0;
        mainPanel.add(iconPanel,c);
        c.gridwidth=3;
        c.gridheight=1;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=2;
        c.anchor=GridBagConstraints.PAGE_END;
        mainPanel.add(colorChooser,c);
        c.gridheight=1;
        c.gridwidth=1;
        c.fill=GridBagConstraints.NONE;
        c.gridx=3;
        mainPanel.add(advancedToggle,c);


    }

}

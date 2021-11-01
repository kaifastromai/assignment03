package com.storm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
    static int FillRows=1;
    static int FillCols=1;
    JPanel mainPanel;
    boolean isAdvancedMode=false;
    Dimension pixelResolution;
    private JButton createBitMapButton;
    BitmapUI bitmapUI;

    MainWindow(){
        showSizePopUp();
        JCheckBox advancedToggle=new JCheckBox();

        advancedToggle.setBackground(Color.BLUE);
        AdvancedDialog ad=new AdvancedDialog(this,"Advanced Options");
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
        bitmapUI=new BitmapUI(pixelResolution.width,pixelResolution.height);

        ColorPanel colorPanel=new ColorPanel(this,"Color chooser");
       colorPanel.setVisible(true);
       colorPanel.addColorConfirmListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              BitmapUI.currentColor=((ColorPanel)e.getSource()).getCurrentColor();
           }
       });
        GridBagConstraints c=new GridBagConstraints();

        JPanel iconPanel=new JPanel();

        iconPanel.setLayout(new GridLayout(pixelResolution.width,pixelResolution.height,0,0));
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
        JLabel toggleLabel=new JLabel("Advanced Mode");
        JPanel advancedTogglePanel=new JPanel();
        advancedTogglePanel.setLayout(new GridLayout(1,2,0,2));
        advancedTogglePanel.add(toggleLabel);
        advancedTogglePanel.add(advancedToggle);
        mainPanel.add(advancedTogglePanel,c);

        createBitMapButton =new JButton("Create bitmap");
        createBitMapButton.setFont(new Font("Arial",Font.BOLD,15));
        c=new GridBagConstraints();
        c.gridy=4;
        c.gridx=2;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.anchor=GridBagConstraints.CENTER;
        createBitMapButton.setBackground( Color.WHITE);
        createBitMapButton.addActionListener(e->createBitmap());
        mainPanel.add(createBitMapButton,c);
        this.add(mainPanel);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        this.setVisible(true);
        this.pack();
        setLocationRelativeTo(null);
    }
    void showSizePopUp(){
        final GetDimensionsDialog dialog=new GetDimensionsDialog(this,"Choose a size for pixel grid");
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        var d=dialog.getDimensions();
        this.pixelResolution=d;
    }
    void createBitmap(){
        CSUtils.toIconAndWrite(bitmapUI.getButtons(), pixelResolution.width,pixelResolution.height);
    }

}

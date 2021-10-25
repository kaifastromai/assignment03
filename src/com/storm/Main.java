package com.storm;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;

public class Main {


    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        JFrame mainFrame = new JFrame("xPaint");

//        final GetDimensionsDialog dialog=new GetDimensionsDialog(mainFrame,"Click a button");
//        dialog.pack();
//        dialog.setLocationRelativeTo(mainFrame);
//      dialog.setVisible(true);
//      var d=dialog.getDimensions();
//        System.out.println(d);
//
//        MainUI mainUI=new MainUI(d.width,d.height,mainFrame);
        mainFrame.setBackground(Color.DARK_GRAY);
        mainFrame.setSize(1000, 1000);

        JMenuBar mb=new JMenuBar();

        var menu=new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_O);
        mb.add(menu);
        var newMenuItem=new JMenuItem("New");
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.META_MASK));
        var openMenuItem=new JMenuItem("Open");
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.META_MASK));

        menu.add(newMenuItem);
        menu.add(openMenuItem);


        mainFrame.setJMenuBar(mb);
        GridBagConstraints c=new GridBagConstraints();
        c.weighty=0;
        c.weightx=0;
        c.gridwidth=3;
        c.gridheight=3;
        c.fill=GridBagConstraints.NONE;
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.getContentPane().setBackground(Color.darkGray);
        //var px=new PixelImage(10,10,1024,1024);
//        var cp=new HSLPreview(200,200);
//        cp.setLayout(new BorderLayout());
//        cp.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                HSLPreview v=(HSLPreview) e.getSource();
//               System.out.println( v.getBounds());
//            }
//        });

        //cp.setPreferredSize(new Dimension(200,200));

//        JScrollPane scrollPane=new JScrollPane(cp);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setLayout(new ScrollPaneLayout());
        //Intensity ramp...
        var colorPanel=new ColorPanel(mainFrame,"Color");

        colorPanel.setVisible(true);
      // var csi=new PixelImage(512,400,1024,800);
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

        colorPanel.addColorConfirmListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        c.weighty=1.0;
        c.weightx=1.0;
        c.fill=GridBagConstraints.BOTH;
        MainPaint mp=new MainPaint(10,10);
        mp.setPreferredSize(new Dimension(500,500));
       mainFrame.add(mp,c);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        JSlider slider=new JSlider();
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                mp.setZoom((float)((JSlider)e.getSource()).getModel().getValue()/100.f);
            }
        });
        c.weighty=0;
        c.weightx=0;
        c.gridy=2;
        //mainFrame.add(slider,c);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mp.initImage(1024,1024);
        mp.setImageScale(1);




    }
}

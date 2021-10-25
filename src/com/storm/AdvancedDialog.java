package com.storm;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class AdvancedDialog extends JDialog implements ActionListener, PropertyChangeListener {

    JPanel pane;
    JTabbedPane tabbedPane;
    JPanel bulkColorPane;
    JPanel addImagePane;
    ImageIcon preview;
   static BufferedImage bufferedImage=null;
     CSImage image;
    String enterString="Apply";
    String cancelString="Exit";
    private JSpinner xSpinner;
    private JSpinner ySpinner;
    static boolean isImageMode=false;
    File file;


    public AdvancedDialog(Frame aFrame, String aWord){

        super(aFrame,aWord,false);
        image= new CSImage(null);

        xSpinner=new JSpinner();
        xSpinner.setModel(new SpinnerNumberModel(2,2,2048,1));
        ySpinner=new JSpinner();
        xSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                var j=(JSpinner)e.getSource();
                MainUI.FillRows=(int)j.getValue();

            }
        });
        ySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                var j=(JSpinner)e.getSource();
                MainUI.FillCols=(int)j.getValue();
            }
        });
        ySpinner.setModel(new SpinnerNumberModel(2,2,2048,1));

        tabbedPane=new JTabbedPane();
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                var v=(JTabbedPane)e.getSource();
               isImageMode=v.indexOfTab("Add image")==v.getSelectedIndex();
            }
        });
       // tabbedPane.setPreferredSize(new Dimension(400,400));
        //Bulk color panel
        bulkColorPane=new JPanel();
        JPanel dimPanel=new JPanel();
        dimPanel.setLayout(new GridLayout(2,2));
        dimPanel.add(new JLabel("Rows"));
        dimPanel.add(xSpinner);
        dimPanel.add(new JLabel("Columns"));
        dimPanel.add(ySpinner);

        bulkColorPane.add(new JLabel("Add a block of row, column pixels based on current position. Click to apply color"));

        //---------------------------/
        //Create image panel
        addImagePane=new JPanel();
        addImagePane.setLayout(new GridBagLayout());
        JButton openFileButton=new JButton("Import image...");
        final JFileChooser fileChooser=new JFileChooser();

        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              int ret=  fileChooser.showOpenDialog(addImagePane);
              if(ret==JFileChooser.APPROVE_OPTION){
                  file=fileChooser.getSelectedFile();
                  try {
                     bufferedImage=ImageIO.read(file);
                     var c=new GridBagConstraints();
                     c.gridy=1;
                     c.weightx=0.9;
                     c.weighty=0.9;
                     c.gridheight=4;
                     c.gridwidth=4;
                     c.fill=GridBagConstraints.BOTH;
                    image.image= bufferedImage.getScaledInstance(400,-1,0);
                     //addImagePane.add(image,c);
                      image.repaint();
                     AdvancedDialog.super.pack();

                  } catch (IOException ex ) {
                      ex.printStackTrace();
                  }catch (NullPointerException ne){
                      System.out.println("No image yet");
                  }
              }
            }
        });
        var c=new GridBagConstraints();
        c.weightx=0.9;
        c.weighty=0.9;
        c.gridheight=4;
        c.gridwidth=4;
        c.fill=GridBagConstraints.BOTH;

        addImagePane.add(image,c);
        var c2=new GridBagConstraints();
        c2.anchor=GridBagConstraints.CENTER;
        c2.gridy = 4;
        addImagePane.add(openFileButton,c2);

        //----------------------------/
        tabbedPane.addTab("Bulk color",bulkColorPane);
        tabbedPane.addTab("Add image",addImagePane);
        pane=new JPanel();
        pane.setLayout(new GridBagLayout());
        c=new GridBagConstraints();
        c.fill=GridBagConstraints.BOTH;
        c.gridwidth=3;
        c.gridheight=4;
        c.weightx=1;
        c.weighty=0.8;
        c.insets=new Insets(10,10,10,10);
        pane.add(tabbedPane,c);

        JButton acceptButton=new JButton(enterString);
        JButton cancelButton=new JButton(cancelString);
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridwidth=1;
        c.gridheight=1;
        c.gridx=2;
        c.gridy=4;
        c.weighty=0.2;
        c.weightx=0;
        c.ipady=4;

        pane.add(acceptButton,c);
        pane.add(dimPanel);
        setContentPane(pane);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            }
        });

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}

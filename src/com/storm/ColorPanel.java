package com.storm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ColorPanel extends JDialog implements PropertyChangeListener, ActionListener {

    PropertyChangeSupport propertyChangeSupport=new PropertyChangeSupport(this);
    private ActionEvent actionEvent=new ActionEvent(this,ActionEvent.ACTION_FIRST,"ButtonClicked");
    List<ActionListener> actionListenerList;
    JPanel mainPane;
    HSLPreview hslPreview;
    ColorSlider intensitySlider;
    ColorSlider hueSlider;
    ColorSlider saturationSlider;
    JButton swatchCurrentColor;
    JButton swatchPreviousColor;
    int swatchCounter=0;
    int swatchCount=5;
    List<JButton> swatchQueue;
    Color[] hueMap =new Color[5];
    float[] hueFractions =new float[5];

    private Color currentColor =Color.WHITE;

    ColorPanel(Frame frame, String title){
        super(frame,title,false);
       setResizable(false);
        swatchQueue=new ArrayList<>(5);
        for(int i=0;i<swatchCount;i++){
            JButton jb=new JButton();
            jb.setBackground(currentColor);
            jb.setForeground(currentColor.brighter());
            jb.addActionListener(e -> {UpdateUIStateOnColor(((JButton)e.getSource()).getBackground());
            });
            jb.setUI(new SwatchButtonUI());
            jb.setPreferredSize(new Dimension(40,30));
            swatchQueue.add(jb);

        }


        actionListenerList=new LinkedList<>();
        swatchCurrentColor=new JButton();
        swatchCurrentColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentColor(((JButton)e.getSource()).getBackground());

            }
        });
        addColorConfirmListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swatchQueue.get(swatchCounter).setBackground(currentColor);
                swatchQueue.get(swatchCounter).setForeground(currentColor.brighter());
                swatchCounter=(swatchCounter+1)%swatchQueue.size();

            }
        });
        swatchPreviousColor=new JButton();
        swatchCurrentColor.setBackground(currentColor);
        swatchCurrentColor.setForeground(currentColor.brighter());
        swatchCurrentColor.setPreferredSize(new Dimension(50,40));
        swatchCurrentColor.setUI(new SwatchButtonUI());

        //Generate hue map
        hslPreview=new HSLPreview(200,200);
        hslPreview.setHueFromColor(currentColor);
        hueSlider=new ColorSlider(hueMap,hueFractions);
        hueSlider.addValueChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                var hsb=new CSUtils.HSB(currentColor);
                var nhsb= new CSUtils.HSB((float)evt.getNewValue(),hsb.s,hsb.b);
                float inHue=(float)evt.getNewValue();
                hslPreview.setHue(inHue);
                saturationSlider.setRampEnds(new CSUtils.HSB(nhsb.h,0,nhsb.b).getColor(),new CSUtils.HSB(nhsb.h,1,nhsb.b).getColor());
                setCurrentColor(nhsb.getColor());
            }
        });


        for(int i=0;i<=4;i++){
            hueMap[i]=new Color(Color.HSBtoRGB(i/4.0f,1,1));
            hueFractions[i]=i/4.0f;
        }
        hslPreview.addColorChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                var c=(Color)evt.getNewValue();
                var hsb=new CSUtils.HSB(c);
                saturationSlider.setCurrentValue(hsb.s);
                intensitySlider.setCurrentValue(hsb.b);
                System.out.println("Before color: "+currentColor);
                //setHueIcon();

            }
        });
        hslPreview.addColorConfirmListener(e->notifyListeners());
        hueSlider.setPreferredSize(new Dimension(200,10));
        hueSlider.addConfirmActionListener(e->notifyListeners());
        intensitySlider=new ColorSlider(
                new Color[]{new Color(Color.HSBtoRGB(0,0,0)),
                new Color(Color.HSBtoRGB(0,0,1))},new float[]{0,1});

        intensitySlider.addValueChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                var hsb=new CSUtils.HSB(currentColor);
                var nhsb= new CSUtils.HSB(hsb.h,hsb.s,(float)evt.getNewValue());
                saturationSlider.setRampEnds(new CSUtils.HSB(nhsb.h,0,nhsb.b).getColor(),new CSUtils.HSB(nhsb.h,1,nhsb.b).getColor());
               setCurrentColor(nhsb.getColor());
                 setHueIcon();
                //  System.out.println(currentColor);
            }
        });
        intensitySlider.addConfirmActionListener(e -> notifyListeners());
        intensitySlider.setPreferredSize(new Dimension(200,10));
        float[] hsb=Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(),null);
        saturationSlider=new ColorSlider(new Color[]{
                new Color(Color.HSBtoRGB(hsb[0],0,hsb[2])),
                new Color(Color.HSBtoRGB(hsb[0],1,hsb[2]))},
                new float[]{0,1});
        saturationSlider.addConfirmActionListener(e->notifyListeners());
        saturationSlider.addValueChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                var hsb=new CSUtils.HSB(currentColor);
                var nhsb= new CSUtils.HSB(hsb.h,(float)evt.getNewValue(),hsb.b);
                saturationSlider.setRampEnds(new CSUtils.HSB(nhsb.h,0,nhsb.b).getColor(),new CSUtils.HSB(nhsb.h,1,nhsb.b).getColor());
               setCurrentColor(nhsb.getColor());
                setHueIcon();
              System.out.println((float)evt.getNewValue());
            }
        });
        saturationSlider.setPreferredSize(new Dimension(200,10));
        hueSlider.setCurrentValue(new CSUtils.HSB(currentColor).h);
        saturationSlider.setCurrentValue(new CSUtils.HSB(currentColor).s);
        intensitySlider.setCurrentValue(new CSUtils.HSB(currentColor).b);
        mainPane=new JPanel();
        mainPane.setLayout(new GridBagLayout());

        var c=new GridBagConstraints();
        c.fill=GridBagConstraints.NONE;
        c.weightx=0;
        c.weighty=0;
        c.gridwidth=1;
        c.gridheight=4;
        c.insets=new Insets(5,10,5,0);

        mainPane.add(hslPreview,c);
        c=new GridBagConstraints();
        c.insets=new Insets(5,10,5,0);
        c.fill=GridBagConstraints.NONE;
        c.gridheight=1;
        c.gridwidth=1;
        c.gridy=5;
        c.gridx=0;
        c.ipady=3;
        mainPane.add(hueSlider,c);

        c.gridy=6;
        mainPane.add(saturationSlider,c);
        c.gridy=7;
        mainPane.add(intensitySlider,c);
        JPanel swatchPane=new JPanel();

        swatchPane.setLayout(new GridLayout(1+swatchQueue.size(),1));
        for(var jb: swatchQueue){
            swatchPane.add(jb);
        }
        c=new GridBagConstraints();
        c.fill=GridBagConstraints.BOTH;
        c.weighty=0.0;
        c.anchor=GridBagConstraints.CENTER;
        c.insets=new Insets(5,5,0,5);
        c.gridx=1;
        c.gridy=0;
        mainPane.add(swatchCurrentColor,c);

        c.gridy=1;
        c.gridheight=5;
        mainPane.add(swatchPane,c);

        setContentPane(mainPane);
        pack();
        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            }
        });



    }
    private void setHueIcon(){
        var hsb=new CSUtils.HSB(currentColor);
        System.out.println(currentColor);
        hslPreview.setRenderIconFromColor(hsb.s,hsb.b);
    }
   private void setCurrentColor(Color color){
        Color oldColor=currentColor;
        currentColor=color;
        propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this,"currentColor",oldColor,currentColor));
        swatchCurrentColor.setBackground(currentColor);

    }
    private void UpdateUIStateOnColor(Color color){
        setHueIcon();
        var hsb=new CSUtils.HSB(color);
        hslPreview.setHue(hsb.h);
        saturationSlider.setRampEnds(new CSUtils.HSB(hsb.h,0,hsb.b).getColor(),new CSUtils.HSB(hsb.h,1,hsb.b).getColor());
        saturationSlider.setCurrentValue(hsb.s);
        hueSlider.setCurrentValue(hsb.h);
        intensitySlider.setCurrentValue(hsb.b);
        setCurrentColor(color);

    }

    public Color getCurrentColor() {
        return currentColor;
    }

    void addColorChangeListener(PropertyChangeListener e){
        propertyChangeSupport.addPropertyChangeListener(e);
    }

    public void addColorConfirmListener(ActionListener e){
        actionListenerList.add(e);

    }
    public void removeActionListener(ActionListener e){
        actionListenerList.remove(e);
    }

    private void notifyListeners(){
        for( var a: actionListenerList){
            a.actionPerformed(actionEvent);
        }
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

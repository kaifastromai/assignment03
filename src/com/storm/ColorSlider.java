package com.storm;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;

public class ColorSlider extends JPanel implements MouseMotionListener, MouseListener {
    private ActionEvent actionEvent=new ActionEvent(this,ActionEvent.ACTION_FIRST,"ConfirmButton");
    List<ActionListener> actionListenerList;
    Color[] colorRamp;
    float[] colorFractions;
    float currentValue;
    private PropertyChangeSupport propertyChangeSupport=new PropertyChangeSupport(this);
    ColorSlider(Color[] colorRamp, float[] colorFractions){
        actionListenerList=new LinkedList<>();
        addMouseListener(this);
        addMouseMotionListener(this);
        this.colorRamp=colorRamp;
        this.colorFractions=colorFractions;
    }

    @Override
    protected void paintComponent(Graphics g) {
        var g2=(Graphics2D)g;
        var d=getPreferredSize();
        for(int y=0;y<d.height;y++){
            var lgp=new LinearGradientPaint(0,0,d.width,0,colorFractions,colorRamp);
            g2.setPaint(lgp);

            g2.drawLine(0,y,d.width,y);

            g2.fillOval(Math.round(currentValue*getWidth()),0,getHeight(),getHeight());
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3));
            g2.drawOval(Math.round(currentValue*getWidth()),0,getHeight(),getHeight());
        }
    }



    void setRampEnds(Color start, Color end){
        colorRamp[0]=start;
        colorRamp[colorRamp.length-1]=end;
        repaint();
    }
    void setCurrentValue(float currentValue){
        float oldValue=currentValue;
        this.currentValue=currentValue;
        propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this,"currentValue",0,currentValue));
        repaint();
    }
    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       setCurrentValue(CSUtils.clamp(e.getX()/(float)getWidth(),0,getWidth()));
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        notifyListeners();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setCurrentValue(CSUtils.clamp(e.getX()/(float)getWidth(),0,1.0f));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    public void addValueChangeListener(PropertyChangeListener e){
        propertyChangeSupport.addPropertyChangeListener(e);
    }
    public void addConfirmActionListener(ActionListener a){
        actionListenerList.add(a);
    }
    public void removeConfirmActionListener(ActionListener a){
        actionListenerList.remove(a);
    }
    private void notifyListeners(){
        for(var ae: actionListenerList){
            ae.actionPerformed(actionEvent);
        }
    }

}

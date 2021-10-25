package com.storm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;

public class HSLPreview extends JPanel implements MouseMotionListener, MouseListener {
    private PropertyChangeSupport propertyChangeSupport=new PropertyChangeSupport(this);
    ActionEvent actionEvent=new ActionEvent(this,ActionEvent.ACTION_FIRST,"ActionEvent");
    List<ActionListener> listenerList;

    private float hue =0.0f;
    BufferedImage colorImage;
    private Color currentColor;
    boolean isMouseHover=false;
    Dimension preferredSize;
    HSLPreview(int width, int height){
        preferredSize=new Dimension(width, height);
        setBackground(Color.BLUE);
        setMaximumSize(preferredSize);
        setMinimumSize(preferredSize);
        addMouseListener(this);
        addMouseMotionListener(this);
        listenerList=new LinkedList<ActionListener>();
        colorImage=new BufferedImage(getPreferredSize().width,getPreferredSize().height,BufferedImage.TYPE_INT_RGB);
        renderHSLImage();
    }

    void setHueFromColor(Color color){
         this.hue=Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),null)[0];
        renderHSLImage();
    }
    void setHue(float hue){
        this.hue=hue;
        renderHSLImage();
    }
    public void addColorConfirmListener(ActionListener e){
        listenerList.add(e);

    }
    public void removeActionListener(ActionListener e){
        listenerList.remove(e);
    }
    private void notifyListeners(){
        for( var a: listenerList){
            a.actionPerformed(actionEvent);
        }
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
        renderHSLImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(colorImage.getScaledInstance(getBounds().width,getBounds().height,BufferedImage.SCALE_FAST),0,0,this);

    }
    void renderHSLImage(){

        var g2=colorImage.createGraphics();
        var d=getPreferredSize();
        for(int y=0;y<d.height;y++){
            Color[] colors=new Color[]{ new Color(Color.HSBtoRGB(hue,0.0f, (d.height-y)/(float)d.height)),new Color(Color.HSBtoRGB(hue,1.0f,
                    (d.height-y)/(float)d.height))};

            float[] fractions=new float[]{0.0f,1.0f};
            g2.setPaint(new LinearGradientPaint(0,0,d.width,0,fractions,colors));
            g2.drawLine(0,y,d.width,y);
        }
        repaint();


    }
    void renderIconAndFire(Point p){
            Color oldColor=currentColor;
            renderIcon(p);
            fire(oldColor);
    }
    void renderIcon(Point p){
        var g2=colorImage.createGraphics();
        int radius=10;
        g2.setColor(Color.WHITE);
        g2.clearRect(0,0,getWidth(),getHeight());
        renderHSLImage();
        int nx=CSUtils.clamp(p.x,0,colorImage.getWidth()-1);
        int ny=CSUtils.clamp(p.y,0,colorImage.getHeight()-1);
        currentColor=new Color(colorImage.getRGB(nx,ny));
        g2.setColor(CSUtils.invertColor(currentColor));
        g2.drawOval(p.x-radius/2 , p.y-radius/2, radius, radius);
        repaint();

    }
    void fire(Color oldColor){
        propertyChangeSupport.firePropertyChange("CurrentColor",oldColor,currentColor);

    }
    void setRenderIconFromColor(float s, float b){
        int x=Math.round(s*preferredSize.width);
        int y=Math.round((1-b)*preferredSize.height);
        Point p=new Point(x,y);
        renderIcon(p);
    }
    @Override
    public Dimension getPreferredSize() {
       return preferredSize;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        renderIconAndFire(e.getPoint());

        //notifyListeners();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        notifyListeners();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    isMouseHover=true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
    isMouseHover=false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        renderIconAndFire(e.getPoint());

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    public void addColorChangeListener(PropertyChangeListener e){
        propertyChangeSupport.addPropertyChangeListener(e);
    }
}

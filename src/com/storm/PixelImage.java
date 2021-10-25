package com.storm;
import com.storm.math.AffineTransform2D;
import com.storm.math.SmoothPolygon;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;

public class PixelImage extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    DrawingMode drawingMode = DrawingMode.BASIC_STROKE;
    byte[] circleBrushFalloff = new byte[0xFF];
    float[] strokeFalloff = new float[0xFF];

    ArrayDeque<Point2D> mousePosDeque;
    int width;
    int height;
    int displayX;
    int displayY;
    float strokeDensity;
    boolean isMouseOver = false;
    Color gridColor = Color.DARK_GRAY;
    Color currentBrushColor = Color.BLACK;
    float gridTolerance = 0.4f;
    float dx;
    float dy;
    boolean drawGridLines = false;
    float scale;
    double rotation;
    double prevRotation;
    Point translation;
    Point prevTranslation;
    AffineTransform affineTransform;
    BufferedImage bufferedImage;
    BufferedImage previewImage;
    private Point mouseDragStartPos;
    private Point prevMousePos;
    private int brushSize = 10;
    private double mousePixelThreshold;

    PixelImage(int width, int height) {

        mousePosDeque = new ArrayDeque<>();
        scale = 1f;
        rotation = 0;
        translation = new Point(0, 0);
        prevTranslation = new Point(0, 0);
        prevMousePos = new Point(0, 0);


        refreshScreen();
        addMouseListener(this);
        addMouseMotionListener(this);
        this.width = width;
        this.height = height;
        this.displayX = Math.round(width * scale);
        this.displayY = Math.round(height * scale);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        previewImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        renderCheckerBoard(bufferedImage.createGraphics());
        dx = displayX / (float) width;
        dy = displayY / (float) height;

    }

    void drawPreview(Point p) {
        switch (drawingMode) {


            case BASIC_STROKE -> drawBasicPreview(p);
            case SQUARE_BRUSH -> {
                drawSquareBrushPreview(p);
            }
            case CIRCLE_BRUSH -> {
                drawCircleBrushPreview(p);
            }
            case INSERT_RECTANGLE -> {
                drawInsertRectanglePreview(p);
            }
            case INSERT_ELLIPSE -> {
                drawInsertEllipsePreview(p);
            }
            case INSERT_IMAGE -> {
                drawInsertImagePreview(p);
            }
            case INSERT_LINE -> {
                drawInsertLinePreview(p);
            }
            default -> throw new IllegalStateException("Unexpected value: " + drawingMode);
        }

    }

    void renderCheckerBoard(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
    }

    private void drawInsertLinePreview(Point p) {
    }

    private void drawInsertImagePreview(Point p) {
    }

    private void drawInsertEllipsePreview(Point p) {
    }

    private void drawInsertRectanglePreview(Point p) {
    }

    private void drawCircleBrushPreview(Point p) {
    }

    private void drawSquareBrushPreview(Point p) {
    }

    private void drawBasicPreview(Point p) {

        var g = previewImage.createGraphics();
        g.setBackground(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, width, height);
        var xcoord = (int) Math.floor(p.x / dx);
        var ycoord = (int) Math.floor(p.y / dy);
        g.setColor(currentBrushColor);
        g.fillRect(xcoord, ycoord, 1, 1);

    }

    void setDrawMode(DrawingMode drawMode) {
        drawMode = drawingMode;
    }

    public void refreshScreen() {
        var timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(MouseInfo.getPointerInfo().getLocation());

                repaint();
            }
        });
        timer.setRepeats(true);
        timer.setDelay(17);
        timer.start();
    }

    void drawBasicStroke() {




        Point[] p=new Point[mousePosDeque.size()];
        var g = bufferedImage.createGraphics();
        for (int i = 0; i < p.length; i++) {
            var pos1 = mousePosDeque.pop();
                p[i]=new Point((int)Math.floor(pos1.getX()),((int)Math.floor(pos1.getY())));

            // g.fillRect((int) Math.floor(pos.x / dx), (int) Math.floor(pos.y / dy), (int) Math.ceil(pixelsToFill / dx), (int) Math.ceil(pixelsToFill / dx));
        }
        g.setColor(currentBrushColor);
        g.setStroke(new BasicStroke(1));
        if(true) {
            var sp = new SmoothPolygon(p, 3);
        for (int i = 0; i < sp.points.length-1; i++) {
            g.drawOval(sp.points[i].x,sp.points[i].y,brushSize/2,brushSize/2);

            // g.fillRect((int) Math.floor(pos.x / dx), (int) Math.floor(pos.y / dy), (int) Math.ceil(pixelsToFill / dx), (int) Math.ceil(pixelsToFill / dx));
        }}else{
            for (int i = 0; i < p.length-1; i++) {
                g.drawLine(p[i].x,p[i].y,p[i+1].x,p[i+1].y);
                // g.fillRect((int) Math.floor(pos.x / dx), (int) Math.floor(pos.y / dy), (int) Math.ceil(pixelsToFill / dx), (int) Math.ceil(pixelsToFill / dx));
            }
        }

    }

    void drawMouse() {
        //previewImage.setData(bufferedImage.getRaster());
        while (mousePosDeque.size() >= 2) {
            switch (drawingMode) {
                case BASIC_STROKE -> drawBasicStroke();
                case CIRCLE_BRUSH -> drawCircleBrush();
                case INSERT_IMAGE -> insertImage(bufferedImage);
                case INSERT_LINE -> drawInsertLine();
                case INSERT_RECTANGLE -> drawInsertRectanle();
                case INSERT_ELLIPSE -> drawInsertEllipse();
            }

        }


    }
    void getMouseAverage(int range){

    }

    private void drawInsertEllipse() {
    }

    private void drawInsertRectanle() {
    }

    private void drawInsertLine() {
    }

    private void drawCircleBrush() {
    }

    void insertImage(Image img) {

    }

    BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(displayX, displayY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        var g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // super.paintComponent(g);
        //    g.setColor(Color.BLUE);
        // g.drawRect(0,0,width+10,height+2);
        //System.out.println(bufferedImage.getTileWidth());
        var testTrans=new Point(10,0);

        AffineTransform at = g2.getTransform();
        AffineTransform nat=g2.getTransform();
       // nat.translate(getWidth()/2.0,getHeight()/2.0);
        nat.rotate(rotation);
       // nat.translate(-getWidth()/2.0,-getHeight()/2.0);
       // t.setToTranslation(translation.x,translation.y);
        //nat.preConcatenate(t);
        //nat.scale(scale, scale);
        g2.setTransform(nat);
//        g2.translate(translation.x, translation.y);
//        g2.rotate(rotation);
        g.drawImage(bufferedImage, translation.x, translation.y, this);
        if (isMouseOver) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(previewImage, translation.x, translation.y, this);
        }
        g2.setTransform(at);
        if (drawGridLines) {
            g.setColor(gridColor);
            if (dx > gridTolerance) {
                for (int w = 0; w <= width; w++) {
                    g.drawLine((int) (dx * w), 0, (int) (dx * w), displayY);
                }
                for (int h = 0; h <= height; h++) {
                    g.drawLine(0, Math.round(dy * h), displayX, Math.round(dy * h));
                }
            }
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        var g = bufferedImage.createGraphics();
        g.drawImage(previewImage, 0, 0, this);
        g.dispose();
    }

    @Override
    public void mousePressed(MouseEvent e) {
            mouseDragStartPos = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePosDeque.clear();
        prevTranslation.x = translation.x;
        prevTranslation.y = translation.y;
        prevRotation=rotation;
       // System.out.println("Mouse released");

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isMouseOver = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isMouseOver = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.isControlDown()) {

            translation.x = e.getPoint().x - mouseDragStartPos.x + prevTranslation.x;
            translation.y = e.getPoint().y - mouseDragStartPos.y + prevTranslation.y;
            repaint();
        } else if (e.isAltDown()) {

            //var initVec = new Point(width / 2 - mouseDragStartPos.x, height /2.0 -mouseDragStartPos.y);
            var g=previewImage.createGraphics();
            Vector2D startVec=new Vector2D(mouseDragStartPos.x,mouseDragStartPos.y);
            var endVec = new Vector2D( e.getX(),  e.getY());
            g.drawLine(mouseDragStartPos.x,mouseDragStartPos.y,e.getX(),e.getY());

            rotation=calcRotation(mouseDragStartPos,e.getPoint())+prevRotation;

        } else {
            var inCoord=pointToLocalCoord(e.getPoint());
            if(!mousePosDeque.isEmpty()) {
                if (Math.abs(inCoord.getX() - mousePosDeque.peek().getX()) > mousePixelThreshold |
                        Math.abs(inCoord.getY() - mousePosDeque.peek().getY()) > mousePixelThreshold) {
                    mousePosDeque.add(inCoord);
                }
                drawStrokePreview();
            }
        }
    }

    private void drawStrokePreview() {

    }

    double calcRotation(Point a, Point b){

        //Signed angle between two vectors
   return Math.atan2(a.getX()*b.getY()-a.getY()*b.getX(),
                a.getX()*b.getX()+a.getY()*b.getY());

    }

    Point pointToLocalCoord(Point p){
        var p2=new Point(p);

        AffineTransform at=new AffineTransform();
        at.rotate(rotation);
        at.translate(translation.x,translation.y);
        var affineTrans=new AffineTransform2D(at);
        var im=affineTrans.inverse();
        im.operate(p2);
        return p2;
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        drawPreview(pointToLocalCoord(e.getPoint()));
    }

    public enum DrawingMode {
        BASIC_STROKE,
        SQUARE_BRUSH,
        CIRCLE_BRUSH,
        INSERT_RECTANGLE,
        INSERT_ELLIPSE,
        INSERT_IMAGE,
        INSERT_LINE
    }

}

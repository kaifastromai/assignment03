package com.storm;

import javax.swing.*;
import java.awt.*;

/**
 * The standard preview panel for the color chooser.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * @author Steve Wilson
 * @see JColorChooser
 */

//This a modification and simplification of Java's standard
// preview for JColorChooser written by Steve Wilson
public class ColorPreview extends JPanel {
    private int squareSize = 25;
    private int squareGap = 5;
    private int innerGap = 5;

    private int swatchWidth = 50;

    //Previous color
    private Color oldColor = null;

    private JColorChooser getColorChooser() {
        return (JColorChooser)SwingUtilities.getAncestorOfClass(
                JColorChooser.class, this);
    }
    //Returns to layout engine what size this pane wants to be
    public Dimension getPreferredSize() {
        int height = squareSize;
        int y = height*2 + squareGap*2;
        int x = swatchWidth;
        return new Dimension( x,y );
    }

    public void paintComponent(Graphics g) {
        if (oldColor == null)
            oldColor = getForeground();

        g.setColor(getBackground());
        g.fillRect(0,0,getWidth(),getHeight());

        if (this.getComponentOrientation().isLeftToRight()) {

            paintSwatch(g, 0 );
        } else {
           paintSwatch(g, 0);
        }
    }

    private int paintSwatch(Graphics g, int offsetX) {
        int swatchX = offsetX;
        g.setColor(oldColor);
        g.fillRect(swatchX, 0, swatchWidth, (squareSize) + (squareGap/2));
        g.setColor(getForeground());
        g.fillRect(swatchX, (squareSize) + (squareGap/2), swatchWidth, (squareSize) + (squareGap/2) );
        return (swatchX+swatchWidth);
    }

    //Paints a number of squares for reference purposes. Not necessary for our case.
    private int paintSquares(Graphics g, int offsetX) {

        int squareXOffset = offsetX;
        Color color = getForeground();

        g.setColor(Color.white);
        g.fillRect(squareXOffset,0,squareSize,squareSize);
        g.setColor(color);
        g.fillRect(squareXOffset+innerGap,
                innerGap,
                squareSize - (innerGap*2),
                squareSize - (innerGap*2));
        g.setColor(Color.white);
        g.fillRect(squareXOffset+innerGap*2,
                innerGap*2,
                squareSize - (innerGap*4),
                squareSize - (innerGap*4));

        g.setColor(color);
        g.fillRect(squareXOffset,squareSize+squareGap,squareSize,squareSize);

        g.translate(squareSize+squareGap, 0);
        g.setColor(Color.black);
        g.fillRect(squareXOffset,0,squareSize,squareSize);
        g.setColor(color);
        g.fillRect(squareXOffset+innerGap,
                innerGap,
                squareSize - (innerGap*2),
                squareSize - (innerGap*2));
        g.setColor(Color.white);
        g.fillRect(squareXOffset+innerGap*2,
                innerGap*2,
                squareSize - (innerGap*4),
                squareSize - (innerGap*4));
        g.translate(-(squareSize+squareGap), 0);

        g.translate(squareSize+squareGap, squareSize+squareGap);
        g.setColor(Color.white);
        g.fillRect(squareXOffset,0,squareSize,squareSize);
        g.setColor(color);
        g.fillRect(squareXOffset+innerGap,
                innerGap,
                squareSize - (innerGap*2),
                squareSize - (innerGap*2));
        g.translate(-(squareSize+squareGap), -(squareSize+squareGap));



        g.translate((squareSize+squareGap)*2, 0);
        g.setColor(Color.white);
        g.fillRect(squareXOffset,0,squareSize,squareSize);
        g.setColor(color);
        g.fillRect(squareXOffset+innerGap,
                innerGap,
                squareSize - (innerGap*2),
                squareSize - (innerGap*2));
        g.setColor(Color.black);
        g.fillRect(squareXOffset+innerGap*2,
                innerGap*2,
                squareSize - (innerGap*4),
                squareSize - (innerGap*4));
        g.translate(-((squareSize+squareGap)*2), 0);

        g.translate((squareSize+squareGap)*2, (squareSize+squareGap));
        g.setColor(Color.black);
        g.fillRect(squareXOffset,0,squareSize,squareSize);
        g.setColor(color);
        g.fillRect(squareXOffset+innerGap,
                innerGap,
                squareSize - (innerGap*2),
                squareSize - (innerGap*2));
        g.translate(-((squareSize+squareGap)*2), -(squareSize+squareGap));

        return (squareSize*3+squareGap*2);

    }



}




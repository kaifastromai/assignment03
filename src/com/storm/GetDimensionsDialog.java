package com.storm;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;

//A modification of Java CustomDialog Example
public class GetDimensionsDialog extends JDialog implements ActionListener, PropertyChangeListener, ChangeListener {

    private JSpinner xSpinner;
    private JSpinner ySpinner;
    private Dimension dimension;
    private int rows;
    private int cols;
    private JOptionPane pane;
    String enterString="Accept";
    String cancelString="Cancel";
    public Dimension getDimensions(){

       return new Dimension(rows,cols);

    }
    public GetDimensionsDialog(Frame aFrame, String aWord){
    super(aFrame,aWord,true);
    xSpinner =new JSpinner();
    xSpinner.setModel(new SpinnerNumberModel(4,4,1024,1));
    ySpinner =new JSpinner();
    ySpinner.setModel(new SpinnerNumberModel(4,4,1024,1));
        dimension=new Dimension((int)xSpinner.getValue(),(int)ySpinner.getValue());
    System.out.println(dimension);
        String msgString="Please enter the desired dimensions";
    JPanel dimPanel=new JPanel();
    dimPanel.setLayout(new GridLayout(2,2));
    dimPanel.add(new JLabel("Rows"));
    dimPanel.add(xSpinner);
    dimPanel.add(new JLabel("Columns"));
    dimPanel.add(ySpinner);
    Object[] array={msgString, dimPanel};
    Object[] options={enterString,cancelString};
    pane=new JOptionPane(array,JOptionPane.PLAIN_MESSAGE,JOptionPane.YES_NO_OPTION,null,options,options[0]);
        //Make this dialog display it.
        setContentPane(pane);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                pane.setValue(Integer.valueOf(
                        JOptionPane.CLOSED_OPTION));
            }
        });

        //Ensure textfield gets first focus
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                xSpinner.requestFocusInWindow();
            }
        });
        xSpinner.addChangeListener(this);
        ySpinner.addChangeListener(this);
        pane.addPropertyChangeListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pane.setValue(enterString);
    }

    @Override
    /** This method reacts to state changes in the option pane. */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible()
                && (e.getSource() == pane)
                && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
                JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = pane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }
            if(enterString.equals(value)){
              rows=(int)xSpinner.getValue();
                cols=(int)ySpinner.getValue();
                clearAndHide();

            }

            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
            pane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);

           System.out.println(value);
        }
    }
    /** This method clears the dialog and hides it. */
    public void clearAndHide() {
        xSpinner.setValue(0);
        ySpinner.setValue(0);
        setVisible(false);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
       dimension.height=(int)ySpinner.getValue();
       dimension.width=(int)xSpinner.getValue();
    }
}

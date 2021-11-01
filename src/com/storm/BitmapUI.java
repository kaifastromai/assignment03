package com.storm;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class BitmapUI  {
    ActionEvent actionEvent;
    Icon _icon;
    int w;
    int h;
    static Color currentColor=Color.WHITE;
    static boolean isAdvanced=false;
    static Dimension CurrentIndex;

    //2d arrays are annoying in Java...
    ArrayList<PixelButton> buttons;

    ArrayList<PixelButton> getButtons(){
        return buttons;
    }

    BitmapUI(int width, int height){
        CurrentIndex=new Dimension(0,0);
        w=width;
        h=height;
        _icon=new Icon(w,h);
        buttons=new ArrayList<>(h*w);
        for( int col=0;col<h;col++){
            for (int row=0;row<w;row++){

                PixelButton px=new PixelButton("",row,col);
                //px.setBorderPainted(false);
                px.setBackground(Color.BLACK);
                //px.setBorder(new EmptyBorder(1,1,1,1));
                px.setUI(new PixelButtonUI());
                px.addActionListener(e -> {
                    CurrentIndex.width=px.x;
                    CurrentIndex.height=px.y;
                    System.out.println("Pixel "+"("+px.x+", "+px.y+")" );

                     colorSelectionCandidates(MainWindow.FillRows-1,MainWindow.FillCols-1);


                });
                px.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {

                        var v=(PixelButton)e.getSource();
                        CurrentIndex.width=v.x;
                        CurrentIndex.height=v.y;
                        if(v.getModel().isRollover()){
                            setIsSelectionCandidate(MainWindow.FillRows-1,MainWindow.FillCols-1);
                        }
                    }
                });
                buttons.add(px);

            }
        }
    }
    void setIsSelectionCandidate(int rangeX, int rangeY){
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                if((i>=CurrentIndex.height&&i<= CurrentIndex.height+rangeY)&&
                        (j>= CurrentIndex.width&&j<= CurrentIndex.width+rangeX)  ) {
                    //buttons.get(i * h + j).setBackground(Color.BLUE);
                    buttons.get(i*h+j).isSelectionCandidate=true;
                    if(isAdvanced&& AdvancedDialog.isImageMode&& AdvancedDialog.bufferedImage!=null){
                        var pixel=AdvancedDialog.bufferedImage.getData().getPixel(j- CurrentIndex.width,
                                i- CurrentIndex.height, (int[]) null);
                        buttons.get(i*h+j).internalHighlight=new Color(pixel[0],pixel[1],pixel[2]);
                    }else{
                        buttons.get(i*h+j).internalHighlight=currentColor;
                    }
                    buttons.get(i*h+j).repaint();
                }else{
                    buttons.get(i*h+j).isSelectionCandidate=false;
                    buttons.get(i*h+j).repaint();
                }
            }
        }
    }
    void colorSelectionCandidates(int rangeX, int rangeY){
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                if((i>=CurrentIndex.height&&i<= CurrentIndex.height+rangeY)&&
                        (j>= CurrentIndex.width&&j<= CurrentIndex.width+rangeX)  ) {
                    //buttons.get(i * h + j).setBackground(Color.BLUE);
                    //Could get slightly better performance by checking common case first...
                    if(isAdvanced&& AdvancedDialog.isImageMode&& AdvancedDialog.bufferedImage!=null){
                        var pixel=AdvancedDialog.bufferedImage.getData().getPixel(j- CurrentIndex.width,
                                i- CurrentIndex.height, (int[]) null);
                        buttons.get(i*h+j).setBackground( new Color(pixel[0],pixel[1],pixel[2]));
                    }else {
                        buttons.get(i * h + j).setBackground(currentColor);
                    }


                }else{

                }
            }
        }
    }


}

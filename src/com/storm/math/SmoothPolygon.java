package com.storm.math;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * An implementation of Chaikin's curve algorithm
 */
public class SmoothPolygon {

    //private final int extraPointDensity;
    int maxRecursiveDepth=3;
    int currentDepth=0;
    public Point[] points;
    Point[] refPoints;
    ArrayList<Point[]> lists;
    List<Point> testList;
    public SmoothPolygon(Point[] pointList, int maxRecursiveDepth){
        lists=new ArrayList<>();
       this.maxRecursiveDepth=maxRecursiveDepth;
        refPoints=pointList;
        testList=new LinkedList<>();
      points= generatePoints(pointList);
      points=lists.get(0);
      System.out.println(points.length);
         //extraPointDensity=2<<maxRecursiveDepth;
//        generatePoints(pointList);
    }

    private Point[] generatePoints(Point[] input){
      System.out.println(currentDepth);
       // int recD=2<<currentDepth;
        if(input.length<3){
            lists.add(input);
            return input;
        }
        int size=input.length;
         int k=0;
         var lpoints=new Point[(input.length-1)*2];
        for(int i=0;i<size-1;i++){
            var q1=new Point((int)Math.round((input[i].x-input[0].x)*(3.0/4.0)),(int)Math.round((input[i].y-input[0].y)*(3.0/4.0)));
            var q2=new Point((int)Math.round((input[i+1].x-input[0].x)*(1.0/4.0)),(int)Math.round((input[i+1].y-input[0].y)*(1.0/4.0)));
            var q=new Point(q1.x+q2.x+input[0].x,q1.y+q2.y+input[0].y);
            var r1=new Point((int)Math.round((input[i].x-input[0].x)*(1.0/4.0)),(int)Math.round((input[i].y-input[0].y)*(1.0/4.0)));
            var r2=new Point((int)Math.round((input[i+1].x-input[0].x)*(3.0/4.0)),(int)Math.round((input[i+1].y-input[0].y)*(3.0/4.0)));
            var r=new Point(r1.x+r2.x+input[0].x,r1.y+r2.y+input[0].y);
            lpoints[k]=q;
            lpoints[k+1]=r;
            k+=2;
        }
        while(currentDepth<maxRecursiveDepth){
            currentDepth++;
            generatePoints(lpoints);
        }
      lists.add(lpoints);
        return lists.get(lists.size()-1);

    }

}

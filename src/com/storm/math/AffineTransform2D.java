package com.storm.math;

import org.apache.commons.math3.analysis.function.Sin;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.*;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class AffineTransform2D {
    private RealMatrix matrix;

    public AffineTransform2D(java.awt.geom.AffineTransform transform){
        matrix= MatrixUtils.createRealIdentityMatrix(3);
        double[] data=new double[6];
        transform.getMatrix(data);
        matrix.setRow(0,new double[]{data[0],data[2],data[4]});
        matrix.setRow(1,new double[]{data[1],data[3],data[5]});


    }

    public AffineTransform2D(RealMatrix matrix) {
        this.matrix=matrix;
    }

    public AffineTransform2D fromSwing(AffineTransform transform){
        var n=new  AffineTransform2D(transform);
        return n;
    }
    public void operate(Point vec){
        ArrayRealVector v3=new ArrayRealVector(3);
        v3.setEntry(0,vec.getX());
        v3.setEntry(1, vec.getY());
        v3.setEntry(2,1);
        v3=(ArrayRealVector)matrix.operate(v3);
        vec.x=Math.round((float)v3.getEntry(0));
        vec.y=Math.round((float)v3.getEntry(1));

    }
    public void setToRotate(double angle){
        double[][] rot=new double[][]{{Math.cos(angle),-Math.sin(angle)},{Math.sin(angle),Math.cos(angle)}};
        matrix.setSubMatrix(rot,0,0);
    }
    public void setToTranslate(Point p){
        matrix.setEntry(0,2,p.getX());
        matrix.setEntry(1,2,p.getY());
    }
    public void updateTranslate(Point p){

        matrix.setEntry(0,2, matrix.getEntry(0,2)+p.getX());
        matrix.setEntry(2,1, matrix.getEntry(1,2)+p.getY());
    }
    public void transposed(){
       matrix=(Array2DRowRealMatrix) matrix.transpose();
    }
    public AffineTransform2D transpose(){
      return new AffineTransform2D(matrix.transpose());
    }
    public void multiplyBySwing(AffineTransform transform){
        var t=fromSwing(transform);
        matrix.multiply(t.matrix);
    }
    public void multiply(AffineTransform2D matrix){
        matrix.multiply(matrix);

    }
    public void applyTranspose(Point vec){
        var t=matrix.transpose();
       var v= t.operate(toRealVector(vec));
       vec.x=Math.round((float)v.getEntry(0));
       vec.y=Math.round((float)v.getEntry(1));
    }
    private RealVector toRealVector(Point vec){
        ArrayRealVector v3=new ArrayRealVector(3);
        v3.setEntry(0,vec.getX());
        v3.setEntry(1, vec.getY());
        v3.setEntry(2,1);
        return  v3;
    }
    /**
     * Invert this matrix in-place
     * /
     */
    public void invert(){
        matrix=MatrixUtils.inverse(matrix);
    }
    /**
     * Return inverse of this matrix
     * /
     */
    public AffineTransform2D inverse(){
        return new AffineTransform2D(MatrixUtils.inverse(matrix));
    }
}

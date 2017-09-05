package com.github.nkzawa.socketio.androidchat.math;

import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.DecompositionFactory;
import org.ejml.interfaces.decomposition.SingularValueDecomposition;
import org.ejml.ops.CommonOps;
import org.ejml.ops.NormOps;
import org.ejml.ops.SingularOps;

/**
 * @author Faris McKay
 * @author Peter Abeles
 */

public class PCA {

    DenseMatrix64F denseMatrix64F;
    DenseMatrix64F storageMatrix = new DenseMatrix64F(1,1);

    private int componentCount, index;
    private double [] mean;

    public PCA(int dimensions,int dimension_length){
        index = 0;
        componentCount = 0;
        mean = new double[dimension_length];
        storageMatrix.reshape(dimensions, dimension_length, false);
    }

    public void addData(double[] data){
        if((storageMatrix.getNumCols() != data.length) || (index >= storageMatrix.getNumRows())){
            throw new IllegalArgumentException("Unexpected sample size");
        }
        for(int i=0; i<data.length; i++){
            storageMatrix.set(index, i, data[i]);
        }
        index++;
    }

    public void principleComponents(int componentCount){
        if( componentCount > storageMatrix.getNumCols() ) {
            throw new IllegalArgumentException("More components requested that the data's length.");
        }
        if( index != storageMatrix.getNumRows() ) {
            throw new IllegalArgumentException("Not all the data has been added");
        }
        if( componentCount > index ) {
            throw new IllegalArgumentException("More data needed to compute the desired number of components");
        }
        this.componentCount = componentCount;

        for( int i = 0; i < storageMatrix.getNumRows(); i++ ) {
            for(int j = 0; j < getMean().length; j++ ) {
                getMean()[j] += storageMatrix.get(i,j);
            }
        }
        for(int j = 0; j < getMean().length; j++ ) {
            getMean()[j] /= storageMatrix.getNumRows();
        }

        for( int i = 0; i < storageMatrix.getNumRows(); i++ ) {
            for(int j = 0; j < getMean().length; j++ ) {
                storageMatrix.set(i,j,storageMatrix.get(i,j)- getMean()[j]);
            }
        }

        SingularValueDecomposition<DenseMatrix64F> svd =
                DecompositionFactory.svd(storageMatrix.numRows, storageMatrix.numCols, false, true, false);
        if( !svd.decompose(storageMatrix) )
            throw new RuntimeException("SVD failed");
        denseMatrix64F = svd.getV(null,true);
        DenseMatrix64F W = svd.getW(null);
        SingularOps.descendingOrder(null,false,W,denseMatrix64F,true);
        denseMatrix64F.reshape(componentCount, getMean().length,true);
    }

    public double response( double[] sample ) {
        if( sample.length != storageMatrix.numCols ) {
            throw new IllegalArgumentException("Expected input vector to be in sample space");
        }
        DenseMatrix64F dots = new DenseMatrix64F(componentCount,1);
        DenseMatrix64F s = DenseMatrix64F.wrap(storageMatrix.numCols,1,sample);
        CommonOps.mult(denseMatrix64F,s,dots);
        return NormOps.normF(dots);
    }

    public double[] getMean() {
        return mean;
    }
}

package com.github.nkzawa.socketio.androidchat.math;

import com.github.nkzawa.socketio.androidchat.security.icmetric.ICMetricKeyGen;
import com.github.nkzawa.socketio.androidchat.util.Constants;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.stat.correlation.Covariance;

/**
 * Class to compute the covariance of inputted data
 *
 * @author Faris McKay
 * @version 09/02/2017
 */

public class CovMatrix {

    /**
     * Apply covariance statistical anlysis to the parsed in feature vector
     * and output the resulting 2D array of results
     * @param featureVector unprocessed raw data
     * @return the covariance matrix
     */
    public double[][] getCovarianceMatrix(double[][] featureVector){
        RealMatrix mx = MatrixUtils.createRealMatrix(featureVector);
        RealMatrix cov = new Covariance(mx.getData()).getCovarianceMatrix();
        return cov.getData();
    }

    /**
     * Generate a 2-dimensional array with each element being a 1D array of the feature measured 5
     * times to allow for statistical analysis to take place.
     * @return this array
     */
    public double [][] generateFeatureVector(){
        double [] [] featureVector = new double[8][3];
        ICMetricKeyGen keyGen = new ICMetricKeyGen(Constants.APPLICATION_CONTEXT);
        for(int i=0;i<3;i++){
            keyGen.startProcedure();
            featureVector[0][i] = keyGen.alphabeticIcmetrics[0].getValue();
            featureVector[1][i] = keyGen.alphabeticIcmetrics[1].getValue();
            featureVector[2][i] = keyGen.numericIcmetrics[0].getValue();
            featureVector[3][i] = keyGen.numericIcmetrics[1].getValue();
            featureVector[4][i] = keyGen.numericIcmetrics[2].getValue();
            featureVector[5][i] = keyGen.numericIcmetrics[3].getValue();
            featureVector[6][i] = keyGen.numericIcmetrics[4].getValue();
            featureVector[7][i] = keyGen.numericIcmetrics[5].getValue();
        }
        return featureVector;
    }

}

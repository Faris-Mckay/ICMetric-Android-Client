package com.github.nkzawa.socketio.androidchat.security.icmetric;

import com.github.nkzawa.socketio.androidchat.math.CovMatrix;
import com.github.nkzawa.socketio.androidchat.math.PCA;

/**
 * A file which handles  icmetric authentication and detection of
 *  os and hardware changes enough to trigger a rejection
 *
 * @author Faris McKay
 */
public class ICMetricAuth {

    /**
     * The instance to the class to perform covariance related operations
     */
    private CovMatrix covarianceMatrix = new CovMatrix();

    /**
     * Generates the ICMetrics 5 different times into a feature vector
     * and applies covariance statistical analysis on to this array
     * @return the resulting covariance matrix
     */
    public double[][] getFeatureCovariance(){
        double[][] featureVector = covarianceMatrix.generateFeatureVector();
        return covarianceMatrix.getCovarianceMatrix(featureVector);
    }

    /**
     * Apply PCA to each of the dimensions from them covariance matrix
     * and print the errors and the response of each
     * @param fullSamples is the covariance matrix
     */
    public double getPCAVector(double [][] fullSamples){
        PCA pca = new PCA(fullSamples.length, fullSamples[0].length);
        for(int i=0; i< fullSamples.length;i++){
            pca.addData(fullSamples[i]);
        }
        pca.principleComponents(fullSamples.length);
        return pca.response(pca.getMean());
    }

    /**
     * Round the given number to nearest 1000, 10000, 100000
     * or 1000000
     * @param response input
     * @return rounded value
     */
    public int roundResponse(double response){
        int length = String.valueOf((int)response).length();
        if(length > 3 && length < 6) {
            return (int) (response + 500) / 1000 * 1000;
        }
        if(length > 5 && length < 9) {
            return (int) (response + 5000) / 10000 * 10000;
        }
        if(length > 8 && length < 11) {
            return (int) (response + 50000) / 100000 * 100000;
        }
        return (int) (response + 500000) / 1000000 * 1000000;
    }

    /**
     * Add amount of zeros to number to make it a 128bit value
     * @param unpadded val which is not 16bytes
     * @return padded value with 1's or 0's added to make it 16bytes
     */
    public String convertTo128BitString(int unpadded){
        long padded = 1000000000000000L;
        long converted = (long)unpadded;
        return new String(Long.toString(padded + converted));

    }
}

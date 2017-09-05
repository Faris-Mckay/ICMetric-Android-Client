package com.github.nkzawa.socketio.androidchat.security.icmetric;

import com.github.nkzawa.socketio.androidchat.security.icmetric.ext.*;

import android.content.Context;

import java.util.List;

/**
 * Class to handle operating on the ICMetric features in order to properly consolidate
 * all of the data and carry out the relevant operations on them for the data to then be used to
 * generate a normalized multi-dimensional map for comparison and ICMetric key for use in the
 * RSA module
 *
 * @author Faris McKay
 * @version 01/02/2017.
 */

public class ICMetricKeyGen {

    /**
     * Stores the context of the application
     */
     private Context context;

    /**
     * Toggles on when the key has been created
     */
    private boolean hasKeyBeenGenerated;

    /**
     * Stores the generated icmetric key
     */
    private double icmetricKey;

    public ICMetricKeyGen(Context context){
        this.context = context;

        /**
         * Store all known numeric ICMetrics
         */
        getNumericIcmetrics()[0] = new AlgorithmCalcTime(context);
        getNumericIcmetrics()[1] = new DiskFreeSpace(context);
        getNumericIcmetrics()[2] = new OSMemoryUsage(context);
        getNumericIcmetrics()[3] = new ApplicationCount(context);
        //numericIcmetrics[4] = new CPUClockSpeed(context);
        getNumericIcmetrics()[4] = new CPUIdleLoad(context);
        getNumericIcmetrics()[5] = new ContactCount(context);

        /**
         * Store all known Alphabetic ICMetrics
         */
        getalphabeticIcmetrics()[0] = new ContactNames(context);
        getalphabeticIcmetrics()[1] = new ApplicationNames(context);
    }

    public static Feature[] numericIcmetrics = new Feature[7];

    public static Feature[] alphabeticIcmetrics = new Feature[2];

    /**
     * Stores the known array of current numeric icmetrics for operating on
     */
    public Feature[] getNumericIcmetrics() {
        return numericIcmetrics;
    }

    /**
     * Stores the known array of current alphabetic icmetrics for operating on
     */
    public Feature[] getalphabeticIcmetrics() {
        return alphabeticIcmetrics;
    }

    /**
     * Begins the operation of extracting and scaling each ICMetric
     */
    public void startProcedure() {
        drawKeys();
        scaleKeys();
        returnKeys();
    }

    /**
     * Force all features to carry out their individual extraction operation
     * and all alphabetic features to then be processed to be ready for the next stage
     */
    private void drawKeys(){
        for (Feature feature : getNumericIcmetrics()){
            if(feature != null) {
                feature.extract();
            }
        }
        for (Feature feature : getalphabeticIcmetrics()){
            feature.extract();
            if (feature.getType() == FeatureType.ALPHABETIC){
                feature.setValue(processAlphaValue(feature.getResult()));
            }
        }
    }

    /**
     * Scales each icmetric worth up or down by their given scale worth
     */
    private void scaleKeys(){
        for (Feature feature : getNumericIcmetrics()){
            if(feature != null) {
                feature.setValue(feature.scale() * feature.getValue());
            }
        }
        for (Feature feature : getalphabeticIcmetrics()){
            feature.setValue(feature.scale() * feature.getValue());
        }
    }

    /**
     * Set the value of the class's icmetric key to the total of all generated
     * outputs added together
     */
    private void returnKeys(){
        hasKeyBeenGenerated = true;
        for (Feature feature : getNumericIcmetrics()){
            if(feature != null) {
                this.icmetricKey = this.getIcmetricKey() + feature.getValue();
                System.out.println(feature.getValue());
            }
        }
        for (Feature feature : getalphabeticIcmetrics()){
            if (feature.getType() == FeatureType.ALPHABETIC){
                this.icmetricKey = this.getIcmetricKey() + feature.getValue();
                System.out.println(feature.getValue());
            }
        }
    }

    /**
     * Compute a value based on the characters given in the list
     * @param args the stored list of collected Strings
     * @return a double which represents this combination of chars
     */
    private double processAlphaValue(List<String> args){
        double val = 0;
        for (String word : args){
            char[] letters = word.toCharArray();
            for(char letter : letters){
                val += Character.getNumericValue(letter);
            }
        }
        return val;
    }

    /**
     * returns the generated ICMetricKey
     */
    public double getIcmetricKey() {
        if(hasKeyBeenGenerated != true){
            startProcedure();
        }
        return icmetricKey;
    }

}

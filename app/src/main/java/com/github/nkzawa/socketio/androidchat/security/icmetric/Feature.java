package com.github.nkzawa.socketio.androidchat.security.icmetric;

import android.app.Activity;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * A feature is an ICMetric blueprint to handle the object oriented basic details of how each metric
 * should function
 *
 * @author Faris McKay
 * @version 2/02/2017
 */

public abstract class Feature {

    /**
     * The instance of the app context
     */
    protected Context context;

    /**
     * Constructor to store the context instance and properly initialize the feature
     * @param context
     */
    public Feature(Context context){
        this.context = context;
    }

    /**
     * The value stores the final extracted metric numeric data to be operated upon
     */
    protected double value;

    /**
     * Information about which type of feature this is and how it should be
     * handled in the future
     * @return the enumeration type
     */
    public FeatureType getType(){
        return type();
    }

    /**
     * Setter of the value
     * @return
     */
    public double getValue(){
        return value;
    }

    /**
     * Handles the different ways each feature must uniquely perform or read in order
     * to measure their own unique characteristics
     */
    public abstract void extract();

    /**
     * Float multiplier value used to morph the final value into a more
     * accurate final weighting value based on how much the benchmark is able to fluctuate
     * in subsequent readings
     * @return
     */
    public abstract float scale();

    /**
     * Stores the individual feature type for each instanciation
     * @return the enum featureType
     */
    public abstract FeatureType type();

    /**
     * Stores the final list type results for alphabetic types of features
     * to be operated upon by conversion algorithms
     */
    private List<String> result = new LinkedList<>();

    /**
     * Getter for the list result
     * @return
     */
    public List<String> getResult() {
        return result;
    }

    /**
     * Setter for the list result
     * @param result
     */
    public void setResult(List<String> result) {
        this.result = result;
    }

    /**
     * Setter for the numeric value
     * @param value
     */
    public void setValue(double value) {
        this.value = value;
    }
}

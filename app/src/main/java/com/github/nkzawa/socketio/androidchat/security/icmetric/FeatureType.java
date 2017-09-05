package com.github.nkzawa.socketio.androidchat.security.icmetric;

/**
 * An enumerated type to be placed in each instanciation of feature to handle how it should be operated upon
 * in order to properly extract final data
 *
 * @author Faris McKay
 * @version 01/02/2017
 */

public enum FeatureType {

    /**
     * A numeric feature should be manipulated by the stored float value and only requires scaling
     * the numeric feature draws a benchmark or reading which is already in the right format after being
     * extracted and only requires the scaling for its importance to the overall ICMetric key
     */
    NUMERIC,

    /**
     * An alphabetic feature type requires an additional step in manipulation before its float value can be set
     * it is required to be processed by the ICMetricAlpha algorithm which converts is stored String values into float
     * type by processing each character in its list one at a time and assigning each char a numeric value in order
     * to get a resulting number, which should be the same each time this algorithm is performed on it
     */
    ALPHABETIC;

}

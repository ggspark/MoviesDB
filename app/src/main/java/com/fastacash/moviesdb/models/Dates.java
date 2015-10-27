package com.fastacash.moviesdb.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Gaurav Gupta <gaurav@thegauravgupta.com>
 * @since 27/Oct/2015
 */

public class Dates {

    @SerializedName("minimum")
    @Expose
    private String minimum;
    @SerializedName("maximum")
    @Expose
    private String maximum;

    /**
     * @return The minimum
     */
    public String getMinimum() {
        return minimum;
    }

    /**
     * @param minimum The minimum
     */
    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    /**
     * @return The maximum
     */
    public String getMaximum() {
        return maximum;
    }

    /**
     * @param maximum The maximum
     */
    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

}
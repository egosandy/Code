package com.rcdriver.cs.gmap.directions;

public class Distance {
    private String text;
    private long value;

    public Distance(String text, long value) {
        this.text = text;
        this.value = value;
    }

    public long getValue() {
        return value;
    }

}

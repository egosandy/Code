package com.rcdriver.cs.gmap.directions;

public class Duration {
    private String text;
    private long value;

    public Duration(String text, long value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

}

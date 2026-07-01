package com.rcdriver.cs.gmap.directions;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Route implements Serializable {

    private static final long serialVersionUID = 1L;
    private Bound bounds;
    private String copyrights;
    private List<Leg> legs;
    private List<LatLng> overviewPolyLine;
    private String summary;
    private List<LatLng> getOverviewPolyLine;

    public Route(Context context) {
        legs = new ArrayList<Leg>();
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void addLeg(Leg leg) {
        this.legs.add(leg);
    }

    public void setOverviewPolyLine(List<LatLng> overviewPolyLine) {
        this.overviewPolyLine = overviewPolyLine;
    }



    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Iterable<LatLng> getOverviewPolyLine() {
        return overviewPolyLine;
    }
}

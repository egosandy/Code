package com.rcdriver.cs.gmap;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.NodeList;

import java.util.ArrayList;

import com.rcdriver.cs.activity.MainActivity;
import com.rcdriver.cs.utils.Log;

public class GMapDirection {
    public final static String MODE_DRIVING = "driving";

    public GMapDirection() {
    }


    public String getUrl(LatLng start, LatLng end, String mode, boolean isAlternative) {

        String url = "https://maps.googleapis.com/maps/api/directions/json?"
                + "origin=" + start.latitude + "," + start.longitude
                + "&destination=" + end.latitude + "," + end.longitude
                + "&sensor=false&units=metric&mode=" + mode
                + "&key=" + MainActivity.apikey;

        if (isAlternative)
            url += "&alternatives=true";

        Log.e("getUrl", url);
        return url;
    }

    public String getUrlVia(String mode, boolean isAlternative, LatLng start, LatLng... end) {
        String via = "&waypoints=";
        if (end.length > 1) {
            for (LatLng end_latLng : end) {
                via += "via:" + end_latLng.latitude + "%2C" + end_latLng.longitude + "%7C";
            }
        } else {
            via = "";
        }


        String url = "https://maps.googleapis.com/maps/api/directions/json?"
                + "origin=" + start.latitude + "," + start.longitude
                + "&destination=" + end[end.length - 1].latitude + "," + end[end.length - 1].longitude
                + via
                + "&sensor=false&units=metric&mode=" + mode
                + "&key=" + MainActivity.apikey;

        if (isAlternative)
            url += "&alternatives=true";

        Log.e("getUrl", url);
        return url;
    }


    private int getNodeIndex(NodeList nl, String nodename) {
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeName().equals(nodename))
                return i;
        }
        return -1;
    }

    private ArrayList<LatLng> decodePoly(String encoded) {
        ArrayList<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng position = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(position);
        }
        return poly;
    }
}

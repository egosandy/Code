package com.rcdriver.cs.mwsdk.Utils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class Utilities {

    public static boolean isMarkerVisible(GoogleMap googleMap, LatLng newLocation) {
        return googleMap.getProjection().getVisibleRegion().latLngBounds.contains(newLocation);
    }

    public interface LatLngInterpolator {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

    }
}

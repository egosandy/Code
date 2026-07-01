package com.rcdriver.cs.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.PolylineOptions;

public class StrokedPolylineOptions implements Parcelable {

    private final PolylineOptions fill;
    private final PolylineOptions stroke;

    public float getStrokeWidth() {
        return stroke.getWidth() - fill.getWidth() / 2;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.fill, flags);
        dest.writeParcelable(this.stroke, flags);
    }

    protected StrokedPolylineOptions(Parcel in) {
        this.fill = in.readParcelable(PolylineOptions.class.getClassLoader());
        this.stroke = in.readParcelable(PolylineOptions.class.getClassLoader());
    }

    public static final Creator<StrokedPolylineOptions> CREATOR = new Creator<StrokedPolylineOptions>() {
        public StrokedPolylineOptions createFromParcel(Parcel source) {
            return new StrokedPolylineOptions(source);
        }

        public StrokedPolylineOptions[] newArray(int size) {
            return new StrokedPolylineOptions[size];
        }
    };
}
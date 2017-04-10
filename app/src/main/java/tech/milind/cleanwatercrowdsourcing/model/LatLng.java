package tech.milind.cleanwatercrowdsourcing.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Created by SuperLinguini on 3/27/2017.
 */

public class LatLng implements Parcelable {
    private Double latitude;
    private Double longitude;

    public LatLng() {}

    public LatLng(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    @SuppressWarnings("unused")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @SuppressWarnings("unused")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Lat/Lng: (%.3f, %.3f)", latitude, longitude);
    }

    LatLng(Parcel in) {
        latitude = in.readByte() == 0x00 ? null : in.readDouble();
        longitude = in.readByte() == 0x00 ? null : in.readDouble();
    }

    public com.google.android.gms.maps.model.LatLng getMapsLatLng() {
        return new com.google.android.gms.maps.model.LatLng(this.latitude,
                this.longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (latitude == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(longitude);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LatLng> CREATOR = new Parcelable.Creator<LatLng>() {
        @Override
        public LatLng createFromParcel(Parcel in) {
            return new LatLng(in);
        }

        @Override
        public LatLng[] newArray(int size) {
            return new LatLng[size];
        }
    };
}
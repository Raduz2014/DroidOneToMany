package com.simetrix.wmbusblueintelisread.contracts;

import android.os.Parcel;
import android.os.Parcelable;

public class BlueDevice implements Parcelable {
    private String devName;
    private String devMac;

    public BlueDevice(String devName, String devMac){
        this.devName = devName;
        this.devMac = devMac;
    }

    public BlueDevice(Parcel in){
        this.devName = in.readString();
        this.devMac = in.readString();
    }

    public String getDeviceName(){
        return devName;
    }

    public String getDevMac(){
        return devMac;
    }

    @Override
    public int hashCode() {
        return devName.hashCode() * devMac.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if(this == o) return true;
        if(o == null) return false;
        if(this.getClass() != o.getClass()) return false;

        BlueDevice b = (BlueDevice)o;
//            if(o instanceof BlueDevice){
        result = b.devMac.equals(devMac);
//            }
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.devName);
        dest.writeString(this.devMac);
    }

    public static final Parcelable.Creator<BlueDevice> CREATOR = new Parcelable.Creator<BlueDevice>(){

        @Override
        public BlueDevice createFromParcel(Parcel src) {
            return new BlueDevice(src);
        }

        @Override
        public BlueDevice[] newArray(int size) {
            return new BlueDevice[size];
        }
    };
}
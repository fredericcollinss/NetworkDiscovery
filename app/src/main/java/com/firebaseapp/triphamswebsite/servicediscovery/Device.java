package com.firebaseapp.triphamswebsite.servicediscovery;

/**
 * Created by phamm on 11/16/2017.
 */

public class Device {
    private String mDeviceName;
    private String mDeviceInfo;

    public Device(String name, String type) {
        mDeviceInfo = type;
        mDeviceName = name;
    }

    public String getName() {
        return mDeviceName;
    }

    public String getInfo() {
        return mDeviceInfo;
    }
}

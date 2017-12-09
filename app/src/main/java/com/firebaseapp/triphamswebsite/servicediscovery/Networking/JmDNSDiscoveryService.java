package com.firebaseapp.triphamswebsite.servicediscovery.Networking;

import android.content.Context;

/**
 * Created by phamm on 12/8/2017.
 */

public class JmDNSDiscoveryService extends DiscoveryService {

    private JmDNSDiscoveryService() {}
    @Override
    public DiscoveryService getServiceInstance(Context context) {
        if(mServiceInstance == null) {
            return new JmDNSDiscoveryService();
        } else {
            return mServiceInstance;
        }
    }

    @Override
    public void registerServiceListener(NetworkServiceListener listener) {

    }

    @Override
    public void unregisterServiceListener(NetworkServiceListener listener) {

    }
}

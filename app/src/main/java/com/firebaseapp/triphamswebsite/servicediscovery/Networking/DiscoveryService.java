package com.firebaseapp.triphamswebsite.servicediscovery.Networking;

import android.content.Context;

/**
 * Created by phamm on 12/8/2017.
 */

public abstract class DiscoveryService {

    protected DiscoveryService mServiceInstance;

    protected DiscoveryService() {}

    public abstract DiscoveryService getServiceInstance(Context context);

    public abstract void registerServiceListener(NetworkServiceListener listener);

    public abstract void unregisterServiceListener(NetworkServiceListener listener);

    public abstract void close();
}

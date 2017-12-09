package com.firebaseapp.triphamswebsite.servicediscovery.Networking;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;

/**
 * Created by phamm on 12/8/2017.
 */

interface NetworkServiceListener {
    void onServiceResolved(ServiceEvent event);
    void onServiceRemove(ServiceEvent event);
}

package com.firebaseapp.triphamswebsite.servicediscovery.Networking;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;

/**
 * Created by phamm on 12/8/2017.
 */

public class JmDNSDiscoveryService extends DiscoveryService {

    /* Multi-cast lock*/
    private android.net.wifi.WifiManager.MulticastLock lock;
    private NetworkServiceListener mServiceListenerCallback;
    private static JmDNS mJmDNS;
    private ServiceTypeListener mServiceTypeListener = new ServiceTypeListener() {
        @Override
        public void serviceTypeAdded(ServiceEvent event) {
            mJmDNS.addServiceListener(event.getType(), mServiceListener);
        }

        @Override
        public void subTypeForServiceTypeAdded(ServiceEvent event) {

        }
    };

    private ServiceListener mServiceListener = new ServiceListener() {
        @Override
        public void serviceAdded(ServiceEvent event) {
            mJmDNS.requestServiceInfo(event.getType(), event.getName());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            mServiceListenerCallback.onServiceRemove(event);
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            mServiceListenerCallback.onServiceResolved(event);
        }
    };

    private JmDNSDiscoveryService() {
    }

    @Override
    public DiscoveryService getServiceInstance(Context context) {
        if (mServiceInstance == null) {
            mServiceInstance = new JmDNSDiscoveryService();
            ((JmDNSDiscoveryService) mServiceInstance).setup(context);
        }

        return mServiceInstance;
    }

    @Override
    public void registerServiceListener(NetworkServiceListener listener) {
        mServiceListenerCallback = listener;
    }

    @Override
    public void unregisterServiceListener(NetworkServiceListener listener) {
        if (mServiceListenerCallback == listener) {
            mServiceListenerCallback = null;
        }

    }

    @Override
    public void close() {
        if(mJmDNS != null) {
            mJmDNS.unregisterAllServices();
            try {
                mJmDNS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mJmDNS = null;
        }
        // Release the lock
        lock.release();
    }

    private void setup(final Context context) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getApplicationContext().getSystemService(android.content.Context.WIFI_SERVICE);

                /*Allows an application to receive
                Wifi Multicast packets. Normally the Wifi stack
                filters out packets not explicitly addressed to
                this device. Acquiring a MulticastLock will cause
                the stack to receive packets addressed to multicast
                addresses. Processing these extra packets can
                cause a noticable battery drain and should be
                disabled when not needed. */
                lock = wifi.createMulticastLock(getClass().getSimpleName());

                /*Controls whether this is a reference-counted or
                non-reference- counted MulticastLock.
                Reference-counted MulticastLocks keep track of the
                number of calls to acquire() and release(), and
                only stop the reception of multicast packets when
                every call to acquire() has been balanced with a
                call to release(). Non-reference- counted
                MulticastLocks allow the reception of multicast
                packets whenever acquire() is called and stop
                accepting multicast packets whenever release() is
                called.*/
                lock.setReferenceCounted(false);

                try {
                    InetAddress addr = getLocalIpAddress(context);
                    String hostname = addr.getHostName();
                    lock.acquire();
                    mJmDNS = JmDNS.create(addr, hostname);
                    mJmDNS.addServiceTypeListener(mServiceTypeListener);

                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }).start();

    }


    private InetAddress getLocalIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        InetAddress address = null;
        try {
            address = InetAddress.getByName(String.format(Locale.ENGLISH,
                    "%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address;
    }
}

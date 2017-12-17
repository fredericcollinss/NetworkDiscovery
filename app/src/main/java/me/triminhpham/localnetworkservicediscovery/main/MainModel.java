package me.triminhpham.localnetworkservicediscovery.main;

/**
 * Created on 12/16/2017.
 */

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;

import me.triminhpham.localnetworkservicediscovery.data.LocalServiceInfo;

/**
 * Service model that holds information of found services on the local network
 * The model also keeps track of an engine that support local service discovery
 *
 * @author Tri Pham
 */
public class MainModel implements MainMvp.Model {
    private static final String TAG = MainModel.class.getSimpleName();
    private android.net.wifi.WifiManager.MulticastLock mMulticastLock;
    private static JmDNS mJmDNS;
    private List<LocalServiceInfo> mServiceInfoList;


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

        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            boolean serviceAdded = MainModel.this.addServiceInfo(event);
            if(serviceAdded) {
                // notify mServiceListenerCallback
            }
        }
    };

    private ServiceListenerCallback mServiceFoundListener;

    public MainModel(Context applicationContext,
                     ServiceListenerCallback listener,
                     List<LocalServiceInfo> serviceInfoStorage)
    {
        mServiceInfoList = serviceInfoStorage;
        mServiceFoundListener = listener;
        setup(applicationContext);
    }

    private void setup(final Context context) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                android.net.wifi.WifiManager wifi =
                        (android.net.wifi.WifiManager) context.getApplicationContext().getSystemService(android.content.Context.WIFI_SERVICE);

                /*Allows an application to receive Wifi Multicast packets. Normally the Wifi stack
                filters out packets not explicitly addressed to this device. Acquiring a MulticastLock will cause
                the stack to receive packets addressed to multicast addresses. Processing these extra packets can
                cause a noticable battery drain and should be disabled when not needed. */
                mMulticastLock = wifi.createMulticastLock(getClass().getSimpleName());

                /*Controls whether this is a reference-counted or non-reference- counted MulticastLock.
                Reference-counted MulticastLocks keep track of the number of calls to acquire() and release(), and
                only stop the reception of multicast packets when every call to acquire() has been balanced with a
                call to release(). Non-reference- counted MulticastLocks allow the reception of multicast
                packets whenever acquire() is called and stop accepting multicast packets whenever release() is
                called.*/
                mMulticastLock.setReferenceCounted(false);

                try {
                    InetAddress addr = getLocalIpAddress(context);
                    String hostname = addr.getHostName();
                    mMulticastLock.acquire();
                    mJmDNS = JmDNS.create(addr, hostname);
                    mJmDNS.addServiceTypeListener(mServiceTypeListener);
                    Log.i(TAG, "Finished setup");
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }).start();

    }

    /**
     * Get local IP address of this device using Wifi Manager service
     *
     * @param context the context to be used to get WifiManager service
     * @return InetAddress that represents the host address of this device
     */
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


    @Override
    public void close() {

    }

    @Override
    public List<LocalServiceInfo> getDeviceList() {
        List<LocalServiceInfo> copyList = new ArrayList<>();
        copyList.addAll(mServiceInfoList);
        return copyList;
    }

    /**
     * Add service event to local device list.
     *
     * @param event the event to be added
     * @return true if a new service info was added to the model, false otherwise
     */
    private boolean addServiceInfo(ServiceEvent event) {

        if(event.getInfo().getInetAddresses().length == 0) {
            return false;
        }

        String serviceName = event.getName();
        InetAddress serviceAddress = event.getInfo().getInetAddresses()[0];
        int servicePort = event.getInfo().getPort();

        LocalServiceInfo info = new LocalServiceInfo(serviceName, serviceAddress, servicePort);
        if(!hasServiceInfo(info)) {
            return true;
        }

        return false;
    }

    /**
     * Check whether or not the service is already in mServiceInfoList
     *
     * @param serviceInfo LocalServiceInfo object to be checked
     * @return true if the service info exists within mServiceInfoList, false otherwise
     */
    private boolean hasServiceInfo(LocalServiceInfo serviceInfo) {
        for (LocalServiceInfo info : mServiceInfoList) {
            if (serviceInfo.equals(info)) {
                return true;
            }
        }
        return false;
    }

}

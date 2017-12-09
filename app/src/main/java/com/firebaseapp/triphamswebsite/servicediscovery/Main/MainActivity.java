package com.firebaseapp.triphamswebsite.servicediscovery.Main;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebaseapp.triphamswebsite.servicediscovery.R;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceTypeListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    public class AllTypeServiceListerner implements ServiceTypeListener {

        @Override
        public void serviceTypeAdded(ServiceEvent event) {
            jmdns.addServiceListener(event.getType(), new ServiceLister());
        }

        @Override
        public void subTypeForServiceTypeAdded(ServiceEvent event) {

        }
    }

    public class ServiceLister implements javax.jmdns.ServiceListener {

        @Override
        public void serviceAdded(ServiceEvent event) {
            jmdns.requestServiceInfo(event.getType(), event.getName());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {

        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            MainActivity.this.addDeviceToDataSet(event.getInfo());
        }
    }

    private void addDeviceToDataSet(final ServiceInfo serviceInfo) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDeviceList.add(serviceInfo);
                mDeviceAdapter.notifyItemInserted(mDeviceList.size() - 1);
            }
        });

    }

    /****************************************************
     * GUI components
     ***************************************************/
    private RecyclerView mDeviceListRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DeviceAdapter mDeviceAdapter;
    private List<ServiceInfo> mDeviceList;

    /* Multi-cast lock*/
    private android.net.wifi.WifiManager.MulticastLock lock;

    private JmDNS jmdns = null;
    private ServiceTypeListener listener = null;

    @Override
    protected void onStart() {
        super.onStart();
        setUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDeviceListRecyclerView = (RecyclerView) findViewById(R.id.rv_device_list);


        mLayoutManager = new LinearLayoutManager(this);
        mDeviceListRecyclerView.setLayoutManager(mLayoutManager);
        mDeviceListRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        mDeviceList = new ArrayList<>();
        mDeviceAdapter = new DeviceAdapter(mDeviceList);
        mDeviceListRecyclerView.setAdapter(mDeviceAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Unregister services
        if (jmdns != null) {
            if (listener != null) {

                listener = null;
            }
            jmdns.unregisterAllServices();
            try {
                jmdns.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            jmdns = null;
        }
        //Release the lock
        lock.release();
    }

    private void setUp() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) getApplicationContext().getSystemService(android.content.Context.WIFI_SERVICE);

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
                    InetAddress addr = getLocalIpAddress();
                    String hostname = addr.getHostName();
                    lock.acquire();
                    jmdns = JmDNS.create(addr, hostname);
                    listener = new AllTypeServiceListerner();

                    jmdns.addServiceTypeListener(listener);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }).start();

    }

    private InetAddress getLocalIpAddress() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
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

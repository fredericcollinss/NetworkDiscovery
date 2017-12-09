package com.firebaseapp.triphamswebsite.servicediscovery.Main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebaseapp.triphamswebsite.servicediscovery.Networking.DiscoveryService;
import com.firebaseapp.triphamswebsite.servicediscovery.Networking.JmDNSDiscoveryService;
import com.firebaseapp.triphamswebsite.servicediscovery.Networking.NetworkServiceListener;
import com.firebaseapp.triphamswebsite.servicediscovery.R;

import java.util.ArrayList;
import java.util.List;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;

public class MainActivity extends AppCompatActivity implements NetworkServiceListener {


    /****************************************************
     * GUI components
     ***************************************************/
    private RecyclerView mDeviceListRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DeviceAdapter mDeviceAdapter;
    private List<ServiceInfo> mDeviceList;
    private DiscoveryService mDiscoveryService;

    @Override
    protected void onStart() {
        super.onStart();
        mDiscoveryService = JmDNSDiscoveryService.getServiceInstance(this);
        mDiscoveryService.registerServiceListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("List size", "Size = " + mDeviceList.size());
        mDeviceAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDeviceListRecyclerView = findViewById(R.id.rv_device_list);

        mLayoutManager = new LinearLayoutManager(this);
        mDeviceListRecyclerView.setLayoutManager(mLayoutManager);
        mDeviceListRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mDeviceList = new ArrayList<>();
        mDeviceAdapter = new DeviceAdapter(mDeviceList);
        mDeviceListRecyclerView.setAdapter(mDeviceAdapter);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDiscoveryService.close();
    }

    @Override
    public synchronized void onServiceResolved(ServiceEvent event) {
        mDeviceList.add(event.getInfo());
        updateUI();
    }

    @Override
    public void onServiceRemove(ServiceEvent event) {

    }

    private void updateUI() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDeviceAdapter.notifyDataSetChanged();
            }
        });
    }
}

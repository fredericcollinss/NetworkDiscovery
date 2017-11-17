package com.firebaseapp.triphamswebsite.servicediscovery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mDeviceListRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DeviceAdapter mDeviceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDeviceListRecyclerView = (RecyclerView) findViewById(R.id.rv_device_list);

        mLayoutManager = new LinearLayoutManager(this);
        mDeviceListRecyclerView.setLayoutManager(mLayoutManager);

        mDeviceAdapter = new DeviceAdapter(new ArrayList<Device>());
        mDeviceListRecyclerView.setAdapter(mDeviceAdapter);

    }
}

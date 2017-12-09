package com.firebaseapp.triphamswebsite.servicediscovery.Main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebaseapp.triphamswebsite.servicediscovery.R;

import java.net.InetAddress;
import java.util.List;

import javax.jmdns.ServiceInfo;

/**
 * Created by phamm on 11/16/2017.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private List<ServiceInfo> mDeviceList;

    public DeviceAdapter(List<ServiceInfo> deviceList) {
        mDeviceList = deviceList;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_item, parent, false);
        DeviceViewHolder viewHolder = new DeviceViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        holder.bindView(mDeviceList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder{

        public TextView mDeviceNameTextView;
        public TextView mDeviceIpTextView;
        public TextView mDevicePortTextView;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            mDeviceNameTextView = itemView.findViewById(R.id.tv_device_name);
            mDeviceIpTextView = itemView.findViewById(R.id.tv_device_ip);
            mDevicePortTextView = itemView.findViewById(R.id.tv_device_port);
        }

        public void bindView(ServiceInfo info) {
            if(info != null) {
                String name = info.getName() == null ? "Unknown name" : info.getName();
                String ipAddress = info.getInet4Addresses().length > 0 ? String.valueOf(info.getInet4Addresses()[0]) : "Unknown IP address";
                String port = String.valueOf(info.getPort());

                mDeviceNameTextView.setText("Device name: " + info.getName());
                mDeviceIpTextView.setText("IP address: " + info.getInet4Addresses()[0]);
                mDevicePortTextView.setText("Port: " + String.valueOf(info.getPort()));
            }
        }
    }
}



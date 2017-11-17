package com.firebaseapp.triphamswebsite.servicediscovery;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by phamm on 11/16/2017.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private List<Device> mDeviceList;

    public DeviceAdapter(List<Device> deviceList) {
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
        public TextView mDeviceInfoTextView;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            mDeviceNameTextView = (TextView) itemView.findViewById(R.id.tv_device_name);
            mDeviceInfoTextView = (TextView) itemView.findViewById(R.id.tv_device_info);
        }

        public void bindView(Device device) {
            mDeviceNameTextView.setText(device.getName());
            mDeviceInfoTextView.setText(device.getInfo());
        }
    }
}



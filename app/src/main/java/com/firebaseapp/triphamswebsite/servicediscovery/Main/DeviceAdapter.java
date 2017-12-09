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
        public TextView mDeviceInfoTextView;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            mDeviceNameTextView = (TextView) itemView.findViewById(R.id.tv_device_name);
            mDeviceInfoTextView = (TextView) itemView.findViewById(R.id.tv_device_info);
        }

        public void bindView(ServiceInfo info) {
            mDeviceNameTextView.setText("Device name: " + info.getName());
            StringBuilder sb = new StringBuilder();
            InetAddress[] addresses = info.getInetAddresses();
            for (InetAddress address : addresses) {
                sb.append(address).append(':').append(info.getPort()).append(' ');
            }
            String[] urls = info.getURLs();
            StringBuilder urlSB = new StringBuilder();

            for(String url: urls) {
                urlSB.append(url + "\n");
            }
            mDeviceInfoTextView.setText("IP addresses: " + sb.toString() + "\n"
                        +"URL: " + urlSB.toString());
        }
    }
}



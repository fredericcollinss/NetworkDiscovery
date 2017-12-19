package me.triminhpham.localnetworkservicediscovery.main;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.triminhpham.localnetworkservicediscovery.R;
import me.triminhpham.localnetworkservicediscovery.data.LocalServiceInfo;

/**
 * Created by phamm on 12/18/2017.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_service_name)
        TextView mServiceNameTextView;

        @BindView(R.id.tv_service_ip)
        TextView mServiceIpTextView;

        @BindView(R.id.tv_service_port)
        TextView mServicePortTextView;

        private Context mContext;
        public ServiceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindView(LocalServiceInfo serviceInfo) {
            mServiceNameTextView.setText(serviceInfo.getName());
            StringBuilder ipAddressBuilder = new StringBuilder();
            Resources resource = mContext.getResources();
            ipAddressBuilder.append(resource.getString(R.string.ip_address_label) + ": "
                    + serviceInfo.getAddress().toString().substring(1));
            mServiceIpTextView.setText(ipAddressBuilder.toString());

            StringBuilder portBuilder = new StringBuilder();
            portBuilder.append(resource.getString(R.string.port_label))
                    .append(": ")
                    .append(String.valueOf(serviceInfo.getPort()));
            mServicePortTextView.setText(portBuilder.toString());
        }
    }

    private MainMvp.Presenter mPresenter;
    private List<LocalServiceInfo> mLocalServiceInfoList;

    public ServiceAdapter(MainMvp.Presenter presenter) {
        mPresenter = presenter;
        mLocalServiceInfoList = presenter.getServiceList();
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list_item, parent, false);

        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        holder.bindView(mLocalServiceInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mLocalServiceInfoList.size();
    }
}

package me.triminhpham.localnetworkservicediscovery.main;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.triminhpham.localnetworkservicediscovery.R;
import me.triminhpham.localnetworkservicediscovery.data.LocalServiceInfo;

public class MainActivity extends AppCompatActivity implements MainMvp.View {
    public static final String TAG = MainActivity.class.getSimpleName();
    private List<LocalServiceInfo> mLocalServiceInfoList = new ArrayList<>();
    private ServiceAdapter mServiceAdapter;
    @BindView(R.id.rv_service_list) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainModel model = new MainModel(getApplicationContext(), mLocalServiceInfoList);
        MainMvp.Presenter presenter = new MainPresenter(this, model);
        model.setPresenterCallback(presenter);

        mServiceAdapter = new ServiceAdapter(mLocalServiceInfoList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mServiceAdapter);

        // add the decoration to the recyclerView
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(decoration);
    }

    @Override
    public void showServiceList(List<LocalServiceInfo> services) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mServiceAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showService(LocalServiceInfo serviceInfo) {

    }

    @Override
    public void removeService(LocalServiceInfo serviceInfo) {

    }
}
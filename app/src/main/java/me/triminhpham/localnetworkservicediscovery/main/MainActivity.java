package me.triminhpham.localnetworkservicediscovery.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.triminhpham.localnetworkservicediscovery.R;

public class MainActivity extends AppCompatActivity implements MainMvp.View {
    public static final String TAG = MainActivity.class.getSimpleName();
    private ServiceAdapter mServiceAdapter;
    @BindView(R.id.rv_service_list) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainModel model = new MainModel(getApplicationContext());
        MainMvp.Presenter presenter = new MainPresenter(this, model);
        model.setPresenterCallback(presenter);

        mServiceAdapter = new ServiceAdapter(presenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mServiceAdapter);

        // add the decoration to the recyclerView
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(decoration);
    }


    @Override
    public void showService(final int position) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO This is not efficient, must be optimized later
                mServiceAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void removeService(int position) {
        mServiceAdapter.notifyItemRemoved(position);
    }
}
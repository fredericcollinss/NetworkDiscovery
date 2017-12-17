package me.triminhpham.localnetworkservicediscovery.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.triminhpham.localnetworkservicediscovery.R;
import me.triminhpham.localnetworkservicediscovery.data.LocalServiceInfo;

public class MainActivity extends AppCompatActivity implements MainMvp.View {
    public static final String TAG = MainActivity.class.getSimpleName();
    private List<LocalServiceInfo> mLocalServiceInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainModel model = new MainModel(getApplicationContext(), mLocalServiceInfos);
        MainMvp.Presenter presenter = new MainPresenter(this, model);
        model.setPresenterCallback(presenter);
    }
}
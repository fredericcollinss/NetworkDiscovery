package me.triminhpham.localnetworkservicediscovery.main;

import me.triminhpham.localnetworkservicediscovery.data.LocalServiceInfo;

/**
 * Created by tripham on 12/17/17.
 */

public class MainPresenter implements MainMvp.Presenter {

    private MainMvp.View mView;
    private MainMvp.Model mModel;

    /**
     * Construct a presenter
     *
     * @param view  a view that will be controlled by this presenter
     * @param model a model that provide discovery service
     */
    public MainPresenter(MainMvp.View view, MainMvp.Model model) {
        mView = view;
        mModel = model;
    }

    public void onServiceFound(LocalServiceInfo serviceInfo) {

    }

    @Override
    public void onServiceRemove(LocalServiceInfo serviceInfo) {

    }

    @Override
    public void setModel(MainMvp.Model model) {
        mModel = model;
    }

    @Override
    public void setView(MainMvp.View view) {
        mView = view;
    }
}

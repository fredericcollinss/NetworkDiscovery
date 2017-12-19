package me.triminhpham.localnetworkservicediscovery.main;

import java.util.List;

import me.triminhpham.localnetworkservicediscovery.data.LocalServiceInfo;

/**
 * Created by phamm on 12/16/2017.
 */

public interface MainMvp {
    interface View {
        /**
         * Display list of services
         *
         * @param services list of services to be displayed
         */
        void showServiceList(List<LocalServiceInfo> services);


        /**
         * Display a new services
         *
         * @param serviceInfo service to be display
         */
        void showService(LocalServiceInfo serviceInfo);

        /**
         * Remove a service from the view
         *
         * @param serviceInfo service to be removed
         */
        void removeService(LocalServiceInfo serviceInfo);
    }

    interface Presenter {
        /**
         * Callback to be called when a new service is found
         *
         * @param serviceInfo LocalServiceInfor that contains information
         *                    of the newly discovered service
         */
        void onServiceFound(LocalServiceInfo serviceInfo);

        /**
         * Callback to be called when a service has been removed
         *
         * @param serviceInfo LocalServiceInfo that contains information
         *                    of removed service
         */
        void onServiceRemove(LocalServiceInfo serviceInfo);

        /**
         * Set the model
         *
         * @param model model to be set
         */
        void setModel(MainMvp.Model model);

        /**
         * Set view
         *
         * @param view new view
         */
        void setView(MainMvp.View view);
    }

    interface Model {

        /**
         * Set the presenter callback entity that can be called when something changes to the model
         *
         * @param presenterCallback presenter listener
         */
        void setPresenterCallback(MainMvp.Presenter presenterCallback);

        /**
         * Call to release any resource used by the model
         */
        void close();

        /**
         * Get list of all found devices
         *
         * @return list of devices known to this model
         */
        List<LocalServiceInfo> getDeviceList();

    }
}

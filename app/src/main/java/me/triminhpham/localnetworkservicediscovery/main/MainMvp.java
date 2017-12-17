package me.triminhpham.localnetworkservicediscovery.main;

import java.util.List;

import me.triminhpham.localnetworkservicediscovery.data.LocalServiceInfo;

/**
 * Created by phamm on 12/16/2017.
 */

public interface MainMvp {
    interface View {

    }

    interface Presenter {

    }

    interface Model {

        /**
         * This interface must be implemented by any class that wants
         * to be notify about service events emits by MainMvp.Model
         */
        interface ServiceListenerCallback {
            void onServiceFound(LocalServiceInfo serviceInfo);

            void onServiceRemove(LocalServiceInfo serviceInfo);
        }

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

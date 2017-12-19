package me.triminhpham.localnetworkservicediscovery.data;

import java.net.InetAddress;

/**
 * Created by Tri Pham on 12/16/2017.
 */


/**
 * LocalServiceInfo contains information about a service within a local network
 *
 * @author Tri Pham
 */
public class LocalServiceInfo {

    private String mName;
    private InetAddress mAddress;
    private int mPort;

    /**
     * Construct a LocalServiceInfo
     *
     * @param name    name of the service
     * @param address address of the local service
     * @param port    port where the service is offered
     */
    public LocalServiceInfo(String name, InetAddress address, int port) {
        mName = name;
        mAddress = address;
        mPort = port;
    }

    /**
     * Get name of the service
     *
     * @return String object that represents name of this service
     */
    public String getName() {
        return mName;
    }


    /**
     * Get address of this service
     *
     * @return InetAddress that represents IP address of this service
     */
    public InetAddress getAddress() {
        return mAddress;
    }


    /**
     * Get port of this service
     *
     * @return port number of this service
     */
    public int getPort() {
        return mPort;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LocalServiceInfo)) {
            return false;
        }
        LocalServiceInfo serviceInfo = (LocalServiceInfo) obj;
        return (this.mName == serviceInfo.mName)
                && (this.mAddress.equals(serviceInfo.mAddress))
                && (this.mPort == serviceInfo.mPort);
    }
}

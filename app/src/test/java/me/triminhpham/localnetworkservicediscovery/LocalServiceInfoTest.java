package me.triminhpham.localnetworkservicediscovery;

import org.junit.Test;

import me.triminhpham.localnetworkservicediscovery.data.LocalServiceInfo;

import static org.junit.Assert.*;
/**
 * Created by tripham on 12/17/17.
 */

public class LocalServiceInfoTest {
    @Test
    public void localServiceInfo_equalTest() throws Exception {
        LocalServiceInfo serviceInfo1 = new LocalServiceInfo("name", null, 90);
        LocalServiceInfo serviceInfo2 = new LocalServiceInfo("name", null, 90);

        assertTrue(serviceInfo1.equals(serviceInfo2));
    }
}

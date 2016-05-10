package com.automation.remarks.remote.hub;

import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.Registry;
import org.openqa.grid.selenium.proxy.DefaultRemoteProxy;

/**
 * Created by Serhii_Pirohov on 10.05.2016.
 */
public class HubProxy extends DefaultRemoteProxy {

    public HubProxy(RegistrationRequest request, Registry registry) {
        super(request, registry);
    }


}

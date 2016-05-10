package com.automation.remarks.remote;

import org.openqa.grid.selenium.GridLauncher;

/**
 * Created by Serhii_Pirohov on 10.05.2016.
 */
public class StartGrid {

    public static void main(String[] args) throws Exception {
        String[] a = {"-port", "4444",
                "-host", "localhost",
                "-role", "hub",
                "-servlets", "com.automation.remarks.remote.hub.VideoRecordingServlet" };
        GridLauncher.main(a);
    }

}

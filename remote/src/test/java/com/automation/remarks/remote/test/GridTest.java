package com.automation.remarks.remote.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.Test;

/**
 * Created by Serhii_Pirohov on 11.05.2016.
 */
public class GridTest {

    @Test
    public void testName() throws Exception {
        Configuration.remote = "http://localhost:4444/wd/hub";
        Selenide.open("http://google.com.ua");
        Thread.sleep(5000);
    }
}

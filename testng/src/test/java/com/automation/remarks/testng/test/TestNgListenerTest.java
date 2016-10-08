package com.automation.remarks.testng.test;

import com.automation.remarks.testng.VideoListener;
import org.testng.annotations.Listeners;

/**
 * Created by sepi on 05.10.16.
 */
@Listeners({VideoListener.class})
public class TestNgListenerTest {
}

@Listeners({CustomVideoListener.class})
class TestNgCustomVideoListenerTest {

}
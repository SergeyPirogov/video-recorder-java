package com.automation.video.test;

import com.automation.video.annotations.Video;
import com.automation.video.testng.VideoListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by sergey on 3/27/16.
 */
@Listeners(VideoListener.class)
public class TestVideoRecording {

    @Test
    @Video(enabled = false)
    public void testName() throws Exception {
        open("http://ukr.net");
        $(".login > input").val("Hello");
        $(".password > input").val("test");
        $(".submit > bu").click();
    }

    @Test
    @Video(name = "second_test")
    public void testName2() throws Exception {
        open("http://ukr.net");
        $(".login > input").val("User");
        $(".password > input").val("User");
        $(".submit > button").click();
    }

    @Test
    @Video()
    public void testName3() throws Exception {
        open("http://ukr.net");
        $(".login > input").val("Hello");
        $(".password > input").val("test");
        $(".submit > bu").click();
    }
}

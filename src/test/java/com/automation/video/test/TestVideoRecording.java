package com.automation.video.test;

import com.automation.video.annotations.Video;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by sergey on 3/27/16.
 */
public class TestVideoRecording {

    @Test
    @Video
    public void testName() throws Exception {
        open("http://ukr.net");
        $(".login > input").val("Hello");
        $(".password > input").val("test");
        $(".submit > button").click();
    }

    @Test
    public void testName2() throws Exception {
        open("http://ukr.net");
        $(".login > input").val("User");
        $(".password > input").val("User");
        $(".submit > button").click();
    }
}

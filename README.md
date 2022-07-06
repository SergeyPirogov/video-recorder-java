# VideoRecorder

[![Build Status](https://travis-ci.org/SergeyPirogov/VideoRecorder.svg?branch=master)](https://travis-ci.org/SergeyPirogov/VideoRecorder)

This library allows easily record video of your UI tests by just putting couple annotations.

Supports popular Java test frameworks: JUnit, TestNg, Spock, Selenium Grid

```
<dependency>
  <groupId>com.automation-remarks</groupId>
  <artifactId>video-recorder-junit</artifactId>
  <version>LATEST</version>
</dependency>
```

Example with JUnit Rule:

```
import com.automation.remarks.video.annotations.Video;  
import com.automation.remarks.video.junit.VideoRule;  
import org.junit.Rule;  
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class JUnitVideoTest {

    @Rule
    public VideoRule videoRule = new VideoRule();

    @Test
    @Video
    public void shouldFailAndCreateRecordWithTestName() {
        Thread.sleep(5000);
        assert false;
    }

    @Test
    @Video(name = "second_test")
    public void videoShouldHaveNameSecondTest() {
        Thread.sleep(10000);
        assertTrue(false);
    }

    @Test
    @Video(name = "third_test", enabled = false)
    public void shouldFailWithoutRecording() {
        Thread.sleep(10000);
        assertTrue(false);
    }
}
```

Example with TestNG:

```
<dependency>
  <groupId>com.automation-remarks</groupId>
  <artifactId>video-recorder-testng</artifactId>
  <version>LATEST</version>
</dependency>
```
By default recorder will include testng dependency 7.4.0. If you want to place other version of TestNG use this declaration:

<dependency>
   <groupId>com.automation-remarks</groupId>
   <artifactId>video-recorder-testng</artifactId>
   <version>2.1</version>
   <exclusions>
      <exclusion>
         <groupId>org.testng</groupId>
         <artifactId>testng</artifactId>
       </exclusion>
    </exclusions>
</dependency>

```
import com.automation.remarks.video.annotations.Video;  
import com.automation.remarks.video.testng.VideoListener;  
import org.testng.annotations.Listeners;  
import org.testng.annotations.Test;

import static junit.framework.Assert.assertTrue;

@Listeners(VideoListener.class)
public class TestNgAnnotationTest {

    @Test
    @Video
    public void shouldFailAndCreateRecordWithTestName() {
        Thread.sleep(1000);
        assert false;
    }

    @Test
    @Video(name = "second_test")
    public void videoShouldHaveNameSecondTest(){
        Thread.sleep(1000);
        assertTrue(false);
    }

    @Test
    @Video(name = "third_test", enabled = false)
    public void shouldFailWithoutRecording() {
        Thread.sleep(1000);
        assertTrue(false);
    }
}
```

Remote Video Recording:

Build remote module:

```
./gradlew remote:jar
```

Run hub:

```
java -jar video-recorder-remote-1.0.jar -role hub -servlets "com.automation.remarks.remote.hub.Video"
```

Run node:

```
java -jar video-recorder-remote-1.0.jar -servlets "com.automation.remarks.remote.node.VideoServlet" -role node -port 5555 -hub "http://localhost:4444/grid/register"
```

TestNG + Remote Video recorder

Change listener in your tests to RemoteVideoListener:

```
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.testng.VideoListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertTrue;

@Listeners(RemoteVideoListener.class)
public class TestNgAnnotationTest {

    @Test
    @Video
    public void shouldFailAndCreateRecordWithTestName() {
        Thread.sleep(1000);
        assert false;
    }

    @Test
    @Video(name = "second_test")
    public void videoShouldHaveNameSecondTest(){
        Thread.sleep(1000);
        assertTrue(false);
    }

    @Test
    @Video(name = "third_test", enabled = false)
    public void shouldFailWithoutRecording() {
        Thread.sleep(1000);
        assertTrue(false);
    }
}
```

more [details](http://automation-remarks.com/remote-recorder/)

by [automation-remarks.com](http://automation-remarks.com/)
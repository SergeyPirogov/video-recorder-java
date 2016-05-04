# VideoRecorder

This library allows easily record video of your UI tests by just putting couple annotations.

Supports popular Java test frameworks: JUnit, TestNg, Spock.

```
<dependency>
  <groupId>com.automation-remarks</groupId>
  <artifactId>video-recorder</artifactId>
  <version>1.0.+</version>
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

by [automation-remarks.com](http://automation-remarks.com/)
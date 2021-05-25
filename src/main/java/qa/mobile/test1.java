package qa.mobile;

import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;

public class test1 {
	public static AppiumDriver driver;
  @Test
  public void f() throws InterruptedException {
	  //driver.activateApp("com.google.android.googlequicksearchbox");
	  Thread.sleep(3000);
  }
  @BeforeClass
  public void beforeClass() throws MalformedURLException, InterruptedException {
	  DesiredCapabilities desiredCapability = new DesiredCapabilities();
      desiredCapability.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
      desiredCapability.setCapability("newCommandTimeout", 300);
      URL url = new URL("http://0.0.0.0:4723/wd/hub");
      desiredCapability.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_3a_API_30_x86");
      desiredCapability.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
      desiredCapability.setCapability(MobileCapabilityType.UDID, "emulator-5554");
     //Use this desiredCapability when installing the app for the first time:
      //desiredCapability.setCapability(MobileCapabilityType.APP, "C:\\Java Files\\FIrstAppiumProjectMaven\\src\\main\\resources\\Testapp.apk");
     
      //Once an app is installed in emulator we have to get the appPackage and appActivity
      //Instructions to get them: open cmd and type: adb shell
      //Press enter and then type this command: dumpsys window | grep -E mCurrentFocus
//      desiredCapability.setCapability("appPackage", "com.google.android.googlequicksearchbox");
//      desiredCapability.setCapability("appActivity", "com.google.android.apps.gsa.searchnow.SearchNowActivity");
      //Thread.sleep(2000);
      //desiredCapability.setCapability("appActivity", "com.google.android.apps.gsa.sidekick.main.optin.OptInActivity");
      desiredCapability.setCapability("appPackage", "io.appium.android.apis");
      desiredCapability.setCapability("appActivity", "io.appium.android.apis.ApiDemos");
      //To Launch emulator automatically:
      desiredCapability.setCapability("avd", "Pixel_3a_API_30_x86");
      desiredCapability.setCapability("avdLaunchTimeout", 180000);
      desiredCapability.setCapability("readyTimeout", 60000);
      
      //To Launch Chrome browser on emulator
//      desiredCapability.setCapability("chromedriverExecutable", "C:\\Softwares\\selenium-java-3.141.59\\chromedriver\\v83\\chromedriver.exe");
//      desiredCapability.setCapability("BROWSER_NAME", "Chrome");
      
      //When unlocking a device using pin, use the following capabilities
      //desiredCapability.setCapability("unlockType", "pin");
     // desiredCapability.setCapability("unlockKey", "1111");
      
      //When unlocking a device using pattern, use the following capabilities
      //desiredCapability.setCapability("unlockType", "pattern");
      //desiredCapability.setCapability("unlockKey", "125478963"); //value is deducted by counting the dots on screen according to their positions
      
       driver = new AndroidDriver(url,desiredCapability);
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
       System.out.println(driver.getTitle());
  }

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}

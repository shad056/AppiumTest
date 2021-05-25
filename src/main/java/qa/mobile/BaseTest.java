package qa.mobile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;


import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BaseTest {

	public BaseTest() {
		
	}
	public static ThreadLocal <AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
	protected static ThreadLocal<Properties> prop = new ThreadLocal<Properties>();
	public ThreadLocal<HashMap<String,String>> strings = new ThreadLocal<HashMap<String,String>>();
	protected ThreadLocal<String> platform = new ThreadLocal<String>();
	protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
	protected static ThreadLocal<String> deviceName = new ThreadLocal<String>();
	TestUtils utils;
	static Logger log = LogManager.getLogger(BaseTest.class.getName());
	
	
	public String getDeviceName() {
		return deviceName.get();
	}
	
	public void setDeviceName(String deviceName2) {
		   deviceName.set(deviceName2);
	}
	
	public void setDriver(AppiumDriver driver) {
		this.driver.set(driver);
	}
	
	public static AppiumDriver getDriver() {
		return driver.get();
	}
	public Properties getProp() {
		return prop.get();
	}
	public void setProp(Properties props2) {
		prop.set(props2);
	}
	
	public String getPlatform() {
		return platform.get();
	}
	public void setPlatform(String platform2) {
		platform.set(platform2);
	}
	
	public HashMap<String, String> getStrings() {
		return strings.get();
	}
	public void setStrings(HashMap<String,String> strings2) {
		strings.set(strings2);
	}
	
	
	  public void initializeDriver(String emulator,String platformName, String platformVersion, String deviceName, String systemPort, String chromeDriverPort) throws InterruptedException, IOException {
		  InputStream stringis=null;
		  InputStream inputStream=null;
		  Properties prop = new Properties();
		  String platform = platformName;
		  AppiumDriver driver = null;
		  setDeviceName(deviceName);
		  
		  String strFile = "logs" + File.separator + platformName + "_" + deviceName;
		  File logFile = new File(strFile);
		  if(!logFile.exists()) {
			  logFile.mkdirs();
		  }
		  ThreadContext.put("ROUTINGKEY", strFile);
		  
		  try {
			  
			 log.info("This is testing of log4j");
			 log.error("This is testing of log4j");
			 log.debug("This is testing of log4j");
			 log.warn("This is testing of log4j");
			 
			  setPlatform(platform);
			  String fileName = "config.properties";
			  String xmlFile = "strings/strings.xml";
			  inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
			  stringis = getClass().getClassLoader().getResourceAsStream(xmlFile);
			  prop.load(inputStream);
			  utils = new TestUtils();
			  setStrings(utils.parseStringXML(stringis));
			  DesiredCapabilities desiredCapability = new DesiredCapabilities();
		      desiredCapability.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
		      desiredCapability.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
		      desiredCapability.setCapability(deviceName, deviceName);
		      setDateTime(utils.dateTime());
		      
		      desiredCapability.setCapability("newCommandTimeout", 300);
		      URL url = new URL(prop.getProperty("appiumURL") + "4723/wd/hub");
		    
		       
		       switch(getPlatform()) {
			      case "Android":
			    	  desiredCapability.setCapability(MobileCapabilityType.AUTOMATION_NAME, prop.getProperty("androidAutomationName"));
				     if(emulator.equalsIgnoreCase("true")) {
			    	  desiredCapability.setCapability(MobileCapabilityType.UDID, "emulator-5554");
			    	  //For iOS to get udid type: adb devices
			    	  //then type: ios-deploy -c
			    	//To Launch emulator automatically:
				      desiredCapability.setCapability("avd", "Pixel_3a_API_30_x86");
				      desiredCapability.setCapability("avdLaunchTimeout", 180000);
				     }
				     else {
				    	  desiredCapability.setCapability(MobileCapabilityType.UDID, "pj4ppzkrem4lv8vw");
				     }
				      
				     //Use this desiredCapability when installing the app for the first time:
				     // String appURL = getClass().getResource(prop.getProperty("androidAppLocation")).getFile();
				      //desiredCapability.setCapability(MobileCapabilityType.APP, appURL);
				     
				      //Once an app is installed in emulator we have to get the appPackage and appActivity
				      //Instructions to get them: open cmd and type: adb shell
				      //Press enter and then type this command: dumpsys window | grep -E mCurrentFocus
//				      desiredCapability.setCapability("appPackage", "com.google.android.googlequicksearchbox");
//				      desiredCapability.setCapability("appActivity", "com.google.android.apps.gsa.searchnow.SearchNowActivity");
				      //Thread.sleep(2000);
				      //desiredCapability.setCapability("appActivity", "com.google.android.apps.gsa.sidekick.main.optin.OptInActivity");
				      desiredCapability.setCapability("appPackage", prop.getProperty("appPackage"));
				      desiredCapability.setCapability("appActivity", prop.getProperty("appActivity"));
				      desiredCapability.setCapability("systemPort", prop.getProperty("systemPort")); //for running the device on a separate port for parallel execution
				      desiredCapability.setCapability("chromeDriverPort", prop.getProperty("chromeDriverPort")); //running chrome browser on device on a separate port for parallel execution
				      
				      desiredCapability.setCapability("readyTimeout", 60000);
				      
				      //To Launch Chrome browser on emulator
//				      desiredCapability.setCapability("chromedriverExecutable", "C:\\Softwares\\selenium-java-3.141.59\\chromedriver\\v83\\chromedriver.exe");
//				      desiredCapability.setCapability("BROWSER_NAME", "Chrome");
				      
				      //When unlocking a device using pin, use the following capabilities
				      //desiredCapability.setCapability("unlockType", "pin");
				     // desiredCapability.setCapability("unlockKey", "1111");
				      
				      //When unlocking a device using pattern, use the following capabilities
				      //desiredCapability.setCapability("unlockType", "pattern");
				      //desiredCapability.setCapability("unlockKey", "125478963"); //value is deducted by counting the dots on screen according to their positions
				      driver = new AndroidDriver(url,desiredCapability);
			    	  break;
			      case "iOS":
			    	  driver = new IOSDriver(url,desiredCapability);
			    	  //for running on a different port:
			    	  //url = new URL(props.getProperty("appiumURL") + "4724/wd/hub");
			    	  break;
			    	  default:
			    		  System.out.println("Invalid platform: " + platformName);
			      }
		      setDriver(driver);
		      setProp(prop);
		  }
		  catch(Exception e) {
			  e.printStackTrace();
		  }
		  finally {
			  if (inputStream != null) {
				  inputStream.close();
			  }
			  if (stringis != null) {
				  stringis.close();
			  }
		  }
	  }
	  public  void waitForVisibility(MobileElement e) {
		  Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
				  .withTimeout(Duration.ofSeconds(30))
				  .pollingEvery(Duration.ofSeconds(5))
				  .ignoring(NoSuchElementException.class);
		  
		  wait.until(ExpectedConditions.visibilityOf(e));
	  }
	  
	  public void click(MobileElement e) {
		  waitForVisibility(e);
		  e.click();
	  }
	  
	  public void sendKeys(MobileElement e, String txt) {
		  waitForVisibility(e);
		  e.sendKeys(txt);
	  }
	  
	  public  String getAttribute(MobileElement e,String attribute) {
		  waitForVisibility(e);
		 return e.getAttribute(attribute);
	  }
	  
	  public String getText(MobileElement e) {
		  if(getPlatform().equalsIgnoreCase("Android")) {
			  return this.getAttribute(e,"text");
		  }
		  else if(getPlatform().equalsIgnoreCase("iOS")) {
			  return this.getAttribute(e,"label");
		  }
		  return this.getAttribute(e,"text");
	  }
	 public void quitDriver() {
		 getDriver().quit();
	 }
	 
	 public static void scrollPageusingTouchActions(String direction) {
		 TouchAction action = new TouchAction(getDriver());
		 Dimension size = getDriver().manage().window().getSize();
		 int startX = (int)(size.width/2);
		 int endX = startX;
		 int startY;
		 int endY;
		 switch(direction) {
		 case "up":
			 startY = (int)(size.height*0.8);
			 endY = (int)(size.height*0.2);
			 action.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
			 .moveTo(PointOption.point(endX,endY)).release().perform();
			 break;
		 case "down":
			 startY = (int)(size.height*0.2);
			 endY = (int)(size.height*0.8);
			 action.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
			 .moveTo(PointOption.point(endX,endY)).release().perform();
			 break;
		 }
		 
	 }
	 
	 public static void scrollPagetoElement(String direction, MobileElement textFieldElement) {
		 for(int i=0;i<3;i++) {
			 if(isDisplayed(textFieldElement)) {
				 break;
			 }
			 else{
				if(direction.equalsIgnoreCase("up")) {
					scrollPageusingTouchActions("up");
				}
				else {
					scrollPageusingTouchActions("down");
				}
			 }
		 }
	 }
	 
	 //The method below is the most safest and efficient way to scroll
	 
	 public static MobileElement scrollPageToElementusingUIAutomator(String ParentResourceID, String ChildDescription) {
		 //using xpaths is not recommended
		 return (MobileElement)((FindsByAndroidUIAutomator)getDriver()).findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\""+ParentResourceID+"\")).scrollIntoView(new UiSelector().description(\""+ChildDescription+"\"));");
	 //For aps where there are ScrollViews available you can use the following code and avoid passing parent locator:
		 //return (MobileElement)((FindsByAndroidUIAutomator)driver).findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(true)).scrollIntoView(new UiSelector().description(\""+ChildDescription+"\"));");

	 }
	 
	 //For IOS scroll to element use the following code:
	 public void iOSScrollToElement() {
		 //Input classname of parent scrollView
		 String parentElementID = (String)getDriver().findElement(By.className("")).getAttribute("Id");
		 HashMap<String,String> scrollObject = new HashMap<String,String>();
		 scrollObject.put("element", parentElementID);
		 scrollObject.put("predicateString", "label==sumthing"); //child element label
		 //scrollObject.put("direction","down"); //you can also use this instead of above line
		 //scrollObject.put("name","sumthing"); //you can also use this instead of above line
		 //scrollObject.put("toVisible","sumthing"); //you can also use this instead of above line
		 getDriver().executeScript("mobile:scroll",scrollObject);
	 }
	 
	 public void closeApp() {
		 ((InteractsWithApps)getDriver()).closeApp();
	 }
	 
	 public void launchApp() {
		 ((InteractsWithApps)getDriver()).launchApp();
	 }
	 
	 public static boolean isDisplayed(final MobileElement e) {
		 getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		 try {
		 WebDriverWait wait = new WebDriverWait(getDriver(),3);
		return wait.until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				if(e.isDisplayed())
					return true;		
				return false;
			} 
			 
		 });
		
		 }
		 catch(Exception exc) {
			 return false;
		 }
	 }
	 
	 public String getDateTime() {
		 return dateTime.get();
	 }
	 
	 public void setDateTime(String dateTime2) {
		  dateTime.set(dateTime2);
	 }
	 
	
}

package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.pages.HomePage;
import com.qa.pages.TextFieldsPage;
import com.qa.pages.ViewsPage;
import com.qa.utils.TestUtils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import jdk.internal.net.http.common.Utils;
import qa.mobile.BaseTest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;
import org.testng.annotations.AfterClass;

public class AppTests{
	  HomePage homePage;
	  ViewsPage viewsPage;
	  TextFieldsPage textFieldsPage;
	  BaseTest baseTest;
	  JSONObject jsonInput;
	  TestUtils utils;
	  static Logger log = LogManager.getLogger(BaseTest.class.getName());
	  
	  private static AppiumDriverLocalService server;
	  
	  @Parameters({"emulator","platformName","platformVersion","deviceName","systemPort","chromeDriverPort"})
	  @BeforeClass
  public void beforeClass(String emulator,String platformName, String platformVersion, String deviceName, @Optional("androidOnly")String systemPort, @Optional("androidOnly")String chromeDriverPort) throws Exception {
		  InputStream inputStream=null;
		  try {
			  String jsonFileName = "data/testdata.json";
			  inputStream = getClass().getClassLoader().getResourceAsStream(jsonFileName);
			  JSONTokener jsonToken = new JSONTokener(inputStream);
			  jsonInput = new JSONObject(jsonToken);
			  baseTest = new BaseTest();
			  baseTest.initializeDriver(emulator,platformName,platformVersion,deviceName,systemPort,chromeDriverPort);
		  
		  }
		  catch(Exception e) {
			  e.printStackTrace();
			  throw(e);
		  }
		  finally {
			  if(inputStream != null) {
				  inputStream.close();
			  }
		  }
		
  }

  @AfterClass
  public void afterClass() {
	  baseTest.quitDriver();
  }

  
  @BeforeMethod
  public void beforeMethod(Method m) {
	  //login here if there is an app to login
	  baseTest.closeApp();
		baseTest.launchApp();
	  homePage = new HomePage();
	  System.out.println("\n" + "***** starting test:" + m.getName() + "\n");
  }

  @AfterMethod
  public void afterMethod() {
	//logout here if there is an app to logout
  }
  
  @Test 
  public void GeneralTest() throws InterruptedException {
	  
		  viewsPage = homePage.clickViews();
		  textFieldsPage = viewsPage.clickTextField();
		  textFieldsPage.EnterText(jsonInput.getJSONObject("GeneralTest").getString("InputText"));
		  Thread.sleep(3000);
		  String expectedTxt = baseTest.getStrings().get("expectedValue");
		  Assert.assertEquals(expectedTxt, textFieldsPage.getTitleText());
  }
  
  @Test
  public void JustaTest() {
	  System.out.println("Testing...");
  }
  
  public boolean checkIfAppiumServerIsRunning(int port) throws Exception{
	  boolean isAppiumServerRunning = false;
	  ServerSocket socket;

		  try {
			  socket = new ServerSocket(port);
			  socket.close();
		  }
		  catch(IOException e) {
			  System.out.println("1");
			  isAppiumServerRunning = true;
		  } finally {
			  socket = null;
		  }

	  return isAppiumServerRunning;
  }
  
  @BeforeSuite
	 public void beforeSuite() throws AppiumServerHasNotBeenStartedLocallyException, Exception {
		 server = getAppiumServerDefault();
		 if(!checkIfAppiumServerIsRunning(4723)) {
			 server.start();
			 server.clearOutPutStreams();
		 }
		 log.info("Server is already running");
		 
		 //utils.log().info("Appium server started");
		 //to stop server we need to type commands in cmd:
		 //netstat -ano|findstr "PID :4723"
		 //taskkill /pid 18264/f
	 }
	 @AfterSuite
	 public void afterSuite() {
		 server.stop();
	 }
	 
	 //For Windows:
	 public AppiumDriverLocalService getAppiumServerDefault() {
		 return AppiumDriverLocalService.buildDefaultService();
	 }
	 
	 //For Mac/iOS:
//	 public AppiumDriverLocalService getAppiumService() {
//		 HashMap<String, String> environment = new HashMap<String,String>();
//		 environment.put("PATH", "C:\\Program Files\\MySQL\\MySQL Shell 8.0\\bin\\;%USERPROFILE%\\AppData\\Local\\Microsoft\\WindowsApps;C:\\Users\\saad-\\AppData\\Local\\Programs\\Microsoft VS Code\\bin;C:\\Softwares\\Hadoop\\hadoop-3.1.4\\bin;C:\\Softwares\\Hadoop\\hadoop-3.1.4\\sbin;C:\\Java\\Java2\\bin;C:\\Program Files\\nodejs;C:\\Users\\saad-\\AppData\\Roaming\\npm;%ANDROID_HOME%\\platform-tools;%ANDROID_HOME%\\tools;%ANDROID_HOME%\\tools\\bin;");
//		 environment.put("ANDROID_HOME", "C:\\Users\\saad-\\AppData\\Local\\Android\\Sdk");
//		 return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
//				 .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
//				 .withAppiumJS(new File("C:\\Users\\saad-\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
//				 .usingPort(4723)
//				 .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
//				 .withEnvironment(environment)
//				 .withLogFile(new File("ServerLogs/server.log")));
//				 
//	 }
  
  
}

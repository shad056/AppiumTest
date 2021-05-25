package com.qa.pages;

import java.util.List;

import org.openqa.selenium.support.PageFactory;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import qa.mobile.BaseTest;

public class HeaderPage {
	
	BaseTest baseTest;
	
	TestUtils utils;
	
	@AndroidFindBy(xpath="(//*[@class=\"android.widget.TextView\"])[1]") private MobileElement TitleElement;
	
	public HeaderPage() {
		baseTest = new BaseTest();
		PageFactory.initElements(new AppiumFieldDecorator(baseTest.getDriver()), this);
	}
	
	public String getTitleText() {
		String titleText = baseTest.getAttribute(TitleElement, "text");
		//utils.log("Title text: " + titleText);
		return titleText;
		
		//return baseTest.getText(TitleElement);
	}
	
}

package com.qa.pages;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import qa.mobile.BaseTest;

public class HomePage extends HeaderPage {
	
	//BaseTest baseTest;
	@AndroidFindBy(accessibility="Views") private MobileElement ViewsElement;
	
	public HomePage() {
		super();
		//baseTest= new BaseTest();
		//PageFactory.initElements(new AppiumFieldDecorator(baseTest.getDriver()),this);
	}
	public ViewsPage clickViews() {
		baseTest.click(ViewsElement);
		//utils.log("Clicked Views element");
		return new ViewsPage();
	}
}

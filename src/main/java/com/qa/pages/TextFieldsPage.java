package com.qa.pages;

import java.util.List;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import qa.mobile.BaseTest;

public class TextFieldsPage extends HeaderPage {
	
	//BaseTest baseTest;
	@AndroidFindBy(id="io.appium.android.apis:id/edit") private MobileElement InputBoxElement;

	
	public TextFieldsPage() {
		super();
		//baseTest = new BaseTest();
		//PageFactory.initElements(new AppiumFieldDecorator(baseTest.getDriver()), this);
		
	}
	
	public TextFieldsPage EnterText(String text) {
		//utils.log("Input text: " + text);
		baseTest.sendKeys(InputBoxElement, text);
		return this;
	}
	

	
}

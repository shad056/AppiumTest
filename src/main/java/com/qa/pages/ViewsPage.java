package com.qa.pages;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import qa.mobile.BaseTest;

public class ViewsPage extends HeaderPage {
	
	//BaseTest baseTest;
	@AndroidFindBy(accessibility="Animation") private MobileElement AnimationElement;
	@AndroidFindBy(accessibility="TextFields") private MobileElement TextFieldElement;
	
	public ViewsPage() {
		super();
		//baseTest = new BaseTest();
		//PageFactory.initElements(new AppiumFieldDecorator(baseTest.getDriver()), this);
	}
	
	public ViewsPage clickAnimation() {
		baseTest.click(AnimationElement);
		utils.log("Clicked Animation Element");
		return this;
	}
	
	public TextFieldsPage clickTextField() {
		baseTest.waitForVisibility(AnimationElement);
//		for(int i=0;i<3;i++) {
//			baseTest.scrollPageusingTouchActions("up");
//		}
		//baseTest.scrollPagetoElement("up",TextFieldElement);
		//The following method is the preferred way for scrolling:
	//	utils.log("Scrolling Page");
		baseTest.scrollPageToElementusingUIAutomator("android:id/list","TextFields");
		baseTest.click(TextFieldElement);
	//	utils.log("Clicked TextFields Element");
		return new TextFieldsPage();
	}
}

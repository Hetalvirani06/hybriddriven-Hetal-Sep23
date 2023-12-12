package testscripts;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.ControlActions;
import pages.LoginPage;

public class LoginTest {
	@BeforeMethod
	public void setup() {
		ControlActions.launchBrowser();
	}

	@Test
	public void verifyLogin() {
		LoginPage loginpage = new LoginPage();
		loginpage.login("hetalusadadiya@gmail.com" , "Heta@0643");
		
		boolean loginFlag = loginpage.isLoginSuccessFullyDisplayed();
		Assert.assertTrue(loginFlag);
	}
	@AfterMethod
	public void tearDown() {
		ControlActions.closeBrowser();
	}
}

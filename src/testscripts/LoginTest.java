package testscripts;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.ControlActions;
import pages.LoginPage;
import utility.ExcelOperations;

public class LoginTest {

	LoginPage loginpage;

	@BeforeMethod
	public void setup() {
		ControlActions.launchBrowser();
		loginpage = new LoginPage();
	}

	@Test
	public void verifyLogin() {
		loginpage.login("hetalusadadiya@gmail.com", "Heta@0643");
		boolean loginFlag = loginpage.isLoginSuccessFullyDisplayed();
		Assert.assertTrue(loginFlag);
	}

	@Test
	public void verifyErrorMessages() {
		System.out.println("STEP - Click on Login button");
		loginpage.clickOnLoginBtn();

		System.out.println("VERIFY - Email required Error messages is visible");
		boolean emailErrorMessageFlag = loginpage.isEmailRequiredElementDisplayed();
		Assert.assertTrue(emailErrorMessageFlag);

		System.out.println("VERIFY - Password required Error messages is visible");
		boolean passwordErrorMessageFlag = loginpage.isPasswordRequiredElementDisplayed();
		Assert.assertTrue(passwordErrorMessageFlag);
	}

	@Test
	public void verifyPasswordErrorMessage() {
		System.out.println("STEP - Enter valid User email");
		loginpage.enterUserEmail("hetalusadadiya@gmail.com");

		System.out.println("STEP - Click on Login button");
		loginpage.clickOnLoginBtn();

		System.out.println("VERIFY - Email required Error messages is not visible");
		boolean emailErrorMessageFlag = loginpage.isEmailRequiredElementDisplayed();
		Assert.assertFalse(emailErrorMessageFlag);

		System.out.println("VERIFY - Password required Error messages is visible");
		boolean passwordErrorMessageFlag = loginpage.isPasswordRequiredElementDisplayed();
		Assert.assertTrue(passwordErrorMessageFlag);
	}

	
	@Test
	public void verifyEmailErrorMessageDisplayed() {
		System.out.println("STEP - Enter valid password");
		loginpage.enterPassword("hetA@12345");

		System.out.println("STEP - Click on Login button");
		loginpage.clickOnLoginBtn();

		System.out.println("VERIFY - Email required Error messages is visible");
		boolean emailErrorMessageFlag = loginpage.isEmailRequiredElementDisplayed();
		Assert.assertTrue(emailErrorMessageFlag);

		System.out.println("VERIFY - Password required Error messages is not visible");
		boolean passwordErrorMessageFlag = loginpage.isPasswordRequiredElementDisplayed();
		Assert.assertFalse(passwordErrorMessageFlag);
	}

	
	@Test(dataProvider = "LoginDataProvider")
	public void verifyloginUsingDataDriven(String username, String password, String loginStatus) {
		System.out.println("STEP - Login with given credentails");
		loginpage.login(username, password);
		String currentURL = "";
		boolean loginFlag;
		if (loginStatus.equalsIgnoreCase("pass")) {
			System.out.println("VERIFY - Login Successful toast message displayed");
			loginFlag = loginpage.isLoginSuccessFullyDisplayed();
			Assert.assertTrue(loginFlag, "Login successfully Message was not displayed");

			//System.out.println("VERIFY - Incorrect email or password message is not displayed");
			//loginFlag = loginpage.isLoginUnSuccessfulElementDisplayed();
			//Assert.assertFalse(loginFlag, "Incorrect email or password message was displayed");

			currentURL = loginpage.getCurrentURL();
			System.out.println("VERIFY - Application should redirect to dahsboard page");
			Assert.assertTrue(currentURL.endsWith("dashboard/dash"), "Current URL :" + currentURL);
		} else {
			loginFlag = loginpage.isLoginUnSuccessfulElementDisplayed();
			Assert.assertTrue(loginFlag, "Incorrect email or password message was not displayed");

			//loginFlag = loginpage.isLoginSuccessFullyDisplayed();
			//Assert.assertFalse(loginFlag, "Login successfully Message was displayed");

			currentURL = loginpage.getCurrentURL();
			Assert.assertTrue(currentURL.endsWith("auth/login"));
		}
	}

	@DataProvider(name="LoginDataProvider")
	public Object[][] getLoginData() throws IOException{
		return ExcelOperations.getAllRows(".//testdata/LoginData.xlsx","Login");
	}

	@AfterMethod
	public void tearDown() {
		ControlActions.closeBrowser();
	}
}

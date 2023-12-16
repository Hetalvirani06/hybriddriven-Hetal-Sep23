package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.ControlActions;

public class Dashboardpage extends ControlActions{
		
	@FindBy(css = "")
	WebElement searchElement;
	
	void m1() {
		PageFactory.initElements(driver,this);
	}
}

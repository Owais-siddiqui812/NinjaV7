package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountPage {
	
	WebDriver driver;
	//constructor

	public AccountPage(WebDriver driver) 
	{
		this.driver=driver;
		//PageFactory.initElements(driver, this);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h1[normalize-space()='My Account']")
	WebElement confirmationText_MyAccount;

	@FindBy(xpath="//span[normalize-space()='My Account']")
	WebElement My_Account;
	
	@FindBy(xpath = "//a[@class='dropdown-item'][normalize-space()='Logout']")
	WebElement Logout;
	
	public void Myaccount_click() {
		My_Account.click();
	}
	
	public void Logout() {
		Logout.click();
	}
	
	public WebElement getMyAccountConfirmation() {
		return confirmationText_MyAccount;
	}

}

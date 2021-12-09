
package com.revature.page;

import java.time.Clock;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	private WebDriver driver;
	private WebDriverWait wdw;
	
	@FindBy(id="username")
	private WebElement usernameInput;
	
	@FindBy(id="password")
	private WebElement passwordInput;
	
	@FindBy(id="loginBtn")
	private WebElement loginBtn;
	
	@FindBy(xpath="//div[@id='incorrect-credentials-container']/p")
	private WebElement errorMessage;
	
	@FindBy(xpath="//p[contains(text(),'Welcome to our Reimbursement System App!')]")
	private WebElement loginWelcomeMessage;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;	
		this.wdw = new WebDriverWait(driver, Duration.ofSeconds(15)); // wait for a maximum of 15 seconds before throwing an exception
		
		// PageFactory initialization
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getUsernameInput() {
		return this.usernameInput;
	}
	
	public WebElement getPasswordInput() {
		return this.passwordInput;
	}
	
	public WebElement getLoginBtn() {
		return this.loginBtn;
	}
	
	public WebElement getErrorMessage() {
		return this.wdw.until(ExpectedConditions.visibilityOf(this.errorMessage));
	}
	
	public WebElement getLoginWelcomeMessage() {
		return this.wdw.until(ExpectedConditions.visibilityOf(this.loginWelcomeMessage));
	}

}

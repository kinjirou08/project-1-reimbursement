package com.revature.page;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class FinanceManagerPage {
		
	private WebDriver driver;
	private WebDriverWait wdw;
	
	@FindBy(xpath= "//p[contains(text(),'Welcome Manager!')]")
	private WebElement welcome;
	
	@FindBy(xpath = "//button[@id='logoutBtn']")
	private WebElement logoutBtn;
	
	@FindBy(xpath = "//a[@class='navbar-link']")
	private WebElement hoverElement;
	
	private Actions action;
	//a.moveToElement(hoverElement).build().perform(); // This will "move the mouse" to that element
	
	@FindBy(xpath ="//a[@id='approve-reject']")
	private WebElement acceptOrRejectLink;
	//a2.moveToElement(acceptOrRejectLink).click().build().perform();
	
	@FindBy(xpath = "//input[@id='reimb-id']")
	private WebElement reimId;

	@FindBy(xpath = "//button[@id='search']")
	private WebElement searchBtn;

	@FindBy(xpath = "//select[@id='approved-rejected']")
	private WebElement dropDownBtn;

	@FindBy(xpath = "//select[@id='approved-rejected']")
	private Select dropDownType; //= new Select (dropDownType);
	
	@FindBy(xpath="//button[@id='approve-reject-reimbursement']")
	private WebElement submitBtn;
	
	@FindBy(xpath="//button[@id='cancel-reimbursementv4']")
	private WebElement cancelBtn;

	
	public FinanceManagerPage(WebDriver driver) {
		this.driver = driver;	
		this.wdw = new WebDriverWait(driver, Duration.ofSeconds(15)); // wait for a maximum of 15 seconds before throwing an exception
		
		// PageFactory initialization
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getLogoutBtn() {
		return this.logoutBtn;
	}

	public WebElement getHoverElement() {
		return this.hoverElement;
	}

	public Actions getAction() {
		return this.action = new Actions(driver);
	}

	public WebElement getAcceptOrRejectLink() {
		return this.acceptOrRejectLink;
	}

	public WebElement getReimId() {
		return this.reimId;
	}

	public WebElement getSearchBtn() {
		return this.searchBtn;
	}

	public WebElement getDropDownBtn() {
		return this.dropDownBtn;
	}

	public Select getDropDownType() {
		return this.dropDownType = new Select (driver.findElement(By.id("approved-rejected")));
	}

	public WebElement getSubmitBtn() {
		return this.submitBtn;
	}

	public WebElement getCancelBtn() {
		return this.cancelBtn;
	}

	public WebElement getWelcomeHeading() {
		return this.wdw.until(ExpectedConditions.visibilityOf(welcome));
	}

}

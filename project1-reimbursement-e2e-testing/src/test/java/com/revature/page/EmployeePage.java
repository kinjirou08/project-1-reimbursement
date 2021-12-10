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


public class EmployeePage {
		
	private WebDriver driver;
	private WebDriverWait wdw;
	Actions action;
	
	@FindBy(xpath= "//p[contains(text(),'Welcome Employee!')]")
	private WebElement welcome;
	
	@FindBy(id="logoutBtn")
	private WebElement logoutBtn;
	
	@FindBy(xpath = "//a[@class='navbar-link']")
	private WebElement hoverElement;
	
	@FindBy(xpath = "//a[@id='add-reimbursement']")
	private WebElement addReimbursementLink;
	
	@FindBy(xpath = "//select[@id='reimb-type']")
	private Select dropDownType;
	
	@FindBy(xpath = "//select[@id='reimb-type']")
	private WebElement dropDownBtn;
	
	@FindBy(xpath = "//textarea[@id='reimb-description']")
	private WebElement reimbDescription;
	
	@FindBy(xpath = "//input[@id='reimb-amount']")
	private WebElement reimbAmount;
	
	@FindBy(xpath = "//input[@id='upload-receipt']")
	private WebElement uploadReceipt;
	
	@FindBy(xpath = "//button[@id='sumbit-reimbursement']")
	private WebElement submitBtn;
	
	@FindBy(xpath = "//button[@id='cancel-reimbursement-v2']")
	private WebElement cancelBtn;
	
	public EmployeePage(WebDriver driver) {
		this.driver = driver;	
		this.wdw = new WebDriverWait(driver, Duration.ofSeconds(15)); // wait for a maximum of 15 seconds before throwing an exception
		
		// PageFactory initialization
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getHoverElement() {
		return this.hoverElement;
	}

	public WebElement getAddReimbursementLink() {
		return this.addReimbursementLink;
	}

	public Select getDropDownType() {
		return this.dropDownType = new Select (driver.findElement(By.id("reimb-type")));
	}
	
	public WebElement getDropDownBtn() {
		return this.dropDownBtn;
	}

	public WebElement getReimbDescription() {
		return this.reimbDescription;
	}

	public WebElement getReimbAmount() {
		return this.reimbAmount;
	}

	public WebElement getUploadReceipt() {
		return this.uploadReceipt;
	}

	public WebElement getSubmitBtn() {
		return this.submitBtn;
	}

	public WebElement getLogoutBtn() {
		return this.logoutBtn;
	}
	
	public WebElement getCancelBtn() {
		return this.cancelBtn;
	}
	
	public Actions getAction() {
		return this.action = new Actions(driver);
	}
	
	public WebElement getWelcomeHeading() {
		return this.wdw.until(ExpectedConditions.visibilityOf(welcome));
	}

}

package com.revature.gluecode;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.page.EmployeePage;
import com.revature.page.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddAReimbursementTest {

	private WebDriver driver;
	private EmployeePage employeePage;
	private LoginPage loginPage;

	@Given("I am at the Employee homepage")
	public void i_am_the_employee_homepage() {
		System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");

		this.driver = new ChromeDriver();

		this.driver.get("http://localhost:5500");
		this.loginPage = new LoginPage(driver);

		this.loginPage.getUsernameInput().sendKeys("employee01");
		this.loginPage.getPasswordInput().sendKeys("password");
		this.loginPage.getLoginBtn().click();
		
		this.employeePage = new EmployeePage(driver);
		String expectedWelcomeHeadingText = "Welcome Employee!";
		Assertions.assertEquals(expectedWelcomeHeadingText, this.employeePage.getWelcomeHeading().getText());
	}

	@When("I hover my mouse over the Welcome Employee!")
	public void i_hover_my_mouse_over_the_welcome_employee() {
		employeePage.getAction().moveToElement(employeePage.getHoverElement()).build().perform();
	}

	@When("I click the Add Reimbursement")
	public void i_click_the_add_reimbursement(){
		employeePage.getAction().moveToElement(employeePage.getAddReimbursementLink()).click().build().perform();
		
	}

	@When("I choose the {int} rd option in the dropdown box")
	public void i_choose_in_the_dropdown_box(Integer int1) throws InterruptedException {
		employeePage.getDropDownBtn().click();
		employeePage.getDropDownType().selectByIndex(2);
	}

	@When("I typed {string} in the description input box")
	public void i_typed_in_the_description_input_box(String string) {
		employeePage.getReimbDescription().sendKeys(string);
	}

	@When("I typed {string} in the amount input box")
	public void i_typed_in_the_amount_input_box(String string) {
		employeePage.getReimbAmount().sendKeys("100.50");
	}

	@When("I uploaded a file")
	public void i_uploaded_a_file() {
		employeePage.getUploadReceipt()
				.sendKeys("C:\\Users\\jymmm\\OneDrive\\Pictures\\Screenshots\\Screenshot (2).png");
	}

	@When("I click the submit button")
	public void i_click_the_submit_button() throws InterruptedException {
		employeePage.getSubmitBtn().click();
		Thread.sleep(2000);
	}

	@Then("I should see the employee homepage again")
	public void i_should_see_the_employee_homepage_again() throws InterruptedException {
//		this.driver.getCurrentUrl();
//		this.employeePage = new EmployeePage(driver);
		employeePage.getCancelBtn();
		
		driver.quit();
	}

}

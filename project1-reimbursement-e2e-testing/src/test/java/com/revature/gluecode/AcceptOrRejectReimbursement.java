package com.revature.gluecode;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.page.FinanceManagerPage;
import com.revature.page.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AcceptOrRejectReimbursement {

	private WebDriver driver;
	private LoginPage loginPage;
	private FinanceManagerPage managerPage;

	@Given("I am at the Finance Manager HomePage")
	public void i_am_at_the_finance_manager_home_page() {
		System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");

		this.driver = new ChromeDriver();

		this.driver.get("http://localhost:5500");
		this.loginPage = new LoginPage(driver);

		this.loginPage.getUsernameInput().sendKeys("kinjirou08");
		this.loginPage.getPasswordInput().sendKeys("p4ssw0rd");
		this.loginPage.getLoginBtn().click();

		this.managerPage = new FinanceManagerPage(driver);
		String expectedWelcomeHeadingText = "Welcome Manager!";
		Assertions.assertEquals(expectedWelcomeHeadingText, this.managerPage.getWelcomeHeading().getText());
	}

	@When("I hover my mouse over the Welcome Manager!")
	public void i_hover_my_mouse_over_the_welcome_manager() {
		managerPage.getAction().moveToElement(managerPage.getHoverElement()).build().perform();
	}

	@When("I click the Approve or Reject Reimbursement")
	public void i_click_the_approve_or_reject_reimbursement() {
		managerPage.getAction().moveToElement(managerPage.getAcceptOrRejectLink()).click().build().perform();
	}

	@When("I input {int} in the id input box")
	public void i_input_in_the_id_input_box(Integer int1) {
		managerPage.getReimId().sendKeys("" + int1);
	}

	@When("I click the search button")
	public void i_click_the_search_button() {
		managerPage.getSearchBtn().click();
	}

	@When("I chose {string} in the dropdown box")
	public void i_chose_in_the_dropdown_box(String string) {
		managerPage.getDropDownBtn().click();
		managerPage.getDropDownType().selectByIndex(1);
	}

	@When("I click the fm submit button")
	public void i_click_the_fm_submit_button() throws InterruptedException {
		managerPage.getSubmitBtn().click();
		Thread.sleep(2000);
	}

	@Then("I should see the finance manager again")
	public void i_should_see_the_finance_manager_again() {
		managerPage.getCancelBtn().click();
		driver.quit();
	}
}

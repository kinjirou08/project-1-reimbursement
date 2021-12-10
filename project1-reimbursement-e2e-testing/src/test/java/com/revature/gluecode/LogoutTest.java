package com.revature.gluecode;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.page.EmployeePage;
import com.revature.page.FinanceManagerPage;
import com.revature.page.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class LogoutTest {
	
	private WebDriver driver;
	private EmployeePage employeePage;
	private FinanceManagerPage managerPage;
	private LoginPage loginPage;

	@Given("Im at the Employee Homepage")
	public void i_m_at_the_employee_homepage() {
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
		
//		this.driver.get("http://localhost:5500/employee-home.html");
//		this.employeePage = new EmployeePage(driver);
	}
	
	@Given("Im at the Finance Manager Homepage")
	public void im_at_the_finance_manager_homepage() {
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

	@Given("I clicked the Logout button")
	public void i_cicked_the_logout_button() {
		this.employeePage.getLogoutBtn().click();
	}
	
	@Given("I clicked the Finance Manager Logout button")
	public void i_clicked_the_finance_manager_logout_button() {
	    managerPage.getLogoutBtn().click();
	}

	@Then("I should be redirected to login page")
	public void i_should_be_redirected_to_login_page() {
		this.loginPage = new LoginPage(this.driver);

		String expectedWelcomeHeadingText = "Welcome to our Reimbursement System App!";

		Assertions.assertEquals(expectedWelcomeHeadingText, this.loginPage.getLoginWelcomeMessage().getText());

		this.driver.quit();
	}

}

package com.revature.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ActionClassDemo {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.get("http://localhost:5500/employee-home.html");

		WebElement hoverElement = driver.findElement(By.className("navbar-link"));
		
		Actions a = new Actions(driver);
		a.moveToElement(hoverElement).build().perform(); // This will "move the mouse" to that element
		
		Thread.sleep(5000);	
		
		Actions a2 = new Actions(driver);
		a2.moveToElement(driver.findElement(By.id("add-reimbursement"))).click().build().perform();
		
		Thread.sleep(5000);	
		
		WebElement dropDropBtn = (driver.findElement(By.id("reimb-type")));
		dropDropBtn.click();
		Select dropDownType = new Select (driver.findElement(By.id("reimb-type")));
		dropDownType.selectByValue("3");
		
		Thread.sleep(2000);	
		
		WebElement reimbDescription = driver.findElement(By.id("reimb-description"));
		reimbDescription.sendKeys("I'm automating inside the Selenium!");
		
		Thread.sleep(2000);
		
		WebElement reimbAmount = driver.findElement(By.id("reimb-amount"));
		reimbAmount.sendKeys("100.50");
		
		Thread.sleep(2000);
		
		WebElement uploadReceipt = driver.findElement(By.id("upload-receipt"));
		uploadReceipt.sendKeys("C:\\Users\\jymmm\\OneDrive\\Pictures\\Screenshots\\Screenshot (2).png");
		
		Thread.sleep(2000);
		
		WebElement submitReimbursement = driver.findElement(By.id("sumbit-reimbursement"));
		submitReimbursement.click();
		
		Thread.sleep(5000);	
		
		driver.quit();

	}

}
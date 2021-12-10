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

		driver.get("http://localhost:5500/finance-manager-home.html");

		WebElement hoverElement = driver.findElement(By.xpath("//a[@class='navbar-link']"));
		
		Actions a = new Actions(driver);
		a.moveToElement(hoverElement).build().perform(); // This will "move the mouse" to that element
		
		Thread.sleep(5000);	
		
		Actions a2 = new Actions(driver);
		a2.moveToElement(driver.findElement(By.xpath("//a[@id='approve-reject']"))).click().build().perform();
		
		Thread.sleep(5000);	
		
		WebElement reimId = driver.findElement(By.xpath("//input[@id='reimb-id']"));
		reimId.sendKeys("3");
		
		Thread.sleep(2000);
		
		WebElement searchBtn = (driver.findElement(By.xpath("//button[@id='search']")));
		searchBtn.click();
	
		Thread.sleep(2000);
		
		WebElement dropDownBtn = (driver.findElement(By.xpath("//select[@id='approved-rejected']")));
		dropDownBtn.click();
		Select dropDownType = new Select (driver.findElement(By.xpath("//select[@id='approved-rejected']")));
		dropDownType.selectByIndex(1);
		
		Thread.sleep(2000);
	
		WebElement submitBtn = driver.findElement(By.xpath("//button[@id='approve-reject-reimbursement']"));
		submitBtn.click();
		
		Thread.sleep(2000);
		
		WebElement cancelBtn = driver.findElement(By.xpath("//button[@id='cancel-reimbursementv4']"));
		cancelBtn.click();
		
		
		
		Thread.sleep(2000);
		
//		
//		WebElement uploadReceipt = driver.findElement(By.id("upload-receipt"));
//		uploadReceipt.sendKeys("C:\\Users\\jymmm\\OneDrive\\Pictures\\Screenshots\\Screenshot (2).png");
//		
//		Thread.sleep(2000);
//		
//		WebElement submitReimbursement = driver.findElement(By.id("sumbit-reimbursement"));
//		submitReimbursement.click();
//		
//		Thread.sleep(5000);	
		
		driver.quit();

	}

}
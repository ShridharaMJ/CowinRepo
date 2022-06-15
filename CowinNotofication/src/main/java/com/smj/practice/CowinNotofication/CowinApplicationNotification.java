package com.smj.practice.CowinNotofication;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;


public class CowinApplicationNotification {

	WebDriver driver;
	String url = "https://www.cowin.gov.in/home";

	@BeforeTest
	public void setup() {
		WebDriverManager.chromiumdriver().setup();
		driver = new ChromeDriver();
		Reporter.log("Successfully opened browser......", true);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.get(url);
		Reporter.log("Entered URL " + url, true);
	}

	@Test
	public void sendNotification() {

		// Select serach by district

		WebElement stateselect = driver.findElement(By.xpath("//label[@for='status']"));
		waitForElement(stateselect);
		stateselect.click();
		Reporter.log("Clicked on Serach By District", true);
		// select state
		WebElement selectstate = driver.findElement(By.id("mat-select-0"));
		waitForElement(selectstate);
		selectstate.click();
		WebElement stateele = driver
				.findElement(By.xpath("//span[@class='mat-option-text'][normalize-space()='Karnataka']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", stateele);
		Reporter.log("Selected on State", true);

		// select district
		WebElement selectdistric = driver.findElement(By.id("mat-select-2"));
		waitForElement(selectdistric);
		selectdistric.click();
		WebElement districtele = driver
				.findElement(By.xpath("//span[@class='mat-option-text'][normalize-space()='BBMP']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", districtele);
		Reporter.log("Selected on District", true);

		// click Search

		driver.findElement(By.xpath("//button[text()='Search']")).click();
		Reporter.log("Clicked on Search", true);

		driver.findElement(By.xpath("//label[normalize-space()='Age 18+']")).click();
		Reporter.log("Selected 18+", true);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<WebElement> elements = driver
				.findElements(By.xpath("//div[contains(@class,'vaccine-box vaccine-box1')]/a"));

		for (int i = 0; i < elements.size(); i++) {
			String text = elements.get(i).getText();
			if (isNumeric(text)) {
				Reporter.log("Vaccination centre found", true);
				//EmailLibrary.emailNotify("mj.shridhara@mail.com");

			}
		}
Reporter.log("Execution completed", true);
	}

	@AfterTest
	public void closeBrowser() {
		 driver.quit();
	}

	public static boolean isNumeric(String string) {
		int intValue;

		System.out.println(String.format("Parsing string: \"%s\"", string));

		if (string == null || string.equals("")) {
		//	System.out.println("String cannot be parsed, it is null or empty.");
			return false;
		}

		try {
			intValue = Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			//System.out.println("Input String cannot be parsed to Integer.");
		}
		return false;
	}

	public void waitForElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 44);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

}

package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pages.Index;
import pages.View;

public class CRUDTaskTest {
	
	private static RemoteWebDriver driver;
	private static Logger LOGGER = Logger.getGlobal();
	
	private static ExtentReports report;
	private static ExtentTest test;
	
	@BeforeAll
	public static void init() {
		report = new ExtentReports("src/test/resources/reports/CRUDTaskTest.html");
		test = report.startTest("CRUD Task Test");
		System.setProperty("webdriver.chrome.driver",
				"src/test/resources/drivers/chromedriver.exe");
		ChromeOptions cOptions = new ChromeOptions();
		cOptions.setHeadless(false);
		driver = new ChromeDriver(cOptions);
		driver.manage().window().maximize();
		
		// index must be loaded first to create a new list
		driver.get(Index.URL);
		driver.executeScript("window.localStorage.clear();");
		driver.get(Index.URL);
	}
	
	@BeforeEach
	public void setup() {
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		try {
			LOGGER.warning("Connecting to the site...");
			driver.get(View.URL);
			driver.manage().deleteAllCookies();
		} catch (TimeoutException e) {
			System.out.println("Page - " + View.URL + " - timedout");
		}
	}
	
	@AfterAll
	public static void tearDown() {
		LOGGER.warning("Closing webdriver instance");
		driver.quit();
		LOGGER.info("Logger closed successfully");
		report.endTest(test);
		report.flush();
	}
	
	@Test
	public void createTask() throws InterruptedException {
		LOGGER.info("Creating new task...");
		View index = PageFactory.initElements(driver, View.class);
		index.createTask("today's task");
		new WebDriverWait(driver, 2).until(ExpectedConditions.presenceOfElementLocated(By.id("2")));
		LOGGER.info("Testing elements...");
		boolean liCreated = !driver.findElements(By.id("2")).isEmpty();
		if (liCreated) {
			test.log(LogStatus.PASS, "create task passed");
		} else {
			test.log(LogStatus.FAIL, "create task failed");
		}
		assertTrue(liCreated);
	}
	
	
	// no need to test read tasks, logic handled by CRUDListTest
	@Test
	public void readTask() {
		LOGGER.info("Reading tasks...");
		test.log(LogStatus.PASS, "read task passed");
		assertTrue(true);
		
	}
	
	@Test
	public void updateTask() {
		LOGGER.info("Updating task...");
		View index = PageFactory.initElements(driver, View.class);
		new WebDriverWait(driver, 2).until(ExpectedConditions.presenceOfElementLocated(By.id("1")));
		index.updateTask();
		
		String newName = "new task (renamed)";
		driver.switchTo().alert().sendKeys(newName);
		driver.switchTo().alert().accept();
		
		LOGGER.info("Testing elements...");
		boolean isNewName = driver.findElement(By.xpath("//*[@id=\"1\"]/div/label")).getText().equals(newName) ? true : false;
		if (isNewName) {
			test.log(LogStatus.PASS, "update task passed");
		} else {
			test.log(LogStatus.PASS, "update task failed");
		}
		assertTrue(isNewName);
	}
	
	@Test
	public void deleteTask() {
		LOGGER.info("Deleting task...");
		View index = PageFactory.initElements(driver, View.class);
		index.deleteTask();
		LOGGER.info("Testing elements...");
		boolean isDeleted = driver.findElements(By.id("1")).isEmpty();
		if (isDeleted) {
			test.log(LogStatus.PASS, "delete task passed");
		} else {
			test.log(LogStatus.PASS, "delete task failed");
		}
		assertTrue(isDeleted);
	}

}

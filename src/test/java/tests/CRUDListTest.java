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

public class CRUDListTest {
	
	private static RemoteWebDriver driver;
	private static Logger LOGGER = Logger.getGlobal();
	
	private static ExtentReports report;
	private static ExtentTest test;
	
	@BeforeAll
	public static void init() {
		report = new ExtentReports("src/test/resources/reports/CRUDListTest.html");
		test = report.startTest("CRUD List Test");
		System.setProperty("webdriver.chrome.driver",
				"src/test/resources/drivers/chromedriver.exe");
		ChromeOptions cOptions = new ChromeOptions();
		cOptions.setHeadless(false);
		driver = new ChromeDriver(cOptions);
		driver.manage().window().maximize();
	}
	
	@BeforeEach
	public void setup() {
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		try {
			LOGGER.warning("Connecting to the site...");
			driver.get(Index.URL);
			driver.manage().deleteAllCookies();
		} catch (TimeoutException e) {
			System.out.println("Page - " + Index.URL + " - timedout");
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
	public void createList() throws InterruptedException {
		LOGGER.info("Creating new list...");
		Index index = PageFactory.initElements(driver, Index.class);
		index.createList("today's list");
		new WebDriverWait(driver, 2).until(ExpectedConditions.presenceOfElementLocated(By.id("2")));
		LOGGER.info("Testing elements...");
		boolean liCreated = !driver.findElements(By.id("2")).isEmpty();
		if (liCreated) {
			test.log(LogStatus.PASS, "create list passed");
		} else {
			test.log(LogStatus.FAIL, "create list failed");
		}
		assertTrue(liCreated);
	}
	
	@Test
	public void readList() {
		LOGGER.info("Reading lists...");
		Index index = PageFactory.initElements(driver, Index.class);
		new WebDriverWait(driver, 2).until(ExpectedConditions.presenceOfElementLocated(By.id("1")));
		index.readList();
		LOGGER.info("Testing elements...");
		new WebDriverWait(driver, 2).until(ExpectedConditions.presenceOfElementLocated(By.id("1")));
		boolean liCreated = !driver.findElements(By.id("1")).isEmpty();
		if (liCreated) {
			test.log(LogStatus.PASS, "read list passed");
		} else {
			test.log(LogStatus.FAIL, "read list failed");
		}
		assertTrue(liCreated);
		
	}
	
	@Test
	public void updateList() {
		LOGGER.info("Updating list...");
		Index index = PageFactory.initElements(driver, Index.class);
		new WebDriverWait(driver, 2).until(ExpectedConditions.presenceOfElementLocated(By.id("1")));
		index.updateList();
		
		String newName = "new list (renamed)";
		driver.switchTo().alert().sendKeys(newName);
		driver.switchTo().alert().accept();
		
		LOGGER.info("Testing elements...");
		boolean isNewName = driver.findElement(By.xpath("//*[@id=\"1\"]/div/a")).getText().equals(newName) ? true : false;
		if (isNewName) {
			test.log(LogStatus.PASS, "update list passed");
		} else {
			test.log(LogStatus.PASS, "update list failed");
		}
		assertTrue(isNewName);
	}
	
	@Test
	public void deleteList() {
		LOGGER.info("Deleting list...");
		Index index = PageFactory.initElements(driver, Index.class);
		index.deleteList();
		LOGGER.info("Testing elements...");
		boolean isDeleted = driver.findElements(By.id("1")).isEmpty();
		if (isDeleted) {
			test.log(LogStatus.PASS, "delete list passed");
		} else {
			test.log(LogStatus.PASS, "delete list failed");
		}
		assertTrue(isDeleted);
	}

}

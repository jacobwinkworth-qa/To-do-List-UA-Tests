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

import pages.View;

public class CRUDTaskTest {
	
	private static RemoteWebDriver driver;
	private static Logger LOGGER = Logger.getGlobal();
	
	@BeforeAll
	public static void init() {
		System.setProperty("webdriver.chrome.driver",
				"src/test/resources/drivers/chromedriver.exe");
		ChromeOptions cOptions = new ChromeOptions();
		cOptions.setHeadless(true);
		driver = new ChromeDriver(cOptions);
		driver.manage().window().maximize();
	}
	
	@BeforeEach
	public void setup() {
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		try {
			LOGGER.warning("Connecting to the site...");
			driver.get(View.URL);
		} catch (TimeoutException e) {
			System.out.println("Page - " + View.URL + " - timedout");
		}
	}
	
	@AfterAll
	public static void tearDown() {
		LOGGER.warning("Closing webdriver instance");
		driver.quit();
		LOGGER.info("Logger closed successfully");
	}
	
	@Test
	public void createList() {
		LOGGER.info("Creating new list...");
		View view = PageFactory.initElements(driver, View.class);
		view.createList("new list");
		LOGGER.info("Testing elements...");
		boolean liCreated = !driver.findElements(By.id("1")).isEmpty();
		assertTrue(liCreated);
	}
	
	@Test
	public void readTasks() {
		LOGGER.info("Reading tasks...");
		View view = PageFactory.initElements(driver, View.class);
		view.readTasks();
		LOGGER.info("Testing elements...");
	}
	
	@Test
	public void updateTask() {
		LOGGER.info("Updating task...");
		View view = PageFactory.initElements(driver, View.class);
		view.updateTask();
		LOGGER.info("Testing elements...");
	}
	
	@Test
	public void deleteTask() {
		LOGGER.info("Deleting task...");
		View view = PageFactory.initElements(driver, View.class);
		view.deleteTask();
		LOGGER.info("Testing elements...");
	}

}

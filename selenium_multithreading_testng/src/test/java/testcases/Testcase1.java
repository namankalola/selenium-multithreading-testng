package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utilities.WebDriverManagerThread;

public class Testcase1 {
	private WebDriver driver;

	@BeforeClass
	@Parameters("browserType")
	public void setupDriver(String browserType) {
		driver = WebDriverManagerThread.getInstance(browserType).getDriver();
	}

	@Test(description = "Launch Browser and open url")
	public void verifyPageTitle() {
		driver.get("https://www.mailinator.com/");
		verifyTitle();
	}

	public void verifyTitle() {
		System.out.println(Thread.currentThread().getName() + " : " + driver.getTitle());
		Assert.assertEquals(driver.getTitle(), "Home Page");
	}

	@AfterClass(description = "Terminating threads and browser")
	public void clearBrowserAndReleaseThread() {
		WebDriverManagerThread.clearThread();
	}
}

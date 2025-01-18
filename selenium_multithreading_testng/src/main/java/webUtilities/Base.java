package webUtilities;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import utilities.WebDriverManagerThread;

public class Base {
	protected WebDriver driver;

	@BeforeClass
	@Parameters("browserType")
	public void setup(String browserType) {
		driver = WebDriverManagerThread.getInstance(browserType).getDriver();
	}

	@AfterClass
	public void tearDown() {
		WebDriverManagerThread.clearThread();
	}
}

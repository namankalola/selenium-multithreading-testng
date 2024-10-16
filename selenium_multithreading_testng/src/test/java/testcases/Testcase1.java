package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utilities.WebDriverManagerThread;

public class Testcase1 
{
	private WebDriver driver;
	@BeforeClass
	@Parameters("browserType")
    @Test
    public void setupDriver(String browserType)
    {
        driver = WebDriverManagerThread.getInstance(browserType).getDriver();
    }
	
	@Test
	public void testcase1() {
		driver.get("https://www.mailinator.com/");
		System.out.println(Thread.currentThread().getName() + " : " + driver.getTitle());
	}
	
	@AfterClass
	public void clearBrowserAndReleaseThread() {
		WebDriverManagerThread.clearThread();
	}
}

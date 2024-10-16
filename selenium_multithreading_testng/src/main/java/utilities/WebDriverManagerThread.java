package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverManagerThread {
	private volatile static WebDriverManagerThread instance;
	private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

	private WebDriverManagerThread() {
	}

	private void initializeWebDriver(String browserType) {
		switch (browserType) {
		case "chrome":
			threadDriver.set(new ChromeDriver());
			break;
		case "firefox":
			threadDriver.set(new FirefoxDriver());
			break;
		case "edge":
			threadDriver.set(new EdgeDriver());
			break;

		default:
			throw new IllegalArgumentException("Unknown web browser : " + browserType);
		}
	}

	public static WebDriverManagerThread getInstance(String browserType) {
		if (instance == null) {
			synchronized (WebDriverManagerThread.class) {
				instance = new WebDriverManagerThread();
			}
		}
		if(threadDriver.get()==null) {
			instance.initializeWebDriver(browserType);
		}
		return instance;
	}

	public WebDriver getDriver() {
		return threadDriver.get();
	}
	
	public static void clearThread() {
		if(threadDriver.get()!=null) {
			threadDriver.get().quit();
			threadDriver.remove();
		}
	}
}

package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * WebDriverManagerThread is a thread-safe singleton class that manages WebDriver instances 
 * for different browsers (Chrome, Firefox, Edge) in a multi-threaded environment.
 * This class uses ThreadLocal to ensure that each thread has its own instance of WebDriver.
 * 
 * Developer: Namankumar Kalola
 * Date Created: October 16, 2023
 * 
 * Key Responsibilities:
 *  - Provides a mechanism to initialize and manage browser-specific WebDriver instances.
 *  - Uses Singleton pattern to create only one instance of WebDriverManagerThread.
 *  - Ensures thread-safety by using `ThreadLocal` for WebDriver instances and synchronized 
 *    blocks to initialize the WebDriverManagerThread instance.
 *  - Supports Chrome, Firefox, and Edge browsers.
 *  - Offers a `clearThread` method to properly quit and remove the WebDriver instance when 
 *    a thread finishes execution, avoiding resource leaks.
 * 
 * Usage:
 *  - Use `WebDriverManagerThread.getInstance(browserType)` to get the WebDriver instance 
 *    for a particular thread and browser type.
 *  - Ensure to call `WebDriverManagerThread.clearThread()` after the test execution to 
 *    close the WebDriver and release resources.
 * 
 * Browser types supported:
 *  - "chrome"
 *  - "firefox"
 *  - "edge"
 * 
 * Known Limitations:
 *  - If the browser type passed is unsupported, the method throws an IllegalArgumentException.
 * 
 * Future Enhancements:
 *  - Add support for more browsers like Safari or Opera.
 *  - Improve the browser initialization to read configurations dynamically.
 */


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

package webUtilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.LoggerManager;

import java.util.List;
import java.util.logging.Logger;

public class Elements {
	private WebDriver driver;
	private By by;
	protected static final Logger logger = LoggerManager.getLogger();

	public Elements(By by, WebDriver driver) {
		this.by = by;
		this.driver = driver;
	}

	public void click() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
			logger.info("Clicking element: " + by);
			element.click();
		} catch (Exception e) {
			throw new RuntimeException("Element not clickable: " + by, e);
		}
	}

	public String getText() {
		logger.info("Getting text from element: " + by);
		return getElement().getText();
	}

	public boolean isDisplayed() {
		logger.info("Checking if element is displayed: " + by);
		try {
			return getElement().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean waitForElementToDisappear(Duration timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception e) {
			throw new RuntimeException("Element did not disappear within timeout: " + by, e);
		}
	}

	protected WebElement getElement(Duration timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			throw new RuntimeException("Element not found or not visible: " + by, e);
		}
	}

	protected WebElement getElement() {
		return getElement(Duration.ofSeconds(10)); // Default timeout
	}

	protected boolean isEnabled() {
		logger.info("Checking if element is Enabled: " + by);
		try {
			return getElement().isEnabled();
		} catch (Exception e) {
			return false;
		}
	}
	
	public List<WebElement> getElementList(Duration timeout){
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
	}
}

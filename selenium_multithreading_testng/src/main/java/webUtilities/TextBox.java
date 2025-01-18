package webUtilities;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextBox extends Elements {
	private By by;
	public TextBox(By by, WebDriver driver) {
		super(by, driver);
		this.by = by;
	}

	public void enterText(String text) {
		logger.info("Entering text: '" + text + "' into element: " + by);
		getElement().sendKeys(text);
	}

	public void clearText() {
		logger.info("Clearing text in element: " + by);
		getElement().clear();
	}

	public void clearTextUsingKeyboard() {
		WebElement webElement = getElement();
		logger.info("Clearing text in element: " + by + " using keyboard.");
		webElement.sendKeys(Keys.CONTROL, "a");
		webElement.sendKeys(Keys.DELETE);
		if (!webElement.getAttribute("value").isEmpty()) {
			throw new RuntimeException("Failed to clear text in element: " + by);
		}
	}
}

package webUtilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Button extends Elements {
	private By by;

	public Button(By by, WebDriver driver) {
		super(by, driver);
		this.by = by;
	}
	
	public void click() {
		getElement().click();;
	}
	
}

package pageobject;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import webUtilities.Elements;
import webUtilities.TextBox;

public class HomePage {
	public TextBox searchBox;
	public Elements goButton;
	public Elements emailList;
	public Elements inboxHeader;
	public Elements emailSubjects;

	public HomePage(WebDriver driver) {
		this.searchBox = new TextBox(By.id("search"), driver);
		this.goButton = new Elements(By.xpath("//button[text()='GO']"), driver);
		this.emailList = new Elements(By.xpath("//tr[@ng-repeat='email in emails']"), driver);
		this.inboxHeader = new Elements(By.xpath("//h1[text()='Inbox']"), driver);
		this.emailSubjects = new Elements(By.xpath("//tr[@ng-repeat='email in emails']/td[3]"), driver);
	}
	
	public boolean openEmailBySubject(String subject) {
        List<WebElement> subjectElements = emailSubjects.getElementList(Duration.ofSeconds(10));
        for (WebElement element : subjectElements) {
            if (element.getText().equalsIgnoreCase(subject)) {
                element.click();
                return true;
            }
        }
        return false; // Email with the given subject not found.
    }
	
	public List<String> getEmailSubjects() {
        List<WebElement> subjectElements = emailSubjects.getElementList(Duration.ofSeconds(10));
        return subjectElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}

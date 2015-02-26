import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import almeida.paulorocha.webdriverexp.annotations.AbstractPage;
import almeida.paulorocha.webdriverexp.annotations.PageElement;
import almeida.paulorocha.webdriverexp.annotations.PageElement.ComponentType;


public class TestTemplate extends AbstractPage {

	public TestTemplate(WebDriver driver) {
		super(driver);
	}

	@PageElement(type = ComponentType.BUTTON)
	protected WebElement button;
	
	@PageElement(type = ComponentType.BUTTON, fluent = false)
	protected WebElement button2;
	
}
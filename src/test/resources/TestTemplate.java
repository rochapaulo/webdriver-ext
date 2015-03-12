import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import almeida.paulorocha.webdriverexp.annotations.AbstractPage;
import almeida.paulorocha.webdriverexp.annotations.PageElement;
import almeida.paulorocha.webdriverexp.annotations.PageElement.ComponentType;


public class TestTemplate extends AbstractPage {

	public TestTemplate(WebDriver driver) {
		super(driver);
	}
	
	@PageElement(type = ComponentType.BUTTON, generateAssert = false)
	protected WebElement cancel;
	
	@PageElement(type = ComponentType.BUTTON, generateAssert = false, returnType = ReturnTemplate.class)
	protected WebElement save;

	@PageElement(type = ComponentType.INPUT)
	protected WebElement firstName;
	
	@PageElement(type = ComponentType.INPUT, fluent = false)
	protected WebElement lastName;

	@PageElement(type = ComponentType.INPUT, generateAssert = false)
	protected WebElement note;
	
	
	public static class ReturnTemplate extends AbstractPage {
	
		public ReturnTemplate(WebDriver driver) {
			super(driver);
		}
		
	}
	
}
package SuperclassErrorTemplate;

import org.openqa.selenium.WebElement;

import almeida.paulorocha.webdriverexp.annotations.PageElement;
import almeida.paulorocha.webdriverexp.annotations.PageElement.ComponentType;

public class SuperclassErrorTemplate {

	public SuperclassErrorTemplate() {
		super();
	}
	
	@PageElement(type = ComponentType.BUTTON)
	protected WebElement button;

}

package almeida.paulorocha.webdriverexp.processors.pageElement;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.mockito.Mockito.when;

import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;

import almeida.paulorocha.webdriverexp.annotations.AbstractPage;
import almeida.paulorocha.webdriverexp.annotations.PageElement;
import almeida.paulorocha.webdriverexp.annotations.PageElement.ComponentType;

public class ButtonTest {

	@Mock private VariableElement fieldElement;
	@Mock private TypeElement classElement;
	@Mock private Name fieldName, className;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void returnVoid() {
		when(fieldElement.getAnnotation(PageElement.class))
			.thenReturn(
					new Annotation.PageElementBuilder()
						.componentType(ComponentType.BUTTON)
						.fluent(false)
						.build()
					);
		when(fieldName.toString()).thenReturn("button");
		when(fieldElement.getSimpleName()).thenReturn(fieldName);
		
		Button button = new Button(fieldElement);
		Method method = button.process();
		
		StringBuilder expectedMethod = new StringBuilder();
		expectedMethod
			.append("_public void clickButton() {\n")
			.append("__System.out.println(\"ButtonClicked\");\n")
			.append("_}");
		
		Assert.assertThat(method.getImportList(), empty());
		Assert.assertEquals(expectedMethod.toString(), method.get());
	}
	
	@Test
	public void returnPage() {
		when(fieldElement.getAnnotation(PageElement.class))
		.thenReturn(
				new Annotation.PageElementBuilder()
					.componentType(ComponentType.BUTTON)
					.returnType(TestTemplate.class)
					.fluent(false)
					.build()
				);
		when(fieldName.toString()).thenReturn("button");
		when(fieldElement.getSimpleName()).thenReturn(fieldName);
		
		Button button = new Button(fieldElement);
		Method method = button.process();
		
		StringBuilder expectedMethod = new StringBuilder();
		expectedMethod
			.append("_public TestTemplate clickButton() {\n")
			.append("__System.out.println(\"ButtonClicked\");\n")
			.append("__return new TestTemplate(driver);\n")
			.append("_}");
		
		Assert.assertThat(method.getImportList(),
				contains(new Import("almeida.paulorocha.webdriverexp.processors.pageElement.ButtonTest.TestTemplate")));
		
		Assert.assertEquals(expectedMethod.toString(), method.get());
	}
	
	@Test
	public void returnThis() {
		when(fieldElement.getAnnotation(PageElement.class))
		.thenReturn(
				new Annotation.PageElementBuilder()
					.componentType(ComponentType.BUTTON)
					.fluent(true)
					.build()
				);
		when(fieldName.toString()).thenReturn("button");
		when(fieldElement.getSimpleName()).thenReturn(fieldName);
		when(fieldElement.getEnclosingElement()).thenReturn(classElement);
		when(classElement.getQualifiedName()).thenReturn(className);
		when(className.toString()).thenReturn("TestTemplate");
		
		Button button = new Button(fieldElement);
		Method method = button.process();
		
		StringBuilder expectedMethod = new StringBuilder();
		expectedMethod
			.append("_public TestPage clickButton() {\n")
			.append("__System.out.println(\"ButtonClicked\");\n")
			.append("__return this;\n")
			.append("_}");
		
		Assert.assertThat(method.getImportList(), empty());
		Assert.assertEquals(expectedMethod.toString(), method.get());
	}
	
	class TestTemplate extends AbstractPage {
		public TestTemplate(WebDriver driver) {
			super(driver);
		}
	}
	
}

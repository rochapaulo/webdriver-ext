package almeida.paulorocha.webdriverexp.processors.pageElement;

import static com.google.testing.compile.JavaFileObjects.forResource;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.truth0.Truth.ASSERT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import almeida.paulorocha.webdriverexp.processors.pageElement.PageElementProcessor;

@RunWith(JUnit4.class)
public class PageElementProcessorTest {

	private final String PROTECTED_MODIFIER_ERROR = "ProtectedModifierErrorTemplate.java";
	private final String SUPERCLASS_ERROR = "SuperclassErrorTemplate.java";
	private final String SUFFIX_PAGE_TEMPLATE_ERROR = "SuffixError.java";

	@Test
	public void verifyGeneratedClass() {
		ASSERT.about(javaSource())
			.that(forResource("TestTemplate.java"))
			.processedWith(new PageElementProcessor())
			.compilesWithoutError()
			.and()
			.generatesSources(forResource("TestPage.java"));
	}
	
	@Test
	public void protectedModifierError() {
		ASSERT.about(javaSource())
			.that(forResource(PROTECTED_MODIFIER_ERROR))
			.processedWith(new PageElementProcessor())
			.failsToCompile()
			.withErrorContaining("Page fields must be: protected");
	}
	
	@Test
	public void superclassError() {
		ASSERT.about(javaSource())
		.that(forResource(SUPERCLASS_ERROR))
		.processedWith(new PageElementProcessor())
		.failsToCompile()
		.withErrorContaining("The page template must extend AbstractPage");
	}
	
	@Test
	public void suffixPageTemplateError() {
		ASSERT.about(javaSource())
		.that(forResource(SUFFIX_PAGE_TEMPLATE_ERROR))
		.processedWith(new PageElementProcessor())
		.failsToCompile()
		.withErrorContaining("The page template must be terminated with suffix: \"Template\"");
	}
	
}

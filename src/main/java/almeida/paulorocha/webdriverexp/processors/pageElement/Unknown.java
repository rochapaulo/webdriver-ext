package almeida.paulorocha.webdriverexp.processors.pageElement;

import javax.lang.model.element.VariableElement;

import almeida.paulorocha.webdriverexp.processors.pageElement.Method.Modifiers;

final class Unknown extends ComponentProcessor {
	
	private static final Script fgAssertScript = Script._assert();

	Unknown(VariableElement fieldElement) {
		super(fieldElement);
	}

	@Override
	public Method process() {
		return new Method.Builder(fieldElement)
			.modifier(Modifiers.PUBLIC)
			.namePreffix("assert")
			.arguments(
					new Method.Argument("Matcher<WebElement>", "matcher")
					)
			.script(fgAssertScript)
			.build();
	}

}

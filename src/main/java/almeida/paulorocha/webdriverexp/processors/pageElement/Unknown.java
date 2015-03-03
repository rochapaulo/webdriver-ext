package almeida.paulorocha.webdriverexp.processors.pageElement;

import javax.lang.model.element.VariableElement;

final class Unknown extends ComponentProcessor {
	
	private static final Script fgAssertScript = Script._assert();

	Unknown(VariableElement fieldElement) {
		super(fieldElement);
	}

	@Override
	public Method process() {
		return new Method.Builder(fieldElement)
			.modifier("public")
			.namePreffix("assert")
			.arguments(
					new Method.Argument("WebElement", "element"),
					new Method.Argument("Matcher<WebElement>", "matcher")
					)
			.script(fgAssertScript)
			.build();
	}

}

package almeida.paulorocha.webdriverexp.processors.pageElement;

import javax.lang.model.element.VariableElement;

import almeida.paulorocha.webdriverexp.processors.pageElement.Method.Modifiers;

final class Assert extends ComponentProcessor {
	
	private static final Script fgAssertScript = Script._assert();

	Assert(VariableElement fieldElement) {
		super(fieldElement);
	}

	@Override
	public MethodSet process() {
		MethodSet methods = new MethodSet(1);
		
		methods.add(
			new Method.Builder(fieldElement)
				.modifier(Modifiers.PUBLIC)
				.namePreffix("assert")
				.arguments(
					new Method.Argument("Matcher<WebElement>", "matcher")
				)
				.script(fgAssertScript)
				.build()
		);
		
		return methods;
	}

}

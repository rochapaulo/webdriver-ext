package almeida.paulorocha.webdriverexp.processors.pageElement;

import javax.lang.model.element.VariableElement;

import almeida.paulorocha.webdriverexp.processors.pageElement.Method.Modifiers;

final class Input extends ComponentProcessor {

	private static final Script fgInputScript = Script._input();

	Input(VariableElement fieldElement) {
		super(fieldElement);
	}

	@Override
	public Method process() {
		return new Method.Builder(fieldElement)
		.modifier(Modifiers.PUBLIC)
			.namePreffix("type")
			.arguments(
					new Method.Argument("String", "value")
					)
			.script(fgInputScript)
			.build();
	}

}

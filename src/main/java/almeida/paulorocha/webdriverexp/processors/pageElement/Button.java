package almeida.paulorocha.webdriverexp.processors.pageElement;

import javax.lang.model.element.VariableElement;

import almeida.paulorocha.webdriverexp.processors.pageElement.Method.Modifiers;


final class Button extends ComponentProcessor {

	private static final Script fgButtonScript = Script._button();

	Button(VariableElement element) {
		super(element);
	}
	
	@Override
	public Method process() {
		Method method = new Method.Builder(fieldElement)
			.modifier(Modifiers.PUBLIC)
			.namePreffix("click")
			.script(fgButtonScript)
			.build();
		
		return method;
	}

}

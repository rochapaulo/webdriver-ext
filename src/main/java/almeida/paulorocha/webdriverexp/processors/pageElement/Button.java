package almeida.paulorocha.webdriverexp.processors.pageElement;

import javax.lang.model.element.VariableElement;


final class Button extends ComponentProcessor {

	private static final Script fgButtonScript = Script._button();

	Button(VariableElement element) {
		super(element);
	}
	
	@Override
	public Method process() {
		Method method = new Method.Builder(fieldElement)
			.modifier("public")
			.namePreffix("click")
			.script(fgButtonScript)
			.build();
		
		return method;
	}

}

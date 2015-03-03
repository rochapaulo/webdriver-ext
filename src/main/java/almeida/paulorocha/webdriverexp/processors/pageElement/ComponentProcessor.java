package almeida.paulorocha.webdriverexp.processors.pageElement;

import javax.lang.model.element.VariableElement;


abstract class ComponentProcessor {

	protected final VariableElement fieldElement;

	ComponentProcessor(VariableElement element) {
		this.fieldElement = element;
	}
	
	abstract Method process();
	
}

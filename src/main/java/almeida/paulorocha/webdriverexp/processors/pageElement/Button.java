package almeida.paulorocha.webdriverexp.processors.pageElement;

import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.VOID;
import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.toCamelCase;
import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.toCanonicalName;
import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.toReturnType;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;

import almeida.paulorocha.webdriverexp.annotations.PageElement;


final class Button extends ComponentProcessor {

	Button(Element element) {
		super(element);
	}

	@Override
	public Method process() {
		PageElement annotation = element.getAnnotation(PageElement.class);
		
		StringBuilder sb = new StringBuilder();
		
		String returnCanonical = null;
		if (annotation.fluent()) {
			TypeElement typeElement = (TypeElement)element.getEnclosingElement();
			returnCanonical = typeElement.getQualifiedName().toString();
			returnCanonical = returnCanonical.replace("Template", "Page");
		} else {
			try {
				annotation.returnType();
			} catch(MirroredTypeException ex) {
				returnCanonical = toCanonicalName(ex.getTypeMirror());
			}
		}
		
		String returnType = toReturnType(returnCanonical);
		sb.append("_public ")
			.append(returnType)
			.append(" click")
			.append(toCamelCase(element.getSimpleName().toString()))
			.append("() {\n")
			.append("__System.out.println(\"" + "ButtonClicked" + "\");\n");
		
		if (!annotation.fluent()) {
			if (!returnType.equals(VOID)) {
				sb.append("__return new ")
					.append(returnType)
					.append("(driver);\n");
			}
		} else {
			sb.append("__return this;\n");
		}
		
		sb.append("_}");
		
		Method method = new Method(sb.toString());
		if (!returnType.equals(VOID) && !annotation.fluent()) {
			method.addDependencies(returnCanonical);
		}
		return method;
	}

}

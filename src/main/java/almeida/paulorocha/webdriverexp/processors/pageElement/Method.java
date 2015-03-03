package almeida.paulorocha.webdriverexp.processors.pageElement;

import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.VOID;
import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.toCamelCase;
import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.toCanonicalName;
import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.toReturnType;
import static java.lang.String.format;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;

import almeida.paulorocha.webdriverexp.annotations.PageElement;

public class Method {
	
	private final Set<String> imports = new HashSet<String>();
	
	private final String method;
	
	static class Builder {
		
		private final boolean fluent;
		private String modifier, preffix, name, returnCanonical;
		private String[] arguments = new String[0];
		private Script script;
		
		public Builder(VariableElement fieldElement) {
			final PageElement annotation = fieldElement.getAnnotation(PageElement.class);
			fluent = annotation.fluent();
			if (fluent) {
				TypeElement typeElement = (TypeElement)fieldElement.getEnclosingElement();
				returnCanonical = typeElement.getQualifiedName().toString();
				returnCanonical = returnCanonical.replace("Template", "Page");
			} else {
				try {
					returnCanonical = toCanonicalName(annotation.returnType());
				} catch(MirroredTypeException ex) {
					returnCanonical = toCanonicalName(ex.getTypeMirror());
				}
			}
			name = toCamelCase(fieldElement.getSimpleName().toString());
		}
		
		Builder modifier(String value) {
			modifier = value;
			return this;
		}
		
		Builder namePreffix(String value) {
			preffix = value;
			return this;
		}
		
		Builder arguments(String... values) {
			arguments = values;
			return this;
		}
		
		Builder script(Script value) {
			script = value;
			return this;
		}
		
		Method build() {
			return new Method(this);
		}

	}
	
	Method(Builder builder) {
		StringBuilder sb = new StringBuilder();
		
		String arguments = buildSignature(builder.arguments);
		
		final String returnType = toReturnType(builder.returnCanonical);
		sb.append(format("_%s", builder.modifier))
			.append(format(" %s", returnType))
			.append(format(" %s%s", builder.preffix, builder.name))
			.append(format("(%s) {\n", arguments))
			.append(builder.script.getBody())
			.append("\n");

		if (!builder.fluent) {
			if (!returnType.equals(VOID)) {
				sb.append(format("__return new %s(driver);\n", returnType));
				imports.add(builder.returnCanonical);
			}
		} else {
			sb.append("__return this;\n");
		}
		
		sb.append("_}");
		imports.addAll(builder.script.getImportList());
		method = sb.toString();
	}

	private String buildSignature(String[] args) {
		if (args.length == 0) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		int argLength = args.length;
		for (int i = 0; i < argLength - 1; i++) {
			sb.append(format("%s %s, ", args[i], lowerFirst(args[i])));
		}
		sb.append(format("%s %s", args[argLength - 1], lowerFirst(args[argLength - 1])));
		
		return sb.toString();
	}
	
	private String lowerFirst(String value) {
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}
	
	public Set<String> getImportList() {
		return Collections.unmodifiableSet(imports);
	}
	
	public String get() {
		return method;
	}
	
}

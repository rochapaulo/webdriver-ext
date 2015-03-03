package almeida.paulorocha.webdriverexp.processors.pageElement;

import static almeida.paulorocha.webdriverexp.processors.pageElement.Method.Modifiers.PUBLIC;
import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.VOID;
import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.toCamelCase;
import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.toCanonicalName;
import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.toReturnType;
import static java.lang.String.format;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;

import almeida.paulorocha.webdriverexp.annotations.PageElement;

public class Method implements Comparable<Method> {
	
	private final SortedSet<Import> imports = new TreeSet<Import>();
	
	private final String method;
	
	public enum Modifiers {	PUBLIC }
	
	static class Builder {
		
		private final boolean fluent;
		private String modifier, preffix, fieldName, returnCanonical;
		private Argument[] arguments = new Argument[0];
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
			fieldName = fieldElement.getSimpleName().toString();
		}
		
		Builder modifier(Modifiers value) {
			modifier = value.toString().toLowerCase();
			return this;
		}
		
		Builder namePreffix(String value) {
			preffix = value;
			return this;
		}
		
		Builder arguments(Argument... values) {
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
	
	private Method(Builder builder) {
		StringBuilder sb = new StringBuilder();
		
		String arguments = buildSignature(builder.arguments);
		
		final String returnType = toReturnType(builder.returnCanonical);
		sb.append(format("_%s", builder.modifier))
			.append(format(" %s", returnType))
			.append(format(" %s%s", builder.preffix, toCamelCase(builder.fieldName)))
			.append(format("(%s) {\n", arguments))
			.append(builder.script.getBody().replace("$element", builder.fieldName))
			.append("\n");

		if (!builder.fluent) {
			if (!returnType.equals(VOID)) {
				sb.append(format("__return new %s(driver);\n", returnType));
				imports.add(new Import(builder.returnCanonical));
			}
		} else {
			sb.append("__return this;\n");
		}
		
		sb.append("_}");
		imports.addAll(builder.script.getImportList());
		method = sb.toString();
	}

	private String buildSignature(Argument[] args) {
		if (args.length == 0) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		int argLength = args.length;
		for (int i = 0; i < argLength - 1; i++) {
			sb.append(format("%s %s, ", args[i].type, args[i].name));
		}
		sb.append(format("%s %s", args[argLength - 1].type, args[argLength - 1].name));
		
		return sb.toString();
	}
	
	public Set<Import> getImportList() {
		return Collections.unmodifiableSet(imports);
	}
	
	public String get() {
		return method;
	}
	
	 public static class Argument {
		 
		private final String type, name;

		public Argument(String type, String name) {
			 this.type = type;
			 this.name = name;
		 }
		
	 }

	@Override
	public int compareTo(Method other) {
		return getName(this).compareTo(getName(other));
	}
	
	private String getName(Method method) {
		Pattern pattern = compile(format("%s (.*?) (.*?)\\(.* ", PUBLIC), CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(method.get());
		if (matcher.find()) {
			return matcher.group(2);
		}
		throw new RuntimeException("Method signature name malformed = " + method.get());
	}
	
}

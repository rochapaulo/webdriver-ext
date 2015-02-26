package almeida.paulorocha.webdriverexp.processors.pageElement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;


abstract class ComponentProcessor {

	protected final Element element;

	ComponentProcessor(Element element) {
		this.element = element;
	}
	
	abstract Method process();
	
	static class Method implements Comparable<Method> {
		
		private final String body;
		private Set<String> dependencies = new HashSet<String>();
		
		Method(String body) {
			this.body = body;
		}
		
		void addDependencies(String... values) {
			for (String value : values) {
				dependencies.add(value);
			}
		}
		
		void removeDependency(String value) {
			dependencies.remove(value);
		}
		
		Set<String> getDependencies() {
			return Collections.unmodifiableSet(dependencies);
		}
		
		public String getBody() {
			return body;
		}

		@Override
		public int compareTo(Method otherMethod) {
			return getName(this).compareTo(getName(otherMethod));
		}
		
		private String getName(Method method) {
			Pattern pattern = Pattern.compile("protected (.*?) ");
			Matcher matcher = pattern.matcher(method.getBody());
			return matcher.group(1);
		}
	}
	
}

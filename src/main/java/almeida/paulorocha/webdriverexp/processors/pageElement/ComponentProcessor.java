package almeida.paulorocha.webdriverexp.processors.pageElement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Element;


abstract class ComponentProcessor {

	protected final Element element;

	ComponentProcessor(Element element) {
		this.element = element;
	}
	
	abstract Method process();
	
	static class Method {
		
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
	}
	
}

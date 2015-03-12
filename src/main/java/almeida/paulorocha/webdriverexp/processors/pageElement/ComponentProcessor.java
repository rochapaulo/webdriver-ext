package almeida.paulorocha.webdriverexp.processors.pageElement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.lang.model.element.VariableElement;


abstract class ComponentProcessor {

	protected final VariableElement fieldElement;

	ComponentProcessor(VariableElement element) {
		this.fieldElement = element;
	}
	
	abstract MethodSet process();
	
	public static class MethodSet implements Iterable<Method> {

		private final Set<Method> methods;
		
		public MethodSet(int initialCapacity) {
			methods = new HashSet<Method>(initialCapacity);
		}
		
		public void add(Method method) {
			methods.add(method);
		}
		
		@Override
		public Iterator<Method> iterator() {
			return methods.iterator();
		}
		
	}
	
}

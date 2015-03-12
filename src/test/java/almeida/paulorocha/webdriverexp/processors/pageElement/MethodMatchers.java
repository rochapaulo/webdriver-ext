package almeida.paulorocha.webdriverexp.processors.pageElement;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import almeida.paulorocha.webdriverexp.processors.pageElement.ComponentProcessor.MethodSet;


public abstract class MethodMatchers {

	private MethodMatchers() {
		super();
	}
	
	public static TypeSafeMatcher<MethodSet> contains(final TypeSafeMatcher<Method> matcher) {
		return new TypeSafeMatcher<ComponentProcessor.MethodSet>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("contains");
			}

			@Override
			protected boolean matchesSafely(MethodSet methods) {
				for (Method method : methods) {
					if (!matcher.matches(method)) {
						return false;
					}
				}
				return true;
			}
		};
	}
	
	public static TypeSafeMatcher<Method> methodImpl(final String methodImpl) {
		return new TypeSafeMatcher<Method>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("method impl");
			}

			@Override
			protected boolean matchesSafely(Method method) {
				return method.get().equals(methodImpl);
			}
		};
	}
	
	public static TypeSafeMatcher<Method> dependency(final Import imports) {
		return new TypeSafeMatcher<Method>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("dependency");
			}

			@Override
			protected boolean matchesSafely(Method method) {
				return method.getImportSet().contains(imports);
			}
		};
	}
	
	public static TypeSafeMatcher<Method> noDependency() {
		return new TypeSafeMatcher<Method>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("no dependency");
			}

			@Override
			protected boolean matchesSafely(Method method) {
				return method.getImportSet().isEmpty();
			}
		};
	}
	
}

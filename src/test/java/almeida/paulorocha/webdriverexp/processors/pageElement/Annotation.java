package almeida.paulorocha.webdriverexp.processors.pageElement;

import almeida.paulorocha.webdriverexp.annotations.AbstractPage;
import almeida.paulorocha.webdriverexp.annotations.PageElement;
import almeida.paulorocha.webdriverexp.annotations.PageElement.ComponentType;

public class Annotation {
	
	public static class PageElementBuilder {
		
		private ComponentType type = ComponentType.UNKNOWN;
		private boolean isFluent = false;
		private Class<? extends AbstractPage> returnType = AbstractPage.class;
		
		public PageElementBuilder() {
			super();
		}
		
		public PageElementBuilder componentType(ComponentType value) {
			type = value;
			return this;
		}
		
		public PageElementBuilder fluent(boolean value) {
			isFluent = value;
			return this;
		}
		
		public PageElementBuilder returnType(Class<? extends AbstractPage> value) {
			returnType = value;
			return this;
		}
		
		public PageElement build() {
			return new PageElement() {

				public Class<? extends java.lang.annotation.Annotation> annotationType() {
					return PageElement.class;
				}

				public ComponentType type() {
					return type;
				}

				public boolean fluent() {
					return isFluent;
				}

				public Class<? extends AbstractPage> returnType() {
					return returnType;
				}
			};
		}
		
	}
	
}

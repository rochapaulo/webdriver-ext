package almeida.paulorocha.webdriverexp.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface PageElement {

	ComponentType type() default ComponentType.UNKNOWN;
	
	boolean fluent() default true;
	
	boolean generateAssert() default true;
	
	Class<? extends AbstractPage> returnType() default AbstractPage.class;
	
	public enum ComponentType {
		INPUT,
		BUTTON, 
		UNKNOWN
	}
	
}

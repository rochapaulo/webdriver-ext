package almeida.paulorocha.webdriverexp.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface HelloWorld {

	Action action() default Action.GENERATE_NOTE;
	String phrase() default "HelloWorld";
	
	public enum Action {
		GENERATE_CLASS,
		GENERATE_NOTE
	}
	
}

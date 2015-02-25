package almeida.paulorocha.webdriverexp.processors.pageElement;

import almeida.paulorocha.webdriverexp.annotations.AbstractPage;



public final class PageElementHelper {

	public static final String VOID = "void";
	private static final String ABSTRACT_PAGE = "almeida.paulorocha.processing.annotations.AbstractPage";
	private static final String $4SPACES = "    ";
	
	private PageElementHelper() {
		super();
	}
	
	public static String toCamelCase(String value) {
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}
	
	public static String toCanonicalName(Class<?> clazz) {
		if (clazz.getCanonicalName().equals(ABSTRACT_PAGE)) {
			return "void";
		}
		return clazz.getCanonicalName();
	}
	
	public static String toReturnType(String canonicalName) {
		if (canonicalName.equals(AbstractPage.class.getCanonicalName())) {
			return VOID;
		}
		String[] split = canonicalName.split("\\.");
		return split[split.length - 1];
	}
	
	public static String indent(String value, String marker) {
		return value.replace(marker, $4SPACES);
	}
	
}

package almeida.paulorocha.webdriverexp.processors.pageElement;

import static almeida.paulorocha.webdriverexp.processors.pageElement.PageElementHelper.indent;
import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import almeida.paulorocha.webdriverexp.annotations.AbstractPage;
import almeida.paulorocha.webdriverexp.annotations.PageElement;
import almeida.paulorocha.webdriverexp.processors.pageElement.ComponentProcessor.Method;

@SupportedAnnotationTypes("almeida.paulorocha.webdriverexp.annotations.PageElement")
public class PageElementProcessor extends AbstractProcessor {

	private Filer filer;
	private Messager messager;
	
	private String packageName;
	private String pageImplName;
	private String pageTemplateName;
	private Set<Method> methodSet = new LinkedHashSet<ComponentProcessor.Method>(); 
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		filer = processingEnv.getFiler();
		messager = processingEnv.getMessager();
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		
		if (roundEnv.errorRaised()) {
			return false;
		}
		
		for (Element element : roundEnv.getElementsAnnotatedWith(PageElement.class)) {
			
			TypeElement typeElement = (TypeElement) element.getEnclosingElement();
			
			pageTemplateName = typeElement.getSimpleName().toString();
			if (!pageTemplateName.matches(".*Template")) {
				messager.printMessage(Kind.ERROR, "The page template must be terminated with suffix: \"Template\"", typeElement);
				return false;
			}
			
			final Types types = processingEnv.getTypeUtils();
			final Elements elements = processingEnv.getElementUtils();
			if (!types.isSameType(typeElement.getSuperclass(), elements.getTypeElement(AbstractPage.class.getCanonicalName()).asType())) {
				messager.printMessage(Kind.ERROR, "The page template must extend AbstractPage", typeElement);
				return false;
			}
			
			if (!element.getModifiers().contains(Modifier.PROTECTED))  {
				messager.printMessage(Kind.ERROR, "Page fields must be: protected", element);
				return false;
			}
			
			pageImplName = pageTemplateName.replace("Template", "Page");
			
			PackageElement packageElement = (PackageElement)typeElement.getEnclosingElement();
			packageName = packageElement.getQualifiedName().toString();
			
			PageElement annotation = element.getAnnotation(PageElement.class);
			switch (annotation.type()) {
			case BUTTON:
				process(new Button(element));
				break;
				
			case INPUT:
				process(new Input(element));
				break;
				
			case UNKNOWN:
				process(new Unknown(element));
				break;
			}
		}
		try {
			
			if (roundEnv.processingOver()) {
				generatePage();
				messager.printMessage(Kind.NOTE, "Processing finished");
			}
			
		} catch (IOException ex) {
			messager.printMessage(Kind.ERROR, ex.getLocalizedMessage());
		}
		
		return true;
	}
	
	private void process(ComponentProcessor processor) {
		methodSet.add(processor.process());
	}
	
	private void generatePage() throws IOException {
		StringBuilder methods = new StringBuilder();
		
		Set<String> dependencySet = new HashSet<String>();
		for (Method method : methodSet) {
			dependencySet.addAll(method.getDependencies());
			methods.append("\n")
				.append(indent(method.getBody(), "_"))
				.append("\n");
		}
		
		StringBuilder pageObject = new StringBuilder();
		
		String sourceFile = null;
		if (!packageName.isEmpty()) {
			sourceFile = format("%s.%s", packageName, pageImplName);
			pageObject.append(format("package %s;\n\n", packageName));
		} else {
			sourceFile = pageImplName;
		}
		
		dependencySet.addAll(asList("org.openqa.selenium.WebDriver"));
		for (String dependency : dependencySet) {
			pageObject.append(format("import %s;\n", dependency));
		}
		pageObject.append("\n");
		pageObject.append(format("public class %s extends %s {\n\n", pageImplName, pageTemplateName))
			.append(constructor())
			.append(methods)
			.append("\n}");

		JavaFileObject file = filer.createSourceFile(sourceFile);
		PrintWriter writer = new PrintWriter(file.openOutputStream());
		try {
			writer.write(pageObject.toString());
		} finally {
			writer.close();
		}
	}
	
	private String constructor() {
		StringBuilder sb = new StringBuilder();
		sb.append("_public " + pageImplName + "(WebDriver driver) {\n")
			.append("__super(driver);\n")
			.append("_}\n");
		return indent(sb.toString(), "_");
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

}

package almeida.paulorocha.webdriverexp.processors;

import static java.lang.String.format;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import almeida.paulorocha.webdriverexp.annotations.HelloWorld;

@SupportedAnnotationTypes("almeida.paulorocha.processing.annotations.HelloWorld")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class HelloWorldProcessor extends AbstractProcessor {

	private Filer filer;
	private Messager messager;
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		filer = processingEnv.getFiler();
		messager = processingEnv.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (Element element: roundEnv.getElementsAnnotatedWith(HelloWorld.class)) {
			HelloWorld annotation = element.getAnnotation(HelloWorld.class);
			
			switch (annotation.action()) {
			case GENERATE_NOTE:
				messager.printMessage(Kind.NOTE, annotation.phrase());
				break;
			case GENERATE_CLASS:
				generateClass((TypeElement)element, annotation.phrase());
				break;
			}
			
		}
		return true;
	}
	
	private void generateClass(TypeElement element, String phrase) {
		try {
			PackageElement packageElement = (PackageElement)element.getEnclosingElement();
			
			JavaFileObject jfo = filer.createSourceFile(String.format("%sGenerated", element.getQualifiedName()));
			BufferedWriter bw = new BufferedWriter(jfo.openWriter());
			bw.append(format("package %s ;", packageElement.getQualifiedName()));
			bw.newLine(); 
			bw.newLine();
			bw.append("public class ");
			bw.append(element.getSimpleName() + "Generated");
			bw.append("{");
			bw.newLine();
			bw.newLine();
			bw.append("    public void sayHello() { ");
			bw.newLine();
			bw.append("        System.out.println(\"" + phrase + "\");");
			bw.newLine();
			bw.append("    }");
			bw.newLine();
			bw.newLine();
			bw.append("}");
			bw.newLine();
			bw.close();
		} catch (IOException ex) {
			messager.printMessage(Kind.ERROR, ex.getLocalizedMessage());
		}
	}
	
}

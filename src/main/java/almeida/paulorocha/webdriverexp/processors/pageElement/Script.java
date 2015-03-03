package almeida.paulorocha.webdriverexp.processors.pageElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;


public class Script {

	private File scriptFile;
	
	private String body;
	private List<Import> imports;
	
	public static Script _button() {
		return new Script("scripts/button.method");
	}
	
	public static Script _assert() {
		return new Script("scripts/assert.method");
	}
	
	Script(String scriptPath) {
		scriptFile = new File(Thread.currentThread().getContextClassLoader().getResource(scriptPath).getFile());
		
		try {
			
			final BufferedReader buffer = new BufferedReader(new FileReader(scriptFile));
			imports = Lists.transform(readContents(buffer, "imports:["), new Function<String, Import>() {

				@Override
				public Import apply(String input) {
					return new Import(input);
				}
				
			});
			
			body = readContent(buffer, "body:[");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getBody() {
		return body;
	}
	
	public Collection<Import> getImportList() {
		return imports;
	}
	
	private String readContent(BufferedReader buffer, String scriptSection) throws IOException {
		List<String> content = readContents(buffer, scriptSection);
		StringBuilder sb = new StringBuilder();
		for (String line : content) {
			sb.append(line);
		}
		return sb.toString();
	}
	
	private List<String> readContents(BufferedReader buffer, String scriptSection) throws IOException {
		List<String> lines = new ArrayList<String>();
		String readBuff = null;
		do {
			readBuff = buffer.readLine();
		} while(buffer != null && !readBuff.startsWith(scriptSection));
		
		while(readBuff != null) {
			readBuff = buffer.readLine();
			
			if (!readBuff.startsWith("]")) {
				lines.add(readBuff);
			} else {
				break;
			}
			
		}
		
		return lines;
	}

}

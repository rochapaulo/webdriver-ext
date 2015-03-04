package almeida.paulorocha.webdriverexp.processors.pageElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Script {

	private String body;
	private List<Import> imports = new ArrayList<Import>();
	
	public static Script _assert() {
		return new Script("scripts/assert.method");
	}
	
	public static Script _button() {
		return new Script("scripts/button.method");
	}
	
	public static Script _input() {
		return new Script("scripts/input.method");
	}
	
	Script(String scriptPath) {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(scriptPath);

		try {
			
			final BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
			
			for (String value : readContents(buffer, "imports:[")) {
				imports.add(new Import(value));
			}
		
			body = readContent(buffer, "body:[");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getBody() {
		return body;
	}
	
	public List<Import> getImportList() {
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

package almeida.paulorocha.webdriverexp.processors.pageElement;


public class Import implements Comparable<Import>{

	private final String value;
	
	public Import(String value) {
		this.value = value;
	}
	
	public String get() {
		return value;
	}

	@Override
	public int compareTo(Import other) {
		return value.compareTo(other.value);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Import other = (Import) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}

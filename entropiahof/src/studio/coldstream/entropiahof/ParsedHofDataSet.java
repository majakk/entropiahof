package studio.coldstream.entropiahof;

public class ParsedHofDataSet {
	private String nameString = null;
	private String resourceString = null;
	private String valueString = null;
	
	public ParsedHofDataSet(String ns, String rs, String vs){
		nameString = ns;
		resourceString = rs;
		valueString = vs;
	}

	public String getNameString() {
		return nameString;
	}
	public void setNameString(String extractedString) {
		this.nameString = extractedString;
	}
	
	public void addToNameString(String extractedString) {
		this.nameString += extractedString;
	}

	public String getResourceString() {
		return resourceString;
	}
	public void setResourceString(String extractedString) {
		this.resourceString = extractedString;
	}
	
	public void addToResourceString(String extractedString) {
		this.resourceString += extractedString;
	}
	
	public String getValueString() {
		return valueString;
	}
	public void setValueString(String extractedString) {
		this.valueString = extractedString;
	}
	
	public String toString(){
		return this.nameString + "  " + this.resourceString + "  " + this.valueString;
	}
}
package studio.coldstream.entropiahof;

public class ParsedItemsDataSet {
	private String itemString = null;
	private String quantityString = null;
	private String valueString = null;
	private String bidString = null;
	private String bidsString = null;
	
	public ParsedItemsDataSet(String is, String qs, String vs, String as, String bs){
		itemString = is;
		quantityString = qs;
		valueString = vs;
		bidString = as;
		bidsString = bs;
	}

	public String getItemString() {
		return itemString;
	}
	public void setItemString(String extractedString) {
		this.itemString = extractedString;
	}

	public String getQuantityString() {
		return quantityString;
	}
	public void setQuantityString(String extractedString) {
		this.quantityString = extractedString;
	}
	
	/*public void addToResourceString(String extractedString) {
		this.resourceString += extractedString;
	}*/
	
	public String getValueString() {
		return valueString;
	}
	public void setValueString(String extractedString) {
		this.valueString = extractedString;
	}
	
	public String getBidString() {
		return bidString;
	}
	public void setBidString(String extractedString) {
		this.bidString = extractedString;
	}
	
	public String getBidsString() {
		return bidsString;
	}
	public void setBidsString(String extractedString) {
		this.bidsString = extractedString;
	}
	
	/*public String toString(){
		return this.itemString + "  " + this.resourceString + "  " + this.valueString;
	}*/
}
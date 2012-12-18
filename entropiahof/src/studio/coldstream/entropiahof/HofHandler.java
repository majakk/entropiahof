package studio.coldstream.entropiahof;

import java.util.LinkedList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;


public class HofHandler extends DefaultHandler{

	// ===========================================================
	// Fields
	// ===========================================================
	static final String TAG = "EH"; // for Log
	
	boolean in_tabletag = false;
	boolean in_trtag = false;
	boolean in_tdtag = false;
	boolean in_atag = false;
	boolean in_fonttag = false;
	int counter = 0;
	int trcounter = 0;
	int indexer[] = new int[5];
	
	String previous = "";
	
	private ParsedHofDataSet myParsedHofDataSet = new ParsedHofDataSet(null, null, null);
	private List<ParsedHofDataSet> myData = new LinkedList<ParsedHofDataSet>();

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public ParsedHofDataSet getParsedData(int index) {
		//return this.myParsedExampleDataSet;
		return this.myData.get(index);
	}
	
	public int getCounterValue() {
		return counter;
	}
	
	public int getCounterValue(int pos) {
		return indexer[pos];
	}


	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		this.myParsedHofDataSet = new ParsedHofDataSet(null, null, null);
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing to do
	}

	/** Gets be called on opening tags like: 
	 * <tag> 
	 * Can provide attribute(s), when xml was like:
	 * <tag attribute="attributeValue">*/
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equals("table")) {
			counter++;
			if(counter > 0){
				indexer[counter] = myData.size();
				Log.v(TAG, String.valueOf(indexer[counter]));
			}
			this.in_tabletag = true;
		}else if (localName.equals("tr")) {
			this.in_trtag = true;
		}else if (localName.equals("td")) {
			this.in_tdtag = true;
		}else if (localName.equals("font")) {
			this.in_fonttag = true;
		}else if (localName.equals("a")) {
			this.in_atag = true;
		//}else if (localName.equals("tagwithnumber")) {
			// Extract an Attribute
			//String attrValue = atts.getValue("thenumber");
			//int i = Integer.parseInt(attrValue);
			//myParsedExampleDataSet.setExtractedInt(i);
		}
	}
	
	/** Gets be called on closing tags like: 
	 * </tag> */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("table")) {
			this.in_tabletag = false;
		}else if (localName.equals("tr")) {
			this.in_trtag = false;
			previous = "";
			if(counter > 0 && ++trcounter % 2 == 0){
				this.myData.add(new ParsedHofDataSet(myParsedHofDataSet.getNameString(),
	    				myParsedHofDataSet.getResourceString(),
	    				myParsedHofDataSet.getValueString()));
				myParsedHofDataSet.setResourceString("");
				myParsedHofDataSet.setNameString("");
				
			}
		}else if (localName.equals("td")) {
			//previous = "";
			this.in_tdtag = false;
		}else if (localName.equals("font")) {
			this.in_fonttag = false;
		}else if (localName.equals("a")) {
			this.in_atag = false;
			
		//}else if (localName.equals("tagwithnumber")) {
			// Nothing to do here
		}
	}
	
	/** Gets be called on the following structure: 
	 * <tag>characters</tag> */
	@Override
    public void characters(char ch[], int start, int length) {
		
		if(this.in_tabletag){
			if(this.in_atag){
	    		myParsedHofDataSet.addToNameString(new String(ch, start, length));
	    	}
			//if(this.in_fonttag){
	    	//	myParsedHofDataSet.addToResourceString(new String(ch, start, length));
	    		//Log.v(TAG, String.valueOf(length));
	    	//}
			if(this.in_tdtag){
				if(counter > 0 && trcounter % 2 == 1)
				myParsedHofDataSet.addToResourceString(previous);
				//myParsedHofDataSet.setValueString(previous);
				previous = new String(ch, start, length);
	    		myParsedHofDataSet.setValueString(new String(ch, start, length));
	    	}
			
		}
		
    }
}
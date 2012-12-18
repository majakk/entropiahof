package studio.coldstream.entropiahof;

import java.util.LinkedList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//import android.util.Log;


public class GlobalHandler extends DefaultHandler{

	// ===========================================================
	// Fields
	// ===========================================================
	static final String TAG = "EH"; // for Log
	
	boolean in_itemtag = false;
	boolean in_descriptiontag = false;
	int counter = 0;
	int trcounter = 0;
	//int indexer[] = new int[5];
	
	//private ParsedHofDataSet myParsedHofDataSet = new ParsedHofDataSet(null, null, null);
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


	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		//this.myParsedHofDataSet = new ParsedHofDataSet(null, null, null);
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
		if (localName.equals("item")) {
			counter++;
			/*if(counter > 0){
				indexer[counter] = myData.size();
				Log.v(TAG, String.valueOf(indexer[counter]));
			}*/
			this.in_itemtag = true;
			//Log.v(TAG, String.valueOf(counter));
		}else if (localName.equals("description")) {
			this.in_descriptiontag = true;
		
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
		if (localName.equals("item")) {
			this.in_itemtag = false;
		}else if (localName.equals("description")) {
			this.in_descriptiontag = false;
			
		//}else if (localName.equals("tagwithnumber")) {
			// Nothing to do here
		}
	}
	
	/** Gets be called on the following structure: 
	 * <tag>characters</tag> */
	@Override
    public void characters(char ch[], int start, int length) {
		if(this.in_itemtag){
			if(this.in_descriptiontag){
				this.myData.add(new ParsedHofDataSet(new String(ch, start, length), null, null));
				//myParsedHofDataSet.setNameString("");
	    		//myParsedHofDataSet.setNameString(new String(ch, start, length));
	    		//Log.v(TAG, String.valueOf(length));
	    	}
			/*if(this.in_fonttag){
	    		myParsedHofDataSet.addToResourceString(new String(ch, start, length));
	    		//Log.v(TAG, String.valueOf(length));
	    	}
			if(this.in_tdtag){
	    		myParsedHofDataSet.setValueString(new String(ch, start, length));
	    	}*/
			
		}
		
    }
}
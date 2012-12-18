package studio.coldstream.entropiahof;

import java.util.LinkedList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

//import android.util.Log;


public class TodaysEventsHandler extends DefaultHandler{

	// ===========================================================
	// Fields
	// ===========================================================
	static final String TAG = "EH"; // for Log
	
	/*boolean in_itemtag = false;
	boolean in_titletag = false;
	boolean in_descriptiontag = false;
	boolean in_pubdatetag = false;*/
	boolean in_item = false;
	boolean in_pubdate = false;
	boolean in_title = false;
	//boolean in_content = false;
	String tableClass = "";
	String tableClassTitle = "title";
	//String tableClassContent = "entryContent";
	
	int counter = 0;
	//int trcounter = 0;
	//int indexer[] = new int[5];
	
	private ParsedHofDataSet myParsedHofDataSet = new ParsedHofDataSet("", "", "");
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
			this.in_item = true;
			
		}
		if (localName.equals("pubDate")) {
				this.in_pubdate = true;
				
		}
		if (localName.equals("title")) {
			this.in_title = true;
			
		}
		/*else if (localName.equals("description")) {
			this.in_descriptiontag = true;
		}else if (localName.equals("pubDate")) {
			this.in_pubdatetag = true;*/
		
		
		//}else if (localName.equals("tagwithnumber")) {
			// Extract an Attribute
			//String attrValue = atts.getValue("thenumber");
			//int i = Integer.parseInt(attrValue);
			//myParsedExampleDataSet.setExtractedInt(i);
		//}
	}
	
	/** Gets be called on closing tags like: 
	 * </tag> */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		
		if (localName.equals("item")) {	
			counter++;
			this.in_item = false;
			this.myData.add(new ParsedHofDataSet(myParsedHofDataSet.getNameString(),
					myParsedHofDataSet.getResourceString(), 
					myParsedHofDataSet.getValueString()));
			myParsedHofDataSet.setNameString("");
			myParsedHofDataSet.setResourceString("");
			
		}
		if (localName.equals("pubDate")) {
				this.in_pubdate = false;				
				
		}
		if (localName.equals("title")) {
			this.in_title = false;
			
		}
		
		/*else if (localName.equals("description")) {
			this.in_descriptiontag = false;
		}else if (localName.equals("pubDate")) {
			this.in_pubdatetag = false;*/
			
		//}else if (localName.equals("tagwithnumber")) {
			// Nothing to do here
		//}
	}
	
	/** Gets be called on the following structure: 
	 * <tag>characters</tag> */
	@Override
    public void characters(char ch[], int start, int length) {
		if(this.in_item){
			
			if(this.in_title){
				//Log.d(TAG, "HEJ1");
				//myParsedHofDataSet.setNameString("");
	    		myParsedHofDataSet.addToNameString(new String(ch, start, length));
	    		
	    		//Log.v(TAG, String.valueOf(length));
	    		Log.v(TAG, myParsedHofDataSet.getNameString());
	    	}
			if(this.in_pubdate){
			/*if(this.in_descriptiontag){
				//myParsedHofDataSet.setNameString("");
				myParsedHofDataSet.addToResourceString(new String(ch, start, length));
	    		//Log.v(TAG, String.valueOf(length));
	    	}*/
			
				
				//myParsedHofDataSet.setNameString("");
				myParsedHofDataSet.addToResourceString(new String(ch, start, length));
				//myParsedHofDataSet.setValueString(new String(ch, start, length));
	    		//Log.v(TAG, String.valueOf(length));
				Log.v(TAG, myParsedHofDataSet.getResourceString());
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
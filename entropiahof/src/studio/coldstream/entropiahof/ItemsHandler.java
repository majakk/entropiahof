package studio.coldstream.entropiahof;

import java.util.LinkedList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;


public class ItemsHandler extends DefaultHandler{

	// ===========================================================
	// Fields
	// ===========================================================
	static final String TAG = "ITEMS"; // for Log
	
	boolean in_tabletag = false;
	boolean in_trtag = false;
	boolean in_tdtag = false;
	boolean in_atag = false;
	boolean in_fonttag = false;
	boolean in_divtag = false;
	String tableClass = "";
	String tableClassName = "clientLoaderTable";
	
	//int counter = 0;
	int trcounter = 0;
	int tdcounter = 0;
	//int indexer[] = new int[5];
	
	private ParsedItemsDataSet myParsedItemsDataSet = new ParsedItemsDataSet(null, null, null, null, null);
	private List<ParsedItemsDataSet> myData = new LinkedList<ParsedItemsDataSet>();

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public ParsedItemsDataSet getParsedData(int index) {
		//return this.myParsedExampleDataSet;
		return this.myData.get(index);
	}
	
	/*public int getCounterValue(int pos) {
		return indexer[pos];
	}*/
	
	public int getCounterValue() {
		return trcounter;
	}


	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		//this.myParsedStatusDataSet = new ParsedHofDataSet(null, null, null);
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
			/*counter++;
			if(counter > 0){
				indexer[counter] = myData.size();
				Log.v(TAG, String.valueOf(indexer[counter]));
			}*/
			tableClass = atts.getValue("class");
			Log.v(TAG, tableClass);
			if(tableClass.equalsIgnoreCase(tableClassName)){
				this.in_tabletag = true;
			}
		}else if (localName.equals("tr")) {
			if(this.in_tabletag){
				trcounter++;
				if(trcounter > 0){
					//indexer[trcounter] = myData.size();
					Log.v(TAG, String.valueOf(trcounter));
				}
				this.in_trtag = true;
			}
		}else if (localName.equals("td")) {
			if(this.in_trtag){
				tdcounter++;
				this.in_tdtag = true;
			}
		}else if (localName.equals("font")) {
			this.in_fonttag = true;
		}else if (localName.equals("a")) {
			this.in_atag = true;
		}else if (localName.equals("div")) {
			this.in_divtag = true;
		//}else if (localName.equals("tagwithnumber")) {
			// Extract an Attribute
			/*if(tableClass.equalsIgnoreCase(tableClassName)){
				status = atts.getValue("class");
				if(serverStatusOK.equalsIgnoreCase(status)){
					status = "Servers Online";
				}
				else{
					status = "Servers Offline";
				}
			}*/
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
			tableClass = "";
		}else if (localName.equals("tr")) {
			this.in_trtag = false;
			if(trcounter > 0){
				Log.v(TAG, String.valueOf(tdcounter));
				this.myData.add(new ParsedItemsDataSet(myParsedItemsDataSet.getItemString(),
	    				myParsedItemsDataSet.getQuantityString(),
	    				myParsedItemsDataSet.getValueString(),
	    				myParsedItemsDataSet.getBidString(),
	    				myParsedItemsDataSet.getBidsString()));
			}
		}else if (localName.equals("td")) {
			this.in_tdtag = false;
		}else if (localName.equals("font")) {
			this.in_fonttag = false;
		}else if (localName.equals("a")) {
			this.in_atag = false;
		}else if (localName.equals("div")) {
			this.in_divtag = false;
		/*}else if (localName.equals("class")) {
			// Nothing to do here*/
		}
	}
	
	/** Gets be called on the following structure: 
	 * <tag>characters</tag> */
	@Override
    public void characters(char ch[], int start, int length) {
		if(this.in_tabletag){
			/*if(this.in_divtag){
	    		//myParsedHofDataSet.setNameString(new String(ch, start, length));
				//Log.v(TAG, status);
	    	}
			if(this.in_fonttag){
	    		//myParsedHofDataSet.addToResourceString(new String(ch, start, length));
				//Log.v(TAG, String.valueOf(length));
	    	}*/
			if(this.in_trtag){
				if(this.in_tdtag){
					if((tdcounter % 5) == 1)
						myParsedItemsDataSet.setItemString(new String(ch, start, length));
					else if((tdcounter % 5) == 2)
						myParsedItemsDataSet.setQuantityString(new String(ch, start, length));
					else if((tdcounter % 5) == 3)
						myParsedItemsDataSet.setValueString(new String(ch, start, length));
					else if((tdcounter % 5) == 4)
						myParsedItemsDataSet.setBidString(new String(ch, start, length));
					else if((tdcounter % 5) == 0){
						myParsedItemsDataSet.setBidsString(new String(ch, start, length));
						
					}
						
		    	}
			}
			
		}
		
    }
}
package studio.coldstream.entropiahof;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import org.ccil.cowan.tagsoup.jaxp.*;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;

public class main extends Activity {

    /* 
	 * Entropia Companion - The app for checking what's going on in EU while being mobile. 
	 * The question we ask ourselves: What information is relevant?
	 * */

	static final String TAG = "Main"; // for Log
	
	public static final int ACTIVITY_CREATE = 8;
	
	protected static final int HOF = 0x8001;
	/*protected static final int GLOBALS = 0x8002;*/
	protected static final int NEWS = 0x8003;
	protected static final int ITEMS = 0x8004;
	protected static final int AUCTION = 0x8005;
	protected static final int CLOCK = 0x8006;
	protected static final int STATUS = 0x8007;
	protected static final int ABOUT = 0x8008;
	protected static final int EVENTS = 0x8009;
	protected static final int TICK = 0x9001;
	
	protected static final String[] iconName = {"Today's HOF", /*"HOF by RSS",*/ "PC News RSS", "Last Call", "Trading MU", "Events RSS", "Game Time", "Server Status", "About"};
	protected static final int[] iconImage = {R.drawable.whirl_md, /*R.drawable.rss_global,*/ R.drawable.rss_small, R.drawable.hammer_md, R.drawable.graph_md, R.drawable.ticket_md, R.drawable.clock_md, R.drawable.status, R.drawable.info};
	
	boolean menu_flag = false;
	
	Thread thread1;
	
	private ProgressDialog m_ProgressDialog = null;
	
	GridView grid_main;
	ListView list_main;
	ListView list_main1;
	ListView list_main2;
	ListView list_main3;
	
	TextView[] tv1 = new TextView[12];
	TextView[] tv2 = new TextView[12];
	TextView[] tv3 = new TextView[12];
	
	TextView[] tv4 = new TextView[12];
	TextView[] tv5 = new TextView[12];
	TextView[] tv6 = new TextView[12];
	
	TextView[] tv7 = new TextView[12];
	TextView[] tv8 = new TextView[12];
	TextView[] tv9 = new TextView[12];
	
	TextView globalsRssText;
	TextView newsRssText;
	TextView statusText;
	TextView clockView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		showMenuView();
		//showTestView();
    }
	
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Intent mainIntent3 = new Intent(main.this,main.class);
		main.this.startActivity(mainIntent3);
		main.this.finish();
	}
	
	public class HofViewHolder{
		TextView text1;
		TextView text2;
		TextView text3;
	}
	
	public class HofAdapter extends BaseAdapter{
        Context mContext;
        List<String> listan1;
        List<String> listan2;
        List<String> listan3;
        public HofAdapter(Context k, List<String> a, List<String> b, List<String> c){
            mContext = k;
            listan1 = new LinkedList<String>();
            listan1 = a;
            listan2 = new LinkedList<String>();
            listan2 = b;
            listan3 = new LinkedList<String>();
            listan3 = c;
        }
        @Override
        public int getCount() {
            return listan1.size();
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HofViewHolder v;
            if(convertView==null){
                LayoutInflater li = getLayoutInflater();
                convertView = li.inflate(R.layout.hoftable, null);
              
                v = new HofViewHolder();
                v.text1 = (TextView)convertView.findViewById(R.id.hof_name_text);
                v.text2 = (TextView)convertView.findViewById(R.id.hof_resource_text);
                v.text3 = (TextView)convertView.findViewById(R.id.hof_value_text);
                v.text3 = (TextView)convertView.findViewById(R.id.hof_value_text);
               
                convertView.setTag(v);
            }
            else
            {
                v = (HofViewHolder) convertView.getTag();
            }
            v.text1.setText(listan1.get(position).toString());
            v.text2.setText(listan2.get(position).toString());
            v.text3.setText(listan3.get(position).toString());
            if(position % 2 == 0){
            	v.text1.setBackgroundColor(Color.rgb(102, 18, 16));
            	v.text2.setBackgroundColor(Color.rgb(102, 18, 16));
            	v.text3.setBackgroundColor(Color.rgb(102, 18, 16));
            }
            else{
            	v.text1.setBackgroundColor(Color.rgb(80, 16, 14));
            	v.text2.setBackgroundColor(Color.rgb(80, 16, 14));
            	v.text3.setBackgroundColor(Color.rgb(80, 16, 14));
            }
            
            return convertView;
        }
              
		@Override
		public Object getItem(int position) {		
			return null;
		}
		@Override
		public long getItemId(int position) {	
			return 0;
		}
	};
	
	public class ViewHolder{
		TextView text1;
		TextView text2;
	}
	
	public class TestAdapter extends BaseAdapter{
        Context mContext;
        List<String> listan1;
        List<String> listan2;
        public TestAdapter(Context c, List<String> a, List<String> b){
            mContext = c;
            listan1 = new LinkedList<String>();
            listan1 = a;
            listan2 = new LinkedList<String>();
            listan2 = b;
        }
        @Override
        public int getCount() {
           
            return listan1.size();
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder v;
            if(convertView==null){
                LayoutInflater li = getLayoutInflater();
                convertView = li.inflate(R.layout.testrow, null);
              
                v = new ViewHolder();
                v.text1 = (TextView)convertView.findViewById(R.id.test_text01);
                v.text2 = (TextView)convertView.findViewById(R.id.test_text02);
               
                convertView.setTag(v);
            }
            else
            {
                v = (ViewHolder) convertView.getTag();
            }
            v.text1.setText(listan1.get(position).toString());
            v.text2.setText(listan2.get(position).toString());
            if(position % 2 == 1){
            	v.text1.setBackgroundColor(Color.rgb(20, 24, 110));
            	v.text2.setBackgroundColor(Color.rgb(20, 24, 110));
            }
            else{
            	v.text1.setBackgroundColor(Color.rgb(15, 18, 78));
            	v.text2.setBackgroundColor(Color.rgb(15, 18, 78));
            }
            
            return convertView;
        }
              
		@Override
		public Object getItem(int position) {		
			return null;
		}
		@Override
		public long getItemId(int position) {	
			return 0;
		}
	};
	
	public class PEAuctionAdapter extends BaseAdapter{
        Context mContext;
        List<String> listan1;
        List<String> listan2;
        public PEAuctionAdapter(Context c, List<String> a, List<String> b){
            mContext = c;
            listan1 = new LinkedList<String>();
            listan1 = a;
            listan2 = new LinkedList<String>();
            listan2 = b;
        }
        @Override
        public int getCount() {
           
            return listan1.size();
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder v;
            if(convertView==null){
                LayoutInflater li = getLayoutInflater();
                convertView = li.inflate(R.layout.auctionrow, null);
              
                v = new ViewHolder();
                v.text1 = (TextView)convertView.findViewById(R.id.test_text01);
                v.text2 = (TextView)convertView.findViewById(R.id.test_text02);
               
                convertView.setTag(v);
            }
            else
            {
                v = (ViewHolder) convertView.getTag();
            }
            v.text1.setText(listan1.get(position).toString());
            v.text2.setText(listan2.get(position).toString());
            if(position % 2 == 1){
            	v.text1.setBackgroundColor(Color.rgb(20, 24, 110));
            	v.text2.setBackgroundColor(Color.rgb(20, 24, 110));
            }
            else{
            	v.text1.setBackgroundColor(Color.rgb(15, 18, 78));
            	v.text2.setBackgroundColor(Color.rgb(15, 18, 78));
            }
            
            return convertView;
        }
              
		@Override
		public Object getItem(int position) {		
			return null;
		}
		@Override
		public long getItemId(int position) {	
			return 0;
		}
	};
	
	public class ItemsViewHolder{
		TextView text1;
		TextView text2;
		TextView text3;
		TextView text4;
		TextView text5;
	}
	
	public class ItemsAdapter extends BaseAdapter{
        Context mContext;
        List<String> listan1;
        List<String> listan2;
        List<String> listan3;
        List<String> listan4;
        List<String> listan5;
        public ItemsAdapter(Context k, List<String> a, List<String> b, List<String> c, List<String> d, List<String> e){
            mContext = k;
            listan1 = new LinkedList<String>();
            listan1 = a;
            listan2 = new LinkedList<String>();
            listan2 = b;
            listan3 = new LinkedList<String>();
            listan3 = c;
            listan4 = new LinkedList<String>();
            listan4 = d;
            listan5 = new LinkedList<String>();
            listan5 = e;
            
        }
        @Override
        public int getCount() {
            return listan1.size();
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemsViewHolder v;
            if(convertView==null){
                LayoutInflater li = getLayoutInflater();
                convertView = li.inflate(R.layout.table, null);
             
                v = new ItemsViewHolder();
                v.text1 = (TextView)convertView.findViewById(R.id.item_text);
                v.text2 = (TextView)convertView.findViewById(R.id.quantity_text);
                v.text3 = (TextView)convertView.findViewById(R.id.value_text);
                v.text4 = (TextView)convertView.findViewById(R.id.bid_text);
                v.text5 = (TextView)convertView.findViewById(R.id.bids_text);
                convertView.setTag(v);
            }
            else
            {
                v = (ItemsViewHolder) convertView.getTag();
            }
            v.text1.setText(listan1.get(position).toString());
            v.text2.setText(listan2.get(position).toString());
            v.text3.setText(listan3.get(position).toString());
            v.text4.setText(listan4.get(position).toString());
            v.text5.setText(listan5.get(position).toString());
            if(position % 2 == 1){
            	v.text1.setBackgroundColor(Color.rgb(20, 24, 110));
            	v.text2.setBackgroundColor(Color.rgb(20, 24, 110));
            	v.text3.setBackgroundColor(Color.rgb(20, 24, 110));
            	v.text4.setBackgroundColor(Color.rgb(20, 24, 110));
            	v.text5.setBackgroundColor(Color.rgb(20, 24, 110));
            }
            else{
            	v.text1.setBackgroundColor(Color.rgb(15, 18, 78));
            	v.text2.setBackgroundColor(Color.rgb(15, 18, 78));
            	v.text3.setBackgroundColor(Color.rgb(15, 18, 78));
            	v.text4.setBackgroundColor(Color.rgb(15, 18, 78));
            	v.text5.setBackgroundColor(Color.rgb(15, 18, 78));
            }
            return convertView;
        }
              
		@Override
		public Object getItem(int position) {		
			return null;
		}
		@Override
		public long getItemId(int position) {	
			return 0;
		}
	};
	
	public class ImageViewHolder{
		ImageView image1;
		TextView text1;
	}
	
	public class ImageAdapter extends BaseAdapter{
        Context mContext;
        public ImageAdapter(Context c){
            mContext = c;
        }
        @Override
        public int getCount() {
            return ACTIVITY_CREATE;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageViewHolder v;
            if(convertView==null){
                LayoutInflater li = getLayoutInflater();
                convertView = li.inflate(R.layout.icon, null);
                
                v = new ImageViewHolder();
                v.text1 = (TextView)convertView.findViewById(R.id.icon_text);
                v.text1.setText(iconName[position]);
                v.image1 = (ImageView)convertView.findViewById(R.id.icon_image);
                v.image1.setImageResource(iconImage[position]);
                convertView.setTag(v);
            }
            else
            {
            	v = (ImageViewHolder) convertView.getTag();
            }
            return convertView;
        }
		@Override
		public Object getItem(int position) {	
			return null;
		}
		@Override
		public long getItemId(int position) {	
			return 0;
		}
    }
	
	public void myTimer(){
		thread1 = new Thread()
	    {
	        public void run() {
	            try {
	                while(true) {	        			
	        			sleep(100);
	        			Message m = new Message();
                    	m.what = TICK;                            
                    	messageHandler.sendMessage(m); 
	                }
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    };
	    thread1.start();
	}
	
	private Handler messageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
			//handle messages
			case HOF:
				showHofView();
				m_ProgressDialog.dismiss();
				break;
			/*case GLOBALS:
				showGlobalsRssView();
				break;*/
			case NEWS:
				showNewsRssView();
				break;
			case ITEMS:
				showItemsView();
				break;
			case AUCTION:
				showPEAuctionView();
				break;
			case EVENTS:	
				showTodaysEventsView();
				break;
			case CLOCK:
				myTimer();
				setContentView(R.layout.clock);
			case TICK:
				DateFormat df = DateFormat.getTimeInstance();
		        df.setTimeZone(TimeZone.getTimeZone("gmt"));
		        String gmtTime = df.format(new Date());
		        //Log.v(TAG, gmtTime);
		        clockView = (TextView)findViewById(R.id.clockView);
		        Typeface font = Typeface.createFromAsset(getAssets(), "digit.ttf");  
			    clockView.setTypeface(font);
				clockView.setText(gmtTime + "  ");
				break;
			case STATUS:
				showStatusView();
				break;
			case ABOUT:	
				setContentView(R.layout.about);
				break;		
			default:
				//break;
			}
		}
	};
	
	private void showMenuView(){
		setContentView(R.layout.menu);
		//showHofView();
		grid_main = (GridView)findViewById(R.id.GridView01);
        grid_main.setAdapter(new ImageAdapter(this));
        grid_main.setOnItemClickListener(new OnItemClickListener() 
        {
            @SuppressWarnings("unchecked")
			public void onItemClick(AdapterView parent, 
            View v, int position, long id) 
            {                
            	menu_flag = false;
    			Message m1 = new Message();
    			switch(position){
    			case 0:
    				showProcessDialogue();
    				//setContentView(R.layout.connecting);
    	        	m1.what = HOF;                            
    	        	messageHandler.sendMessage(m1);
    				break;
    			/*case 1:
    				setContentView(R.layout.connecting);
    	        	m1.what = GLOBALS;                            
    	        	messageHandler.sendMessage(m1);
    				break;*/
    			case 1:
    				setContentView(R.layout.connecting);
    	        	m1.what = NEWS;                            
    	        	messageHandler.sendMessage(m1);
    				break;
    			case 2:
    				setContentView(R.layout.connecting);
    	        	m1.what = ITEMS;                            
    	        	messageHandler.sendMessage(m1);
    				break;
    			case 3:
    				setContentView(R.layout.connecting);
    	        	m1.what = AUCTION;                            
    	        	messageHandler.sendMessage(m1);
    				break;
    			case 4:
    				setContentView(R.layout.connecting);
    	        	m1.what = EVENTS;                            
    	        	messageHandler.sendMessage(m1);
    				break;
    			case 5:
    				m1.what = CLOCK;                            
    	        	messageHandler.sendMessage(m1);
    				break;
    			case 6:
    				setContentView(R.layout.connecting);
    	        	m1.what = STATUS;                            
    	        	messageHandler.sendMessage(m1);
    				break;
    			case 7:
    	        	m1.what = ABOUT;                            
    	        	messageHandler.sendMessage(m1);
    				break;
    		
    			default:
    				menu_flag = true;
    			}
    			
                /*Toast.makeText(getBaseContext(), 
                        "pic" + (position + 1) + " selected", 
                        Toast.LENGTH_SHORT).show();*/
            }
        });
        menu_flag = true;
	}
	
	private void showProcessDialogue(){
		m_ProgressDialog = ProgressDialog.show(main.this,    
	              "Please wait...", "Retrieving data ...", true);
	}
	/*private void showTestView(){
		setContentView(R.layout.testlayout);
		//showHofView();
		List<String> listan = new LinkedList<String>();
		listan.add("1");
		listan.add("2");
		listan.add("3");
		listan.add("4");
		listan.add("5");
		listan.add("6");
		listan.add("98");
		
		list_main = (ListView)findViewById(R.id.ListView01);
        list_main.setAdapter(new TestAdapter(this, listan, listan));
        //menu_flag = true;
	}*/
	
	private void showHofView(){
		setContentView(R.layout.main);
		
		TabHost tab_host = (TabHost) findViewById(R.id.edit_item_tab_host);
		tab_host.setup();
		
		TabSpec ts1 = tab_host.newTabSpec("TAB_HUNTING");
		ts1.setIndicator("Hunting");
		ts1.setContent(R.id.hunting_tab);
		tab_host.addTab(ts1);

		TabSpec ts2 = tab_host.newTabSpec("TAB_MINING");
		ts2.setIndicator("Mining");
		ts2.setContent(R.id.mining_tab);
		tab_host.addTab(ts2);

		TabSpec ts3 = tab_host.newTabSpec("TAB_CRAFTING");
		ts3.setIndicator("Crafting");
		ts3.setContent(R.id.crafting_tab);
		tab_host.addTab(ts3);

		tab_host.setCurrentTab(2);
		TextView tv = (TextView) tab_host.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#FFFFFFFF"));
        
        tab_host.setCurrentTab(1);
		tv = (TextView) tab_host.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#FFFFFFFF"));
        
        tab_host.setCurrentTab(0);
		tv = (TextView) tab_host.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#FFFFFFFF"));
       
        retrieveWebData();
	}

	public void retrieveWebData(){
		List<String> listan1 = new LinkedList<String>();
		List<String> listan2 = new LinkedList<String>();
		List<String> listan3 = new LinkedList<String>();
		List<String> listan4 = new LinkedList<String>();
		List<String> listan5 = new LinkedList<String>();
		List<String> listan6 = new LinkedList<String>();
		List<String> listan7 = new LinkedList<String>();
		List<String> listan8 = new LinkedList<String>();
		List<String> listan9 = new LinkedList<String>();
		try{
			//URL url = new URL("http://scalar.tumblr.com/");
			URL url = new URL("http://www.entropialife.com/");

			/* Get a SAXParser from the SAXPArserFactory. */
			//SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParserImpl spf = org.ccil.cowan.tagsoup.jaxp.SAXParserImpl.newInstance(null);
			//SAXParser sp = spf.getXMLReader();

			/* Get the XMLReader of the SAXParser we created. */
			//XMLReader xr = sp.getXMLReader();
			XMLReader xr = spf.getXMLReader();
			/* Create a new ContentHandler and apply it to the XML-Reader*/ 
			HofHandler myExampleHandler = new HofHandler();
			xr.setContentHandler(myExampleHandler);
			
			/* Parse the xml-data from our URL. */
			
			HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.connect(); 
			//xr.parse(new InputSource(url.openStream()));
			xr.parse(new InputSource(httpConnection.getInputStream()));
			/* Parsing has finished. */

			/* Our ExampleHandler now provides the parsed data to us. */
			String a = new String();
			String b = new String();
			String c = new String();
			for(int i = 1; i <= 25; i++){
				a = myExampleHandler.getParsedData(myExampleHandler.getCounterValue(2) + i - 1).getNameString().toString();	
				b = myExampleHandler.getParsedData(myExampleHandler.getCounterValue(2) + i - 1).getResourceString().toString();	
				c = myExampleHandler.getParsedData(myExampleHandler.getCounterValue(2) + i - 1).getValueString().toString();	
				
				listan1.add(a);
				listan2.add(b);
				listan3.add(c);
			}
			list_main1 = (ListView)findViewById(R.id.ListView02);
	        list_main1.setAdapter(new HofAdapter(this, listan1, listan2, listan3));
	        list_main1.invalidate();
	        
	        /*listan1.clear();
	        listan2.clear();
	        listan3.clear();*/
	        for(int i = 1; i <= 25; i++){
				a = myExampleHandler.getParsedData(myExampleHandler.getCounterValue(3) + i - 1).getNameString().toString();	
				b = myExampleHandler.getParsedData(myExampleHandler.getCounterValue(3) + i - 1).getResourceString().toString();	
				c = myExampleHandler.getParsedData(myExampleHandler.getCounterValue(3) + i - 1).getValueString().toString();	
				
				listan4.add(a);
				listan5.add(b);
				listan6.add(c);
			}
			list_main2 = (ListView)findViewById(R.id.ListView03);
	        list_main2.setAdapter(new HofAdapter(this, listan4, listan5, listan6));
	        list_main2.invalidate();
	        
	        for(int i = 1; i <= 25; i++){
				a = myExampleHandler.getParsedData(myExampleHandler.getCounterValue(4) + i - 1).getNameString().toString();	
				b = myExampleHandler.getParsedData(myExampleHandler.getCounterValue(4) + i - 1).getResourceString().toString();	
				c = myExampleHandler.getParsedData(myExampleHandler.getCounterValue(4) + i - 1).getValueString().toString();	
				
				listan7.add(a);
				listan8.add(b);
				listan9.add(c);
			}
			list_main3 = (ListView)findViewById(R.id.ListView04);
	        list_main3.setAdapter(new HofAdapter(this, listan7, listan8, listan9));
	        list_main3.invalidate();
	        			
			//Log.v(TAG, String.valueOf(myExampleHandler.getCounterValue()));
			
		} catch (Exception e) {
			/* Display any Error to the GUI. */
			//tv.setText("Error: " + e.getMessage());
			Log.e(TAG, "WeatherQueryError", e);
		}
	}
	
	public void showGlobalsRssView(){
		this.setContentView(R.layout.testlayout);

		List<String> listan1 = new LinkedList<String>();
		List<String> listan2 = new LinkedList<String>();
		
		try{
			//URL url = new URL("http://scalar.tumblr.com/");
			URL url = new URL("http://www.entropialife.com/rss/rss.asp");

			/* Get a SAXParser from the SAXPArserFactory. */
			//SAXParserFactory sp = SAXParserFactory.newInstance();
			SAXParserImpl spf = org.ccil.cowan.tagsoup.jaxp.SAXParserImpl.newInstance(null);
			//SAXParser sp = spf.getXMLReader();

			/* Get the XMLReader of the SAXParser we created. */
			//XMLReader xr = sp.;
			XMLReader xr = spf.getXMLReader();
			/* Create a new ContentHandler and apply it to the XML-Reader*/ 
			GlobalHandler myExampleHandler = new GlobalHandler();
			xr.setContentHandler(myExampleHandler);
			
			/* Parse the xml-data from our URL. */
			HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.connect(); 
			//xr.parse(new InputSource(url.openStream()));
			xr.parse(new InputSource(httpConnection.getInputStream()));
			/*Log.v(TAG, String.valueOf(myExampleHandler.getCounterValue()));
			Log.v(TAG, myExampleHandler.getParsedData(1).getNameString().toString());
			Log.v(TAG, myExampleHandler.getParsedData(2).getNameString().toString().replaceAll("\n", " "));*/
			String adam = new String();
			for(int i = 0; i < myExampleHandler.getCounterValue(); i++){
				adam = myExampleHandler.getParsedData(i).getNameString().toString();	
				adam = adam.replaceAll("\n", "");
				adam = adam.replaceAll("\t", "");
				
				listan1.add(adam);
				listan2.add("");	
			}
			list_main = (ListView)findViewById(R.id.ListView01);
	        list_main.setAdapter(new TestAdapter(this, listan1, listan2));
	        list_main.invalidate();
			//Log.v(TAG, String.valueOf(myExampleHandler.getParsedData(0)));
			
		} catch (Exception e) {
			/* Display any Error to the GUI. */
			//tv.setText("Error: " + e.getMessage());
			Log.e(TAG, "WeatherQueryError", e);
		}
	}
	
	public void showNewsRssView(){
		this.setContentView(R.layout.testlayout);

		List<String> listan1 = new LinkedList<String>();
		List<String> listan2 = new LinkedList<String>();
	
		try{
			//URL url = new URL("http://scalar.tumblr.com/");
			//URL url = new URL("http://www.planetcalypso.com/rss-news.xml");
			URL url = new URL("http://cl.entropiauniverse.com/(en)/clientloader/planets/calypso/index.html");

			/* Get a SAXParser from the SAXPArserFactory. */
			//SAXParserFactory sp = SAXParserFactory.newInstance();
			SAXParserImpl spf = org.ccil.cowan.tagsoup.jaxp.SAXParserImpl.newInstance(null);
			//SAXParser sp = spf.getXMLReader();

			/* Get the XMLReader of the SAXParser we created. */
			//XMLReader xr = sp.;
			XMLReader xr = spf.getXMLReader();
			/* Create a new ContentHandler and apply it to the XML-Reader*/ 
			NewsHandler myExampleHandler = new NewsHandler();
			xr.setContentHandler(myExampleHandler);
			
			/* Parse the xml-data from our URL. */
			HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.connect(); 
			//xr.parse(new InputSource(url.openStream()));
			xr.parse(new InputSource(httpConnection.getInputStream()));
			//Log.v(TAG, String.valueOf(myExampleHandler.getCounterValue()));
			//Log.v(TAG, myExampleHandler.getParsedData(1).getNameString().toString());
			//Log.v(TAG, myExampleHandler.getParsedData(2).getNameString().toString().replaceAll("\n", " "));
			String adam = new String();
			String bertil = new String();
			for(int i = 0; i < myExampleHandler.getCounterValue(); i++){
				adam = myExampleHandler.getParsedData(i).getValueString().toString();	
				adam = adam.replaceAll("\n", "");
				adam = adam.replaceAll("\t", "");
				listan1.add(adam);
				bertil = myExampleHandler.getParsedData(i).getNameString().toString();	
				bertil = bertil.replaceAll("\n", "");
				bertil = bertil.replaceAll("\t", "");
				listan2.add(bertil);
			}
			list_main = (ListView)findViewById(R.id.ListView01);
	        list_main.setAdapter(new TestAdapter(this, listan1, listan2));
	        list_main.invalidate();
			//Log.v(TAG, String.valueOf(myExampleHandler.getParsedData(0)));
			
		} catch (Exception e) {
			/* Display any Error to the GUI. */
			//tv.setText("Error: " + e.getMessage());
			Log.e(TAG, "WeatherQueryError", e);
		}
	}
	
	public void showPEAuctionView(){
		this.setContentView(R.layout.testlayout);

		List<String> listan1 = new LinkedList<String>();
		List<String> listan2 = new LinkedList<String>();
		
		try{
			//URL url = new URL("http://scalar.tumblr.com/");
			//URL url = new URL("http://www.planetcalypso.com/rss-news.xml");
			URL url = new URL("http://www.peauction.com/");

			/* Get a SAXParser from the SAXPArserFactory. */
			//SAXParserFactory sp = SAXParserFactory.newInstance();
			SAXParserImpl spf = org.ccil.cowan.tagsoup.jaxp.SAXParserImpl.newInstance(null);
			//SAXParser sp = spf.getXMLReader();

			/* Get the XMLReader of the SAXParser we created. */
			//XMLReader xr = sp.;
			XMLReader xr = spf.getXMLReader();
			/* Create a new ContentHandler and apply it to the XML-Reader*/ 
			PEAuctionHandler myExampleHandler = new PEAuctionHandler();
			xr.setContentHandler(myExampleHandler);
			
			/* Parse the xml-data from our URL. */
			HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.connect(); 
			//xr.parse(new InputSource(url.openStream()));
			xr.parse(new InputSource(httpConnection.getInputStream()));
			//Log.v(TAG, String.valueOf(myExampleHandler.getCounterValue()));
			//Log.v(TAG, myExampleHandler.getParsedData(1).getNameString().toString());
			//Log.v(TAG, myExampleHandler.getParsedData(2).getNameString().toString().replaceAll("\n", " "));
			String adam = new String();
			String bertil = new String();
			for(int i = 0; i < myExampleHandler.getCounterValue(); i++){
				adam = myExampleHandler.getParsedData(i).getNameString().toString();	
				adam = adam.replaceAll("\n", "");
				adam = adam.replaceAll("\t", "");
				bertil = myExampleHandler.getParsedData(i).getValueString().toString();	
				bertil = bertil.replaceAll("\n", "");
				bertil = bertil.replaceAll("\t", "");
				bertil = "  " + bertil;
				listan1.add(adam);
				listan2.add(bertil);
			}
			list_main = (ListView)findViewById(R.id.ListView01);
	        list_main.setAdapter(new PEAuctionAdapter(this, listan1, listan2));
	        list_main.invalidate();
			//Log.v(TAG, String.valueOf(myExampleHandler.getParsedData(0)));
			
		} catch (Exception e) {
			/* Display any Error to the GUI. */
			//tv.setText("Error: " + e.getMessage());
			Log.e(TAG, "WeatherQueryError", e);
		}
	}
	
	public void showItemsView(){
		this.setContentView(R.layout.testlayout);

		List<String> listan1 = new LinkedList<String>();
		List<String> listan2 = new LinkedList<String>();
		List<String> listan3 = new LinkedList<String>();
		List<String> listan4 = new LinkedList<String>();
		List<String> listan5 = new LinkedList<String>();

		try{
			//URL url = new URL("http://scalar.tumblr.com/");
			URL url = new URL("http://cl.entropiauniverse.com/%28en%29/clientloader/planets/calypso/expiring-auctions/index.html");

			/* Get a SAXParser from the SAXPArserFactory. */
			//SAXParserFactory sp = SAXParserFactory.newInstance();
			SAXParserImpl spf = org.ccil.cowan.tagsoup.jaxp.SAXParserImpl.newInstance(null);
			//SAXParser sp = spf.getXMLReader();

			/* Get the XMLReader of the SAXParser we created. */
			//XMLReader xr = sp.;
			XMLReader xr = spf.getXMLReader();
			/* Create a new ContentHandler and apply it to the XML-Reader*/ 
			ItemsHandler myExampleHandler = new ItemsHandler();
			xr.setContentHandler(myExampleHandler);
			
			/* Parse the xml-data from our URL. */
			HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.connect(); 
			//xr.parse(new InputSource(url.openStream()));
			xr.parse(new InputSource(httpConnection.getInputStream()));
			//Log.v(TAG, String.valueOf(myExampleHandler.getCounterValue()));
			//Log.v(TAG, myExampleHandler.getParsedData(1).getNameString().toString());
			//Log.v(TAG, myExampleHandler.getParsedData(2).getNameString().toString().replaceAll("\n", " "));
			String a = new String();
			String b = new String();
			String c = new String();
			String d = new String();
			String e = new String();
			listan1.add("Expiring Aution Items");
			listan2.add("Quantity");
			listan3.add("Value");
			listan4.add("Bid");
			listan5.add("Bids");
			for(int i = 1; i < myExampleHandler.getCounterValue(); i++){
				a = myExampleHandler.getParsedData(i).getItemString().toString();	
				a = a.replaceAll("\n", "");
				a = a.replaceAll("\t", "");
			
				b = myExampleHandler.getParsedData(i).getQuantityString().toString();	
				b = b.replaceAll("\n", "");
				b = b.replaceAll("\t", "");
			
				c = myExampleHandler.getParsedData(i).getValueString().toString();	
				c = c.replaceAll("\n", "");
				c = c.replaceAll("\t", "");
				
				d = myExampleHandler.getParsedData(i).getBidString().toString();	
				d = d.replaceAll("\n", "");
				d = d.replaceAll("\t", "");
				
				e = myExampleHandler.getParsedData(i).getBidsString().toString();	
				e = e.replaceAll("\n", "");
				e = e.replaceAll("\t", "");
				
				listan1.add(a);
				listan2.add(b);
				listan3.add(c);
				listan4.add(d);
				listan5.add(e);
			}
			list_main = (ListView)findViewById(R.id.ListView01);
	        list_main.setAdapter(new ItemsAdapter(this, listan1, listan2, listan3, listan4, listan5));
	        list_main.invalidate();
			//newsRssText.setText(sb2.toString());
			//Log.v(TAG, String.valueOf(myExampleHandler.getParsedData(0)));		
		} catch (Exception e) {
			/* Display any Error to the GUI. */
			//tv.setText("Error: " + e.getMessage());
			Log.e(TAG, "WeatherQueryError", e);
		}
	}
	
	public void showTodaysEventsView(){
		this.setContentView(R.layout.testlayout);

		List<String> listan1 = new LinkedList<String>();
		List<String> listan2 = new LinkedList<String>();
	
		try{
			//URL url = new URL("http://scalar.tumblr.com/");
			//URL url = new URL("http://www.planetcalypso.com/rss-news.xml");
			/*DateFormat df = DateFormat.getDateInstance();
	        df.setTimeZone(TimeZone.getTimeZone("gmt"));
	        String gmtDate = df.format("YYY-MM-DD");*/

	        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        //sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
	        //String gmtDate =  sdf.format(new Date());
	        //Log.v(TAG, gmtDate);
	        //StringBuilder sb = new StringBuilder();
	        //sb.append();
	        //sb.append(gmtDate);
	        //sb.append("&c=2");
	        
			URL url = new URL("http://www.planetcalypsoforum.com/forums/external.php?type=RSS2&forumids=33");

			/* Get a SAXParser from the SAXPArserFactory. */
			//SAXParserFactory sp = SAXParserFactory.newInstance();
			SAXParserImpl spf = org.ccil.cowan.tagsoup.jaxp.SAXParserImpl.newInstance(null);
			//SAXParser sp = spf.getXMLReader();

			/* Get the XMLReader of the SAXParser we created. */
			//XMLReader xr = sp.;
			XMLReader xr = spf.getXMLReader();
			/* Create a new ContentHandler and apply it to the XML-Reader*/ 
			TodaysEventsHandler myExampleHandler = new TodaysEventsHandler();
			xr.setContentHandler(myExampleHandler);
			
			/* Parse the xml-data from our URL. */
			HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.connect(); 
			//xr.parse(new InputSource(url.openStream()));
			xr.parse(new InputSource(httpConnection.getInputStream()));
			Log.v(TAG, String.valueOf(myExampleHandler.getCounterValue()));
			//Log.v(TAG, myExampleHandler.getParsedData(1).getNameString().toString());
			//Log.v(TAG, myExampleHandler.getParsedData(2).getNameString().toString().replaceAll("\n", " "));
			String adam = new String();
			String bertil = new String();
			Log.v(TAG, "Before");
			for(int i = 0; i < myExampleHandler.getCounterValue(); i++){
				Log.v(TAG, "In" + i);
				adam = myExampleHandler.getParsedData(i).getNameString().toString();	
				adam = adam.replaceAll("\n", "");
				adam = adam.replaceAll("\t", "");
				listan1.add(adam);
				bertil = myExampleHandler.getParsedData(i).getResourceString().toString();	
				bertil = bertil.replaceAll("\n", "");
				bertil = bertil.replaceAll("\t", "");
				listan2.add(bertil);
				//Log.v(TAG, String.valueOf(myExampleHandler.getParsedData(i).getNameString().toString()));
				Log.v(TAG, "In" + i);
			}
			list_main = (ListView)findViewById(R.id.ListView01);
	        list_main.setAdapter(new TestAdapter(this, listan1, listan2));
	        list_main.invalidate();
			//Log.v(TAG, String.valueOf(myExampleHandler.getParsedData(0)));
	        Log.v(TAG, "After");
			
		} catch (Exception e) {
			/* Display any Error to the GUI. */
			//tv.setText("Error: " + e.getMessage());
			Log.e(TAG, "WeatherQueryError", e);
		}
	}
	
	public void showStatusView(){
		this.setContentView(R.layout.connecting);
		statusText = (TextView)findViewById(R.id.connectingText);
		//statusText.setText("Unresolved");
		
		try{
			//URL url = new URL("http://scalar.tumblr.com/");
			URL url = new URL("http://cl.entropiauniverse.com/%28en%29/clientloader/planets/calypso/index.html?memory=6144&dx=9.0&Description=NVIDIA%20GeForce%20GTX%20275&Build=6099&VendorId=10de&DeviceId=5e6&SM=3.0&os=Windows%207%2064-bit&version=11.9.0.73854&lang=EN&ccode=&territoryid=1&pid=1&redir=40");
			//URL url = new URL("http://cl.entropiauniverse.com/%28en%29/clientloader/planets/calypso/index.html");

			/* Get a SAXParser from the SAXPArserFactory. */
			//SAXParserFactory sp = SAXParserFactory.newInstance();
			SAXParserImpl spf = org.ccil.cowan.tagsoup.jaxp.SAXParserImpl.newInstance(null);
			//SAXParser sp = spf.getXMLReader();

			/* Get the XMLReader of the SAXParser we created. */
			//XMLReader xr = sp.;
			XMLReader xr = spf.getXMLReader();
			/* Create a new ContentHandler and apply it to the XML-Reader*/ 
			StatusHandler myExampleHandler = new StatusHandler();
			xr.setContentHandler(myExampleHandler);
			
			/* Parse the xml-data from our URL. */
			HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.connect(); 
			xr.parse(new InputSource(httpConnection.getInputStream()));
			
			String adam = new String();
			adam = myExampleHandler.getStatusValue();
			statusText.setText(adam);
			//Log.v(TAG, String.valueOf(myExampleHandler.getParsedData(0)));	
		} catch (Exception e) {
			/* Display any Error to the GUI. */
			//tv.setText("Error: " + e.getMessage());
			Log.e(TAG, "WeatherQueryError", e);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (  Integer.valueOf(android.os.Build.VERSION.SDK) < 7 //Instead use android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
	            && keyCode == KeyEvent.KEYCODE_BACK
	            && event.getRepeatCount() == 0) {
	        // Take care of calling this method on earlier versions of
	        // the platform where it doesn't exist.
	        onBackPressed();
	    }
	    return super.onKeyDown(keyCode, event);
	}

	//@Override
	public void onBackPressed() {
	    // This will be called either automatically for you on 2.0
	    // or later, or by the code above on earlier versions of the
	    // platform.
		try{		
			if (thread1.isAlive())
				thread1.interrupt();
		}
		catch (NullPointerException e) {
            e.printStackTrace();
        }
		
		if(menu_flag)
			this.finish();
		else
			showMenuView();
		
	    return;
	}
	
	@Override
	protected void onResume() {
		/*
		 * onResume is is always called after onStart, even if the app hasn't been
		 * paused
		 */
		
		super.onResume();
	}

	@Override
	protected void onPause() {
		/* GPS, as it turns out, consumes battery like crazy */
		
		super.onResume();
	}

	@Override
	protected void onStop() {
		/* may as well just finish since saving the state is not important for this toy app */
		
		finish();
		super.onStop();
	}
}


package com.map1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.util.EntityUtils;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Map1Activity extends MapActivity 
{    
    MapView mapView; 
    MapController mc;
    GeoPoint gp;
    Spinner PlanSelector;
    
    /** Called when the activity is first created. **/
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        Debug.startMethodTracing("report");
        setContentView(R.layout.map);
        this.setTitle("TourPlan-Alpha_v0.2");
        Debug.stopMethodTracing();
        
        
        mapView = (MapView) findViewById(R.id.MapView);  
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(false);

        mc = mapView.getController();
        String coordinates[] = {"23.94", "121.00"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
        
        gp = new GeoPoint(
        		(int)(lat*1E6),
        		(int)(lng*1E6));
        
        mc.animateTo(gp);
        mc.setZoom(8);
        mapView.invalidate();
        
        
        
        TextView UserName = (TextView)findViewById(R.id.UserName);
        final TextView displayText = (TextView)findViewById(R.id.displayText);
        PlanSelector = (Spinner) findViewById(R.id.PlanSelector);
        
        Bundle userName = this.getIntent().getExtras();		//Obtain Bundle
        final String Name = userName.getString("name").toString();
        UserName.setText(Name);								//Output the contents of Bundle
    	
        /*
    	String xmlURL = "http://140.128.198.44:408/plandata/"+Name;
    	String xmlString = getStringByUrl(xmlURL);
      
        PlanVO planVO = XmlParser.parse(xmlString);
       
        String res = "-----";
        StringTokenizer stPlan = new StringTokenizer(planVO.getPlan(),",");
        StringTokenizer stPid = new StringTokenizer(planVO.getPid(),",");
		stPlan.nextToken();
		stPid.nextToken();
			while (stPlan.hasMoreTokens() & stPid.hasMoreTokens()) {
					res = res + stPid.nextToken()+ "  " + stPlan.nextToken();
        
		        	final String[] plans = res.split(":");
		        	       
		        	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, plans);
		        	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        	        
		        	PlanSelector.setAdapter(adapter);
		        	PlanSelector.setSelection(0,true);
		        	
			PlanSelector.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					//TODO Auto-generated method stub
					//Toast.makeText(Map1Activity.this, "Plan: " + arg0.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
					
					String selected = plans[arg2];
					String pid = selected.substring(0,3);
					
					if (pid.contains("--")) 
					{
						displayText.setText(pid);
					} else {
						Toast.makeText(Map1Activity.this, "Plan: " + arg0.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
						String pidBuffer = pid.trim();
						String xmlPidUrl = "http://140.128.198.44:408/plandata/" +Name +"/" +pidBuffer;
						String xmlPidString = getStringByUrl(xmlPidUrl);
						PlanVO pidVO = XmlParser.parse(xmlPidString);
					
						String spot = pidVO.getSpot();
						String[] spotList = spot.split(",");
						
						TableRow info1 = (TableRow)findViewById(R.id.Info1);
						info1.removeAllViews();
						for(int i = 0; i < spotList.length; i++) {
							TextView infoList = new TextView(Map1Activity.this);
							infoList.setText(spotList[i]);
							info1.addView(infoList);
						}
						
						displayText.setText(spot);
					}
				}
				
				public void onNothingSelected(AdapterView<?> arg0){
					Toast.makeText(Map1Activity.this, "Please select a plan", Toast.LENGTH_LONG).show();
				}
			});
			}
			
			/*
			String resSpot = "Spots:";
			StringTokenizer stSpot = new StringTokenizer(planVO.getSpot(),",");
			stSpot.nextToken();
				while (stSpot.hasMoreTokens()) {
						resSpot = resSpot + "\n"+ stSpot.nextToken();
					}
			displayText.setText(resSpot);
			*/
    }
    

    public void testButClick(View testClick) {
    	//Bundle userName = this.getIntent().getExtras();
    	//String Name = userName.getString("name").toString();
    	
    	TextView displayText = (TextView)findViewById(R.id.displayText);
        PlanSelector = (Spinner) findViewById(R.id.PlanSelector);
        
    	String xmlURL = "http://labm406.serveftp.com/mobileApp/xml/plan_spot.php?uid=22";
    	String xmlString = getStringByUrl(xmlURL);

        PlanVO planVO = XmlParser.parse(xmlString);

        String res = "-----";
        StringTokenizer stPlan = new StringTokenizer(planVO.getPlan(),",");
		stPlan.nextToken();
			while (stPlan.hasMoreTokens()) {
					res = res + "\n" + stPlan.nextToken();
        
					displayText.setText(res);
			}
    }

    
    
  //Create HTTP Connection!!
    
    private String getStringByUrl(String url)
    {
    	String uriXML = url;
    	HttpGet httpRequest = new HttpGet(uriXML);

    	try
    	{
    		HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
    		if (httpResponse.getStatusLine().getStatusCode() == 200)
    		{
    			String strResult = EntityUtils.toString(httpResponse.getEntity());
    			strResult = eregi_replace("(.*)<\\?xml","<?xml",strResult);
    			return strResult;
    		}
    	}
    	
    	catch (ClientProtocolException e)
    	{
    		Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
    		e.printStackTrace();
    	}
    	
    	catch (IOException e)
    	{
    		Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
    		e.printStackTrace();
    	}
    	
    	catch (Exception e)
    	{
    		Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
    		e.printStackTrace();
    	}
    	
    	return null;
    }
    
    public String eregi_replace(String strFrom, String strTo, String strTarget)  
    {  
    	String strPattern = "(?i)"+strFrom;  
    	Pattern p = Pattern.compile(strPattern);  
    	Matcher m = p.matcher(strTarget);  
    	if(m.find())  
    	{  
    		return strTarget.replaceAll(strFrom, strTo);  
    	}
    	else  
    	{
    		return strTarget;  
    	}
    }
      
 
    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0,1,0,"Read Me");
    	menu.add(0,0,1,"Logout");
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    		case 0:
    			finish();
    			break;
    		default:
    		
    		case 1:
    			Toast.makeText(Map1Activity.this, "Still working on it...", Toast.LENGTH_LONG).show();
    			break;
    	}
    	return super.onOptionsItemSelected(item);
    }
}
package com.seph.todolist;

import android.os.Bundle;
import android.view.Menu;

import com.mapquest.android.maps.MapActivity;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mapquest.android.Geocoder;
import com.mapquest.android.maps.AnnotationView;
import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.ItemizedOverlay;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.Overlay;
import com.mapquest.android.maps.OverlayItem;

public class AddnewMap extends MapActivity {
	
	protected MapView map;
	AnnotationView annotation;
	RelativeLayout customInnerView;
	MySQLiteHelper db = new MySQLiteHelper(this);
	TextView customTitle;
	ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
	int lastTouchedIndex;
	boolean started = false;
	Drawable icon;
	DefaultItemizedOverlay poiOverlay;
	int latitude,longitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		
		map = (MapView) findViewById(R.id.map);
		map.getController().setZoom(9);
	    map.getController().setCenter(new GeoPoint(51.8,4.5));
	    
	    icon = getResources().getDrawable(R.drawable.location_marker);
	    poiOverlay = new DefaultItemizedOverlay(icon);
		
        setupOverlays();
        int savedLong = getIntent().getIntExtra("savedLong",-1);
        int savedLat = getIntent().getIntExtra("savedLat",-1);
        
        if(savedLat > -1 && savedLong > -1)
        {
        	addAnnotation(new GeoPoint(savedLong,savedLat));
        }
	}
	
	/**
     * Add an overlay to reverse geocode on touch event.
     */
    private void setupOverlays() {
        map.getOverlays().add(new ReverseGeocodeOverlay());
    }
    
    private class ReverseGeocodeOverlay extends Overlay {

        @Override
        public boolean onTap(GeoPoint p, MapView mapView) {
            
        	if(items.isEmpty())
            addAnnotation(p);
            return false;
        }
        
    }
        
        public void addAnnotation(GeoPoint p){
        	
        	latitude = p.getLatitudeE6();
        	longitude = p.getLongitudeE6();
        	
        	// initialize the annotation to be shown later 
    	    annotation = new AnnotationView(map);

    	    float density = map.getContext().getResources().getDisplayMetrics().density;
    	  	annotation.setBubbleRadius((int)(12*density+0.5f));
    	  	// make the annotation not animate
    	  	annotation.setAnimated(false);

    	  	// init our custom innerView from an xml file
    	  	LayoutInflater li = (LayoutInflater)map.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	    customInnerView = (RelativeLayout) li.inflate(R.layout.inner_view_addnew, null);
    	    customTitle = (TextView) customInnerView.findViewById(R.id.title);

    	    // now use the customInnerView as the annotation's innerView
    	    annotation.setInnerView(customInnerView);
    	
    	    addPoiOverlay(longitude,latitude);
        }

    
    
 // add an itemized overlay to map 
    private void addPoiOverlay(int longitude, int latitude) {


      // set GeoPoints and title/snippet to be used in the annotation view 
     
        
			OverlayItem poi = new OverlayItem(new GeoPoint (latitude,longitude), null, null);
			items.add(poi);
			poiOverlay.addItem(poi);
    	
    	if(!started)
    	{
    		
			OverlayItem tapped = poiOverlay.getItem(0);
			showCustomAnnotation(tapped);

    		
    		map.getOverlays().add(poiOverlay);
        	map.invalidate();
        	
	    	Button button = (Button) findViewById(R.id.button2);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	String name = getIntent().getExtras().getString("todoName");
	            	db.addLocation(name,getLongitude(),getLatitude());
	            	
	            	Intent intent = new Intent(AddnewMap.this, MainActivity.class);
	                startActivity(intent);
	            }
	        });
	        boolean started = true;
    	}
    }
    
    /**
     * This will show the annotation view and use a custom innerView
     * @param item
     */
    private void showCustomAnnotation(OverlayItem item){
    	// set title on the custom inner view
    	//customTitle.setText(item.getTitle());
    	
		// show the customized annotation
		annotation.showAnnotationView(item);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.addnew_map, menu);
		return true;
	}
	
	@Override
    public boolean isRouteDisplayed() {
      return false;
    }
	
	public int getLongitude(){
	return longitude;	
	}
	
	public int getLatitude(){
		return latitude;
	}

}

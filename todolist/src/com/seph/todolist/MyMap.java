package com.seph.todolist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mapquest.android.maps.AnnotationView;
import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.ItemizedOverlay;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.OverlayItem;

public class MyMap extends MapActivity {

	protected MapView map;
    AnnotationView annotation;
    int listId;
    MySQLiteHelper db = new MySQLiteHelper(this);
    RelativeLayout customInnerView;
    TextView customTitle;
    ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
    int lastTouchedIndex;
    List<Integer> ids;
    boolean started = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
		
      listId = getIntent().getIntExtra("listid",-1);
      
      map = (MapView) findViewById(R.id.map);
      if(listId == -1)
      {
	      map.getController().setZoom(9);
	      map.getController().setCenter(new GeoPoint(51.8,4.5));
      }
      else
      {
    	  map.getController().setZoom(13);
    	  map.getController().setCenter(new GeoPoint(db.getYval(listId),db.getXval(listId)));
      }
      map.setBuiltInZoomControls(true);
      
      init();
      
    }

      public void init(){

      // initialize the annotation to be shown later 
      annotation = new AnnotationView(map);
      
    float density = map.getContext().getResources().getDisplayMetrics().density;
  	annotation.setBubbleRadius((int)(12*density+0.5f));
  	// make the annotation not animate
  	annotation.setAnimated(false);
  	
  	// init our custom innerView from an xml file
  		LayoutInflater li = (LayoutInflater)map.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      customInnerView = (RelativeLayout) li.inflate(R.layout.inner_view, null);
      customTitle = (TextView) customInnerView.findViewById(R.id.title);
      
      // now use the customInnerView as the annotation's innerView
      annotation.setInnerView(customInnerView);

      addPoiOverlay();
    }

    // add an itemized overlay to map 
    private void addPoiOverlay() {

      // use a custom POI marker by referencing the bitmap file directly,
      // using the filename as the resource ID 
      Drawable icon = getResources().getDrawable(R.drawable.location_marker);
      final DefaultItemizedOverlay poiOverlay = new DefaultItemizedOverlay(icon);

      // set GeoPoints and title/snippet to be used in the annotation view 
      
      	ids = db.findAllLocations();
		
		for(int i : ids)
		{
			OverlayItem poi = new OverlayItem(new GeoPoint (db.getYval(i),db.getXval(i)), db.getName(i), null);
			items.add(poi);
			poiOverlay.addItem(poi);
		}

		poiOverlay.setTapListener(new ItemizedOverlay.OverlayTapListener() {
			@Override
			public void onTap(GeoPoint pt, MapView mapView) {
				// when tapped, show the annotation for the overlayItem
				lastTouchedIndex = poiOverlay.getLastFocusedIndex();
				if(lastTouchedIndex>-1){
					OverlayItem tapped = poiOverlay.getItem(lastTouchedIndex);
					showCustomAnnotation(tapped);
				}
			}
		});
    	
    	map.getOverlays().add(poiOverlay);
    	map.invalidate();
    	
    	if(!started)
    	{
	    	Button button = (Button) findViewById(R.id.button);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	Log.i("Deleting...", ""+ids.get(lastTouchedIndex));
	            	db.deleteLocation(ids.get(lastTouchedIndex));
	            	/*poiOverlay.clear();
	            	annotation.hide();
	            	init();*/
	            	Intent intent = new Intent(MyMap.this, MyMap.class);
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
    	customTitle.setText(item.getTitle());
    	
		// show the customized annotation
		annotation.showAnnotationView(item);
    }

    @Override
    public boolean isRouteDisplayed() {
      return false;
    }
   } 
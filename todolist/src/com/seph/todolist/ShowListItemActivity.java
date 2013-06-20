package com.seph.todolist;

import android.os.Bundle;
import android.util.*;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.TextView;


public class ShowListItemActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_list_item);
		MySQLiteHelper db = new MySQLiteHelper(this);
		int listId;
		
		listId = getIntent().getIntExtra("listid",1);
		
		 /*if (extras != null) {
		     listId = parseInt(extras.getIntExtra("newscategory"));
		 }
		 else
			listId = "algemeen";*/
		
		TextView t=new TextView(this); 
		
		//String itemText = "Name: "+ db.getName(listId) + " , X coordinate: "+ db.getXval(listId) + " , Y coordinate: "
		//		+ db.getYval(listId) + ".";
		
		String xVal = "" + db.getXval(listId);
		Log.d("xVal",xVal);
	    t = (TextView)findViewById(R.id.textView8);t.setText(db.getName(listId));
	    t = (TextView)findViewById(R.id.textView9);t.setText("X coordinate ");
	    t = (TextView)findViewById(R.id.textView10);t.setText(xVal);
	    t = (TextView)findViewById(R.id.textView11);t.setText("Y coordinate ");
	    t = (TextView)findViewById(R.id.textView12);t.setText(""+ db.getYval(listId));
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_list_item, menu);
		return true;
	}
	

}

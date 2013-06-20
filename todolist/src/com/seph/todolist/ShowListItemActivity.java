package com.seph.todolist;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ShowListItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_list_item);
		MySQLiteHelper db = new MySQLiteHelper(this);
		int listId;
		
		listId = getIntent().getIntExtra("listid",-1);
		
		 /*if (extras != null) {
		     listId = parseInt(extras.getIntExtra("newscategory"));
		 }
		 else
			listId = "algemeen";*/
		
		TextView t=new TextView(this); 
		
		String itemText = "Name: "+ db.getName(listId) + " , X coordinate: "+ db.getXval(listId) + " , Y coordinate: "
				+ db.getYval(listId) + ".";

	    t=(TextView)findViewById(R.id.textView1); 
	    t.setText(itemText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_list_item, menu);
		return true;
	}

}

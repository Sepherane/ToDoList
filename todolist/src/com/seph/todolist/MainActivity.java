package com.seph.todolist;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.app.ListActivity;
import android.content.Intent;

import java.util.*;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ArrayList<String> itemList = new ArrayList<String>();
		
		itemList.add("Add new");
		
		MySQLiteHelper db = new MySQLiteHelper(this);
		
		List<Integer> ids = db.findAllLocations();
		
		for(int i : ids)
		itemList.add(db.getName(i));
		
		ArrayAdapter adapter = new ArrayAdapter<String>(this, 
		        R.layout.listview_main, itemList);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		
		listView.setTextFilterEnabled(true);
		
		/* Lijst knoppen */
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent;
				
				switch (position){
				case 0:
					intent = new Intent(MainActivity.this, AddnewActivity.class);
					startActivity(intent);
					break;
				default:
					intent = new Intent(MainActivity.this, MyMap.class);
					intent.putExtra("listid", position);
					startActivity(intent);
					break;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

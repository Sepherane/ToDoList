package com.seph.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddnewActivity extends Activity {
	
	MySQLiteHelper db = new MySQLiteHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addnew);
		
		Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	EditText text = (EditText)findViewById(R.id.editText1);
            	String name = text.getText().toString();
        		
        		float randomY = (float) (51.80 + (Math.random() * ((52 - 51.80))));
        		float randomX = (float) (4.3 + (Math.random() * ((4.7 - 4.3))));
            	
            	db.addLocation(name, randomX, randomY);
                
                Intent intent = new Intent(AddnewActivity.this, MainActivity.class);
                startActivity(intent);
                
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.addnew, menu);
		return true;
	}

}

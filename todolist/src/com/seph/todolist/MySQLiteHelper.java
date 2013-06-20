package com.seph.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.*;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_LOCS = "todolist";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_NAME = "name";
  public static final String COLUMN_XVAL = "xval";
  public static final String COLUMN_YVAL = "yval";

  private static final String DATABASE_NAME = "locs.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_LOCS + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_NAME
      + " text not null, " + COLUMN_XVAL
      + " integer not null, " + COLUMN_YVAL
      + " integer not null);";

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCS);
    onCreate(db);
  }
  
  public void addLocation(String name, int xloc, int yloc) {

      ContentValues values = new ContentValues();
      values.put(COLUMN_NAME, name);
      values.put(COLUMN_XVAL, xloc);
      values.put(COLUMN_YVAL, yloc);

      SQLiteDatabase db = this.getWritableDatabase();
      
      db.insert(TABLE_LOCS, null, values);
      db.close();
  }
  
  public String getName(int id) {
		String query = "Select * FROM " + TABLE_LOCS + " WHERE " + COLUMN_ID + " =  " + id;
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(query, null);
		
		String locationName = "";
		
		if (cursor.moveToFirst()) {
			cursor.moveToFirst();
			locationName = cursor.getString(1);
			cursor.close();
		} else {
			locationName = null;
		}
		db.close();
		return locationName;
	}
  
  public int getXval(int id) {
		String query = "Select * FROM " + TABLE_LOCS + " WHERE " + COLUMN_ID + " =  " + id;
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(query, null);
		
		int xval = 0;
		
		if (cursor.moveToFirst()) {
			cursor.moveToFirst();
			xval = cursor.getInt(2);
			cursor.close();
		} else {
			xval = 0;
		}
		db.close();
		return xval;
	}
  
  public int getYval(int id) {
		String query = "Select * FROM " + TABLE_LOCS + " WHERE " + COLUMN_ID + " =  " + id;
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(query, null);
		
		int yval = 0;
		
		if (cursor.moveToFirst()) {
			cursor.moveToFirst();
			yval = cursor.getInt(3);
			cursor.close();
		} else {
			yval = 0;
		}
		db.close();
		return yval;
	}
  
  public List<Integer> findAllLocations() { // Find ALL the locations!
      List<Integer> idList = new ArrayList<Integer>();
      // Select All Query
      String selectQuery = "SELECT  * FROM " + TABLE_LOCS;

      SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = db.rawQuery(selectQuery, null);

      // looping through all rows and adding to list
      if (cursor.moveToFirst()) {
          do {
              int db_name = cursor.getInt(0);
              // Adding contact to list
              idList.add(db_name);
          } while (cursor.moveToNext());
      }
      db.close();
      // return contact list
      return idList;
  }
  
  public boolean deleteLocation(int id) {
		
		boolean result = false;
		
		String query = "Select * FROM " + TABLE_LOCS + " WHERE " + COLUMN_ID + " =  " + id;

		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(query, null);
		
		if (cursor.moveToFirst()) {
			db.delete(TABLE_LOCS, COLUMN_ID + " = "+ id, null);
		    db.close();
			cursor.close();
			result = true;
		}
		return result;
	}
  

} 
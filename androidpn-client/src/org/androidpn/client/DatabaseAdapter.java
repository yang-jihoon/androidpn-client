package org.androidpn.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {
	private static final String DATABASE_NAME = "androidpn.db";
	public static final String DATABASE_TABLE = "androidpn";
	private static final int DATABASE_VERSION = 2;
	
	public static final String KEY_ID="_id";
	public static final String TITLE = "title";
	public static final String MESSAGE = "message";
	public static final String DATE = "date";
	
	private SQLiteDatabase db;
	private final Context context;
	private DatabaseHelper dbHelper;
	
	private String[] field = new String[]{
			KEY_ID,
			TITLE,
			MESSAGE,
			DATE
	};
	
	public DatabaseAdapter(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
		
	public DatabaseAdapter open() {
		db = dbHelper.getWritableDatabase();
		//dbHelper.onUpgrade(db, 1, 2);
		return this;
	}
	
	public void close() {
		db.close();
	}
	
	public int insert(NotificationIQ iq) {
		ContentValues values = new ContentValues(2);
		 
		values.put(TITLE, iq.getTitle());
		values.put(MESSAGE, iq.getMessage());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date = new Date();
		values.put(DATE, dateFormat.format(date));
		
		return (int) db.insert(DATABASE_TABLE, TITLE, values);
		
	}
	
	public Cursor getAll() {					
		return db.query(DATABASE_TABLE, field,null,null,null,null,DATE+" desc");
	}
	
	public NotificationIQ getData(String keyId) {
		Cursor cursor = db.query(DATABASE_TABLE, field,KEY_ID+"="+keyId,null,null,null,DATE+" desc");
		NotificationIQ iq = new NotificationIQ();
		iq.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
		iq.setMessage(cursor.getString(cursor.getColumnIndex(MESSAGE)));
		return iq;
	}
}
package org.androidpn.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ApnListAdapter extends CursorAdapter {
	
    private static final String LOGTAG = LogUtil
            .makeLogTag(NotificationService.class);

	public ApnListAdapter(Context context, Cursor c) {
		super(context, c, true);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor){
		TextView titleView = (TextView) view.findViewById(R.id.title);
		titleView.setText(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.TITLE)));

		TextView messageView = (TextView) view.findViewById(R.id.message);
		messageView.setText(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.MESSAGE)));

		TextView dateView = (TextView) view.findViewById(R.id.date);
		String dateTime = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.DATE));
		Date date = null;
		try {
			SimpleDateFormat orgDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = orgDateFormat.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
	        Log.d(LOGTAG, "date parse error "+dateTime);
			date = new Date();
		}
		SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd HH:mm");  
		dateView.setText(newDateFormat.format(date));		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater layoutInflater = LayoutInflater.from(context); 
		View view = layoutInflater.inflate(R.layout.list, parent, false);
		return view;
	}

}

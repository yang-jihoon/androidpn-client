/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.client;

import org.androidpn.client.R;
import org.androidpn.client.ServiceManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

/**
 * This is an androidpn client application.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class ApnActivity extends Activity {
	
	public static Context context;
	public static ServiceManager serviceManager;
	private Cursor mCursor = null;
	private DatabaseAdapter databaseAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("ApnActivity", "onCreate()...");

        super.onCreate(savedInstanceState);   

        ApnActivity.context = this;
        
        // Start the service
        serviceManager = new ServiceManager(this);
        serviceManager.setNotificationIcon(R.drawable.notification);
        serviceManager.startService();    
        
        databaseAdapter = new DatabaseAdapter(this).open();
        mCursor = databaseAdapter.getAll();
		
        setContentView(R.layout.main);
		ListView listView = (ListView) findViewById(R.id.list);
		
        ApnListAdapter adapter = new ApnListAdapter(this, mCursor);
		listView.setAdapter(adapter);

		
    }
    
        
	@Override
	protected void onResume() {
		super.onResume();
		
		mCursor.requery();
	}



	@Override
	public void onDestroy() {
		super.onDestroy();

		databaseAdapter.close();
		mCursor.close();
		
	}
	
	// 메뉴키 설정
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		Intent intent = new Intent().setClass(ApnActivity.this,
                NotificationSettingsActivity.class);
		ApnActivity.this.startActivity(intent);
		   
		return true;
		
	}

}
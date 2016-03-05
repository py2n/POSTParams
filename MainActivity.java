package com.hanselandpetal.catalog;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
//		Initialize the TextView for vertical scrolling
		Button r1 = (Button) findViewById(R.id.button);
		final getRequest gr=new getRequest();
		r1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				setContentView(R.layout.activity_main);
				if (isOnline()) {
					Intent intent=new Intent(MainActivity.this,getRequest.class);
					intent.putExtra("request", "6545412");
					startActivity(intent);
				} else {
					toast("اشکال در ارتباط با اینترنت");
				}
			}
		});
		Button r2=(Button) findViewById(R.id.button2);
		r2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (isOnline()) {
					Intent intent=new Intent(MainActivity.this,getRequest.class);
					intent.putExtra("request", "4234412");
					startActivity(intent);
				} else {
					toast("اشکال در ارتباط با اینترنت");
				}
			}
		});
		Button r3=(Button) findViewById(R.id.button3);
		r3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isOnline()) {
					Intent intent = new Intent(MainActivity.this,getRequest.class);
					intent.putExtra("request", "981236");
					startActivity(intent);
				} else {
					toast("اشکال در ارتباط با اینترنت");
				}
			}
		});

		Button getCam=(Button)findViewById(R.id.button7);
		getCam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isOnline()) {
					Intent intent = new Intent(MainActivity.this,FTPConnect.class);
					startActivity(intent);
				} else {
					toast("اشکال در ارتباط با اینترنت");
				}
			}
		});
	}



	public void toast(String a){
		Toast.makeText(this, a, Toast.LENGTH_LONG).show();
	}

	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_get_data) {

		}
		return false;
	}
}
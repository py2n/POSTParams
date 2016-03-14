package com.hanselandpetal.catalog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gitonway.lee.niftynotification.lib.Effects;

import java.util.Date;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import picker.ugurtekbas.com.Picker.TimeChangedListener;

public class MainActivity extends FragmentActivity implements TimeChangedListener {
	boolean doubleBackToExitPressedOnce = false;
	private Effects effect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		PushLink.start(this, R.mipmap.ic_launcher_off, "e18ts0p51hih6paa", "jkhgfyf48664");
		setContentView(R.layout.activity_main1);
		crouton("خوش آمدید");
		Button r1 = (Button) findViewById(R.id.button);
		r1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isOnline()) {
					Intent intent=new Intent(MainActivity.this,getRequest.class);
					intent.putExtra("request", "6545412");
					startActivity(intent);
				} else {
//					toast("اشکال در ارتباط با اینترنت");
					crouton("خطا در ارتباط با اینترنت");

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
//					toast("اشکال در ارتباط با اینترنت");
					crouton("خطا در ارتباط با اینترنت");
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
//					toast("اشکال در ارتباط با اینترنت");
					crouton("خطا در ارتباط با اینترنت");
				}
			}
		});

		Button getCam=(Button)findViewById(R.id.button7);
		getCam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isOnline()) {
					Intent intent = new Intent(MainActivity.this, FTPConnect.class);
					startActivity(intent);
				} else {
//					toast("اشکال در ارتباط با اینترنت");
					crouton("خطا در ارتباط با اینترنت");
				}
			}
		});

	}

	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
			super.onBackPressed();
			return;
		}

		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, "با کلیک مجدد خارج شوید", Toast.LENGTH_SHORT).show();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);
	}


	public void toast(String a){
		Toast.makeText(this, a, Toast.LENGTH_LONG).show();
	}

	public void crouton(String a){

		Configuration croutonConfiguration = new Configuration.Builder()
				.setDuration(2500).build();
		Style InfoStyle = new Style.Builder()
				.setBackgroundColorValue(Color.parseColor("#0099cc"))
				.setGravity(Gravity.CENTER_HORIZONTAL)
				.setConfiguration(croutonConfiguration)
				.setHeight(120)
				.setTextColorValue(Color.parseColor("#323a2c")).setImageResource(R.mipmap.ic_launcher).build();



		Style AlertStyle = new Style.Builder()
				.setBackgroundColorValue(Color.parseColor("#cc0000"))
				.setGravity(Gravity.CENTER_HORIZONTAL)
				.setConfiguration(croutonConfiguration)
				.setHeight(120)
				.setTextColorValue(Color.parseColor("#323a2c")).setImageResource(R.mipmap.ic_launcher).build();


		Style ConfirmStyle = new Style.Builder()
				.setBackgroundColorValue(Color.parseColor("#FF00FF0D"))
				.setGravity(Gravity.CENTER_HORIZONTAL)
				.setConfiguration(croutonConfiguration)
				.setHeight(120)
				.setTextColorValue(Color.parseColor("#323a2c")).setImageResource(R.mipmap.ic_launcher).build();


		Crouton.clearCroutonsForActivity(this);
		if (a.contains("error"))
			Crouton.makeText(this,a, AlertStyle).show();
		else if (a.contains("been"))
			Crouton.makeText(this,a,ConfirmStyle).show();
		else if (a.contains("خطا"))
			Crouton.makeText(this,a,AlertStyle).show();
		else
			Crouton.makeText(this,a,InfoStyle).show();

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

	@Override
	public void timeChanged(Date date) {

	}
}
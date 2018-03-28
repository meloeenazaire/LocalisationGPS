package com.example.localisation_gps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AndroidLocationActivity extends Activity
{

	Button btnGPSShowLocation;

	Loc_service LocationService;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LocationService = new Loc_service(AndroidLocationActivity.this);

		btnGPSShowLocation = (Button) findViewById(R.id.button1);
		btnGPSShowLocation.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{

				Location gpsLocation = LocationService.getLocation(LocationManager.GPS_PROVIDER);

				if (gpsLocation != null)
				{
					double latitude = gpsLocation.getLatitude();
					double longitude = gpsLocation.getLongitude();
					Toast.makeText(
							getApplicationContext(),
							"Mobile Location (GPS): \nLatitude: " + latitude + "\nLongitude: " + longitude,
							Toast.LENGTH_LONG).show();
				}
				else
				{
					showSettingsAlert("GPS");
				}

			}
		});
	}

	public void showSettingsAlert(String provider)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(AndroidLocationActivity.this);
		
		alertDialog.setTitle(provider + " SETTINGS");
		alertDialog.setMessage(provider + " is not enabled! Want to go to settings menu?");
		alertDialog.setPositiveButton("Settings",new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				AndroidLocationActivity.this.startActivity(intent);
			}
		});

		alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		});

		alertDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.layout.activity_main, menu);
		return true;
	}

}
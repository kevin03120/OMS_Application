package fr.oms.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapPane extends Activity implements OnMapReadyCallback, LocationListener {

	private String nomEquipement;
	private Location loc;
	private Location locUser;
	private double latitude;
	private double longitude;
	private double latitudeUser = 0;
	private double longitudeUser = 0;
	private LocationManager lm;
	private Button go;

	@Override
	protected void onPause() {
		super.onPause();
		lm.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		go = (Button) findViewById(R.id.btnGPS);
		String la = getIntent().getExtras().getString("latitude");
		String lo = getIntent().getExtras().getString("longitude");
		nomEquipement = getIntent().getExtras().getString("nom");
		latitude = Double.parseDouble(la);
		longitude = Double.parseDouble(lo);
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}


	@Override
	public void onMapReady(GoogleMap map) {   
		loc = new Location("Point B");
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		LatLng equipement = new LatLng(latitude, longitude);
		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(equipement, 13));
		map.addMarker(new MarkerOptions()
		.title(nomEquipement)
		.position(equipement)).showInfoWindow();   
		map.getUiSettings().setMapToolbarEnabled(true);
	}


	@Override
	public void onLocationChanged(Location location) {
		latitudeUser = location.getLatitude();
		longitudeUser = location.getLongitude();
		locUser = new Location("Point A");
		locUser.setLatitude(latitudeUser);
		locUser.setLongitude(longitudeUser);
		afficherBoutonGo();

	}

	private void afficherBoutonGo() {
		go.setVisibility(View.VISIBLE);
		go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url1 = "http://maps.google.com/maps?saddr="+latitudeUser+", "+ longitudeUser+
						"&daddr="+loc.getLatitude()+", "+loc.getLongitude();
				System.out.println("GEOLOC" + url1.toString());
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url1));
				startActivity(intent);

			}
		});
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}
}

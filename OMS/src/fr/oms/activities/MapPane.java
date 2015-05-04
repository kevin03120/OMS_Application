package fr.oms.activities;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.Toast;


public class MapPane extends Activity implements OnMapReadyCallback, LocationListener {

	private String nomEquipement;
	private Location loc;
	private Location locUser;
	private double latitude;
	private double longitude;
	private double latitudeUser = 0;
	private double longitudeUser = 0;
	private double altitude;
	private double accuracy;
	private LocationManager lm;
	
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
                .position(equipement));
        
    }

	@Override
	public void onLocationChanged(Location location) {
		latitudeUser = location.getLatitude();
		longitudeUser = location.getLongitude();
		altitude = location.getAltitude();
		accuracy = location.getAccuracy();


     	
    	locUser = new Location("Point A");
    	locUser.setLatitude(latitudeUser);
    	locUser.setLongitude(longitudeUser);
    	
		String msg = String.format(
				getResources().getString(R.string.new_location), latitude,
				longitude, altitude, accuracy);
		
		Toast.makeText(this, "Distance : " + locUser.distanceTo(loc) + " metres", Toast.LENGTH_SHORT).show();
		
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		String newStatus = "";
		switch (status) {
			case LocationProvider.OUT_OF_SERVICE:
				newStatus = "OUT_OF_SERVICE";
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				newStatus = "TEMPORARILY_UNAVAILABLE";
				break;
			case LocationProvider.AVAILABLE:
				newStatus = "AVAILABLE";
				break;
		}
		String msg = String.format(getResources().getString(R.string.provider_disabled), provider, newStatus);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		String msg = String.format(getResources().getString(R.string.provider_enabled), provider);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
		String msg = String.format(getResources().getString(R.string.provider_disabled), provider);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}

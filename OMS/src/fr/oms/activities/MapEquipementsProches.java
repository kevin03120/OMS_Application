package fr.oms.activities;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.oms.metier.Equipement;
import fr.oms.modele.Manager;

public class MapEquipementsProches extends Activity implements OnMapReadyCallback {


	private double latitudeUser = 0;
	private double longitudeUser = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		latitudeUser = getIntent().getExtras().getDouble("latitude");
		longitudeUser = getIntent().getExtras().getDouble("longitude");
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}


	@Override
	public void onMapReady(GoogleMap map) {   
		LatLng geolocUser = new LatLng(latitudeUser, longitudeUser);
		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(geolocUser, 15));
		int cpt = 0;
		for(Equipement e : Manager.getInstance().getListEquipementProches()){
			if(cpt<10){
				cpt++;
				LatLng geolocEquipement = new LatLng(Double.parseDouble(e.getGeoloc().getLatitude()), Double.parseDouble(e.getGeoloc().getLongitude()));
				map.addMarker(new MarkerOptions()
				.title(e.getNom())
				.position(geolocEquipement));
				map.getUiSettings().setMapToolbarEnabled(true);
			}
		}
	}

}

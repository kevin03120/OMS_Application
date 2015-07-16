package fr.oms.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.oms.metier.Equipement;
import fr.oms.modele.Manager;

public class MapEquipementsDisciplineProches extends Activity implements OnMapReadyCallback {


	private double latitudeUser = 0;
	private double longitudeUser = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.v("test", "t0");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        
        latitudeUser = Double.parseDouble(getIntent().getExtras().getString("latitude"));
        longitudeUser = Double.parseDouble(getIntent().getExtras().getString("longitude"));
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        
        mapFragment.getMapAsync(this);
    }
    
   
    @Override
    public void onMapReady(GoogleMap map) {   
        LatLng geolocUser = new LatLng(latitudeUser, longitudeUser);
        
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(geolocUser, 13));
        for(Equipement e : Manager.getInstance().getListEquipementProches()){
	        	LatLng geolocEquipement = new LatLng(Double.parseDouble(e.getGeoloc().getLatitude()), Double.parseDouble(e.getGeoloc().getLongitude()));
	        
	        	map.addMarker(new MarkerOptions()
	            .title(e.getNom())
	            .position(geolocEquipement));
	        	map.getUiSettings().setMapToolbarEnabled(true);
        }
        Equipement equipementFirst = Manager.getInstance().getListEquipementProches().get(0);
        LatLng geolocEquipement = new LatLng(Double.parseDouble(equipementFirst.getGeoloc().getLatitude()), Double.parseDouble(equipementFirst.getGeoloc().getLongitude()));
        map.addMarker(new MarkerOptions()
        .title(equipementFirst.getNom()+ " \n " +"(cliquez pour aller)")
        .position(geolocEquipement)).showInfoWindow();
    	map.getUiSettings().setMapToolbarEnabled(true);
    }

}

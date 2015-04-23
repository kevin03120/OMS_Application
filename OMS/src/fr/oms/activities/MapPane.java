package fr.oms.activities;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.app.Activity;
import android.os.Bundle;

public class MapPane extends Activity implements OnMapReadyCallback {

	private String nomEquipement;
	private double latitude;
	private double longitude;
	
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
        LatLng equipement = new LatLng(latitude, longitude);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(equipement, 13));

        map.addMarker(new MarkerOptions()
                .title(nomEquipement)
                .position(equipement));
    }
}

package fr.oms.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import fr.oms.activities.FragmentEquipementActivity;
import fr.oms.activities.MapEquipementsProches;
import fr.oms.activities.R;
import fr.oms.adapter.EquipementGeolocAdapter;
import fr.oms.metier.Equipement;
import fr.oms.modele.Manager;

public class FragmentGeolocEquipements extends Fragment implements LocationListener{

	private List<Equipement> equipementTriesLocalisation;
	private ListView listEquipement;
	private Button equipementGeoloc;
	private double latitudeUser = 0;
	private double longitudeUser = 0;
	private LocationManager lm;
	
	@Override
	public void onResume() {
			super.onResume();
			lm = (LocationManager) getActivity().getSystemService(Activity.LOCATION_SERVICE);
			if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0,
						this);
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0,
					this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		lm.removeUpdates(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.list_geoloc_equipements, container,false);
		equipementTriesLocalisation = new ArrayList<Equipement>();
		listEquipement = (ListView)v.findViewById(R.id.listeGeolocEquipement);
		equipementGeoloc = (Button)v.findViewById(R.id.goEquipementsMap);
	    onTouchItem();
	    onTouchButton();
	    return v;
	}
	
	private void onTouchButton(){
		equipementGeoloc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Manager.getInstance().setListEquipementProches(equipementTriesLocalisation);
				Intent intent = new Intent(FragmentGeolocEquipements.this.getActivity(), MapEquipementsProches.class);
				intent.putExtra("longitude", longitudeUser);
				intent.putExtra("latitude", latitudeUser);
				startActivity(intent);
			}
		});
	}
	private void onTouchItem(){
		listEquipement.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Equipement e = equipementTriesLocalisation.get(position);
				Intent intent = new Intent(FragmentGeolocEquipements.this.getActivity(), FragmentEquipementActivity.class);
				intent.putExtra("position", e.getUid());
				startActivity(intent);
			}
		});
	}

	private void donneListe(){
		List<Equipement> listeTemporaire = new ArrayList<Equipement>();
		for(Equipement e : Manager.getInstance().getListeEquipement()){
			if(!e.getGeoloc().getLatitude().equals("")){
				listeTemporaire.add(e);
			}
		}
		equipementTriesLocalisation.clear();
		for(int i = 0; i < listeTemporaire.size(); i++){
			Equipement valeurTest = listeTemporaire.get(0);
			for(Equipement e : listeTemporaire){
				if(donneDistance(e) <= donneDistance(valeurTest)){
					valeurTest = e;
				}
			}
			equipementTriesLocalisation.add(valeurTest);
			listeTemporaire.remove(valeurTest);
		}
	}

	private double donneDistance(Equipement e){
		Location locUser = new Location("Point A");
		locUser.setLatitude(latitudeUser);
		locUser.setLongitude(longitudeUser);
		
		Location loc = new Location("Point B");
		loc.setLatitude(Double.parseDouble(e.getGeoloc().getLatitude()));
		loc.setLongitude(Double.parseDouble(e.getGeoloc().getLongitude()));
	
		return locUser.distanceTo(loc);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		latitudeUser = location.getLatitude();
		longitudeUser = location.getLongitude();
		donneListe();
		EquipementGeolocAdapter adapterAssoc = new EquipementGeolocAdapter(getActivity(), 0, equipementTriesLocalisation, latitudeUser, longitudeUser);
		listEquipement.setAdapter(adapterAssoc);
		equipementGeoloc.setVisibility(0);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}

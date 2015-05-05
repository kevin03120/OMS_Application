package fr.oms.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.oms.activities.FragmentEquipementActivity;
import fr.oms.activities.R;
import fr.oms.adapter.EquipementGeolocAdapter;
import fr.oms.metier.Equipement;
import fr.oms.modele.Manager;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentGeolocAdresse extends Fragment {

	private EditText edit;
	private Button btnSearch;
	private ListView listEquipement;
	private List<Equipement> equipementsTriesDistance;
	private Address ad;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.geoloc_adress, container,false);
		edit = (EditText)v.findViewById(R.id.editAdress);
		btnSearch = (Button)v.findViewById(R.id.btnSearch);
		listEquipement = (ListView)v.findViewById(R.id.list_equipement_adress);
		equipementsTriesDistance = new ArrayList<Equipement>();
		onTouchBtn();
	    return v;
	}
	
	private void donneListe(){
		List<Equipement> listeTemporaire = new ArrayList<Equipement>();
		for(Equipement e : Manager.getInstance().getListeEquipement()){
			if(!e.getGeoloc().getLatitude().equals("")){
				listeTemporaire.add(e);
			}
		}
		equipementsTriesDistance.clear();
		for(int i = 0; i < listeTemporaire.size(); i++){
			Equipement valeurTest = listeTemporaire.get(0);
			for(Equipement e : listeTemporaire){
				if(donneDistance(e) <= donneDistance(valeurTest)){
					valeurTest = e;
				}
			}
			equipementsTriesDistance.add(valeurTest);
			listeTemporaire.remove(valeurTest);
			i++;
		}
	}
		
		private double donneDistance(Equipement e){
			Location locUser = new Location("Point A");
			locUser.setLatitude(ad.getLatitude());
			locUser.setLongitude(ad.getLongitude());
			
			Location loc = new Location("Point B");
			loc.setLatitude(Double.parseDouble(e.getGeoloc().getLatitude()));
			loc.setLongitude(Double.parseDouble(e.getGeoloc().getLongitude()));
		
			return locUser.distanceTo(loc);
		}
	
	private void onTouchBtn(){
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Geocoder geocoder = new Geocoder(new Activity());
				List<Address> adresse;
				try {
					adresse = geocoder.getFromLocationName(edit.getText().toString(), 1);
					if(adresse.size() != 0){
						ad = adresse.get(0);
						donneListe();
						EquipementGeolocAdapter equipementAdapter = new EquipementGeolocAdapter(getActivity(), 0, equipementsTriesDistance, ad.getLatitude(), ad.getLongitude());
						listEquipement.setAdapter(equipementAdapter);
						onTouchItem();
					}
					else{
						Toast.makeText(getActivity(), "Adresse inconnu", Toast.LENGTH_SHORT).show();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void onTouchItem(){
		listEquipement.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Equipement e = equipementsTriesDistance.get(position);
				Intent intent = new Intent(FragmentGeolocAdresse.this.getActivity(), FragmentEquipementActivity.class);
				intent.putExtra("position", e.getUid());
				startActivity(intent);
			}
		});
	}
}

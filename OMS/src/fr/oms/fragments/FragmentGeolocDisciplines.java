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
import android.widget.ListView;
import fr.oms.activities.FragmentDisciplineActivity;
import fr.oms.activities.R;
import fr.oms.adapter.DisciplineGeolocAdapter;
import fr.oms.metier.Discipline;
import fr.oms.modele.Manager;

public class FragmentGeolocDisciplines extends Fragment implements LocationListener{

	private List<Discipline> disciplineTriesLocalisation;
	private ListView listDiscipline;
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
		View v = inflater.inflate(R.layout.list_geoloc_disciplines, container,false);
		disciplineTriesLocalisation = new ArrayList<Discipline>();
		listDiscipline = (ListView)v.findViewById(R.id.listeGeolocDiscipline);
	    onTouchItem();
	    return v;
	}
	
	private void onTouchItem(){
		listDiscipline.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Discipline d = disciplineTriesLocalisation.get(position);
				Intent intent = new Intent(FragmentGeolocDisciplines.this.getActivity(), FragmentDisciplineActivity.class);
				intent.putExtra("position", d.getUid());
				startActivity(intent);
			}
		});
	}

	private void donneListe(){
		disciplineTriesLocalisation.clear();
		for(Discipline d : Manager.getInstance().getListeDiscipline()){
			disciplineTriesLocalisation.add(d);
		}
	}
	
	@Override
	public void onLocationChanged(Location location) {
		latitudeUser = location.getLatitude();
		longitudeUser = location.getLongitude();
		donneListe();
		DisciplineGeolocAdapter adapterDisc = new DisciplineGeolocAdapter(getActivity(), 0, disciplineTriesLocalisation, latitudeUser, longitudeUser);
		listDiscipline.setAdapter(adapterDisc);
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

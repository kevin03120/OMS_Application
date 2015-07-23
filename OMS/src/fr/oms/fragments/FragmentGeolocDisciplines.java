package fr.oms.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
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

public class FragmentGeolocDisciplines extends Fragment{

	private List<Discipline> disciplineTriesLocalisation;
	private ListView listDiscipline;
	private double latitudeUser = 0;
	private double longitudeUser = 0;
	private LocationManager lm;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.list_geoloc_disciplines, container,false);
		disciplineTriesLocalisation = new ArrayList<Discipline>();
		listDiscipline = (ListView)v.findViewById(R.id.listeGeolocDiscipline);
		donneListe();
		DisciplineGeolocAdapter adapterDisc = new DisciplineGeolocAdapter(getActivity(), 0, disciplineTriesLocalisation, latitudeUser, longitudeUser);
		listDiscipline.setAdapter(adapterDisc);
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
}

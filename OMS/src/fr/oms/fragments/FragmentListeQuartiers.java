package fr.oms.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.oms.activities.R;
import fr.oms.adapter.ExpandableListAdapterQuartier;
import fr.oms.metier.Equipement;
import fr.oms.metier.Quartier;
import fr.oms.modele.Manager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class FragmentListeQuartiers extends Fragment{

	private ExpandableListView listeQuartiers;
	private HashMap<String, List<String>> mapEquipementParQuartier;
	private List<String> listeDesEquipementQuartier;
	private List<String> listeDesNomsQuartier;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		mapEquipementParQuartier=new HashMap<String, List<String>>();
		listeDesNomsQuartier = new ArrayList<String>();
		for(Quartier q:Manager.getInstance().getListeQuartier()){
			listeDesNomsQuartier.add(q.getNom());
			listeDesEquipementQuartier = new ArrayList<String>();
			for(Equipement e:q.getMesEquipements()){
				listeDesEquipementQuartier.add(e.getNom());
			}
			mapEquipementParQuartier.put(q.getNom(), listeDesEquipementQuartier);
		}
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//DisciplineAdapter disciplineAdapter = new DisciplineAdapter(getActivity(), 0, Manager.getInstance().getListeDiscipline());
		ExpandableListAdapterQuartier adapter=new ExpandableListAdapterQuartier(getActivity(), listeDesNomsQuartier, mapEquipementParQuartier);
		View v = inflater.inflate(R.layout.list_discipline, container,false);
		listeQuartiers = (ExpandableListView) v.findViewById(R.id.lvExp);
		//listeDiscipline = (ListView)v.findViewById(R.id.listeDiscipline);
		//listeDiscipline.setAdapter(disciplineAdapter);
		listeQuartiers.setAdapter(adapter);
		return v;
	}
	
}

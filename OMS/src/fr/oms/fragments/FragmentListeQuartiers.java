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
import android.widget.ExpandableListView.OnGroupExpandListener;

public class FragmentListeQuartiers extends Fragment{

	private int lastExpandedPosition =-1;
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
		ExpandableListAdapterQuartier adapter=new ExpandableListAdapterQuartier(getActivity(), listeDesNomsQuartier, mapEquipementParQuartier);
		View v = inflater.inflate(R.layout.list_discipline, container,false);
		listeQuartiers = (ExpandableListView) v.findViewById(R.id.lvExp);
		listeQuartiers.setAdapter(adapter);
		ajouterExpandListener();
		return v;
	}

	private void ajouterExpandListener() {
		listeQuartiers.setOnGroupExpandListener(new OnGroupExpandListener() {
		    @Override
		    public void onGroupExpand(int groupPosition) {
		            if (lastExpandedPosition != -1
		                    && groupPosition != lastExpandedPosition) {
		            	listeQuartiers.collapseGroup(lastExpandedPosition);
		            }
		            lastExpandedPosition = groupPosition;
		    }
		});
	}
	
}

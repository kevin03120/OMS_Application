package fr.oms.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import fr.oms.activities.R;
import fr.oms.adapter.EquipementAdapter;
import fr.oms.modele.Manager;

public class FragmentListeEquipements extends Fragment{

	private ListView listEquipement;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		EquipementAdapter equipementAdapter = new EquipementAdapter(getActivity(), 0, Manager.getInstance().getListeEquipement());
		View v = inflater.inflate(R.layout.list_equipement, container,false);
		listEquipement = (ListView)v.findViewById(R.id.listeEquipement);
		listEquipement.setAdapter(equipementAdapter);
		return v;
	}

}

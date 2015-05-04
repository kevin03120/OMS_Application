package fr.oms.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import fr.oms.activities.R;
import fr.oms.adapter.ExpandableListAdapterDiscipline;
import fr.oms.metier.Discipline;
import fr.oms.metier.Sport;
import fr.oms.modele.Manager;

public class FragmentListeDisciplines extends Fragment {

	private ExpandableListView listeDiscipline;
	private List<String> listeDesNomsDeDisciplines;
	private List<String> listeDesNomsDeSports;
	private HashMap<String, List<String>> mapNomDesSportParDisciplines;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mapNomDesSportParDisciplines=new HashMap<String, List<String>>();
		listeDesNomsDeDisciplines=new ArrayList<String>();
		for(Discipline d:Manager.getInstance().getListeDiscipline()){
			listeDesNomsDeDisciplines.add(d.getNom());
			listeDesNomsDeSports=new ArrayList<String>();
			for(Sport s:d.getListeSport()){
				listeDesNomsDeSports.add(s.getNom());
			}
			mapNomDesSportParDisciplines.put(d.getNom(), listeDesNomsDeSports);
		}

		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//DisciplineAdapter disciplineAdapter = new DisciplineAdapter(getActivity(), 0, Manager.getInstance().getListeDiscipline());
		ExpandableListAdapterDiscipline adapter=new ExpandableListAdapterDiscipline(getActivity(), listeDesNomsDeDisciplines, mapNomDesSportParDisciplines);
		View v = inflater.inflate(R.layout.list_discipline, container,false);
		listeDiscipline = (ExpandableListView) v.findViewById(R.id.lvExp);
		//listeDiscipline = (ListView)v.findViewById(R.id.listeDiscipline);
		//listeDiscipline.setAdapter(disciplineAdapter);
		listeDiscipline.setAdapter(adapter);
		donneSport();
		return v;
	}


	private void donneSport(){
		listeDiscipline.setOnItemClickListener(new ListView.OnItemClickListener(){
			private ListView listeSport;
			private Discipline discipline;

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Dialog dialog=new Dialog(getActivity());
				dialog.setContentView(R.layout.dialog);
				listeSport = (ListView)dialog.findViewById(R.id.listeDialog);
				discipline = Manager.getInstance().getListeDiscipline().get(position);
				dialog.setTitle(discipline.getNom() + " : ");
				List<String> mesNomsSports = new ArrayList<String>();
				for(Sport sport : discipline.getListeSport()){
					mesNomsSports.add(sport.getNom());
				}
				ArrayAdapter<String> sportAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mesNomsSports);
				listeSport.setAdapter(sportAdapter);
				clicSurUnSport();
				dialog.show();
			}

			private void clicSurUnSport(){
				listeSport.setOnItemClickListener(new ListView.OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						/*Sport sport = discipline.getListeSport().get(position);
						int idSport = sport.getId();
						Intent intent = new Intent(getActivity(), ListAssociationActivity.class);
						intent.putExtra("idSport", idSport);
						startActivity(intent);*/
					}

				});
			}
		});
	}
}

package fr.oms.fragments;

import java.util.ArrayList;
import java.util.List;

import fr.oms.activities.FragmentAssociationActivity;
import fr.oms.activities.MapEquipementsDisciplineProches;
import fr.oms.activities.R;
import fr.oms.adapter.AssociationAdapter;
import fr.oms.metier.Association;
import fr.oms.metier.Discipline;
import fr.oms.metier.Equipement;
import fr.oms.metier.Sport;
import fr.oms.modele.Manager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentDiscipline extends Fragment {

	private static final String GEOLOCNULL = "0.00000";
	private Discipline discipline;
	private ListView listAssociation;
	private TextView txtPasAssoc;
	private List<Association> lesAssocsDiscipline;
	private List<Equipement> equipement;
	private LinearLayout header;

	public static FragmentDiscipline newInstance(Discipline d) {
		Bundle extras = new Bundle();
		extras.putInt("id", d.getUid());
		FragmentDiscipline fragment = new FragmentDiscipline();
		fragment.setArguments(extras);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v;
		for(Discipline d : Manager.getInstance().getListeDiscipline()){
			if(d.getUid() == getArguments().getInt("id")){
				discipline = d;
			}
		}
		if(compteListAssociation() != 0){
			v = inflater.inflate(R.layout.detail_discipline, container, false);
			View header = getLayoutInflater(getArguments()).inflate(R.layout.header_discipline, null);
			recupererToutesViews(header);
			txtPasAssoc.setVisibility(View.GONE);
			listAssociation = (ListView)v.findViewById(R.id.listAssocDiscipline);
			listAssociation.addHeaderView(header);
			adapterPourListAssociation();
			clicItemListAssoc();
			getActivity().setTitle(getResources().getString(R.string.titreDetailDiscipline));
		}
		else{
			v = inflater.inflate(R.layout.header_discipline, container, false);
			recupererToutesViews(v);
		}
		return v;
	}
	
	private void adapterPourListAssociation(){
		lesAssocsDiscipline = new ArrayList<Association>();
		boolean CorrespondanceSportsAssoDiscipline = false;
		List<Sport> sAsso;
		List<Sport> sDisc;
		for(Association a : Manager.getInstance().getListeAssociation()){
			if(a.isAdherent()){
				sAsso = a.getListeSport();
				sDisc = discipline.getListeSport();
				if(a.getListeSport()!=null){
					for(int i=0; i<sAsso.size(); i++){
						CorrespondanceSportsAssoDiscipline = false;
						for(int j=0; j<sDisc.size(); j++){
							if(sAsso.get(i).getNom().equals(sDisc.get(j).getNom())){
								CorrespondanceSportsAssoDiscipline = true;
								break;
							}
						}
						if(CorrespondanceSportsAssoDiscipline){
							lesAssocsDiscipline.add(a);
							break;
						}
					}
					AssociationAdapter adapterAssoc = new AssociationAdapter(getActivity(), 0, lesAssocsDiscipline);
					listAssociation.setAdapter(adapterAssoc);
					listAssociation.setVisibility(0);
				}
			}
		}
	}

	private int compteListAssociation(){
		lesAssocsDiscipline = new ArrayList<Association>();
		boolean CorrespondanceSportsAssoDiscipline = false;
		List<Sport> sAsso;
		List<Sport> sDisc;
		for(Association a : Manager.getInstance().getListeAssociation()){
			if(a.isAdherent()){
				sAsso = a.getListeSport();
				sDisc = discipline.getListeSport();
				if(a.getListeSport()!=null){
					for(int i=0; i<sAsso.size(); i++){
						CorrespondanceSportsAssoDiscipline = false;
						for(int j=0; j<sDisc.size(); j++){
							if(sAsso.get(i).getNom().equals(sDisc.get(j).getNom())){
								CorrespondanceSportsAssoDiscipline = true;
								break;
							}
						}
						if(CorrespondanceSportsAssoDiscipline){
							lesAssocsDiscipline.add(a);
							break;
						}
					}
				}
			}
		}
		return lesAssocsDiscipline.size();
	}

	private void clicItemListAssoc(){
		listAssociation.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Association a = lesAssocsDiscipline.get(position-1);
				Intent intent = new Intent(getActivity(), FragmentAssociationActivity.class);
				intent.putExtra("position", a.getId());
				intent.putExtra("adherents", true);
				intent.putExtra("nonAdherents", true);
				intent.putExtra("sport", false);
				intent.putExtra("idSport", 0);
				startActivity(intent);
			}
		});
	}

	private String listeDesSports(){
		String liste = new String("");
		List<Sport> s = discipline.getListeSport();
		for(int i = 0; i < s.size(); i++){
			if(i<s.size()-1)
				liste += s.get(i).getNom()+"\r\n";
			else
				liste += s.get(i).getNom();
		}
		return liste;
	}
	
	private void recupererToutesViews(View v){
		TextView txtTitreDiscipline = (TextView)v.findViewById(R.id.nomDiscipline);
		txtTitreDiscipline.setText(discipline.getNom());
		TextView listeSports = (TextView)v.findViewById(R.id.listeSports);
		listeSports.setText(listeDesSports());
		Button btnGoMap = (Button)v.findViewById(R.id.btn_map);
		txtPasAssoc = (TextView)v.findViewById(R.id.txtPasAssoc);
		goMap(btnGoMap);
	}

	private void recupererTousEquipements(){
		equipement = new ArrayList<Equipement>();
		//Si des associations proposent la discipline selectionnée:
		if(lesAssocsDiscipline != null && lesAssocsDiscipline.size() > 0) {
			//Pour chaque association:
			for(Association a: lesAssocsDiscipline) {
				//l'association est-elle membre de l'OMS?
				if(a.isAdherent()){
					//L'association possède t'elle au moins 1 équipement:
					if(a.getListeEquipement() != null && a.getListeEquipement().size() > 0) {
						//Pour chaque équipement:
						for(Equipement e : a.getListeEquipement()){
							//vérification que la liste ne contient pas déjà l'équipement sélectionné avant de l'ajouter:
							boolean alreadyAdded = false;
							if(equipement != null && equipement.size() > 0){
								for(Equipement e2 : equipement){
									if(e2.equals(e)){
										alreadyAdded = true;
									}
								}
							}
							//ajout de l'équipement dans la liste si il n'est pas deja ajouté:
							if(!alreadyAdded)
								equipement.add(e);
						}
					}
				}
			}
		}
	}
	
	private void goMap(Button b){
		//récupération de tous les equiepements des associations listées:
		recupererTousEquipements();
		//evenement onClick() sur le bouton de gélocalisation:
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//si on a au moins 1 équipement d'enregistré:
				if(equipement.size() > 0){
					Manager.getInstance().setListEquipementProches(equipement);
					Intent intent = new Intent(FragmentDiscipline.this.getActivity(), MapEquipementsDisciplineProches.class);
					intent.putExtra("longitude", equipement.get(0).getGeoloc().getLongitude());
					intent.putExtra("latitude", equipement.get(0).getGeoloc().getLatitude());
					startActivity(intent);
				}
			}
		});
	}
}

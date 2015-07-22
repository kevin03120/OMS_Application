package fr.oms.fragments;

import java.util.ArrayList;
import java.util.List;

import fr.oms.activities.FragmentAssociationActivity;
import fr.oms.activities.MapEquipementsDisciplineProches;
import fr.oms.activities.R;
import fr.oms.adapter.DiscGeolocListAssoAdapter;
import fr.oms.metier.Association;
import fr.oms.metier.Discipline;
import fr.oms.metier.Equipement;
import fr.oms.metier.Sport;
import fr.oms.modele.Manager;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentDiscipline extends Fragment  implements LocationListener{

	private static final String GEOLOCNULL = "0.00000";
	private Discipline discipline;
	private ListView listAssociation;
	private ArrayList<Association> listeTemporaire;
	private TextView txtPasAssoc;
	private List<Association> lesAssocsDiscipline;
	private List<Equipement> equipement;
	private LinearLayout header;
	private double latitudeUser;
	private double longitudeUser;
	private LocationManager lm;
	
	@Override
	public void onResume() {
			super.onResume();
			lm = (LocationManager)getActivity().getSystemService(Activity.LOCATION_SERVICE);
			if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		lm.removeUpdates(this);
	}

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
		listeTemporaire = new ArrayList<Association>();
		boolean CorrespondanceSportsAssoDiscipline = false;
		List<Sport> sAsso;
		List<Sport> sDisc;
		for(Association a : Manager.getInstance().getListeAssociation()){
			if(a.isAdherent()){
				sAsso = a.getListeSport();
				sDisc = discipline.getListeSport();
				if(a.getListeSport()!=null && a.getListeEquipement() != null){
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
		//Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
        DiscGeolocListAssoAdapter mSchedule = new DiscGeolocListAssoAdapter(this.getActivity(), 0, lesAssocsDiscipline, latitudeUser, longitudeUser);
        //On attribut à notre listView l'adapter que l'on vient de créer
        listAssociation.setAdapter(mSchedule);
		listAssociation.setVisibility(View.VISIBLE);
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

	private double donneDistance(Association a){
		Location locUser = new Location("Point A");
		locUser.setLatitude(latitudeUser);
		locUser.setLongitude(longitudeUser);

		Location loc = new Location("Point B");
		loc.setLatitude(Double.parseDouble(a.getListeEquipement().get(0).getGeoloc().getLatitude()));
		loc.setLongitude(Double.parseDouble(a.getListeEquipement().get(0).getGeoloc().getLongitude()));

		return locUser.distanceTo(loc);
	}
	
	private void ordonnerParDistance(){
		List<Association> listeTemporaire = new ArrayList<Association>();
		for(Association a : lesAssocsDiscipline)
			listeTemporaire.add(a);
		if(listeTemporaire != null && listeTemporaire.size() > 0){
			lesAssocsDiscipline.clear();
			while(listeTemporaire.size() > 0){
				Association valeurTest = listeTemporaire.get(0);
				for(Association a : listeTemporaire){
					if(donneDistance(a) <= donneDistance(valeurTest)){
						valeurTest = a;
					}
				}
				lesAssocsDiscipline.add(valeurTest);
				listeTemporaire.remove(valeurTest);
			}
		}
	}
	
	private void clicItemListAssoc(){
		listAssociation.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Association a = lesAssocsDiscipline.get(position-1);
				Intent intent = new Intent(getActivity(), FragmentAssociationActivity.class);
				intent.putExtra("position", a.getId());
				intent.putExtra("adherents", true);
				intent.putExtra("nonAdherents", false);
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

	
	@Override
	public void onLocationChanged(Location location) {
		latitudeUser = location.getLatitude();
		longitudeUser = location.getLongitude();
		
		if(latitudeUser != 0 || longitudeUser != 0){
			//ordonnancement par distance des associations:
			ordonnerParDistance();
		}
		
		//Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
        DiscGeolocListAssoAdapter mSchedule = new DiscGeolocListAssoAdapter(this.getActivity(), 0, lesAssocsDiscipline, latitudeUser, longitudeUser);
        //On attribut à notre listView l'adapter que l'on vient de créer
        listAssociation.setAdapter(mSchedule);
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

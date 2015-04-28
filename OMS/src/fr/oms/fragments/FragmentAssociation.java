package fr.oms.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.oms.activities.MapPane;
import fr.oms.activities.R;
import fr.oms.metier.Association;
import fr.oms.metier.Equipement;
import fr.oms.metier.Personne;
import fr.oms.modele.Manager;

public class FragmentAssociation extends Fragment {

	private Association association;
	private TextView btnSite;
	private LinearLayout ficheAdherente;
	private LinearLayout ficheNonAdherente;
	private TextView infoAssocNonAdherente;
	private TextView nomAssocPasAdherente;
	private TextView nomAssociation;
	private TextView nomContact;
	private ImageView iconeAdherent;
	private TextView telFixContact;
	private TextView telPortContact;
	private TextView mailContact;
	private TextView horaire;
	private TextView equipement1;
	private TextView equipement2;
	private Button lieu_map_1;
	private Button lieu_map_2;
	private Personne pers;
	
	public static FragmentAssociation newInstance(Association a) {
		Bundle extras = new Bundle();
		extras.putInt("id", a.getId());
		FragmentAssociation fragment = new FragmentAssociation();
		fragment.setArguments(extras);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 View v = inflater.inflate(R.layout.association, container, false);
			for(Association a : Manager.getInstance().getListeAssociation()){
				if(a.getId() == getArguments().getInt("id")){
					association = a;
				}
			}
			recupererToutesViews(v);
			placeDonneeDansView();
			getActivity().setTitle(getResources().getString(R.string.titreDetailAssociation));
	     return v;
	}
	
	private void recupererToutesViews(View v){
		nomAssociation = (TextView)v.findViewById(R.id.nomAssociation);
		nomContact = (TextView)v.findViewById(R.id.nomContact);
		telFixContact = (TextView)v.findViewById(R.id.telFixContact);
		telPortContact = (TextView)v.findViewById(R.id.telPortContact);
		mailContact = (TextView)v.findViewById(R.id.mailContact);
		horaire = (TextView)v.findViewById(R.id.horaire);
		equipement1 = (TextView)v.findViewById(R.id.lieu_equipement1);
		equipement2 = (TextView)v.findViewById(R.id.lieu_equipement2);
		lieu_map_1 = (Button)v.findViewById(R.id.btn_map);
		lieu_map_2 = (Button)v.findViewById(R.id.btn_map_2);
		iconeAdherent = (ImageView)v.findViewById(R.id.iconeAdherentAssociationFiche);
		btnSite = (TextView)v.findViewById(R.id.btn_info_site);
		ficheAdherente = (LinearLayout)v.findViewById(R.id.fiche_assoc);
		ficheNonAdherente = (LinearLayout)v.findViewById(R.id.layout_assoc_pas_adherente);
		nomAssocPasAdherente = (TextView)v.findViewById(R.id.nomAssocPasAdherente);
		infoAssocNonAdherente = (TextView)v.findViewById(R.id.info_assoc_non_adherente);
		onGoSite();
	}
	
	private void changeLayoutSiPasAdherent(){
		if(!association.isAdherent()){
			nomAssociation.setVisibility(0);
			ficheAdherente.setVisibility(4);
			ficheNonAdherente.setVisibility(0);
			infoAssocNonAdherente.setVisibility(0);
			nomAssocPasAdherente.setVisibility(0);
			nomAssocPasAdherente.setText(association.getNom());
		}
	}
	
	private void onMap1(){
		lieu_map_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Equipement equipement = association.getListeEquipement().get(0);
            	Intent intent = new Intent(getActivity(), MapPane.class);
				intent.putExtra("nom", equipement.getNom());
				intent.putExtra("latitude", equipement.getGeoloc().getLatitude());
				intent.putExtra("longitude", equipement.getGeoloc().getLongitude());
				startActivity(intent);
            }
        });
	}
	
	private void onMap2(){
		lieu_map_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Equipement equipement = association.getListeEquipement().get(0);
            	Intent intent = new Intent(getActivity(), MapPane.class);
				intent.putExtra("nom", equipement.getNom());
				intent.putExtra("latitude", equipement.getGeoloc().getLatitude());
				intent.putExtra("longitude", equipement.getGeoloc().getLongitude());
				startActivity(intent);
            }
        });
	}
	
	public void onGoSite(){
		btnSite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String nomAssoc = association.getNom();
				nomAssoc = nomAssoc.replace("Œ", "OE");
				nomAssoc = nomAssoc.replace("AS ", "");
				nomAssoc = nomAssoc.replace(" A ", "-");
				nomAssoc = nomAssoc.replace(" ", "-");
				nomAssoc = nomAssoc.replace(".", "");
				nomAssoc = nomAssoc.replace("(", "");
				nomAssoc = nomAssoc.replace(")", "");
				nomAssoc = nomAssoc.replace("&", "");
				nomAssoc = nomAssoc.replace("/", "");
				nomAssoc = nomAssoc.replace("\"", "");
				nomAssoc = nomAssoc.replace("'", "");
				nomAssoc = nomAssoc.replace("--", "-");
				String url = getResources().getString(R.string.lienSite);
				url = url + "club/" + nomAssoc;
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
            }
        });
	}
	
	private void placeDonneeDansView(){
		nomAssociation.setText(association.getNom());
		pers = association.getContact();
		if(pers != null){
			if(!pers.getNom().equals("")){
				nomContact.setText(pers.getTitre() + " " + pers.getNom() + " " + pers.getPrenom());
			}
			else{
				nomContact.setText("Nom du contact inconnu");
			}
			if(!pers.getTelFixe().equals("")){
				telFixContact.setText("Telephone Fix : " + pers.getTelFixe());
			}
			else{
				telFixContact.setText("Telephone Fix inconnu");
			}
			if(!pers.getTelPortable().equals("")){
				telPortContact.setText("Telephone Portable : " + pers.getTelPortable());
			}
			else{
				telPortContact.setText("Telephone Portable inconnu");
			}
			if(!pers.getEmail().equals("")){
				mailContact.setText(pers.getEmail());
			}
			else{
				mailContact.setText("Adresse email inconnue");
			}
		}
		if(!association.getHorraire().equals("")){
			horaire.setText(association.getHorraire());
		}
		else{
			horaire.setText("Horaires inconnues");
		}
		if(association.getListeEquipement() != null){
			equipement1.setText(association.getListeEquipement().get(0).getNom());
			onMap1();
			if(association.getListeEquipement().size() == 2){
				equipement2.setText(association.getListeEquipement().get(1).getNom());
				onMap2();
			}
			else {
				equipement2.setText("Pas d'equipement secondaire");
				lieu_map_2.setVisibility(4);
			}
		}
		else{
			equipement1.setText("Pas d'equipement principale");
			lieu_map_1.setVisibility(4);
			equipement2.setText("Pas d'equipement secondaire");
			lieu_map_2.setVisibility(4);
		}
		changeLayoutSiPasAdherent();
	}
}

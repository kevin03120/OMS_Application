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
import fr.oms.activities.R;
import fr.oms.metier.Association;
import fr.oms.metier.Personne;
import fr.oms.modele.Manager;

public class FragmentAssociation extends Fragment {

	private Association association;
	private LinearLayout layoutPasAdherente;
	private LinearLayout layoutTextPasAdherente;
	private LinearLayout layoutContact;
	private LinearLayout layoutHoraire;
	private LinearLayout layoutEquipement;
	private Button btnMap;
	private Button btnSite;
	private TextView textPasAdherente;
	private TextView nomAssociation;
	private TextView nomContact;
	private ImageView iconeAdherent;
	private TextView telFixContact;
	private TextView telPortContact;
	private TextView mailContact;
	private TextView horaire;
	private TextView equipement1;
	private TextView equipement2;
	private Personne pers;
	
	public static FragmentAssociation newInstance(Association a) {
		Bundle extras = new Bundle();
		extras.putInt("id", a.getUid());
		FragmentAssociation fragment = new FragmentAssociation();
		fragment.setArguments(extras);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 View v = inflater.inflate(R.layout.association, container, false);
			for(Association a : Manager.getInstance().getListeAssociation()){
				if(a.getUid() == getArguments().getInt("id")){
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
		iconeAdherent = (ImageView)v.findViewById(R.id.iconeAdherentAssociationFiche);
		layoutPasAdherente = (LinearLayout)v.findViewById(R.id.layout_assoc_pas_adherente);
		layoutTextPasAdherente = (LinearLayout)v.findViewById(R.id.layout_text_pas_adherente);
		textPasAdherente = (TextView)v.findViewById(R.id.info_assoc_non_adherente);
		layoutContact = (LinearLayout)v.findViewById(R.id.contact_fiche_assoc);
		layoutHoraire = (LinearLayout)v.findViewById(R.id.horaire_fiche_assoc);
		layoutEquipement = (LinearLayout)v.findViewById(R.id.equipement_fiche_assoc);
		btnMap = (Button)v.findViewById(R.id.btn_map);
		btnSite = (Button)v.findViewById(R.id.btn_info_site);
		onGoSite();
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
			nomContact.setText(pers.getTitre() + " " + pers.getNom() + " " + pers.getPrenom());
			telFixContact.setText("Tel Fix : " + pers.getTelFixe());
			telPortContact.setText("Tel Port : " + pers.getTelPortable());
			mailContact.setText(pers.getEmail());
		}
		else{
			nomContact.setText(getResources().getString(R.string.contactNonDispo));
		}
		horaire.setText(association.getHorraire());
		if(association.getListeEquipement()!=null){
			if(association.getListeEquipement().size() >=2){	
				equipement1.setText(association.getListeEquipement().get(0).getNom());
				equipement2.setText(association.getListeEquipement().get(1).getNom());
			}
			else if(association.getListeEquipement().size() == 1){
				equipement1.setText(association.getListeEquipement().get(0).getNom());
				equipement2.setVisibility(4);
			}			
		}else{
			equipement1.setText("Aucun equipement connu");
			equipement2.setVisibility(4);
		}
		if(!association.isAdherent()){
			iconeAdherent.setVisibility(4);
			layoutPasAdherente.setVisibility(0);
			layoutTextPasAdherente.setVisibility(0);
			textPasAdherente.setVisibility(0);
			layoutContact.setVisibility(4);
			layoutEquipement.setVisibility(4);
			layoutHoraire.setVisibility(4);
			btnMap.setVisibility(4);
			btnSite.setVisibility(4);
		}
	}
}

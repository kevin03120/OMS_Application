package fr.oms.fragments;

import java.util.ArrayList;
import java.util.List;

import fr.oms.activities.FragmentAssociationActivity;
import fr.oms.activities.FragmentEquipementActivity;
import fr.oms.activities.MapPane;
import fr.oms.activities.R;
import fr.oms.adapter.AssociationAdapter;
import fr.oms.metier.Association;
import fr.oms.metier.Equipement;
import fr.oms.modele.Manager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentEquipement extends Fragment {

	private static final String GEOLOCNULL = "0.00000";
	private Equipement equipement;
	private ListView listAssociation;
	private TextView txtPasAssoc;
	private List<Association> lesAssocsEquipement;
	private ImageView arrow_left;
	private TextView txtTel;
	private ImageView arrow_right;

	public static FragmentEquipement newInstance(Equipement e) {
		Bundle extras = new Bundle();
		extras.putInt("id", e.getUid());
		FragmentEquipement fragment = new FragmentEquipement();
		fragment.setArguments(extras);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v;
		for(Equipement e : Manager.getInstance().getListeEquipement()){
			if(e.getUid() == getArguments().getInt("id")){
				equipement = e;
			}
		}
		if(compteListAssociation() != 0){
			v = inflater.inflate(R.layout.detail_equipement, container, false);
			View header = getLayoutInflater(getArguments()).inflate(R.layout.header_equipement, null);
			recupererToutesViews(header);
			txtPasAssoc.setVisibility(View.GONE);
			listAssociation = (ListView)v.findViewById(R.id.listAssocEquipement);
			listAssociation.addHeaderView(header);
			adapterPourListAssociation();
			clicItemListAssoc();
			getActivity().setTitle(getResources().getString(R.string.titreDetailEquipement));
		}
		else{
			//			v = inflater.inflate(R.layout.header_equipement, container, false);
			//			recupererToutesViews(v);
			v = inflater.inflate(R.layout.detail_equipement, container, false);
			View header = getLayoutInflater(getArguments()).inflate(R.layout.header_equipement, null);
			recupererToutesViews(header);
			txtPasAssoc.setVisibility(View.VISIBLE);
			listAssociation = (ListView)v.findViewById(R.id.listAssocEquipement);
			listAssociation.addHeaderView(header);
			adapterPourListAssociation();
			getActivity().setTitle(getResources().getString(R.string.titreDetailEquipement));
		}
		clicFlecheLeft();
		clicFlecheRight();
		return v;
	}

	public void clicFlecheLeft(){
		arrow_left.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				List<Equipement> equips = Manager.getInstance().getListeEquipement();
				ViewPager pager = FragmentEquipementActivity.fragmentEquipementActivity.getPager();
				if(equips.indexOf(equipement) != 0){
					pager.setCurrentItem(pager.getCurrentItem() - 1);
				}
				else{
					pager.setCurrentItem(equips.size() - 1);
				}
			}
		});
	}

	public void clicFlecheRight(){
		arrow_right.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				List<Equipement> equips = Manager.getInstance().getListeEquipement();
				ViewPager pager = FragmentEquipementActivity.fragmentEquipementActivity.getPager();
				if(equips.indexOf(equipement) != equips.size() - 1){
					pager.setCurrentItem(pager.getCurrentItem() + 1);
				}
				else{
					pager.setCurrentItem(0);
				}
			}
		});
	}

	private void adapterPourListAssociation(){
		lesAssocsEquipement = new ArrayList<Association>();
		for(Association a : Manager.getInstance().getListeAssociation()){
			if(a.getListeEquipement()!=null){
				for(Equipement e : a.getListeEquipement()){
					if(e.equals(equipement)){
						lesAssocsEquipement.add(a);
					}
				}
				AssociationAdapter adapterAssoc = new AssociationAdapter(getActivity(), 0, lesAssocsEquipement);
				listAssociation.setAdapter(adapterAssoc);
				listAssociation.setVisibility(0);
			}
		}
	}

	private int compteListAssociation(){
		lesAssocsEquipement = new ArrayList<Association>();
		for(Association a : Manager.getInstance().getListeAssociation()){
			if(a.getListeEquipement()!=null){
				for(Equipement e : a.getListeEquipement()){
					if(e.equals(equipement)){
						lesAssocsEquipement.add(a);
					}
				}
			}
		}
		return lesAssocsEquipement.size();
	}

	private void clicItemListAssoc(){
		listAssociation.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Association a = lesAssocsEquipement.get(position-1);
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

	private void recupererToutesViews(View v){
		TextView txtTitreEquipement = (TextView)v.findViewById(R.id.nomEquipement);
		txtTitreEquipement.setText(equipement.getNom());
		txtTel = (TextView)v.findViewById(R.id.telFixEquipement);
		if(!equipement.getTelephone().equals("")){
			txtTel.setText(equipement.getTelephone());
		}
		else{
			//txtTel.setText("Aucun numero de telephone affilié");
			txtTel.setVisibility(4);
		}
		TextView txtAdresse = (TextView)v.findViewById(R.id.adresse);
		if(!equipement.getAdresse().equals("")){
			txtAdresse.setText("Adresse : " + equipement.getAdresse());
		}
		else{
			//txtAdresse.setText("Aucune adresse affiliée");
			txtAdresse.setVisibility(4);
		}
		TextView txtCodePostal = (TextView)v.findViewById(R.id.codePostal);
		if(!equipement.getCodePostal().equals("")){
			txtCodePostal.setText("Code Postal : " + equipement.getCodePostal());
		}
		else{
			//txtCodePostal.setText("Aucun code postal affilié");
			txtCodePostal.setVisibility(4);
		}
		TextView txtVille = (TextView)v.findViewById(R.id.ville);
		if(!equipement.getVille().equals("")){
			txtVille.setText("Ville : " + equipement.getVille());
		}
		else{
			//txtVille.setText("Aucune ville affiliée");
			txtVille.setVisibility(4);
		}
		TextView txtQuartier = (TextView)v.findViewById(R.id.quartier);
		if(equipement.getQuartier() != null){
			txtQuartier.setText("Quartier : " + equipement.getQuartier().getNom());
		}
		else{
			//txtQuartier.setText("Aucun quartier affilié à cet équipement");
			txtQuartier.setVisibility(4);
		}
		Button btnGoMap = (Button)v.findViewById(R.id.btn_map);
		if((equipement.getGeoloc().getLatitude().equals(GEOLOCNULL))&&(equipement.getGeoloc().getLatitude().equals(GEOLOCNULL))){
			btnGoMap.setVisibility(4);
		}
		txtPasAssoc = (TextView)v.findViewById(R.id.txtPasAssoc);
		arrow_left = (ImageView)v.findViewById(R.id.arrow_l_equip);
		arrow_right = (ImageView)v.findViewById(R.id.arrow_r_equip);
		goMap(btnGoMap);
		clicTel();
	}

	private void clicTel(){
		txtTel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
				alertDialogBuilder.setTitle("Passer un appel");
				alertDialogBuilder
				.setMessage("Appeler " + equipement.getNom() + " au " + equipement.getTelephone() + " ?")
				.setPositiveButton("Appeler",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						Intent callIntent = new Intent(Intent.ACTION_CALL);
						callIntent.setData(Uri.parse("tel:" + equipement.getTelephone()));
						startActivity(callIntent);
					}
				})
				.setNegativeButton("Annuler",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.dismiss();
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
	}

	private void goMap(Button b){
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MapPane.class);
				intent.putExtra("nom", equipement.getNom());
				intent.putExtra("latitude", equipement.getGeoloc().getLatitude());
				intent.putExtra("longitude", equipement.getGeoloc().getLongitude());
				startActivity(intent);
			}
		});
	}
}

package fr.oms.fragments;

import fr.oms.activities.R;
import fr.oms.metier.Equipement;
import fr.oms.modele.Manager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentEquipement extends Fragment {

	private Equipement equipement;
	
	public static FragmentEquipement newInstance(Equipement e) {
		Bundle extras = new Bundle();
		extras.putInt("id", e.getUid());
		FragmentEquipement fragment = new FragmentEquipement();
		fragment.setArguments(extras);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 View v = inflater.inflate(R.layout.detail_equipement, container, false);
			for(Equipement e : Manager.getInstance().getListeEquipement()){
				if(e.getUid() == getArguments().getInt("id")){
					equipement = e;
				}
			}
			recupererToutesViews(v);
			getActivity().setTitle(getResources().getString(R.string.titreDetailEquipement));
	     return v;
	}
	
	private void recupererToutesViews(View v){
		TextView txtTitreEquipement = (TextView)v.findViewById(R.id.nomEquipement);
		txtTitreEquipement.setText(equipement.getNom());
		TextView txtTel = (TextView)v.findViewById(R.id.telFixEquipement);
		txtTel.setText("Tel : " + equipement.getTelephone());
		TextView txtAdresse = (TextView)v.findViewById(R.id.adresse);
		txtAdresse.setText(equipement.getAdresse());
		TextView txtCodePostal = (TextView)v.findViewById(R.id.codePostal);
		txtCodePostal.setText(equipement.getCodePostal());
		TextView txtVille = (TextView)v.findViewById(R.id.ville);
		txtVille.setText(equipement.getVille());
		TextView txtQuartier = (TextView)v.findViewById(R.id.quartier);
		txtQuartier.setText("Quartier : " + equipement.getQuartier().getNom());
		Button btnGoMap = (Button)v.findViewById(R.id.btn_map);
		goMap(btnGoMap);
	}
	
	private void goMap(Button b){
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), equipement.getGeoloc().getLatitude() + " : " + equipement.getGeoloc().getLongitude(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}

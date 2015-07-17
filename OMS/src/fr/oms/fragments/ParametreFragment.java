package fr.oms.fragments;

import fr.oms.activities.Activity_Chargement;
import fr.oms.activities.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ParametreFragment extends Fragment {

	private CheckBox box;
	private Button effectuerMaj;
	private TextView redem;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_parametres, container,false);
		final SharedPreferences prefs = getActivity().getSharedPreferences(
				"fr.oms.activities", Context.MODE_PRIVATE);
		
		effectuerMaj=(Button) v.findViewById(R.id.effectuer_maj);
		redem=(TextView) v.findViewById(R.id.redemar);
		box=(CheckBox) v.findViewById(R.id.chek_maj);
		box.setChecked(prefs.getBoolean("MAJ", true));
		if(!prefs.getBoolean("MAJ", true)){
			effectuerMaj.setVisibility(View.VISIBLE);
			redem.setVisibility(View.VISIBLE);
		}else{
			effectuerMaj.setVisibility(View.GONE);
			redem.setVisibility(View.GONE);
		}
		box.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {				

				
				prefs.edit().putBoolean("MAJ", isChecked).apply();
				if(!isChecked){
					effectuerMaj.setVisibility(View.VISIBLE);
					redem.setVisibility(View.VISIBLE);
				}else{
					effectuerMaj.setVisibility(View.GONE);
					redem.setVisibility(View.GONE);
				}
			}
		});
		clicEffectueMAJ();
		return v;
	}
	
	private void clicEffectueMAJ(){
		effectuerMaj.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ParametreFragment.this.getActivity(), Activity_Chargement.class);
				i.putExtra("MAJ", true);
				startActivity(i);
				getActivity().finish();
			}
		});
	}
	
}

package fr.oms.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import fr.oms.activities.R;

public class AccueilFragment extends Fragment {

	private Button btnAgenda;
	private Button btnActu;
	private Button btnGeolocalisation;
	private Button btnTuto;
	private RelativeLayout topLevelLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.accueil, container, false);
		topLevelLayout=(RelativeLayout) v.findViewById(R.id.top_layout);
		btnAgenda = (Button)v.findViewById(R.id.annuaire);
		btnActu = (Button)v.findViewById(R.id.actualite);
		btnGeolocalisation = (Button)v.findViewById(R.id.maPosition);
		btnTuto=(Button) v.findViewById(R.id.buttontuto);
		clicBtnAnnuaire();
		clicBtnActu();
		clicBtnGeolocalisation();
//		  if (isFirstTime()) {
//	        	topLevelLayout.setVisibility(View.INVISIBLE);
//	        }
		return v;
	}

	private void clicBtnAnnuaire(){
		btnAgenda.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AnnuaireFragment fragment = new AnnuaireFragment(0);
				FragmentManager fragM = getActivity().getSupportFragmentManager();
				FragmentTransaction fragT = fragM.beginTransaction();
				fragT.replace(R.id.container, fragment).commit();
				getActivity().getActionBar().setBackgroundDrawable(
						new ColorDrawable((getResources().getColor(R.color.VertOms))));
			}
		});
	}


	private void clicBtnActu(){
		btnActu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AgendaFragment fragment = new AgendaFragment(0);
				FragmentManager fragM = getActivity().getSupportFragmentManager();
				FragmentTransaction fragT = fragM.beginTransaction();
				fragT.replace(R.id.container, fragment).commit();
				getActivity().getActionBar().setBackgroundDrawable(
						new ColorDrawable((getResources().getColor(R.color.BleuOms))));
			}
		});
	}


	private void clicBtnGeolocalisation(){
		btnGeolocalisation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GeolocalisationFragment fragment = new GeolocalisationFragment(0);
				FragmentManager fragM = getActivity().getSupportFragmentManager();
				FragmentTransaction fragT = fragM.beginTransaction();
				fragT.replace(R.id.container, fragment).commit();
				getActivity().getActionBar().setBackgroundDrawable(
						new ColorDrawable((getResources().getColor(R.color.OrangeOms))));
			}
		});
	}

	private boolean isFirstTime()
	{
		SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
		boolean ranBefore = preferences.getBoolean("RanBefore", false);
		if (!ranBefore) {

			SharedPreferences.Editor editor = preferences.edit();
			editor.putBoolean("RanBefore", true);
			editor.commit();
			topLevelLayout.setVisibility(View.VISIBLE);
			btnTuto.setOnTouchListener(new View.OnTouchListener(){

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					topLevelLayout.setVisibility(View.INVISIBLE);
					return false;
				}

			});


		}
		return ranBefore;

	}

}

package fr.oms.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import fr.oms.activities.R;

public class AccueilFragment extends Fragment {

	private Button btnAgenda;
	private Button btnActu;
	private Button btnGeolocalisation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.accueil, container, false);
		return v;
	}
}

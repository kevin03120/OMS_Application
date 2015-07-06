package fr.oms.fragments;


import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import fr.oms.activities.FragmentActuActivity;
import fr.oms.activities.R;
import fr.oms.adapter.ActualiteAdapter;
import fr.oms.metier.Actualite;
import fr.oms.modele.Manager;

public class FragmentActus extends Fragment{

	private ListView listActualite;
	private EditText editRechercher;
	private TextView failRecherche;
	private ActualiteAdapter actuAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_layout, container, false);
		listActualite = (ListView)v.findViewById(R.id.listActualiteEvenement);
		editRechercher = (EditText)v.findViewById(R.id.rechercheActuEvent);
		failRecherche = (TextView) v.findViewById(R.id.failRecherche);
		actuAdapter = new ActualiteAdapter(getActivity(), 0, Manager.getInstance().getListActualites());
		listActualite.setAdapter(actuAdapter);
		touchActu();
		ajouterRecherche();

		editRechercher.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		});
		return v;
	}

	private void ajouterRecherche() {

		editRechercher.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				ArrayList<Actualite> listeRecherche=new ArrayList<Actualite>();

				for(Actualite a : Manager.getInstance().getListActualites()){
					if(a.getTitre().toLowerCase(Locale.FRENCH).contains(s)){						
						listeRecherche.add(a);
						actuAdapter = new ActualiteAdapter(getActivity(), 0, listeRecherche);
						actuAdapter.notifyDataSetChanged();
						listActualite.setVisibility(View.VISIBLE);
						failRecherche.setVisibility(View.GONE);
						listActualite.setAdapter(actuAdapter);
						touchActuFiltre(listeRecherche);
					}
				}
				if (listeRecherche.size()==0) {
					listActualite.setVisibility(View.GONE);
					failRecherche.setVisibility(View.VISIBLE);
					failRecherche.setText("Aucun résulat trouvé");
				}


			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if(s.length()==0){
					System.out.println("il est passé par ici");
					actuAdapter = new ActualiteAdapter(getActivity(), 0, Manager.getInstance().getListActualites());
					actuAdapter.notifyDataSetChanged();
					listActualite.setAdapter(actuAdapter);
					touchActu();
				}

			}
		});

	}

	private void touchActuFiltre(final ArrayList<Actualite> liste){
		listActualite.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Actualite actu = liste.get(position);
				Intent intent = new Intent(getActivity(), FragmentActuActivity.class);
				intent.putExtra("position", actu.getId());
				startActivity(intent);
			}
		}); 
	}

	private void touchActu(){
		listActualite.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Actualite actu = Manager.getInstance().getListActualites().get(position);
				Intent intent = new Intent(getActivity(), FragmentActuActivity.class);
				intent.putExtra("position", actu.getId());
				startActivity(intent);
			}
		}); 
	}

}

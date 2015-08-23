package fr.oms.fragments;


import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

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
import fr.oms.activities.FragmentEquipementActivity;
import fr.oms.activities.R;
import fr.oms.adapter.EquipementAdapter;
import fr.oms.metier.Equipement;
import fr.oms.modele.Manager;

public class FragmentListeEquipements extends Fragment{

	private ListView listEquipement;
	private EditText editRechercher;
	private TextView failRecherche;
	private EquipementAdapter equipementAdapter;;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		equipementAdapter = new EquipementAdapter(getActivity(), 0, Manager.getInstance().getListeEquipement());
		View v = inflater.inflate(R.layout.list_equipement, container,false);
		listEquipement = (ListView)v.findViewById(R.id.listeEquipement);
		editRechercher = (EditText) v.findViewById(R.id.rechercheEquip);
		failRecherche = (TextView) v.findViewById(R.id.failRecherche);
		listEquipement.setAdapter(equipementAdapter);
		touchEquipement(listEquipement);
		ajouterRecherche();
		ajouterFocusChangedListener();
		return v;
	}

	private void ajouterFocusChangedListener() {
		editRechercher.setOnFocusChangeListener(new OnFocusChangeListener() {          
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		});
	}

	private void touchEquipement(ListView list){
		list.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), FragmentEquipementActivity.class);
				Equipement equipement = Manager.getInstance().getListeEquipement().get(position);
				intent.putExtra("position", equipement.getUid());
				startActivity(intent);
			}
		}); 
	}

	public static String sansAccent(String s) 
	{

		String strTemp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(strTemp).replaceAll("");
	}

	private void ajouterRecherche() {
		editRechercher.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				ArrayList<Equipement> listeRecherche=new ArrayList<Equipement>();
				s=sansAccent(s.toString());
				for(Equipement e : Manager.getInstance().getListeEquipement()){
					if(e.getNom().toLowerCase(Locale.FRENCH).contains(s.toString().toLowerCase(Locale.FRENCH))){						
						listeRecherche.add(e);
					}
				}
				equipementAdapter = new EquipementAdapter(getActivity(), 0, listeRecherche);
				equipementAdapter.notifyDataSetChanged();
				listEquipement.setVisibility(View.VISIBLE);
				failRecherche.setVisibility(View.GONE);
				listEquipement.setAdapter(equipementAdapter);
				touchEquipementFiltre(listeRecherche);

				if (listeRecherche.size()==0) {
					listEquipement.setVisibility(View.GONE);
					failRecherche.setVisibility(View.VISIBLE);
					failRecherche.setText("Aucun résulat trouvé");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				if(s.length()==0){
					System.out.println("il est passé par ici");
					EquipementAdapter equipAdapter= new EquipementAdapter(getActivity(), 0, Manager.getInstance().getListeEquipement());
					equipAdapter.notifyDataSetChanged();
					listEquipement.setAdapter(equipAdapter);
					touchEquipement(listEquipement);
				}
			}
		});
	}

	private void touchEquipementFiltre(final ArrayList<Equipement> liste){
		listEquipement.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Equipement equip = liste.get(position);
				Intent intent = new Intent(getActivity(), FragmentEquipementActivity.class);
				intent.putExtra("position", equip.getUid());
				startActivity(intent);
			}
		}); 
	}

}

package fr.oms.fragments;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import fr.oms.activities.FragmentAssociationActivity;
import fr.oms.activities.R;
import fr.oms.adapter.AssociationAdapter;
import fr.oms.metier.Association;
import fr.oms.metier.Sport;
import fr.oms.modele.Manager;

public class FragmentListeAssociations extends Fragment {

	private ListView listeAssociation;
	private int filtre;
	private List<Association> mesAssoc;
	private boolean isFiltreSport = false;
	private List<Association> mesAssocFiltreSport;
	private String nomSport = "";
	private RelativeLayout layoutFiltre;
	private TextView txtFiltre;
	private EditText editRechercher;
	private TextView failRecherche;
	private ImageView btn_supp_filtre;
	private AssociationAdapter associationAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.list_association, container,false);
		btn_supp_filtre = (ImageView) v.findViewById(R.id.imageSuppFiltre);
		layoutFiltre = (RelativeLayout) v.findViewById(R.id.filtre);
		txtFiltre = (TextView)v.findViewById(R.id.txt_filtre);
		listeAssociation = (ListView)v.findViewById(R.id.listeAssociation);
		editRechercher = (EditText) v.findViewById(R.id.rechercheAssoc);
		failRecherche = (TextView) v.findViewById(R.id.failRecherche);
		testerScrollingVersion();
		onDeleteFiltre();
		filtre = 1;
		if(getArguments()!=null){
			isFiltreSport = true;
			nomSport = getArguments().getString("nomSport");
			filtre = 5;
		}
		mesAssoc = rendNouvelleListe();
		mesAssocFiltreSport = new ArrayList<Association>();
		ajouterFiltre();
		ajouterRecherche();
		ajouterFocusChangerListener();
		return v;
	}

	private void ajouterFocusChangerListener() {
		editRechercher.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		});
	}

	private void testerScrollingVersion() {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion <= android.os.Build.VERSION_CODES.FROYO){
			listeAssociation.setFastScrollEnabled(true);
		} else if(currentapiVersion > android.os.Build.VERSION_CODES.HONEYCOMB){
			listeAssociation.setFastScrollEnabled(true);
			listeAssociation.setFastScrollAlwaysVisible(true);
		}
	}

	private void ajouterRecherche() {
		editRechercher.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				ArrayList<Association> listeRecherche=new ArrayList<Association>();
				if(isFiltreSport){
					for(Association a : mesAssocFiltreSport){
						if(a.getNom().toLowerCase(Locale.FRENCH).contains(s)){						
							listeRecherche.add(a);
							associationAdapter = new AssociationAdapter(getActivity(), 0, listeRecherche);
							associationAdapter.notifyDataSetChanged();
							listeAssociation.setVisibility(View.VISIBLE);
							failRecherche.setVisibility(View.GONE);
							listeAssociation.setAdapter(associationAdapter);
							touchAssocFiltre(listeRecherche);
						}
					}
					if (listeRecherche.size()==0) {
						listeAssociation.setVisibility(View.GONE);
						failRecherche.setVisibility(View.VISIBLE);
						failRecherche.setText("Aucun résulat trouvé");
					}
				}else{
					for(Association a : Manager.getInstance().getListeAssociation()){
						if(a.getNom().toLowerCase(Locale.FRENCH).contains(s)){						
							listeRecherche.add(a);
							associationAdapter = new AssociationAdapter(getActivity(), 0, listeRecherche);
							associationAdapter.notifyDataSetChanged();
							listeAssociation.setVisibility(View.VISIBLE);
							failRecherche.setVisibility(View.GONE);
							listeAssociation.setAdapter(associationAdapter);
							touchAssocFiltre(listeRecherche);
						}
					}
					if (listeRecherche.size()==0) {
						listeAssociation.setVisibility(View.GONE);
						failRecherche.setVisibility(View.VISIBLE);
						failRecherche.setText("Aucun résulat trouvé");
					}
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
					associationAdapter = new AssociationAdapter(getActivity(), 0, rendNouvelleListe());
					associationAdapter.notifyDataSetChanged();
					listeAssociation.setAdapter(associationAdapter);
					touchAssoc();
				}
			}
		});

	}

	private void touchAssocFiltre(final ArrayList<Association> liste){
		listeAssociation.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Association assoc = liste.get(position);
				Intent intent = new Intent(FragmentListeAssociations.this.getActivity(), FragmentAssociationActivity.class);
				intent.putExtra("position", assoc.getId());
				intent.putExtra("adherents", true);
				intent.putExtra("nonAdherents", true);
				intent.putExtra("sport", isFiltreSport);
				intent.putExtra("nomSport", nomSport);
				startActivity(intent);
			}
		}); 
	}



	private void ajouterFiltre() {
		if(isFiltreSport){
			for(Association a : rendNouvelleListe()){
				for(Sport s : a.getListeSport()){
					txtFiltre.setText("FILTRE : " + nomSport);
					if(s.getNom().equals(nomSport)){
						layoutFiltre.setVisibility(View.VISIBLE);
						mesAssocFiltreSport.add(a);
					}
				}
			}
			if(mesAssocFiltreSport.size()==0){
				layoutFiltre.setVisibility(View.VISIBLE);
				listeAssociation.setVisibility(View.GONE);
				failRecherche.setVisibility(View.VISIBLE);
				failRecherche.setText("Aucun résulat trouvé");
			}
			associationAdapter = new AssociationAdapter(getActivity(), 0, mesAssocFiltreSport);
			associationAdapter.notifyDataSetChanged();
			listeAssociation.setAdapter(associationAdapter);
			touchAssoc();
		}
		else{
			associationAdapter = new AssociationAdapter(getActivity(), 0,rendNouvelleListe());
			associationAdapter.notifyDataSetChanged();
			listeAssociation.setAdapter(associationAdapter);
			touchAssoc();
		}
	}

	private void touchAssoc(){
		listeAssociation.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				List<Association> mesAssociationsAdherentes = new ArrayList<Association>();
				List<Association> mesAssociationsAdherentesFiltresSport = new ArrayList<Association>();
				for(Association a : Manager.getInstance().getListeAssociation()){
					if(a.isAdherent()){
						mesAssociationsAdherentes.add(a);
						if(mesAssocFiltreSport.contains(a)){
							mesAssociationsAdherentesFiltresSport.add(a);
						}
					}
				}
				Association assoc = null;
				switch(FragmentListeAssociations.this.filtre){
				case 0 : assoc = Manager.getInstance().getListeAssociation().get(position); break;
				case 1 : assoc = mesAssociationsAdherentes.get(position); break;
				case 3 : assoc = mesAssocFiltreSport.get(position); break;
				case 5 : assoc = mesAssociationsAdherentesFiltresSport.get(position); break;
				}
				Intent intent = new Intent(FragmentListeAssociations.this.getActivity(), FragmentAssociationActivity.class);
				intent.putExtra("position", assoc.getId());
				intent.putExtra("adherents", true);
				intent.putExtra("nonAdherents", false);
				intent.putExtra("sport", isFiltreSport);
				intent.putExtra("nomSport", nomSport);
				startActivity(intent);
			}
		}); 
	}

	public void onDeleteFiltre(){
		btn_supp_filtre.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isFiltreSport = false;
				afficheListe();
				listeAssociation.setVisibility(View.VISIBLE);
				failRecherche.setVisibility(View.GONE);
				layoutFiltre.setVisibility(View.GONE);
			}
		});
	}



	private void afficheListe(){
		if(!isFiltreSport){
			filtre = 1;
		}
		else{
			filtre = 5;
		}
		mesAssoc = rendNouvelleListe();
		rentreAssociationDansListeSelonFiltre(mesAssoc);
		associationAdapter.notifyDataSetChanged();
		associationAdapter = new AssociationAdapter(this.getActivity(), 0, mesAssoc);
		associationAdapter.notifyDataSetChanged();
		listeAssociation.setAdapter(associationAdapter);
	}

	private List<Association> rendNouvelleListe(){
		List<Association> assocs = new ArrayList<Association>();
		for(Association a : Manager.getInstance().getListeAssociation()){
			if(a.isAdherent()){
				assocs.add(a);
			}
		}
		return assocs;
	}

	private void rentreAssociationDansListeSelonFiltre(List<Association> mesAssocs){
		boolean sportExist = false;
		switch(filtre){
		case 1 : for(Association a : Manager.getInstance().getListeAssociation()){
			if(!a.isAdherent()){
				mesAssocs.remove(a);
			}
		}
		break;
		case 5 : for(Association a : Manager.getInstance().getListeAssociation()){
			if(!a.isAdherent()){
				mesAssocs.remove(a);
			}
			if(mesAssocs.contains(a)){
				for(Sport s : a.getListeSport()){
					if(s.getNom().equals(nomSport)){
						sportExist = true;
					}
				}
				if(!sportExist){
					mesAssocs.remove(a);
				}
				sportExist = false;
			}
		}
		break;
		}
	}
}

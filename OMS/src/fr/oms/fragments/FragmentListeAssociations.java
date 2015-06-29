package fr.oms.fragments;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
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
	private TextView txtFiltre;
	private AssociationAdapter associationAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.list_association, container,false);
		txtFiltre = (TextView)v.findViewById(R.id.txt_filtre);
		listeAssociation = (ListView)v.findViewById(R.id.listeAssociation);
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion <= android.os.Build.VERSION_CODES.FROYO){
            // Do something for froyo and above versions
        	listeAssociation.setFastScrollEnabled(true);


        } else if(currentapiVersion > android.os.Build.VERSION_CODES.HONEYCOMB){
            // do something for phones running an SDK before froyo
        	listeAssociation.setFastScrollEnabled(true);
        	listeAssociation.setFastScrollAlwaysVisible(true);
        }
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
		return v;
	}



	private void ajouterFiltre() {
		if(isFiltreSport){
			for(Association a : rendNouvelleListe()){
				for(Sport s : a.getListeSport()){
					if(s.getNom().equals(nomSport)){
						txtFiltre.setText("Filtre " + s.getNom() + " (Cliquez ici pour le supprimer)");
						txtFiltre.setVisibility(0);
						mesAssocFiltreSport.add(a);
					}
				}
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
		txtFiltre.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				isFiltreSport = false;
				afficheListe();
				txtFiltre.setVisibility(4);
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

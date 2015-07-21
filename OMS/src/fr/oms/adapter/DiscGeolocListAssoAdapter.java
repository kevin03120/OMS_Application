package fr.oms.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;
import fr.oms.activities.R;
import fr.oms.metier.Association;
import fr.oms.metier.Discipline;
import fr.oms.metier.Equipement;

public class DiscGeolocListAssoAdapter extends ArrayAdapter<Association>  implements SectionIndexer {

	HashMap<String, Integer> alphaIndexer;  
	String[] sections;
	private double latitudeUser;
	private double longitudeUser;
	private TextView distance;

	/**
	 * Constructeur permettant de créer les sections pour la scrollBar
	 * @param context
	 * @param resource
	 * @param items
	 */
	public DiscGeolocListAssoAdapter(Context context, int resource, List<Association> items, double uneLatitude, double uneLongitude) {
		super(context, resource, items);
		latitudeUser = uneLatitude;
		longitudeUser = uneLongitude;
		alphaIndexer = new HashMap<String, Integer>();  
		int size = items.size();  
		for (int x = 0; x < size; x++) {
			String s = items.get(x).getNom().trim();
			String ch = s.substring(0, 1);
			ch = ch.toUpperCase(Locale.FRANCE);
			if (!alphaIndexer.containsKey(ch)&& !ch.equals(" "))
				alphaIndexer.put(ch, x);
		}  
		Set<String> sectionLetters = alphaIndexer.keySet();
        ArrayList<String> sectionList = new ArrayList<String>( sectionLetters);
        Collections.sort(sectionList);
        sections = new String[sectionList.size()];
        sectionList.toArray(sections);
	}
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_disc_liste_assoc, parent,false);
		Association association = getItem(position);
		ImageView logoAdherent = (ImageView)convertView.findViewById(R.id.logoAsso);
		TextView nomAssociation = (TextView)convertView.findViewById(R.id.nomAssoc);
		nomAssociation.setText(association.getNom());
		distance = (TextView)convertView.findViewById(R.id.distance);
		distance.setText(getDistance(association));
		//On affiche la distance seulement si au moins l'une des coordonnées n'est pas à 0 
		//Si les 2 sont à 0, c'est que la géolocalisation ne s'est pas encore lancée
		if(latitudeUser != 0 || longitudeUser != 0){
			distance.setVisibility(View.VISIBLE);
		}
		else{
			distance.setVisibility(View.GONE);
		}
		if(association.isAdherent()){
			logoAdherent.setImageResource(R.drawable.logo_ad);
			logoAdherent.setVisibility(0);
		}
		LinearLayout item = (LinearLayout)convertView.findViewById(R.id.background_item);
		if (position % 2 == 0) {
			item.setBackgroundResource(R.drawable.customborder);
		}
		else{
			item.setBackgroundResource(R.drawable.customborder_alt);
		}
		return convertView;
	}
	
	private String getDistance(Association a) {
		
		String s = "";
		double distance;
		
		//calcul de la distance:
		Location locUser = new Location("Point A");
		locUser.setLatitude(latitudeUser);
		locUser.setLongitude(longitudeUser);
		Location loc = new Location("Point B");
		if(a.getListeEquipement() != null && a.getListeEquipement().get(0) != null){
			Equipement e = a.getListeEquipement().get(0);
			if(e.getGeoloc() != null){
				loc.setLatitude(Double.parseDouble(e.getGeoloc().getLatitude()));
				loc.setLongitude(Double.parseDouble(e.getGeoloc().getLongitude()));
				distance = locUser.distanceTo(loc);
				distance = Math.round(distance*1000)/1000;
			}
			else {
				distance = 0;
			}
		}
		else{
			distance = 0;
		}
		
		if(distance > 999){
			s = String.valueOf(distance/1000.0) + " km";
		}
		else{
			s = String.valueOf(distance) + " m";
		}
		return s;
	}
	
	@Override
	public Object[] getSections() {
		return sections;
	}

	@Override
	public int getPositionForSection(int section) {
		return alphaIndexer.get(sections[section]);
	}

	@Override
	public int getSectionForPosition(int position) {
		 for(int i = sections.length - 1; i >= 0; i--) {
	            if(position > alphaIndexer.get(sections[i]))
	                return i;
	        }
	        return 0;

	}
	
}

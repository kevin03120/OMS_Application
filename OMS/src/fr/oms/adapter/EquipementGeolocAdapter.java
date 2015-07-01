package fr.oms.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.oms.activities.R;
import fr.oms.metier.Equipement;

public class EquipementGeolocAdapter extends ArrayAdapter<Equipement> {

	private double latitudeUser;
	private double longitudeUser;
	
	public EquipementGeolocAdapter(Context context, int resource, List<Equipement> objects, double uneLatitude, double uneLongitude) {
		super(context, resource, objects);
		latitudeUser = uneLatitude;
		longitudeUser = uneLongitude;
	}
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_equipement_geoloc, parent,false);
		Equipement equipement = getItem(position);
		TextView nomEquipement = (TextView)convertView.findViewById(R.id.nom_element_geoloc_equipement);
		nomEquipement.setText(equipement.getNom());
		LinearLayout item = (LinearLayout)convertView.findViewById(R.id.background_item_geoloc_equipement);
		if (position % 2 == 0) {
			item.setBackgroundResource(R.drawable.customborder);
		}
		else{
			item.setBackgroundResource(R.drawable.customborder_orange);
		}
		double distance = donneDistanceAvecEquipement(equipement);
		int distanceArrondie = (int) Math.round(distance);
		
		TextView txtDistance = (TextView)convertView.findViewById(R.id.distance_equipement);
		if(distance!=0.0){
			txtDistance.setText(""+distanceArrondie + " m");
		}
		else{
			txtDistance.setVisibility(0);
		}
		ImageView logoNature = (ImageView)convertView.findViewById(R.id.Logo_geoloc_equipement);		

		 String nature = equipement.getNature();
		 chargeImage(nature, logoNature);
		return convertView;
	}

	private double donneDistanceAvecEquipement(Equipement e){
		double distance = 0;
		
		Location locUser = new Location("Point A");
		locUser.setLatitude(latitudeUser);
		locUser.setLongitude(longitudeUser);
		
		Location loc = new Location("Point B");
		if(e.getGeoloc() != null){
			loc.setLatitude(Double.parseDouble(e.getGeoloc().getLatitude()));
			loc.setLongitude(Double.parseDouble(e.getGeoloc().getLongitude()));
			distance = locUser.distanceTo(loc);
		}
		else {
			distance = 0;
		}
		return distance;
	}
	
	private void chargeImage(String nature, ImageView logoNature){
		if(nature.equals("Patinoire")){
			 logoNature.setImageResource(R.drawable.picto_patinoire);
		 }
		 else if(nature.equals("Court de Tennis")){
			 logoNature.setImageResource(R.drawable.picto_tennis);
		 }
		 else if(nature.equals("Piscine")){
			 logoNature.setImageResource(R.drawable.picto_swim);
		 }
		 else if(nature.equals("Terrain de boules et de pétanque")){
			 logoNature.setImageResource(R.drawable.picto_petanque);
		 }
		 else if(nature.equals("Terrain multisports")){
			 logoNature.setImageResource(R.drawable.picto_mulisports);
		 }
		 else if(nature.equals("Complexe sportif")){
			 logoNature.setImageResource(R.drawable.picto_mulisports);
		 }
		 else if(nature.equals("Gymnase")){
			 logoNature.setImageResource(R.drawable.picto_gymnase);
		 }
		 else if(nature.equals("Terrain de grand jeu")){
			 logoNature.setImageResource(R.drawable.picto_interrogation);
		 }
		 else if(nature.equals("Site dédié à l'athlétisme")){
			 logoNature.setImageResource(R.drawable.picto_athletisme);
		 }
		 else if(nature.equals("Equipement spécialisé")){
			 logoNature.setImageResource(R.drawable.picto_specialise);
		 }
		 else{
			 logoNature.setImageResource(R.drawable.picto_interrogation);
		 }
	}
	
}

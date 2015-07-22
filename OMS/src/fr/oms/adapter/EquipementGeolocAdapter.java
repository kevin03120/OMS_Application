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
		double distance = donneDistanceAvecEquipement(equipement);
		int distanceArrondie = (int) Math.round(distance);
		TextView txtDistance = (TextView)convertView.findViewById(R.id.distance_equipement);
		if (position % 2 == 0) {
			item.setBackgroundResource(R.drawable.customborder);
		}
		else{
			item.setBackgroundResource(R.drawable.customborder_orange);
		}
		afficheDistanceArrondie(distance, distanceArrondie, txtDistance);
		if(equipement.getLogo()!=0){
			 ImageView logoNature = (ImageView)convertView.findViewById(R.id.Logo_geoloc_equipement);		
			 logoNature.setVisibility(0);
			 logoNature.setImageResource(equipement.getLogo());
		 }
		return convertView;
	}

	private void afficheDistanceArrondie(double distance, int distanceArrondie,
			TextView txtDistance) {
		if(distance>= 0.999){
			if(distance!=0.0){
				txtDistance.setText("" + (double)Math.round(distance * 1000) / 1000  + " km");
			}
			else{
				txtDistance.setVisibility(0);
			}
		}
		else{
			if(distance!=0.0){
				txtDistance.setText("" + String.valueOf(distance).substring(2, 5) + " m");
			}
			else{
				txtDistance.setVisibility(0);
			}
		}
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
			distance = locUser.distanceTo(loc) / 1000;
		}
		else {
			distance = 0;
		}
		return distance;
	}

}

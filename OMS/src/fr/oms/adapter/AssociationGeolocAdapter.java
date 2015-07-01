package fr.oms.adapter;

import java.util.List;
import fr.oms.activities.R;
import fr.oms.metier.Association;
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

public class AssociationGeolocAdapter extends ArrayAdapter<Association> {

	private double latitudeUser;
	private double longitudeUser;
	
	public AssociationGeolocAdapter(Context context, int resource, List<Association> objects, double uneLatitude, double uneLongitude) {
		super(context, resource, objects);
		latitudeUser = uneLatitude;
		longitudeUser = uneLongitude;
	}
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_association_geoloc, parent,false);
		Association association = getItem(position);
		TextView nomAssociation = (TextView)convertView.findViewById(R.id.nom_element_geoloc_association);
		nomAssociation.setText(association.getNom());
		ImageView logoAdherent = (ImageView)convertView.findViewById(R.id.Logo_adherent_geoloc_association);
		if(association.isAdherent()){
			logoAdherent.setImageResource(R.drawable.logo_ad);
			logoAdherent.setVisibility(0);
		}
		LinearLayout item = (LinearLayout)convertView.findViewById(R.id.background_item_geoloc_association);
		if (position % 2 == 0) {
			item.setBackgroundResource(R.drawable.customborder);
		}
		else{
			item.setBackgroundResource(R.drawable.customborder_orange);
		}
		double distance = donneDistanceAvecAssoc(association);
		int distanceArrondie = (int) Math.round(distance);
		
		TextView txtDistance = (TextView)convertView.findViewById(R.id.distance_association);
		if(distance!=0.0){
			txtDistance.setText(""+distanceArrondie + " m");
		}
		else{
			txtDistance.setVisibility(0);
		}
		return convertView;
	}

	private double donneDistanceAvecAssoc(Association a){
		double distance = 0;
		
		Location locUser = new Location("Point A");
		locUser.setLatitude(latitudeUser);
		locUser.setLongitude(longitudeUser);
		
		Location loc = new Location("Point B");
		if(a.getListeEquipement() != null){
			loc.setLatitude(Double.parseDouble(a.getListeEquipement().get(0).getGeoloc().getLatitude()));
			loc.setLongitude(Double.parseDouble(a.getListeEquipement().get(0).getGeoloc().getLongitude()));
			distance = locUser.distanceTo(loc);
		}
		else{
			distance = 0;
		}
		return distance;
	}
	
}

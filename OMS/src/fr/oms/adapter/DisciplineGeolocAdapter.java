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
import fr.oms.metier.Discipline;

public class DisciplineGeolocAdapter extends ArrayAdapter<Discipline> {

	private double latitudeUser;
	private double longitudeUser;
	
	public DisciplineGeolocAdapter(Context context, int resource, List<Discipline> objects, double uneLatitude, double uneLongitude) {
		super(context, resource, objects);
		latitudeUser = uneLatitude;
		longitudeUser = uneLongitude;
	}
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_discipline_geoloc, parent,false);
		Discipline discipline = getItem(position);
		TextView nomDiscipline = (TextView)convertView.findViewById(R.id.nom_element_geoloc_discipline);
		nomDiscipline.setText(discipline.getNom());
		LinearLayout item = (LinearLayout)convertView.findViewById(R.id.background_item_geoloc_discipline);
		if (position % 2 == 0) {
			item.setBackgroundResource(R.drawable.customborder);
		}
		else{
			item.setBackgroundResource(R.drawable.customborder_orange);
		}
		return convertView;
	}
	
	
	
}

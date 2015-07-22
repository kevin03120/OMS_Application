package fr.oms.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.oms.activities.R;
import fr.oms.metier.Evenement;
import fr.oms.modele.DownloadImageTask;

public class EvenementAdapter extends ArrayAdapter<Evenement> {

	public EvenementAdapter(Context context, int resource, List<Evenement> objects) {
		super(context, resource, objects); 
	}
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_actualite_evenement, parent,false);
		Evenement evenement = getItem(position);
		LinearLayout item = (LinearLayout)convertView.findViewById(R.id.background_item);
		if (position % 2 == 0) {
			 item.setBackgroundResource(R.drawable.customborder);
		 }
		 else{
			 item.setBackgroundResource(R.drawable.customborder_blue);
		 }
		TextView titreActu = (TextView)convertView.findViewById(R.id.nom_element);
		titreActu.setText(evenement.getTitre());
		return convertView;
	}
}

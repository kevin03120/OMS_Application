package fr.oms.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.oms.activities.R;
import fr.oms.metier.Quartier;
import fr.oms.modele.Manager;

public class QuartierAdapter extends ArrayAdapter<Quartier> {

	public QuartierAdapter(Context context, int resource, List<Quartier> objects) {
		super(context, resource, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_association_equipement, parent, false);
		Quartier quartier = Manager.getInstance().getListeQuartier().get(position);
		TextView nomQuartier = (TextView)convertView.findViewById(R.id.nom_element);
		nomQuartier.setText(quartier.getNom());
		LinearLayout item = (LinearLayout)convertView.findViewById(R.id.background_item);
		 if (position % 2 == 0) {
			 item.setBackgroundResource(R.drawable.customborder);
		 }
		 else{
			 item.setBackgroundResource(R.drawable.customborder_alt);
		 }
		return convertView;
	}

}

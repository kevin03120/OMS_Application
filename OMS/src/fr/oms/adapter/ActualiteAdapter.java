package fr.oms.adapter;

import java.util.List;
import fr.oms.activities.R;
import fr.oms.metier.Actualite;
import fr.oms.modele.DownloadImageTask;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActualiteAdapter extends ArrayAdapter<Actualite> {

	public ActualiteAdapter(Context context, int resource, List<Actualite> objects) {
		super(context, resource, objects); 
	}
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_actualite_evenement, parent,false);
		Actualite actualite = getItem(position);
		ImageView image = (ImageView)convertView.findViewById(R.id.logoElement);
		new DownloadImageTask(image)
        .execute(actualite.getLienImage()+"=?reqwidth=40");
		TextView titreActu = (TextView)convertView.findViewById(R.id.nom_element);
		titreActu.setText(actualite.getTitre());
		return convertView;
	}
}

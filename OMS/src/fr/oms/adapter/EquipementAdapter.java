package fr.oms.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.oms.activities.R;
import fr.oms.metier.Equipement;

public class EquipementAdapter extends ArrayAdapter<Equipement> {

	private Context context;
	
	public EquipementAdapter(Context context, int resource, List<Equipement> objects) {
		super(context, resource, objects);
	}
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_association_equipement, parent, false);
		Equipement equipement = getItem(position);
		TextView nomEquipement = (TextView) convertView.findViewById(R.id.nom_element);
		nomEquipement.setText(equipement.getNom());
		LinearLayout item = (LinearLayout)convertView.findViewById(R.id.background_item);
		 if (position % 2 == 0) {
			 item.setBackgroundResource(R.drawable.customborder);
		 }
		 else{
			 item.setBackgroundResource(R.drawable.customborder_alt);
		 }
		 
		 if(equipement.getLogo()!=0){
			 ImageView logoNature = (ImageView)convertView.findViewById(R.id.Logo_adherent);		
			 logoNature.setVisibility(0);
			 logoNature.setImageResource(equipement.getLogo());
		 }
		return convertView;
	}
}

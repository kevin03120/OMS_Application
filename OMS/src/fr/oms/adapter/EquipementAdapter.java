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
import fr.oms.metier.Equipement;

public class EquipementAdapter extends ArrayAdapter<Equipement> {

	
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
		 ImageView logoNature = (ImageView)convertView.findViewById(R.id.Logo_adherent);		

		 logoNature.setVisibility(0);
		 String nature = equipement.getNature();
		 chargeImage(nature, logoNature);
		return convertView;
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
		 else if(nature.equals("Terrain de boules et de p�tanque")){
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
		 else if(nature.equals("Site d�di� � l'athl�tisme")){
			 logoNature.setImageResource(R.drawable.picto_athletisme);
		 }
		 else if(nature.equals("Equipement sp�cialis�")){
			 logoNature.setImageResource(R.drawable.picto_specialise);
		 }
		 else{
			 logoNature.setImageResource(R.drawable.picto_interrogation);
		 }
	}
}

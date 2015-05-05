package fr.oms.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import fr.oms.activities.R;
import fr.oms.metier.Association;

public class AssociationAdapter extends ArrayAdapter<Association>  implements SectionIndexer{

	HashMap<String, Integer> mapIndex;
	List<String> noms;

	HashMap<String, Integer> alphaIndexer;  
	String[] sections;  

	public AssociationAdapter(Context context, int resource, List<Association> objects) {
		super(context, resource, objects);
		alphaIndexer = new HashMap<String, Integer>();  
		int size = objects.size();  
		for (int x = 0; x < size; x++) {  
			String s = objects.get(x).getNom();  
			String ch = s.substring(0, 1);  
			ch = ch.toUpperCase(Locale.FRENCH); 
			if(!alphaIndexer.containsKey(ch)){
				alphaIndexer.put(ch, x);
			}
		}
		Set<String> sectionLetters = alphaIndexer.keySet();  	    
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);  
        Collections.sort(sectionList);  
        sections = new String[sectionList.size()];  
        sectionList.toArray(sections);  
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_association_equipement, parent,false);
		Association association = getItem(position);
		TextView nomAssociation = (TextView)convertView.findViewById(R.id.nom_element);
		nomAssociation.setText(association.getNom());
		ImageView logoAdherent = (ImageView)convertView.findViewById(R.id.Logo_adherent);
		if(association.isAdherent()){
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
	
	@Override
	public Object[] getSections() {
		return sections;
	}

	@Override
	public int getPositionForSection(int section) {
		System.out.println("SECTION : "+section);
		return alphaIndexer.get(sections[section]);
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}
}

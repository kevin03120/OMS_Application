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
import fr.oms.metier.Discipline;

public class DisciplineAdapter extends ArrayAdapter<Discipline> {

	public Context mContext;
	
	public DisciplineAdapter(Context context, int resource, List<Discipline> objects) {
		super(context, resource, objects);
		mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_discipline, parent, false);
		final Discipline discipline = getItem(position);
		TextView nomDiscipline = (TextView) convertView.findViewById(R.id.nom_element);
		nomDiscipline.setText(discipline.getNom());
		LinearLayout item = (LinearLayout)convertView.findViewById(R.id.background_item_element);
		 if (position % 2 == 0) {
			 item.setBackgroundResource(R.drawable.customborder);
		 }
		 else{
			 item.setBackgroundResource(R.drawable.customborder_alt);
		 }
		return convertView;
	}

}

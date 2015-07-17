package fr.oms.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.oms.activities.R;
import fr.oms.modele.NavDrawerItem;

public class NavDrawerListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;

	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolderItem {
		LinearLayout layout;
		ImageView imgIcon;
		TextView txtTitle;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderItem viewHolder;
		if (convertView == null) {
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.drawer_nav_item, null);

			viewHolder = new ViewHolderItem();
			viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.layout_drawer);
			viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
			viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			viewHolder.txtTitle.setText(navDrawerItems.get(position).getTitle());
			afficherIcon(position, viewHolder.layout, viewHolder.imgIcon);
			Resources r = context.getResources();
			if((viewHolder.txtTitle.getText()==r.getString(R.string.accueil_underline))||(viewHolder.txtTitle.getText()==r.getString(R.string.annuaire_underline))||(viewHolder.txtTitle.getText()==r.getString(R.string.agenda_underline))){
				viewHolder.txtTitle.setTextAppearance(context, R.style.TextItemNavTitre);           
			}
			else{
				viewHolder.txtTitle.setTextAppearance(context, R.style.TextItemNavSousTitre);
				if((viewHolder.txtTitle.getText()==r.getString(R.string.localise_association))||(viewHolder.txtTitle.getText()==r.getString(R.string.localise_equipement))||(viewHolder.txtTitle.getText()==r.getString(R.string.localise_discipline))){
					viewHolder.txtTitle.setTextSize(12);
				}
			}
			if(viewHolder.txtTitle.getText()==r.getString(R.string.geolocalisation_underline)){
				viewHolder.txtTitle.setTextAppearance(context, R.style.TextItemNavTitreGeoloc);
			}
			if((viewHolder.txtTitle.getText()==r.getString(R.string.parametres))||(viewHolder.txtTitle.getText()==r.getString(R.string.Guide_Sport))||(viewHolder.txtTitle.getText()==r.getString(R.string.mentions))){
				viewHolder.layout.setBackgroundResource(navDrawerItems.get(position).getmColor());
				viewHolder.imgIcon.setVisibility(View.INVISIBLE);
			}
			convertView.setTag(viewHolder);

		}
		else{
			viewHolder = (ViewHolderItem) convertView.getTag();
		}
		NavDrawerItem objectItem = (NavDrawerItem) getItem(position);
		    if(objectItem != null) {
		        viewHolder.txtTitle.setText(objectItem.getTitle());
		        viewHolder.imgIcon.setImageResource(objectItem.getIcon());
		        viewHolder.layout.setBackgroundResource(objectItem.getmColor());
		    }
		 

		return convertView;
	}

	private void afficherIcon(int position, LinearLayout layout,
			ImageView imgIcon) {
		if(navDrawerItems.get(position).getIcon()!=0){
			imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
			Double f=0.7;
			layout.setBackgroundResource(navDrawerItems.get(position).getmColor());
			if(navDrawerItems.get(position).getIcon()==R.drawable.ic_arrow4){
				imgIcon.setScaleX(f.floatValue());
				imgIcon.setScaleY(f.floatValue());
			}
		}
	}
}

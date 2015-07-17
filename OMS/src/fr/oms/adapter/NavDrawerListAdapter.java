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

    @SuppressLint("InflateParams")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.drawer_nav_item, null);
        }
        LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.layout_drawer);
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        txtTitle.setText(navDrawerItems.get(position).getTitle());
        afficherIcon(position, layout, imgIcon);
        Resources r = context.getResources();
        if((txtTitle.getText()==r.getString(R.string.accueil_underline))||(txtTitle.getText()==r.getString(R.string.annuaire_underline))||(txtTitle.getText()==r.getString(R.string.agenda_underline))){
            txtTitle.setTextAppearance(context, R.style.TextItemNavTitre);           
        }
        else{
            txtTitle.setTextAppearance(context, R.style.TextItemNavSousTitre);
            if((txtTitle.getText()==r.getString(R.string.localise_association))||(txtTitle.getText()==r.getString(R.string.localise_equipement))||(txtTitle.getText()==r.getString(R.string.localise_discipline))){
            	txtTitle.setTextSize(12);
            }
        }
        if(txtTitle.getText()==r.getString(R.string.geolocalisation_underline)){
        	txtTitle.setTextAppearance(context, R.style.TextItemNavTitreGeoloc);
        }
        if((txtTitle.getText()==r.getString(R.string.parametres))||(txtTitle.getText()==r.getString(R.string.Guide_Sport))||(txtTitle.getText()==r.getString(R.string.mentions))){
        	layout.setBackgroundResource(navDrawerItems.get(position).getmColor());
        	imgIcon.setVisibility(View.INVISIBLE);
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

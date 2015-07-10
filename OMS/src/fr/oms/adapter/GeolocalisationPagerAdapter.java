package fr.oms.adapter;

import fr.oms.fragments.FragmentGeolocAssociations;
import fr.oms.fragments.FragmentGeolocEquipements;
import fr.oms.fragments.FragmentGeolocDisciplines;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class GeolocalisationPagerAdapter extends FragmentStatePagerAdapter {

	final int PAGE_COUNT = 3;
	private String tabTitles[] = new String[] { "Associations", "Equipements", "Disciplines"};
    private Context context;

    public GeolocalisationPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.setContext(context);        
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
    	switch(position){
    	case 0: return new FragmentGeolocAssociations();
    	case 1: return new FragmentGeolocEquipements();
    	case 2: return new FragmentGeolocDisciplines();
    	default: return  null;
    	}
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
}

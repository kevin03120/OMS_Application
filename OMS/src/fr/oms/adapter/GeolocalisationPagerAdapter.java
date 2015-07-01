package fr.oms.adapter;

import fr.oms.fragments.FragmentGeolocAssociations;
import fr.oms.fragments.FragmentGeolocEquipements;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class GeolocalisationPagerAdapter extends FragmentStatePagerAdapter {

	final int PAGE_COUNT = 2;
	private String tabTitles[] = new String[] { "Associations", "Equipements"};
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
    	default: return  null;
    	}
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
}

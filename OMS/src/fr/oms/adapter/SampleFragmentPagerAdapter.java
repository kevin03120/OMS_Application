package fr.oms.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import fr.oms.fragments.FragmentListeAssociations;
import fr.oms.fragments.FragmentListeDisciplines;
import fr.oms.fragments.FragmentListeEquipements;
import fr.oms.fragments.FragmentListeQuartiers;
import fr.oms.fragments.PageFragment;

public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Associations", "Equipements", "Disciplines", "Quartiers" };
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;        
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
    	switch(position){
    	case 0: return new FragmentListeAssociations();
    	case 1: return new FragmentListeEquipements();
    	case 2: return new FragmentListeDisciplines();
    	case 3: return new FragmentListeQuartiers();
    	default: return new FragmentListeAssociations();
    	}
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
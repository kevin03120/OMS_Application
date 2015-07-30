package fr.oms.activities;

import java.util.List;

import fr.oms.fragments.FragmentEquipement;
import fr.oms.metier.Equipement;
import fr.oms.modele.Manager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

public class FragmentEquipementActivity extends FragmentActivity {

	private Equipement equipement;
	private ViewPager pager;
	public static FragmentEquipementActivity fragmentEquipementActivity;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentEquipementActivity = this;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.detail_pager);     
        int pos = getIntent().getExtras().getInt("position");
        int position = 0;
        initPager(pos, position);
	}
	
	/**
	 * Initialisation du pager
	 * @param pos
	 * @param position
	 */
	private void initPager(int pos, int position) {
		pager = (ViewPager) findViewById(R.id.viewPager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        List<Equipement> equipements = Manager.getInstance().getListeEquipement();
        for(Equipement e : equipements){
        	if(pos == e.getUid()){
        		equipement = e;
        		position = pagerAdapter.getEquipements().indexOf(e);
        	}
        }
        pager.setCurrentItem(position);
        getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		onBackPressed();
		return super.onOptionsItemSelected(item);
	}
	
	public ViewPager getPager() {
		return pager;
	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

	 	private List<Equipement> equipements;
	 	
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            setEquipements(Manager.getInstance().getListeEquipement());
        }

        @Override
        public Fragment getItem(int pos) {
        	equipement = getEquipements().get(pos);
            return FragmentEquipement.newInstance(equipement);
        }
        
        @Override
        public int getCount() {
            return getEquipements().size();
        }

		public List<Equipement> getEquipements() {
			return equipements;
		}

		public void setEquipements(List<Equipement> equipements) {
			this.equipements = equipements;
		}
    }
}

package fr.oms.activities;

import java.util.List;

import fr.oms.fragments.FragmentDetailEvenement;
import fr.oms.metier.Evenement;
import fr.oms.modele.Manager;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class FragmentEventActivity extends FragmentActivity {

	private Evenement evenement;
	private ViewPager pager;
	public static FragmentEventActivity fragmentEventActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragmentEventActivity = this;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.detail_pager); 
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(R.color.BleuOms)));
        int pos = getIntent().getExtras().getInt("position");
        initPager(pos);
	}

	public ViewPager getPager() {
		return pager;
	}

	/**
	 * Initialisation du pager
	 * @param pos
	 */
	private void initPager(int pos) {
		pager = (ViewPager) findViewById(R.id.viewPager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        for(Evenement e : Manager.getInstance().getListEvenements()){
        	if(e.getId() == pos){
        		evenement = e;
        	}
        }
        pager.setCurrentItem(Manager.getInstance().getListEvenements().indexOf(evenement));
	}
	
	private class MyPagerAdapter extends FragmentPagerAdapter {

	 	private List<Evenement> evenements;
	 	
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            setEvenements(Manager.getInstance().getListEvenements());
        }

        public List<Evenement> getEvenements() {
			return evenements;
		}

		public void setEvenements(List<Evenement> evenements) {
			this.evenements = evenements;
		}

		@Override
        public Fragment getItem(int pos) {
        	evenement = getEvenements().get(pos);
            return FragmentDetailEvenement.newInstance(evenement);
        }
        
        @Override
        public int getCount() {
            return getEvenements().size();
        }      
    }
}

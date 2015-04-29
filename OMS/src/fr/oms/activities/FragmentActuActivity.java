package fr.oms.activities;

import java.util.List;

import fr.oms.fragments.FragmentDetailActualite;
import fr.oms.metier.Actualite;
import fr.oms.modele.Manager;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class FragmentActuActivity extends FragmentActivity {

	private Actualite actualite;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.detail_pager); 
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(R.color.BleuOms)));
        int pos = getIntent().getExtras().getInt("position");
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        actualite = Manager.getInstance().getListActualites().get(pos);
        pager.setCurrentItem(pos);
	}
	
	private class MyPagerAdapter extends FragmentPagerAdapter {

	 	private List<Actualite> actualites;
	 	
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            setActualites(Manager.getInstance().getListActualites());
        }

        public List<Actualite> getActualites() {
			return actualites;
		}

		public void setActualites(List<Actualite> actualites) {
			this.actualites = actualites;
		}

		@Override
        public Fragment getItem(int pos) {
        	actualite = getActualites().get(pos);
            return FragmentDetailActualite.newInstance(actualite);
        }
        
        @Override
        public int getCount() {
            return getActualites().size();
        }      
    }
}

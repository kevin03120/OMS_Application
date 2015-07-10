package fr.oms.activities;

import java.util.List;

import fr.oms.fragments.FragmentDiscipline;
import fr.oms.metier.Discipline;
import fr.oms.metier.Sport;
import fr.oms.modele.Manager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class FragmentDisciplineActivity extends FragmentActivity {

	private Discipline discipline;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.detail_pager);     
        int pos = getIntent().getExtras().getInt("position");
        int position = 0;
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        List<Discipline> disciplines = Manager.getInstance().getListeDiscipline();
        for(Discipline d : disciplines){
        	if(pos == d.getUid()){
        		discipline = d;
        		position = pagerAdapter.getDisciplines().indexOf(d);
        	}
        }
        pager.setCurrentItem(position);
	}
	private class MyPagerAdapter extends FragmentPagerAdapter {

	 	private List<Discipline> disciplines;
	 	private List<Sport> sports;
	 	
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            setDisciplines(Manager.getInstance().getListeDiscipline());
        }

        @Override
        public Fragment getItem(int pos) {
        	discipline = getDisciplines().get(pos);
            return FragmentDiscipline.newInstance(discipline);
        }
        
        @Override
        public int getCount() {
            return getDisciplines().size();
        }

		public List<Discipline> getDisciplines() {
			return disciplines;
		}

		public void setDisciplines(List<Discipline> disciplines) {
			this.disciplines = disciplines;
		}
    }
}

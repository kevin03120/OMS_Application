package fr.oms.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import fr.oms.fragments.FragmentAssociation;
import fr.oms.metier.Association;
import fr.oms.metier.Sport;
import fr.oms.modele.Manager;

public class FragmentAssociationActivity extends FragmentActivity {

	private Association association;
	private ViewPager pager;
	public static FragmentAssociationActivity fragmentAssociationActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.detail_pager); 

		fragmentAssociationActivity = this;
		//R�cup�ration des valeurs extras
		int pos = getIntent().getExtras().getInt("position");
		boolean adherent = getIntent().getExtras().getBoolean("adherents");
		boolean nonAdherent = getIntent().getExtras().getBoolean("nonAdherents");
		boolean sport = getIntent().getExtras().getBoolean("sport");
		String nomSport = getIntent().getExtras().getString("nomSport");
		int position = 0;

		//Cr�ation du pager
		pager = (ViewPager) findViewById(R.id.viewPager);
		MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), adherent, nonAdherent, sport, nomSport);
		pager.setAdapter(pagerAdapter);
		List<Association> associations = Manager.getInstance().getListeAssociation();
		for(Association a : associations){
			if(pos == a.getId()){
				association = a;
				position = pagerAdapter.getAssociations().indexOf(a);
			}
		}
		pager.setCurrentItem(position);
	}

	public ViewPager getPager() {
		return pager;
	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

		private List<Association> associations;

		public MyPagerAdapter(FragmentManager fragmentManager, Boolean adherent, Boolean nonAdherent, Boolean sport, String nomSport) {
			super(fragmentManager);
			setAssociations(rendListeActualise(adherent, nonAdherent, sport, nomSport));
		}

		@Override
		public Fragment getItem(int pos) {
			association = getAssociations().get(pos);
			return FragmentAssociation.newInstance(association);
		}

		private List<Association> rendListeActualise(boolean adherent, boolean nonAdherent, boolean sport, String nomSport){
			List<Association> assocs = new ArrayList<Association>();
			for(Association a : Manager.getInstance().getListeAssociation()){
				if((adherent) && (nonAdherent)){
					if(sport){
						for(Sport s : a.getListeSport()){
							if(s.getNom().equals(nomSport)){
								assocs.add(a);
							}
						}
					}
					else{
						assocs.add(a);
					}
				}
				else if((!adherent) && (nonAdherent)){
					if(!a.isAdherent()){
						if(sport){
							for(Sport s : a.getListeSport()){
								if(s.getNom().equals(nomSport)){
									assocs.add(a);
								}
							}
						}
						else{
							assocs.add(a);
						}
					}
				}
				else if((adherent) && (!nonAdherent)){
					if(a.isAdherent()){
						if(sport){
							for(Sport s : a.getListeSport()){
								if(s.getNom().equals(nomSport)){
									assocs.add(a);
								}
							}
						}
						else{
							assocs.add(a);
						}
					}
				}
			}
			for(Association a : assocs){
				Log.i("vuList", a.getNom());
			}
			return assocs;
		}

		@Override
		public int getCount() {
			return getAssociations().size();
		}

		public List<Association> getAssociations() {
			return associations;
		}

		public void setAssociations(List<Association> associations) {
			this.associations = associations;
		}       
	}
}

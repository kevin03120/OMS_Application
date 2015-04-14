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
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        setContentView(R.layout.association_pager);     
	        int pos = getIntent().getExtras().getInt("position");
	        boolean adherent = getIntent().getExtras().getBoolean("adherents");
	        boolean nonAdherent = getIntent().getExtras().getBoolean("nonAdherents");
	        boolean sport = getIntent().getExtras().getBoolean("sport");
	        int idSport = getIntent().getExtras().getInt("idSport");
	        Log.i("etat", "adherent : " + adherent);
	        Log.i("etat", "nonAdherent : " + nonAdherent);
	        int position = 0;
	        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
	        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), adherent, nonAdherent, sport, idSport);
	        pager.setAdapter(pagerAdapter);
	        List<Association> associations = Manager.getInstance().getListeAssociation();
	        for(Association a : associations){
	        	if(pos == a.getUid()){
	        		association = a;
	        		position = pagerAdapter.getAssociations().indexOf(a);
	        	}
	        }
	        pager.setCurrentItem(position);
	    }
	 	
	 private class MyPagerAdapter extends FragmentPagerAdapter {

		 	private List<Association> associations;
		 	
	        public MyPagerAdapter(FragmentManager fragmentManager, Boolean adherent, Boolean nonAdherent, Boolean sport, int idSport) {
	            super(fragmentManager);
	            setAssociations(rendListeActualise(adherent, nonAdherent, sport, idSport));
	        }

	        @Override
	        public Fragment getItem(int pos) {
	        	association = getAssociations().get(pos);
	            return FragmentAssociation.newInstance(association);
	        }

	        private List<Association> rendListeActualise(boolean adherent, boolean nonAdherent, boolean sport, int idSport){
		 		List<Association> assocs = new ArrayList<Association>();
		 		for(Association a : Manager.getInstance().getListeAssociation()){
		 			if((adherent) && (nonAdherent)){
		 				if(sport){
		 					for(Sport s : a.getListeSport()){
		 						if(s.getId() == idSport){
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
			 						if(s.getId() == idSport){
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
			 						if(s.getId() == idSport){
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

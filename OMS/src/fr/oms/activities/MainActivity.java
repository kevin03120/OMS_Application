package fr.oms.activities;



import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import fr.oms.dataloader.JSONTags;
import fr.oms.dataloader.ParserJson;
import fr.oms.fragments.AccueilFragment;
import fr.oms.fragments.AgendaFragment;
import fr.oms.fragments.AnnuaireFragment;
import fr.oms.fragments.GeolocalisationFragment;
import fr.oms.fragments.NavigationDrawerFragment;
import fr.oms.modele.Manager;

public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	private DrawerLayout mDrawerLayout;
	private Boolean exit = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Manager.getInstance().clearDonnees();
		setContentView(R.layout.activity_main);
		Manager.getInstance().getTousLesSport(getApplicationContext());
		if(this.getFileStreamPath(JSONTags.FICHIER_ACTUS).exists()){
			ParserJson parser=new ParserJson(getApplicationContext());
			parser.effectuerParsing();
		}
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		initDrawer();
		

	}

	/**
	 * Initialisation du navigation drawer
	 */
	private void initDrawer(){
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(
				R.id.navigation_drawer);
		mTitle = getTitle();
		mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		displayView(position);
	}

	/**
	 * Affiche le fragment correspondant à la position selectionnée
	 * @param position position de l'item selectionné
	 */
	private void displayView(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new AccueilFragment();
			getActionBar().setBackgroundDrawable(
					new ColorDrawable(getResources().getColor(R.color.Rouge1)));
			break;

		case 1:
			fragment = new AnnuaireFragment(0);
			getActionBar().setBackgroundDrawable(
					new ColorDrawable(getResources().getColor(R.color.VertOms)));
			break;

		case 2:
			fragment = new AnnuaireFragment(0);			
			getActionBar().setBackgroundDrawable(
					new ColorDrawable((getResources().getColor(R.color.VertOms))));
			break;

		case 3:
			fragment = new AnnuaireFragment(1);			
			getActionBar().setBackgroundDrawable(
					new ColorDrawable((getResources().getColor(R.color.VertOms))));
			break;

		case 4:
			fragment = new AnnuaireFragment(2);			
			getActionBar().setBackgroundDrawable(
					new ColorDrawable((getResources().getColor(R.color.VertOms))));
			break;

		case 5:
			fragment = new AnnuaireFragment(3);			
			getActionBar().setBackgroundDrawable(
					new ColorDrawable((getResources().getColor(R.color.VertOms))));
			break;

		case 6:	    	
			fragment = new AgendaFragment(0);
			getActionBar().setBackgroundDrawable(
					new ColorDrawable((getResources().getColor(R.color.BleuOms))));
			break;

		case 7:	    	
			fragment = new AgendaFragment(0);
			getActionBar().setBackgroundDrawable(
					new ColorDrawable((getResources().getColor(R.color.BleuOms))));
			break;

		case 8:	    	
			fragment = new AgendaFragment(1);
			getActionBar().setBackgroundDrawable(
					new ColorDrawable((getResources().getColor(R.color.BleuOms))));
			break;

		case 9:
			fragment = new GeolocalisationFragment(0);
			getActionBar().setBackgroundDrawable(
					new ColorDrawable((getResources().getColor(R.color.OrangeOms))));
			break;

		case 10:
			fragment = new GeolocalisationFragment(0);
			getActionBar().setBackgroundDrawable(
					new ColorDrawable((getResources().getColor(R.color.OrangeOms))));
			break;

		case 11:
			fragment = new GeolocalisationFragment(1);
			getActionBar().setBackgroundDrawable(
					new ColorDrawable((getResources().getColor(R.color.OrangeOms))));
			break;

		case 12:
			fragment = new GeolocalisationFragment(2);
			getActionBar().setBackgroundDrawable(
					new ColorDrawable((getResources().getColor(R.color.OrangeOms))));
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
			restoreActionBar(position);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	private void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	private void restoreActionBar(int position) {
		ActionBar actionBar = getActionBar();
		mTitle = donneTitreActionBar(position);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	private String donneTitreActionBar(int position){
		Resources r = getResources();
		switch (position) {
		case 0: return r.getString(R.string.accueil);

		case 1: return r.getString(R.string.annuaire);

		case 2: return r.getString(R.string.annuaire);

		case 3: return r.getString(R.string.annuaire);

		case 4: return r.getString(R.string.annuaire);

		case 5: return r.getString(R.string.annuaire);

		case 6: return r.getString(R.string.agenda);

		case 7: return r.getString(R.string.agenda);

		case 8: return r.getString(R.string.agenda);

		case 9: return r.getString(R.string.geolocalisation);

		case 10: return r.getString(R.string.geolocalisation);

		case 11: return r.getString(R.string.geolocalisation);

		case 12: return r.getString(R.string.geolocalisation);

		default: return r.getString(R.string.oms);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onBackPressed() {
		mDrawerLayout.openDrawer(mNavigationDrawerFragment.getView());
		if(mDrawerLayout.isDrawerOpen(mNavigationDrawerFragment.getView())){
			if (exit) {
				finish(); 
			} else {
				Toast.makeText(this, "Appuyez encore pour quitter.",
						Toast.LENGTH_SHORT).show();
				exit = true;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						exit = false;
					}
				}, 3 * 1000);
			}
		}
	}
}

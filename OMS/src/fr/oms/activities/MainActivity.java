package fr.oms.activities;


import java.util.concurrent.ExecutionException;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import fr.oms.dataloader.JsonDataLoader;
import fr.oms.dataloader.ParserJson;
import fr.oms.fragments.AccueilFragment;
import fr.oms.fragments.AgendaFragment;
import fr.oms.fragments.AnnuaireFragment;
import fr.oms.fragments.NavigationDrawerFragment;
import fr.oms.modele.Manager;

public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	private DrawerLayout mDrawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Manager.getInstance().clearDonnees();
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		initDrawer();
//		JsonDataLoader loader=JsonDataLoader.getInstance();
//		effectuerConnexion(loader);
		Manager.getInstance().getTousLesSport(getApplicationContext());
		ParserJson parser=new ParserJson(getApplicationContext());

	}
	
	private void initDrawer(){
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(
				R.id.navigation_drawer);
		mTitle = getTitle();
		mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);
	}

	private void effectuerConnexion(JsonDataLoader loader) {
		if(isNetworkAvailable(this)){
			try {
				loader.execute(getApplicationContext()).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
			alertDialogBuilder.setTitle(R.string.detailCo);
			alertDialogBuilder
			.setMessage(getResources().getString(R.string.detailCo))
			.setCancelable(false)
			.setPositiveButton("Annuler la synchronisation",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.dismiss();
				}
			});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();

		}
	}

	public boolean isNetworkAvailable( Activity mActivity ) 
	{ 
		Context context = mActivity.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) 
		{   
			return false;
		} 
		else 
		{  
			NetworkInfo[] info = connectivity.getAllNetworkInfo();   
			if (info != null) 
			{   
				for (int i = 0; i <info.length; i++) 
				{ 
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true; 
					}
				}     
			} 
			return false;
		}
	} 
	
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		displayView(position);
	}

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
			Toast.makeText(this, "en attente Geo", Toast.LENGTH_SHORT).show();
			//	        getActionBar().setBackgroundDrawable(
			//	                new ColorDrawable((getResources().getColor(R.color.BleuOms))));
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

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	public void restoreActionBar(int position) {
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

		case 2: return r.getString(R.string.association);

		case 3: return r.getString(R.string.equipement);

		case 4: return r.getString(R.string.discipline);

		case 5: return r.getString(R.string.quartier);

		case 6: return r.getString(R.string.agenda);

		case 7: return r.getString(R.string.actualite);

		case 8: return r.getString(R.string.evenements);

		case 9: return r.getString(R.string.geolocalisation);

		case 10: return r.getString(R.string.association);

		case 11: return r.getString(R.string.equipement);

		case 12: return r.getString(R.string.adresse);

		default: return r.getString(R.string.oms);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
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
			super.onBackPressed();
		}
		//super.onBackPressed();
	}
}

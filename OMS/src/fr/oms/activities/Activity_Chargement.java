package fr.oms.activities;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import fr.oms.dataloader.JSONTags;
import fr.oms.dataloader.JsonDataLoader;

public class Activity_Chargement extends Activity {

	public static Activity actiCharg;
	private ProgressBar pgrBar;
	private TextView txtTitre;
	private JsonDataLoader loader;
	private boolean faireMaj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actiCharg=this;		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_activity__chargement);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		txtTitre = (TextView)findViewById(R.id.txtInfo);
		pgrBar = (ProgressBar)findViewById(R.id.progressBar1);
		loader=JsonDataLoader.getInstance(this, pgrBar, txtTitre);
		
		SharedPreferences prefs = getSharedPreferences(
				"fr.oms.activities", Context.MODE_PRIVATE);
		faireMaj=prefs.getBoolean("MAJ", true);
		
		if(getIntent().getExtras() != null){
			if(getIntent().getExtras().getBoolean("MAJ")){
				faireMaj = true;
			}
		}
		
		if(faireMaj){
			effectuerConnexion();
		}else{
			Intent i = new Intent(Activity_Chargement.this, MainActivity.class);
			startActivity(i);
			finish();
		}
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	/**
	 * Méthode permettant de lancer le JsonDataLoader pour récupérer les fichiers
	 * @param loader 
	 */
	private void effectuerConnexion() {
		if(isNetworkAvailable()){	
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						loader.execute(getApplicationContext()).get(30000, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TimeoutException e) {
						if(actiCharg.getFileStreamPath(JSONTags.FICHIER_ACTUS).exists()){
							afficherDialogDejaFichier();
						}else{
							afficherDialogPasFichier();
						}
					}
					
				}
			}).start();
				
		}
		else{
			if(this.getFileStreamPath(JSONTags.FICHIER_ACTUS).exists()){
				afficherDialogDejaFichier();
			}
			else{
				afficherDialogPasFichier();
			}
		}
	}

	/**
	 * Affiche un dialog lorsqu'il n'y a pas de connexion et qu'aucun fichier n'est présent.
	 */
	private void afficherDialogPasFichier() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Activity_Chargement.this);
		alertDialogBuilder.setTitle(R.string.detailAucuneDonnees);
		alertDialogBuilder
		.setMessage(getResources().getString(R.string.infoAucuneDonnees))
		.setCancelable(false)
		.setPositiveButton("Fermer l'application",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				finish();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	/**
	 * Affiche un dialog lorsqu'il n'y a pas de connexion et que les fichiers de données sont présent.
	 */
	private void afficherDialogDejaFichier() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Activity_Chargement.this);
		alertDialogBuilder.setTitle(R.string.detailCo);
		alertDialogBuilder
		.setMessage(getResources().getString(R.string.detailCo))
		.setCancelable(false)
		.setPositiveButton("Annuler la synchronisation",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.dismiss();
				Intent i = new Intent(Activity_Chargement.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	/**
	 * Test s'il existe une connexion internet
	 * @return true si une connexion existe false sinon
	 */
	private boolean isNetworkAvailable() 
	{ 
		Context context = getApplicationContext();
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
	protected void onStop() {
		loader.cancel(true);
		super.onStop();
	}
}

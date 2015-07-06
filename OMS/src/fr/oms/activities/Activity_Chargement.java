package fr.oms.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import fr.oms.dataloader.ParserJson;

public class Activity_Chargement extends Activity {

	public static Activity actiCharg;
	private ProgressBar pgrBar;
	private TextView txtTitre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actiCharg=this;
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_activity__chargement);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		txtTitre = (TextView)findViewById(R.id.txtInfo);
		pgrBar = (ProgressBar)findViewById(R.id.progressBar1);
		JsonDataLoader loader=JsonDataLoader.getInstance(this, pgrBar, txtTitre);	
		effectuerConnexion(loader);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


		//		if(isNetworkAvailable(this)){
		//			Manager.getInstance().getTousLesSport(getApplicationContext());
		//			ParserJson parser=new ParserJson(getApplicationContext(),bar);
		//		}
		//		else{
		//			JSONObject jsObj=JsonDataLoader.getInstance(this).LoadFile(this.getFileStreamPath(JSONTags.FICHIER_ACTUS));
		//			if(jsObj != null){
		//				
		//			}
		//			else{
		//				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		//				alertDialogBuilder.setTitle(R.string.detailCo);
		//				alertDialogBuilder
		//				.setMessage(getResources().getString(R.string.detailCo))
		//				.setCancelable(false)
		//				.setPositiveButton("Fermer l'application",new DialogInterface.OnClickListener() {
		//					public void onClick(DialogInterface dialog,int id) {
		//						dialog.dismiss();
		//						System.exit(0);
		//					}
		//				});
		//				AlertDialog alertDialog = alertDialogBuilder.create();
		//				alertDialog.show();
		//			}
		//		}

	}

	private void effectuerConnexion(JsonDataLoader loader) {
		if(isNetworkAvailable(this)){		
			loader.execute(getApplicationContext());				
		}
		else{
			if(this.getFileStreamPath(JSONTags.FICHIER_ACTUS).exists()){
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
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
			else{
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity__chargement, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

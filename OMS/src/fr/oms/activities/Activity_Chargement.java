package fr.oms.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import fr.oms.dataloader.JsonDataLoader;

public class Activity_Chargement extends Activity {
	
	private  ProgressBar bar=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity__chargement);
		bar=(ProgressBar) findViewById(R.id.progressBar1);
		JsonDataLoader loader=JsonDataLoader.getInstance(this,bar);	
		effectuerConnexion(loader);
		
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
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Activity_Chargement.this);
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

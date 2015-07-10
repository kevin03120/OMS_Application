package fr.oms.dataloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;
import fr.oms.activities.Activity_Chargement;
import fr.oms.activities.MainActivity;
import fr.oms.activities.R;

public class JsonDataLoader extends AsyncTask<Context, Void, Void> implements iLoadData  {

	private static JsonDataLoader instance=null;
	private Map<String,String> urlsFichiers;
	private Context context=null;
	private ProgressBar barre=null;
	private TextView txtInfo;
	private boolean download = true;
	private HttpURLConnection connect;

	public JsonDataLoader(Context context, ProgressBar bar, TextView txtTitre) {
		barre=bar;
		this.context=context;
		this.txtInfo=txtTitre;

		urlsFichiers = new HashMap<String, String>();
		urlsFichiers.put("actus.json", "http://www.oms-clermont-ferrand.fr/api/v1/actus.json");
		urlsFichiers.put("evenements.json", "http://www.oms-clermont-ferrand.fr/api/v1/evenements.json");
		urlsFichiers.put("associations.json", "http://www.oms-clermont-ferrand.fr/api/v1/associations.json");
		urlsFichiers.put("equipements.json", "http://www.oms-clermont-ferrand.fr/api/v1/equipements.json");
	}

	public static JsonDataLoader getInstance(Context context, ProgressBar bar, TextView txt) {
		if(instance==null){
			return new JsonDataLoader(context,bar, txt);
		}
		return instance;
	}	



	@Override
	protected void onPreExecute() {
		super.onPreExecute();		
	}

	@Override
	protected Void doInBackground(Context... params) {

		try{
			if(!isCancelled()){
				loadAllFileFromServer(params[0]);
			}else{
				connect.disconnect();
			}
		} catch (Exception e) {
            e.printStackTrace();
        }
		
		

		//		JSONArray array=null;
		//		try {
		//			array=obj.getJSONArray("result");
		//			System.out.println(array.getJSONObject(0).getString("title"));
		//		} catch (JSONException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		txtInfo.setText(context.getResources().getString(R.string.Recuperation_donnees));
		if(download){
			Intent intent=new Intent(context, MainActivity.class);
			context.startActivity(intent);
			Activity_Chargement.actiCharg.finish();
		}else{
			if(this.context.getFileStreamPath(JSONTags.FICHIER_ACTUS).exists()){
				afficherDialogDejaFichier();
			}
			else{
				afficherDialogPasFichier();
			}	
		}
	}

	private void writeInLocalFile(FileOutputStream f, JSONObject jsonObj) {
		try {
			f.write(jsonObj.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void loadAllFileFromServer(Context context) {
		int cpt = 25;
		for(Map.Entry<String, String> entry : urlsFichiers.entrySet()){
			InputStream is=null;
			connect=null;
			URL url = null;
			JSONObject jsonObj=null;

			try {
				url = new URL(entry.getValue());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			try{
				connect = (HttpURLConnection) url.openConnection();
				connect.setConnectTimeout(5000);
				is = connect.getInputStream();
				//System.out.println(connect.getResponseCode());
			} catch (java.net.SocketTimeoutException e) {
				download = false;
				if(this.context.getFileStreamPath(JSONTags.FICHIER_ACTUS).exists()){
					afficherDialogDejaFichier();
				}
				else{
					afficherDialogPasFichier();
				}	
			} catch (java.io.IOException e) {
				
			}
			String contentAsString = convertStreamToString(is);
			try {
				jsonObj=new JSONObject(contentAsString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			FileOutputStream f = null;
			try {
				f = context.openFileOutput(entry.getKey(),Context.MODE_PRIVATE);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			writeInLocalFile(f,jsonObj);	
			barre.setProgress(cpt);
			cpt+=25;
		}
	}

	@Override
	public JSONObject LoadFile(File f) {
		BufferedReader reader=null;
		JSONObject jsObj=null;
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			reader=new BufferedReader(new FileReader(f));			
			while ((line=reader.readLine())!=null) {
				sb.append(line);			
			}
			jsObj=new JSONObject(sb.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
			try {
				if(reader!=null){
					reader.close();
				}				

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsObj;

	}

	@Override
	public String convertStreamToString(InputStream is) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"),8);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	private void afficherDialogPasFichier() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(R.string.detailAucuneDonnees);
		alertDialogBuilder
		.setMessage(context.getResources().getString(R.string.infoAucuneDonnees))
		.setCancelable(false)
		.setPositiveButton("Fermer l'application",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				Activity_Chargement.actiCharg.finish();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	/**
	 * Affiche un dialog lorsqu'il n'y a pas de connexion et que les fichiers de données sont présent.
	 */
	private void afficherDialogDejaFichier() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(R.string.detail_server_short);
		alertDialogBuilder
		.setMessage(context.getResources().getString(R.string.detail_server_long))
		.setCancelable(false)
		.setPositiveButton("Continuer",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.dismiss();
				Intent i = new Intent(context, MainActivity.class);
				Activity_Chargement.actiCharg.startActivity(i);
				Activity_Chargement.actiCharg.finish();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}


}

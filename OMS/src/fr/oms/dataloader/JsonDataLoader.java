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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import fr.oms.activities.MainActivity;

public class JsonDataLoader extends AsyncTask<Context, Void, Void> implements iLoadData  {
	
	private static JsonDataLoader instance=null;
	private Map<String,String> urlsFichiers;	
	private ProgressDialog progess=null;
	private Context context=null;
	
	public JsonDataLoader(Context context) {
		this.context=context;
		urlsFichiers = new HashMap<String, String>();
		urlsFichiers.put("actus.json", "http://www.oms-clermont-ferrand.fr/api/v1/actus.json");
		urlsFichiers.put("evenements.json", "http://www.oms-clermont-ferrand.fr/api/v1/evenements.json");
		urlsFichiers.put("associations.json", "http://www.oms-clermont-ferrand.fr/api/v1/associations.json");
		urlsFichiers.put("equipements.json", "http://www.oms-clermont-ferrand.fr/api/v1/equipements.json");
	}

	public static JsonDataLoader getInstance(Context context) {
		if(instance==null){
			return new JsonDataLoader(context);
		}
		return instance;
	}	
	


	@Override
	protected void onPreExecute() {
		super.onPreExecute();		
		progess=new ProgressDialog(context);
		progess.show();
	}
	
	@Override
	protected Void doInBackground(Context... params) {
		
		loadAllFileFromServer(params[0]);
					
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
		progess.dismiss();
		Intent intent=new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}
	
	private void writeInLocalFile(FileOutputStream f, JSONObject jsonObj) {
		try {
			f.write(jsonObj.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadAllFileFromServer(Context context) {
		for(Map.Entry<String, String> entry : urlsFichiers.entrySet()){
			InputStream is=null;
			HttpURLConnection connect=null;
			URL url = null;
			JSONObject jsonObj=null;
			try {
				url = new URL(entry.getValue());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			try {
				connect = (HttpURLConnection) url.openConnection();
				is = connect.getInputStream();
				//System.out.println(connect.getResponseCode());
			} catch (IOException e) {
				e.printStackTrace();
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

	
}

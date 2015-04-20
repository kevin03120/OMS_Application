package fr.oms.dataloader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import fr.oms.activities.MainActivity;

public class ClientHttp extends AsyncTask<FileOutputStream, Void, Void> {

	private HttpClient client=null;
	private HttpResponse response=null;
	private String monCookie;
	private ProgressDialog progressDialog;
	private MainActivity list;
	
	public ClientHttp(MainActivity acti){
		client = new DefaultHttpClient();
		
		this.list=acti;
	}	
	
	@Override
	protected void onPreExecute() {		
		progressDialog= ProgressDialog.show(list, "Synchronisation","Synchronisation des données en cours", true);
	}

	
	
	private void connexionViaPost(String path){

		HttpPost post = new HttpPost(path);

		// add header
		post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.36");

		//Déclaration des paramètres 
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("name", "q.septier"));
		urlParameters.add(new BasicNameValuePair("pass", "titi0206"));
		urlParameters.add(new BasicNameValuePair("form_build_id", "form-edf6b281c2de5cacf6b18aa2f50e6a42"));
		urlParameters.add(new BasicNameValuePair("form_id", "user_login"));
		urlParameters.add(new BasicNameValuePair("op", "Se+connecter"));

		//Envoie des parametres
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		//Récupération de la réponse
		try {
			response = client.execute(post);
			System.out.println("Response Code : "+ response.getStatusLine().getStatusCode());
			for(Header h:response.getAllHeaders()){
				System.out.println(h.toString());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		//création du cookie
		if(response.getHeaders("Set-Cookie") != null){
			//monCookie=response.getHeaders("Set-Cookie")[1].toString().split(";")[0].split(":")[1];
		}

		//System.out.println("MON COOKIE : "+monCookie);		
	}


	private void recupererFichierViaGet(String path){

		HttpGet request = new HttpGet(path);

		// add request header		
		request.addHeader("Host", "www.oms-clermont-ferrand.fr"); 
		request.addHeader("Connection", "keep-alive");
		request.addHeader("Cache-Control", "no-cache"); ; 
		request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"); 
		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.36"); 
		request.addHeader("Referer", "http://www.oms-clermont-ferrand.fr/utilisateurs/qseptier");
		request.addHeader("Accept-Encoding","gzip, deflate"); 
		request.addHeader("Accept-Language","fr-FR,fr;q=0.8,en-US;q=0.6,en;q=0.4");
		if(monCookie!=null){
			request.addHeader("Cookie",monCookie.toString());
		}


		//Réponse
		try {
			response = client.execute(request);
			System.out.println("Response Code : "+ response.getStatusLine().getStatusCode());
			System.out.println(response.toString());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Void doInBackground(FileOutputStream... params) {
		String UTF8 = "utf8";
		int BUFFER_SIZE = 8192;
		connexionViaPost("http://www.oms-clermont-ferrand.fr/user");
		recupererFichierViaGet("http://www.oms-clermont-ferrand.fr/admin/tableau-de-bord-associations/export.csv?status=1&field_club_adherent_value_many_to_one[0]=1&field_club_adherent_value_many_to_one[1]=0&field_club_pc_vil_value=&field_ref_categorie_nid=All&field_club_discipline_nid=All&field_ref_quartier_nid=All&field_club_lieu1_nid=All");
		try {
			BufferedReader bis = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), UTF8),BUFFER_SIZE);
			BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(params[0], UTF8),BUFFER_SIZE);
//			InputStreamReader bis = new InputStreamReader(response.getEntity().getContent(),"UTF-8");
//			OutputStreamWriter bos = new OutputStreamWriter((params[0]),Charset.forName("UTF-8").newEncoder());
			int inByte;
			while((inByte = bis.read()) != -1){
				bos.write(inByte);
				//System.out.println((char)inByte);
			}
			bis.close();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
        progressDialog.dismiss();
        InputStream instream = null;
		try {
			instream = list.openFileInput("testDonnees.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if (instream != null)
		{
			InputStreamReader inputreader = new InputStreamReader(instream); 
			BufferedReader buffreader = new BufferedReader(inputreader); 
			String line,line1 = "";
			try
			{
				while ((line = buffreader.readLine()) != null)
					line1+=line;
			}catch (Exception e) 
			{
				e.printStackTrace();
			}
			System.out.println(line1);
		}else{
			System.out.println("pas de stream");
		}			
	}
}
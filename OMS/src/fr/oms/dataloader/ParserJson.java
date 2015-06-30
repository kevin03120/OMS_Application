package fr.oms.dataloader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.ProgressBar;
import fr.oms.metier.Actualite;
import fr.oms.metier.Association;
import fr.oms.metier.Equipement;
import fr.oms.metier.Evenement;
import fr.oms.metier.Geolocalisation;
import fr.oms.metier.Personne;
import fr.oms.metier.Quartier;
import fr.oms.metier.Sport;
import fr.oms.modele.Manager;

public class ParserJson{

	private Context context;
	private int timeLastMaj;
	private ProgressBar pgr;

	public ParserJson(Context context) {
		this.context=context;
		parseEquipements();
		parseAssociations();
		parseActus();
		parseEvenements();
		Collections.sort(Manager.getInstance().getListeAssociation());
		Collections.sort(Manager.getInstance().getListeEquipement());
		Collections.sort(Manager.getInstance().getListeQuartier());
		Collections.sort(Manager.getInstance().getListEvenements());
		Collections.reverse(Manager.getInstance().getListEvenements());
		
		//		List<Association> assocs=Manager.getInstance().getListeAssociation();
//		System.out.println("TAILLE LISTE : "+Manager.getInstance().getListeQuartier().size());
	}

	private void parseActus(){
		JSONObject jsObj=JsonDataLoader.getInstance(null,null, null).LoadFile(context.getFileStreamPath(JSONTags.FICHIER_ACTUS));
		JSONArray jsArr=null;
		try {
			jsArr=jsObj.getJSONArray(JSONTags.RESULT);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		for(int i=0; i<jsArr.length();i++){
			try {
				JSONObject actuObj=jsArr.getJSONObject(i);
				int id=actuObj.getInt(JSONTags.IDENTIFIER);
				Actualite actuTmp=Manager.getInstance().recupereActu(id);		
				//Check si l'actu existe deja						
				if(actuTmp!=null){
					//Si elle existe => regarder date MAJ
					int timeObj=actuObj.getInt(JSONTags.CREATED);
					if(timeObj > timeLastMaj){
						//La date de mise a jour est plus récente => mettre à jour les données
						Manager.getInstance().getListActualites().remove(actuTmp);
						creerNouvelleActu(actuObj);
					}
				}else{
					//Nouvelle actu
					creerNouvelleActu(actuObj);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void creerNouvelleActu(JSONObject actuObj) {
		int id = 0;
		String title = "";
		String description = "";
		String image = "";
		Association assoc=null;
		try {
			id= actuObj.getInt(JSONTags.IDENTIFIER);
			title=actuObj.getString(JSONTags.TITLE);
			description=actuObj.getString(JSONTags.BODY);
			//description=Html.fromHtml(description).toString();			
//			System.out.println(description);
			image=actuObj.getString(JSONTags.IMAGE);
			assoc=Manager.getInstance().recupereAssociationAvecNom(actuObj.getString(JSONTags.ASSOCIATION));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Actualite actu=new Actualite(id, title, description, image, assoc);
		Manager.getInstance().getListActualites().add(actu);
	}

	private void parseEvenements(){
		JSONObject jsObj=JsonDataLoader.getInstance(null,null,null).LoadFile(context.getFileStreamPath(JSONTags.FICHIER_EVENTS));
		JSONArray jsArr=null;
		try {
			jsArr=jsObj.getJSONArray(JSONTags.RESULT);
		} catch (JSONException e) {
			e.printStackTrace();
		}
//		System.out.println("IL Y A EVENTS : "+jsArr.length());
		for(int i=0; i<jsArr.length();i++){
			try {
//				System.out.println("CURRENT : "+i);
				JSONObject eventObj=jsArr.getJSONObject(i);
				int id=eventObj.getInt(JSONTags.IDENTIFIER);
				Evenement eventTmp=Manager.getInstance().recupereEvenement(id);		
				if(eventTmp!=null){
					int timeObj=eventObj.getInt(JSONTags.CREATED);
					if(timeObj > timeLastMaj){
						Manager.getInstance().getListEvenements().remove(eventTmp);
						creerNouveauEvent(eventObj);
					}
				}else{
					creerNouveauEvent(eventObj);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void creerNouveauEvent(JSONObject eventObj) {
		int id = 0;
		String title = "";
		String image ="";
		Association association=null;
		String lieu1=null;
		Equipement lieu2=null;
		int date=0;
		
		String description="";
		int created=0;
		try {
			id= eventObj.getInt(JSONTags.IDENTIFIER);
			title=eventObj.getString(JSONTags.TITLE);
			image=eventObj.getString(JSONTags.IMAGE);
			association=Manager.getInstance().recupereAssociationAvecNom(eventObj.getString(JSONTags.ASSOCIATION));
			lieu1=eventObj.getString(JSONTags.LOCATION_01);
			lieu2=Manager.getInstance().recupereEquipementAvecNom(eventObj.getString(JSONTags.LOCATION_02));
			date=eventObj.getInt(JSONTags.DATE);
			description=eventObj.getString(JSONTags.BODY);
			//description=Html.fromHtml(description).toString();
			created=eventObj.getInt(JSONTags.CREATED);			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Evenement event=new Evenement(id, title, image, association, lieu1, lieu2, date, description, created);
		Manager.getInstance().getListEvenements().add(event);
	}

	private void parseEquipements(){
		JSONObject jsObj=JsonDataLoader.getInstance(null,null,null).LoadFile(context.getFileStreamPath(JSONTags.FICHIER_EQUIPS));
		JSONArray jsArr=null;
		try {
			jsArr=jsObj.getJSONArray(JSONTags.RESULT);
		} catch (JSONException e) {
			e.printStackTrace();
		}
//		System.out.println("IL Y A EQUIPS : "+jsArr.length());
		for(int i=0; i<jsArr.length();i++){
			try {
//				System.out.println("CURRENT : "+i);
				JSONObject equipObj=jsArr.getJSONObject(i);
				int id=equipObj.getInt(JSONTags.IDENTIFIER);
				Equipement equipTmp=Manager.getInstance().recupereEquipement(id);		
				if(equipTmp!=null){
					int timeObj=equipObj.getInt(JSONTags.CREATED);
					if(timeObj > timeLastMaj){
						Manager.getInstance().getListeEquipement().remove(equipTmp);
						creerNouveauEquip(equipObj);
					}
				}else{
					creerNouveauEquip(equipObj);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void creerNouveauEquip(JSONObject equipObj) {
		int id = 0;
		String title = "";
		Quartier quartier=null;
		Geolocalisation goeLoc=null;
		String address="";
		String codePostal="";
		String ville="";
		String telephone="";		
		try {
			id= equipObj.getInt(JSONTags.IDENTIFIER);
			title=equipObj.getString(JSONTags.TITLE);
			quartier=recupereQuartier(equipObj.getJSONArray(JSONTags.QUARTIER));

			goeLoc=recupereGeo(equipObj.getJSONObject(JSONTags.GEOLOC)) ;
			address=equipObj.getString(JSONTags.ADDRESS);
			codePostal=equipObj.getString(JSONTags.CODE_POSTAL);
			ville=equipObj.getString(JSONTags.VILLE);
			telephone=equipObj.getString(JSONTags.TEL);			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Equipement equip=new Equipement(id, title, address, codePostal, ville, telephone, goeLoc, quartier);
		if(quartier!=null){
			quartier.getMesEquipements().add(equip);
		}
		Manager.getInstance().getListeEquipement().add(equip);		
	}

	private Quartier recupereQuartier(JSONArray jsArr) {
		Quartier quartier=null;
		String nomQuartier="";
		for (int i=0;i<jsArr.length();i++){
			try {
				nomQuartier+=jsArr.getString(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if(i!=jsArr.length()-1){
				nomQuartier+=", ";
			}
		}		
		if(nomQuartier.trim()!=""){
			quartier=Manager.getInstance().recupereQuartierAPartirDuNom(nomQuartier);
			if (quartier==null) {
				quartier=new Quartier(Manager.getInstance().getListeQuartier().size(), nomQuartier, new ArrayList<Equipement>());
				Manager.getInstance().getListeQuartier().add(quartier);
			}
			return quartier;
		}


		return null;
	}

	private Geolocalisation recupereGeo(JSONObject jsonObject) {
		String nom="";
		String rue="";
		String ville="";
		String province="";
		String codePostal="";
		String pays="";
		String longitude="";
		String latitude="";
		try {
			nom=jsonObject.getString(JSONTags.GEO_NAME);
			rue=jsonObject.getString(JSONTags.GEO_RUE);
			ville=jsonObject.getString(JSONTags.GEO_VILLE);
			province=jsonObject.getString(JSONTags.GEO_PROVINCE);
			codePostal=jsonObject.getString(JSONTags.GEO_POSTAL);
			pays=jsonObject.getString(JSONTags.GEO_PAYS);
			longitude=jsonObject.getString(JSONTags.GEO_LONGITUDE);
			latitude=jsonObject.getString(JSONTags.GEO_LATITUDE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Geolocalisation geo=new Geolocalisation(nom, rue, ville, province, codePostal, pays, longitude, latitude);
		return geo;
	}

	private void parseAssociations(){
		JSONObject jsObj=JsonDataLoader.getInstance(null,null, null).LoadFile(context.getFileStreamPath(JSONTags.FICHIER_ASSOCS));
		JSONArray jsArr=null;
		try {
			jsArr=jsObj.getJSONArray(JSONTags.RESULT);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		for(int i=0; i<jsArr.length();i++){
			try {
				JSONObject assocObj=jsArr.getJSONObject(i);
				int id=assocObj.getInt(JSONTags.IDENTIFIER);
				Association assocTmp=Manager.getInstance().recupereAssociation(id);		
				if(assocTmp!=null){
					int timeObj=assocObj.getInt(JSONTags.CREATED);
					if(timeObj > timeLastMaj){
						Manager.getInstance().getListeAssociation().remove(assocTmp);
						creerNouvelleAssoc(assocObj);
					}
				}else{
					creerNouvelleAssoc(assocObj);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}



	private void creerNouvelleAssoc(JSONObject assocObj) {
		int id=0;
		String nom="";
		Boolean adherent=false;
		List<Equipement> listEquips=null;
		String horraire ="";
		List<Sport> listSports=null;
		Personne contact=null;
		try {
			id=assocObj.getInt(JSONTags.IDENTIFIER);
			nom=assocObj.getString(JSONTags.TITLE);
			if(assocObj.getInt(JSONTags.OMS_MEMBER)==1){
				adherent=true;
			}
			else{
				adherent = false;
			}
			horraire=assocObj.getString(JSONTags.HOURS);
			listEquips=recupererLesEquipements(assocObj);
			listSports=recupererLesSports(assocObj.getJSONArray(JSONTags.DISCIPLINES));
			contact = recupererLeContact(assocObj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Association assoc=new Association(id, nom, adherent, listEquips, horraire, listSports, contact);
		Manager.getInstance().getListeAssociation().add(assoc);
	}

	private Personne recupererLeContact(JSONObject assocObj) {
		String nom="";
		String prenom="";
		int id=0;
		String titre="";
		String add1="";
		String add2="";
		String codePostal="";
		String ville="";
		String email="";
		String telFixe="";
		String telPort="";
		String site ="";
		try {
			nom=assocObj.getString(JSONTags.CONTACT_NOM);
			prenom=assocObj.getString(JSONTags.CONTACT_PRENOM);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Personne pers=Manager.getInstance().recupererPersonneAvecNomPrenom(nom,prenom);
		if(pers==null){
			try {
				id=Manager.getInstance().getListPersonne().size();
				titre=assocObj.getString(JSONTags.CONTACT_TITLE);
				add1=assocObj.getString(JSONTags.CONTACT_ADDRESS_01);
				add2=assocObj.getString(JSONTags.CONTACT_ADDRESS_02);
				codePostal=assocObj.getString(JSONTags.CONTACT_CODE_POSTAL);
				ville=assocObj.getString(JSONTags.CONTACT_VILLE);
				email=assocObj.getString(JSONTags.CONTACT_MAIL);
				telFixe=assocObj.getString(JSONTags.CONTACT_TEL);
				telPort=assocObj.getString(JSONTags.CONTACT_TEL_MOBILE);
				site=assocObj.getString(JSONTags.CONTACT_WEBSITE);
				pers=new Personne(id, titre, nom, prenom, add1, add2, codePostal, ville, email, telFixe, telPort, site);
				Manager.getInstance().getListPersonne().add(pers);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return pers;
	}

	private List<Sport> recupererLesSports(JSONArray jsonArray) {
		ArrayList<Sport> sports=new ArrayList<Sport>();
		for(int i=0;i<jsonArray.length();i++){
			try {
				Sport s=new Sport(jsonArray.getString(i));
				sports.add(s);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return sports;
	}

	private List<Equipement> recupererLesEquipements(JSONObject assocObj) {
		List<Equipement> equipements=new ArrayList<Equipement>();
		String location01="";
		String location02="";
		try {
			location01=assocObj.getString(JSONTags.LOCATION_01);
			location02=assocObj.getString(JSONTags.LOCATION_02);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Equipement equip1=Manager.getInstance().recupereEquipementAvecNom(location01);
		Equipement equip2=Manager.getInstance().recupereEquipementAvecNom(location02);
		if(equip1!=null){
			equipements.add(equip1);
		}
		if(equip2!=null){
			equipements.add(equip2);
		}
		return equipements;
	}

	

	
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public int getLastMaj() {
		return timeLastMaj;
	}

	public void setLastMaj(int lastMaj) {
		this.timeLastMaj = lastMaj;
	}	

}

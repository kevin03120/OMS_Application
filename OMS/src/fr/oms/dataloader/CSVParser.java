package fr.oms.dataloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import fr.oms.activities.R;
import fr.oms.metier.Association;
import fr.oms.metier.Equipement;
import fr.oms.metier.Personne;
import fr.oms.metier.Quartier;
import fr.oms.metier.Sport;
import fr.oms.modele.Manager;

public class CSVParser {

	private BufferedReader br = null;
	private String line ="";
	private Pattern pattern=Pattern.compile("\\d{4}");

	public CSVParser(Context context) {
		Manager.getInstance().getTousLesSport(context);
		br=new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.export)));
	}


	public void readCSV(){

		Manager.getInstance().getListeAssociation().clear();;
		Manager.getInstance().getListeEquipement().clear();;
		Manager.getInstance().getListeQuartier().clear();;

		try {
			line=br.readLine();
			line=br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}	


		while(!line.equals("")){

			String ligneCourante="";
			ligneCourante+=line;

			//Récupère l'UID
			String uid=line.replace("\"", "").split(";")[0];
			Matcher matcher=pattern.matcher(uid);	

			try{
				//Si la ligne commence par %d%d%d%d
				if(matcher.matches()){

					line=br.readLine();
					if(line==null){
						break;
					}
					uid=line.replace("\"", "").split(";")[0];
					matcher=pattern.matcher(uid);

					//Tant que la ligne suivante ne commence pas par %d%d%d%d
					while(!matcher.matches()){
						ligneCourante+=line;
						line=br.readLine();
						if(line==null){
							break;
						}

						uid=line.replace("\"", "").split(";")[0];
						matcher=pattern.matcher(uid);
					}



					String[]mots=ligneCourante.replace("\"", "").split(";");	

					recupererAssociation(mots);
				}

			}catch(IOException e){
				e.printStackTrace();
			}

			if(line==null){
				System.out.println("Fin du parsing des données");
				System.out.println("Nombre d'associations récupérées : "+Manager.getInstance().getListeAssociation().size());
				break;
			}

		}
		Collections.sort(Manager.getInstance().getListeAssociation());
		Collections.sort(Manager.getInstance().getListeEquipement());
		Collections.sort(Manager.getInstance().getListeQuartier());
	}


	private void recupererAssociation(String[] mots) {
		// Index 0 = ID de l'association

		int id=Integer.valueOf(mots[0]);
		String nom=mots[1];
		boolean adherent=false;
		if(mots[3].equals("Adhérent")){
			adherent=true;
		}
		List<Equipement> equipements=recupererEquipement(mots);
		SportParser parser=new SportParser();
		List<Sport> sports=parser.parse(mots[8]);
		Personne personne=recupererPersonne(mots);
		String horraire="non communiqué";
		if(mots.length>20){
			horraire=mots[21];		
		}
		Manager.getInstance().getListeAssociation().add(new Association(id, nom, adherent, equipements, horraire, sports, personne));


	}


	private Personne recupererPersonne(String[] mots) {
		if(mots.length>30){
			Personne tmp = Manager.getInstance().recupereUnePersonneAPartirDuMail(mots[29]);
			if(tmp!=null){
				return tmp;
			}
			int id=Manager.getInstance().getListPersonne().size();
			String titre=mots[23];
			String nom=mots[24];
			String prenom=mots[25];
			String adresse=mots[26];
			String codePostal=mots[27];
			String ville = mots[28];
			String email=mots[29];
			String telfixe=mots[30];
			String telport=mots[31];

			//tmp=new Personne(id, titre, nom, prenom, adresse, codePostal, ville, email, telfixe, telport);
			Manager.getInstance().getListPersonne().add(tmp);

			return tmp;
		}
		return null;
	}


	private List<Equipement> recupererEquipement(String[] mots) {
		ArrayList<Equipement> listeEquipements=new ArrayList<Equipement>();
		Equipement equip1=Manager.getInstance().recupererEquipementAPartirDuNom(mots[6]);
		Equipement equip2=Manager.getInstance().recupererEquipementAPartirDuNom(mots[7]);

		if(equip1!=null && equip2!=null){
			listeEquipements.add(equip1);
			listeEquipements.add(equip2);
			return listeEquipements;
		}		

		if(equip1==null){
			//equip1=readEquipement(mots[6]);
		}

		if(equip2==null){
			//equip2=readEquipement(mots[7]);
		}			
		Quartier q=recupereQuartier(mots[5]);
		if(equip1!=null){
			
			if(q!=null){
				equip1.setQuartier(q);
				if(!Manager.getInstance().getListeQuartier().get(q.getUid()).getMesEquipements().contains(equip1)){
					Manager.getInstance().getListeQuartier().get(q.getUid()).getMesEquipements().add(equip1);
				}

			}
			listeEquipements.add(equip1);			
		}
		if(equip2!=null){
			listeEquipements.add(equip2);
		}	

		return listeEquipements;
	}


	private Quartier recupereQuartier(String mots) {
		if(!mots.equals("")){
			Quartier tmp=Manager.getInstance().recupereQuartierAPartirDuNom(mots);
			if(tmp!=null){
				return tmp;
			}else{
				int id=Manager.getInstance().getListeQuartier().size();
				System.out.println("ID "+id);
				String nom=mots;
				List<Equipement> listEquipements=new ArrayList<Equipement>();
				tmp=new Quartier(id, nom, listEquipements);
				Manager.getInstance().getListeQuartier().add(tmp);
				return tmp;
			}
		}	
		return null;
	}


	/*private Equipement readEquipement(String mots) {
		if(!mots.equals("")){
			int id=Manager.getInstance().getListeEquipement().size();
			String nom=mots;
			String adresse="Pas encore d'adresse";
			Quartier quartier=null;
			Equipement nouveauEquip=new Equipement(id, nom, adresse, quartier);
			Manager.getInstance().getListeEquipement().add(nouveauEquip);
			return nouveauEquip;
		}
		return null;
	}*/

}
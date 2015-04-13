package fr.oms.modele;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import fr.oms.activities.R;
import fr.oms.metier.Association;
import fr.oms.metier.Discipline;
import fr.oms.metier.Equipement;
import fr.oms.metier.Personne;
import fr.oms.metier.Quartier;
import fr.oms.metier.Sport;

public class Manager {

	private static Manager INSTANCE;
	private List<Discipline> listeDiscipline;
	private List<Association> listeAssociation;
	private List<Equipement> listeEquipement;
	private List<Quartier> listeQuartier;
	private List<Sport> listeSport;
	private List<Personne> listPersonne;
	private iAccesDonnees accesDonnees;
	
	private Manager(){
		listeDiscipline = new ArrayList<Discipline>();
		listeAssociation = new ArrayList<Association>();
		listeEquipement = new ArrayList<Equipement>();
		listeQuartier = new ArrayList<Quartier>();
		listeSport = new ArrayList<Sport>();
		listPersonne = new ArrayList<Personne>();
		//getTousLesSport();
	}
	
	private void remplieListeDiscipline(Context context){
		listeDiscipline.clear();
		Discipline discipline = new Discipline(1, "Activités de détente et d'entretien", null);
		listeDiscipline.add(discipline);
		discipline = new Discipline(2, "Multisports - Omnisports", null);
		listeDiscipline.add(discipline);
		discipline = new Discipline(3, "Sport Collectif en Salle", null);
		listeDiscipline.add(discipline);
		discipline = new Discipline(4, "Sport Collectif Extérieur", null);
		listeDiscipline.add(discipline);
		discipline = new Discipline(5, "Sport d'eau", null);
		listeDiscipline.add(discipline);
		discipline = new Discipline(6, "Sport de Combat", null);
		listeDiscipline.add(discipline);
		discipline = new Discipline(7, "Sport de nature", null);
		listeDiscipline.add(discipline);
		discipline = new Discipline(8, "Sport de neige ou de glace", null);
		listeDiscipline.add(discipline);
		discipline = new Discipline(9, "Sport Individuel en salle", null);
		listeDiscipline.add(discipline);
		discipline = new Discipline(10, "Sport Individuel Extérieur", null);
		listeDiscipline.add(discipline);
		discipline = new Discipline(11, "Sports Mécaniques", null);
		listeDiscipline.add(discipline);
		discipline = new Discipline(12, "Pratiques Adaptées", null);
		listeDiscipline.add(discipline);
		remplieListSportDiscipline(context);
	}
	
	private void remplieListSportDiscipline(Context context){
		BufferedReader br = null;
		String line="";	
		int numDiscipline = 0;
		Reader reader = null;
		try {
			reader = new InputStreamReader(context.getResources().openRawResource(R.raw.sport_discipline), "UTF8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (NotFoundException e1) {
			e1.printStackTrace();
		}

		br = new BufferedReader(reader);

		try {
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (line!=null && !line.equals("")) {
			if(line.contains("d.")){
				numDiscipline = 0;
				line = line.replace("d.", "");
				for(Discipline d : listeDiscipline){
					if(d.getNom().equals(line)){
						numDiscipline = d.getUid();
					}
				}
			}
			else{
				line = line.replace("s.", "");
				for(Sport s : listeSport){
					if(s.getNom().equals(line)){
						for(Discipline d : listeDiscipline){
							if(d.getUid()==numDiscipline){
								d.getListeSport().add(s);
							}
						}
					}
				}
			}
			try {
				line=br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clearDonnees(){
		listeAssociation.clear();
		listeDiscipline.clear();
		listeEquipement.clear();
		listeQuartier.clear();
		listeSport.clear();
	}
	
	public void getTousLesSport(Context context) {
		BufferedReader br = null;
		String line="";		
		int idSport=0;
		
		try {
			br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.sports), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (NotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		while (line!=null && !line.equals("")) {
			listeSport.add(new Sport(idSport,line.trim()));		
			try {
				line=br.readLine();
				idSport++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		remplieListeDiscipline(context);
	}

	public static Manager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new Manager();
		}
		return INSTANCE;
	}
	

	public List<Discipline> getListeDiscipline() {
		return listeDiscipline;
	}

	public void setListeDiscipline(List<Discipline> listeDiscipline) {
		this.listeDiscipline = listeDiscipline;
	}

	public List<Association> getListeAssociation() {
		return listeAssociation;
	}

	public void setListeAssociation(List<Association> listeAssociation) {
		this.listeAssociation = listeAssociation;
	}

	public List<Equipement> getListeEquipement() {
		return listeEquipement;
	}

	public void setListeEquipement(List<Equipement> listeEquipement) {
		this.listeEquipement = listeEquipement;
	}

	public List<Quartier> getListeQuartier() {
		return listeQuartier;
	}

	public void setListeQuartier(List<Quartier> listeQuartier) {
		this.listeQuartier = listeQuartier;
	}

	public iAccesDonnees getAccesDonnees() {
		return accesDonnees;
	}

	public void setAccesDonnees(iAccesDonnees accesDonnees) {
		this.accesDonnees = accesDonnees;
	}
	
	public void lireDonnees(){
		accesDonnees.lireDonnees();
	}

	public List<Sport> getListeSport() {
		return listeSport;
	}

	public void setListeSport(List<Sport> listeSport) {
		this.listeSport = listeSport;
	}

	public Sport recupereLeSport(String compare) {
		for(Sport s:listeSport){
			if(s.getNom().equals(compare)){
				return s;
			}
		}
		return null;
	}

	public List<Personne> getListPersonne() {
		return listPersonne;
	}

	public void setListPersonne(List<Personne> listPersonne) {
		this.listPersonne = listPersonne;
	}

	public Personne recupereUnePersonneAPartirDuMail(String mail) {
		for(Personne p : listPersonne){
			if(p.getEmail().equals(mail)){
				return p;
			}
		}
		return null;
	}

	public Equipement recupererEquipementAPartirDuNom(String nom) {
		for(Equipement e:listeEquipement){
			if(e.getNom().equals(nom)){
				return e;
			}
		}
		return null;
	}

	public Quartier recupereQuartierAPartirDuNom(String nom) {
		for(Quartier q:listeQuartier){
			if(q.getNom().equals(nom)){
				return q;
			}
		}
		return null;
	}

}

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
import fr.oms.dataloader.iLoadData;
import fr.oms.metier.Actualite;
import fr.oms.metier.Association;
import fr.oms.metier.Discipline;
import fr.oms.metier.Equipement;
import fr.oms.metier.Evenement;
import fr.oms.metier.Personne;
import fr.oms.metier.Quartier;
import fr.oms.metier.Sport;

public class Manager {

	private static Manager INSTANCE;
	private List<Discipline> listeDisciplines;
	private List<Association> listeAssociations;
	private List<Equipement> listeEquipements;
	private List<Quartier> listeQuartiers;
	private List<Sport> listeSports;
	private List<Personne> listPersonnes;
	private List<Actualite> listActualites;
	private List<Evenement> listEvenements;
	private iLoadData accesDonnees;
	
	private Manager(){
		listeDisciplines = new ArrayList<Discipline>();
		listeAssociations = new ArrayList<Association>();
		listeEquipements = new ArrayList<Equipement>();
		listeQuartiers = new ArrayList<Quartier>();
		listeSports = new ArrayList<Sport>();
		listPersonnes = new ArrayList<Personne>();
		setListActualites(new ArrayList<Actualite>());
		setListEvenements(new ArrayList<Evenement>());
		//getTousLesSport();
	}
	
	private void remplieListeDiscipline(Context context){
		listeDisciplines.clear();
		Discipline discipline = new Discipline(1, "Activités de détente et d'entretien", null);
		listeDisciplines.add(discipline);
		discipline = new Discipline(2, "Multisports - Omnisports", null);
		listeDisciplines.add(discipline);
		discipline = new Discipline(3, "Sport Collectif en Salle", null);
		listeDisciplines.add(discipline);
		discipline = new Discipline(4, "Sport Collectif Extérieur", null);
		listeDisciplines.add(discipline);
		discipline = new Discipline(5, "Sport d'eau", null);
		listeDisciplines.add(discipline);
		discipline = new Discipline(6, "Sport de Combat", null);
		listeDisciplines.add(discipline);
		discipline = new Discipline(7, "Sport de nature", null);
		listeDisciplines.add(discipline);
		discipline = new Discipline(8, "Sport de neige ou de glace", null);
		listeDisciplines.add(discipline);
		discipline = new Discipline(9, "Sport Individuel en salle", null);
		listeDisciplines.add(discipline);
		discipline = new Discipline(10, "Sport Individuel Extérieur", null);
		listeDisciplines.add(discipline);
		discipline = new Discipline(11, "Sports Mécaniques", null);
		listeDisciplines.add(discipline);
		discipline = new Discipline(12, "Pratiques Adaptées", null);
		listeDisciplines.add(discipline);
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
				for(Discipline d : listeDisciplines){
					if(d.getNom().equals(line)){
						numDiscipline = d.getUid();
					}
				}
			}
			else{
				line = line.replace("s.", "");
				for(Sport s : listeSports){
					if(s.getNom().equals(line)){
						for(Discipline d : listeDisciplines){
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
		listeAssociations.clear();
		listeDisciplines.clear();
		listeEquipements.clear();
		listeQuartiers.clear();
		listeSports.clear();
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
			listeSports.add(new Sport(line.trim()));		
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
	
	public Actualite recupereActu(int idActu){
		for(Actualite actu : listActualites){
			if (actu.getId() == idActu){
				return actu;
			}
		}
		return null;
	}
	
	public Evenement recupereEvenement(int idEvent){
		for (Evenement event : listEvenements){
			if (event.getId()==idEvent){
				return event;
			}
		}
		return null;
	}
	
	public Equipement recupereEquipement (int idEquip){
		for (Equipement equip : listeEquipements){
			if(equip.getUid()==idEquip){
				return equip;
			}
		}
		return null;
	}
	
	public Association recupereAssociation(int idAssoc){
		for (Association assoc : listeAssociations){
			if(assoc.getId()==idAssoc){
				return assoc;
			}
		}
		return null;
	}

	public List<Discipline> getListeDiscipline() {
		return listeDisciplines;
	}

	public void setListeDiscipline(List<Discipline> listeDiscipline) {
		this.listeDisciplines = listeDiscipline;
	}

	public List<Association> getListeAssociation() {
		return listeAssociations;
	}

	public void setListeAssociation(List<Association> listeAssociation) {
		this.listeAssociations = listeAssociation;
	}

	public List<Equipement> getListeEquipement() {
		return listeEquipements;
	}

	public void setListeEquipement(List<Equipement> listeEquipement) {
		this.listeEquipements = listeEquipement;
	}

	public List<Quartier> getListeQuartier() {
		return listeQuartiers;
	}

	public void setListeQuartier(List<Quartier> listeQuartier) {
		this.listeQuartiers = listeQuartier;
	}

	public iLoadData getAccesDonnees() {
		return accesDonnees;
	}

	public void setAccesDonnees(iLoadData accesDonnees) {
		this.accesDonnees = accesDonnees;
	}
	
	public void lireDonnees(){
		//accesDonnees.loadAllFileFromServer();;
	}

	public List<Sport> getListeSport() {
		return listeSports;
	}

	public void setListeSport(List<Sport> listeSport) {
		this.listeSports = listeSport;
	}

	public List<Personne> getListPersonne() {
		return listPersonnes;
	}

	public void setListPersonne(List<Personne> listPersonne) {
		this.listPersonnes = listPersonne;
	}

	public Quartier recupereQuartierAPartirDuNom(String nom) {
		for(Quartier q:listeQuartiers){
			if(q.getNom().equals(nom)){
				return q;
			}
		}
		return null;
	}

	public List<Actualite> getListActualites() {
		return listActualites;
	}

	public void setListActualites(List<Actualite> listActualites) {
		this.listActualites = listActualites;
	}

	public List<Evenement> getListEvenements() {
		return listEvenements;
	}

	public void setListEvenements(List<Evenement> listEvenements) {
		this.listEvenements = listEvenements;
	}

	public Association recupereAssociationAvecNom(String nom) {
		for(Association assoc : listeAssociations){
			if(assoc.getNom().equals(nom)){
				return assoc;
			}
		}
		return null;
	}

	public Equipement recupereEquipementAvecNom(String nom) {
		for(Equipement equip : listeEquipements){
			if(equip.getNom().equals(nom)){
				return equip;
			}
		}
		return null;
	}

	public Personne recupererPersonneAvecNomPrenom(String nom, String prenom) {
		for(Personne p : listPersonnes){
			if(p.getNom().equals(nom) && p.getPrenom().equals(prenom)){
				return p;
			}
		}
		return null;
	}

}

package fr.oms.metier;

import java.util.List;

public class Association implements Comparable<Association> {

	private int uid;
	private String nom;
	private boolean adherent;
	private List<Equipement> listeEquipement;
	private String horraire;
	private List<Sport> listeSport;
	private Personne contact;
	
	public Association(int unId, String unNom, boolean adherent, List<Equipement> uneListeEquipement, String horraire,List<Sport> uneListeSport, Personne unContact){
		uid = unId;
		nom = unNom;
		this.adherent = adherent;
		if(uneListeEquipement.size() > 0){
			listeEquipement = uneListeEquipement;
		}
		else{
			listeEquipement = null;
		}
		setHorraire(horraire);
		setListeSport(uneListeSport);
		contact = unContact;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isAdherent() {
		return adherent;
	}

	public void setAdherent(boolean adherent) {
		this.adherent = adherent;
	}

	public List<Equipement> getListeEquipement() {
		return listeEquipement;
	}

	public void setListeEquipement(List<Equipement> listeEquipement) {
		this.listeEquipement = listeEquipement;
	}

	public Personne getContact() {
		return contact;
	}

	public void setContact(Personne contact) {
		this.contact = contact;
	}

	public List<Sport> getListeSport() {
		return listeSport;
	}

	public void setListeSport(List<Sport> listeSport) {
		this.listeSport = listeSport;
	}
	public String getHorraire() {
		return horraire;
	}

	public void setHorraire(String horraire) {
		this.horraire = horraire;
	}

	@Override
	public int compareTo(Association another) {
		int i = nom.compareTo(another.getNom());
	        if(i != 0) {            
	              return i;
	        }       
		return 0;
	}
	
	
}

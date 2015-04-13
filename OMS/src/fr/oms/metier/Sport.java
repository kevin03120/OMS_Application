package fr.oms.metier;

public class Sport {

	private int uid;
	private String nom;
	
	public Sport(int unId, String unNom){
		uid = unId;
		nom = unNom;
	}

	public int getId() {
		return uid;
	}

	public void setId(int id) {
		this.uid = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
}

package fr.oms.metier;


public class Equipement implements Comparable<Equipement> {

	private int uid;
	private String nom;
	private String adresse;
	private Quartier quartier;
	
	public Equipement(int unId, String unNom, String uneAdresse, Quartier unQuartier){
		uid = unId;
		nom = unNom;
		adresse = uneAdresse;
		quartier = unQuartier;
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

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Quartier getQuartier() {
		return quartier;
	}

	public void setQuartier(Quartier quartier) {
		this.quartier = quartier;
	}

	@Override
	public int compareTo(Equipement another) {
		int i = nom.compareTo(another.getNom());
        if(i != 0) {            
              return i;
        }       
	return 0;
	}
	
	
}

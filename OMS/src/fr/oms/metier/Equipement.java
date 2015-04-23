package fr.oms.metier;


public class Equipement implements Comparable<Equipement> {

	private int uid;
	private String nom;
	private String adresse;
	private String codePostal;
	private String ville;
	private String telephone;
	private Geolocalisation geoloc;
	private Quartier quartier;
	
	public Equipement(int unId, String unNom, String uneAdresse, String unCodePostal, String uneVille, String unTelephone, Geolocalisation uneGeoloc, Quartier unQuartier){
		setUid(unId);
		setNom(unNom);
		setAdresse(uneAdresse);
		setCodePostal(unCodePostal);
		setVille(uneVille);
		setTelephone(unTelephone);
		setGeoloc(uneGeoloc);
		setQuartier(unQuartier);
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Geolocalisation getGeoloc() {
		return geoloc;
	}

	public void setGeoloc(Geolocalisation geoloc) {
		this.geoloc = geoloc;
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

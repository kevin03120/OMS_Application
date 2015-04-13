package fr.oms.metier;

public class Personne {

	private int uid;
	private String titre;
	private String nom;
	private String prenom;
	private String adresse;
	private String codePostal;
	private String ville;
	private String email;
	private String telFixe;
	private String telPortable;
	
	public Personne(int unId, String unTitre, String unNom, String unPrenom, String uneAdresse, String unCodePostal, String uneVille, String unEmail, String unTelFixe, String unTelPortable){
		uid = unId;
		titre = unTitre;
		nom = unNom;
		prenom = unPrenom;
		adresse = uneAdresse;
		codePostal = unCodePostal;
		ville = uneVille;
		email = unEmail;
		telFixe= unTelFixe;
		telPortable = unTelPortable;
	}
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelFixe() {
		return telFixe;
	}

	public void setTelFixe(String telFixe) {
		this.telFixe = telFixe;
	}

	public String getTelPortable() {
		return telPortable;
	}

	public void setTelPortable(String telPortable) {
		this.telPortable = telPortable;
	}
}

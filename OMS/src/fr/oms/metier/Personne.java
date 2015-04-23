package fr.oms.metier;

public class Personne {

	private int uid;
	private String titre;
	private String nom;
	private String prenom;
	private String adresse1;
	private String adresse2;
	private String codePostal;
	private String ville;
	private String email;
	private String telFixe;
	private String telPortable;
	private String lienSiteWeb;
	
	public Personne(int unId, String unTitre, String unNom, String unPrenom, String uneAdresse1, String uneAdresse2, String unCodePostal, String uneVille, String unEmail, String unTelFixe, String unTelPortable, String unLienSite){
		setUid(unId);
		setTitre(unTitre);
		setNom(unNom);
		setPrenom(unPrenom);
		setAdresse1(uneAdresse1);
		setAdresse2(uneAdresse2);
		setCodePostal(unCodePostal);
		setVille(uneVille);
		setEmail(unEmail);
		setTelFixe(unTelFixe);
		setTelPortable(unTelPortable);
		setLienSiteWeb(unLienSite);
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

	public String getAdresse1() {
		return adresse1;
	}

	public void setAdresse1(String adresse1) {
		this.adresse1 = adresse1;
	}

	public String getAdresse2() {
		return adresse2;
	}

	public void setAdresse2(String adresse2) {
		this.adresse2 = adresse2;
	}

	public String getLienSiteWeb() {
		return lienSiteWeb;
	}

	public void setLienSiteWeb(String lienSiteWeb) {
		this.lienSiteWeb = lienSiteWeb;
	}
}

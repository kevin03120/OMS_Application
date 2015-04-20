package fr.oms.metier;

public class Geolocalisation {

	private String nom;
	private String rue;
	private String ville;
	private String province;
	private String codePostal;
	private String pays;
	private String longitude;
	private String latitude;
	
	public Geolocalisation(String unNom, String uneRue, String uneVille, String uneProvince, String unCodePostal, String unPays, String uneLongitude, String uneLatitude){
		setNom(unNom);
		setRue(uneRue);
		setVille(uneVille);
		setProvince(uneProvince);
		setCodePostal(unCodePostal);
		setPays(unPays);
		setLongitude(uneLongitude);
		setLatitude(uneLatitude);
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}

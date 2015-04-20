package fr.oms.metier;

public class Evenement {

	private int id;
	private String titre;
	private String lienImage;
	private Association associationConcernee;
	private Equipement lieu1;
	private Equipement lieu2;
	private int date;
	private String description;
	private int creation;
	
	public Evenement(int unId, String unTitre, String unLienImage, Association uneAssociationConcernee, Equipement unLieu1, Equipement unLieu2, int uneDate, String uneDescription, int uneCreation ){
		setId(unId);
		setTitre(unTitre);
		setLienImage(unLienImage);
		setAssociationConcernee(uneAssociationConcernee);
		setLieu1(unLieu1);
		setLieu2(unLieu2);
		setDate(uneDate);
		setDescription(uneDescription);
		setCreation(uneCreation);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getLienImage() {
		return lienImage;
	}
	public void setLienImage(String lienImage) {
		this.lienImage = lienImage;
	}
	public Association getAssociationConcernee() {
		return associationConcernee;
	}
	public void setAssociationConcernee(Association associationConcernee) {
		this.associationConcernee = associationConcernee;
	}
	public Equipement getLieu1() {
		return lieu1;
	}
	public void setLieu1(Equipement lieu1) {
		this.lieu1 = lieu1;
	}
	public Equipement getLieu2() {
		return lieu2;
	}
	public void setLieu2(Equipement lieu2) {
		this.lieu2 = lieu2;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getCreation() {
		return creation;
	}
	
	public void setCreation(int creation) {
		this.creation = creation;
	}
	
}

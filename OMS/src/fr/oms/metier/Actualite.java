package fr.oms.metier;

public class Actualite {
	
	private int id;
	private String titre;
	private String description;
	private String lienImage;
	private Association associationConcernee;
	

	public Actualite(int unId, String unTitre, String uneDescription, String unLienImage, Association uneAssociationConcerncee){
		setId(unId);
		setTitre(unTitre);
		setDescription(uneDescription);
		setLienImage(unLienImage);
		setAssociationConcernee(uneAssociationConcerncee);
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
}

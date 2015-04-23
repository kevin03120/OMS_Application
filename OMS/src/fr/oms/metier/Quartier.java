package fr.oms.metier;

import java.util.List;

public class Quartier implements Comparable<Quartier> {

	private int uid;
	private String nom;
	private List<Equipement> mesEquipements;
	
	public Quartier(int unId, String unNom, List<Equipement> lesEquipements){
		setUid(unId);
		setNom(unNom);
		setMesEquipements(lesEquipements);
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

	public List<Equipement> getMesEquipements() {
		return mesEquipements;
	}

	public void setMesEquipements(List<Equipement> mesEquipements) {
		this.mesEquipements = mesEquipements;
	}

	@Override
	public int compareTo(Quartier another) {
		int i = nom.compareTo(another.getNom());
        if(i != 0) {            
              return i;
        }       
	return 0;
	}
}

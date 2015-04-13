package fr.oms.dataloader;

import java.util.ArrayList;
import java.util.List;

import fr.oms.metier.Equipement;
import fr.oms.metier.Quartier;
import fr.oms.modele.Manager;
import fr.oms.modele.iAccesDonnees;

public class GestionDonnees implements iAccesDonnees{

	@Override
	public void lireDonnees() {
		Manager.getInstance().getListeAssociation().clear();
		Manager.getInstance().getListeEquipement().clear();
		Manager.getInstance().getListeDiscipline().clear();
		Manager.getInstance().getListeQuartier().clear();
		List<Equipement> lesEquipements = new ArrayList<Equipement>();
		Equipement equipement = new Equipement(0, "Salle 1", "", null);
		lesEquipements.add(equipement);
		Manager.getInstance().getListeEquipement().add(equipement);
		equipement = new Equipement(1, "Gymnase 1", "", null);
		lesEquipements.add(equipement);
		Manager.getInstance().getListeEquipement().add(equipement);
		equipement = new Equipement(2, "Stade Marcel Michelin", "", null);
		lesEquipements.add(equipement);
		Manager.getInstance().getListeEquipement().add(equipement);
		equipement = new Equipement(3, "Parc des sports", "", null);
		lesEquipements.add(equipement);
		Manager.getInstance().getListeEquipement().add(equipement);
		equipement = new Equipement(4, "Boulodrome", "", null);
		lesEquipements.add(equipement);
		Manager.getInstance().getListeEquipement().add(equipement);
		equipement = new Equipement(5, "Salle 2", "", null);
		lesEquipements.add(equipement);
		Manager.getInstance().getListeEquipement().add(equipement);
		
		Quartier quartier = new Quartier(0, "Croix de Neyrat", lesEquipements);
		Manager.getInstance().getListeQuartier().add(quartier);
		quartier = new Quartier(1, "Jaude", lesEquipements);
		Manager.getInstance().getListeQuartier().add(quartier);
		quartier = new Quartier(2, "Montferrand", lesEquipements);
		Manager.getInstance().getListeQuartier().add(quartier);
		quartier = new Quartier(3, "Saint Jacques", lesEquipements);
		Manager.getInstance().getListeQuartier().add(quartier);
	}

}
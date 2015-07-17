package fr.oms.fragments;

import fr.oms.activities.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MentionsFragment extends Fragment {
	
	private static final String conditionsGeneralesUtilisation ="Par le biais de cette base de données réalisée grâce au soutien de la Ville de Clermont-Ferrand, l'Office Municipal du Sport de Clermont-Ferrand dispose d'un site internet qui permette à tout(e)s les habitant(e)s de l’agglomération clermontoise de trouver les informations rassemblées dans le guide papier « Sport à Clermont ». Ce site permet de faire connaître le sport à Clermont-Ferrand, ses lieux de pratique, ses clubs et de retrouver en ligne l’annuaire des associations sportives de Clermont-Ferrand.\nToute utilisation/reproduction/modification pour une utilisation dans le cadre d'un produit payant (prestation vendue, édition d'un livre, etc) ou à objectif publicitaire/promotionnel (site web, flyer, prospectus, etc) ou de toute action à but lucratif en général est strictement interdite sans l'accord préalable de l'Office Municipal du Sport de Clermont-Ferrand.";
	private static final String informationsEditeur = "Ce site Internet est la propriété de l'Office Municipal du Sport de Clermont-Ferrand dont le siège social est situé :\nPavillon du Stade Philippe MARCOMBES - 121 Avenue de la Libération\n63 000 Clermont-Ferrand\nTél : 04 73 35 15 53\nLe Directeur de la publication du site Internet est Monsieur le Président de l'OMS\nLes mises à jour sont assurées par la Commission Promotion et Nouvelles Technologie de l'O.M.S. \nPour toute suggestion, information, réaction concernant ce site, n’hésitez pas à écrire au webmestre via le formulaire de contact.";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.activity_mentions, container,false);
		TextView conditions=(TextView) v.findViewById(R.id.txt_conditions);
		conditions.setText(conditionsGeneralesUtilisation);
		TextView editeur=(TextView) v.findViewById(R.id.txt_editeur);
		editeur.setText(informationsEditeur);
		return v;
	}
}

package fr.oms.fragments;

import fr.oms.activities.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MentionsFragment extends Fragment {
	
	private static final String conditionsGeneralesUtilisation ="Par le biais de cette base de données réalisée grâce au soutien de la Ville de Clermont-Ferrand, l'Office Municipal du Sport de Clermont-Ferrand dispose d’une application qui permet à tous les habitants de l’agglomération clermontoise de trouver les informations rassemblées dans le guide papier « Sport à Clermont ». Cette application permet de faire connaître le sport à Clermont-Ferrand, ses lieux de pratique, ses clubs et de retrouver en ligne l’annuaire des associations sportives de Clermont-Ferrand.\nToute utilisation/reproduction/modification pour une utilisation dans le cadre d'un produit payant (prestation vendue, édition d'un livre, etc) ou à objectif publicitaire/promotionnel (site web, flyer, prospectus, etc) ou de toute action à but lucratif en général est strictement interdite sans l'accord préalable de l'Office Municipal du Sport de Clermont-Ferrand.";
	private static final String informationsEditeur = "Cette application est la propriété de l'Office Municipal du Sport de Clermont-Ferrand dont le siège social est situé :\nPavillon du Stade Philippe MARCOMBES - 121 Avenue de la Libération\n63 000 Clermont-Ferrand\nTél : 04 73 35 15 53\nLe Directeur de la publication du site Internet est Monsieur le Président de l'OMS\nLes mises à jour sont assurées par la Commission Promotion et Nouvelles Technologie de l'O.M.S. \nPour toute suggestion, information, réaction, n’hésitez pas à écrire au webmaster via oms.clermont@gmail.com.";
	private static final String informationsTechnique ="- Conception et réalisation de l’application : Kévin MALLERET et Quentin SEPTIER, étudiants en Licence Professionnelle Développement d’Applications sur Plateformes Mobiles à l’IUT de Clermont-Ferrand. Avec la participation de Guillaume VRILLIAUX.";
	private static final String informationsJuridique = "L’utilisateur s’engage pour sa part à respecter les règles suivantes :\n\n- Clause de non responsabilité\nLa présente application ne peut garantir l’exactitude de toutes les informations contenues. Cependant, l’éditeur s’efforcera de diffuser des informations exactes et à jour, ainsi que de corriger les erreurs qui lui seront signalées.\n\n- Crédits iconographiques\nLe logo de l'Office Municipal du Sport de Clermont-Ferrand, ainsi que toute iconographie qui la caractérise, ne peut et ne doit être utilisé sans l’accord explicite de l'O.M.S.\n\n- Traitement des données personnelles\nConcernant les associations sportives mentionnées, les informations recueillies sont nécessaires pour votre adhésion. Elles font l'objet d'un traitement informatique et sont destinées au secrétariat de l'association et à l’actualisation du site internet de l’O.M.S. de Clermont-Ferrand. En application des articles 39 et suivants de la loi du 6 janvier 1978 modifiée, les associations bénéficient d'un droit d'accès et de rectification aux informations qui les concernent. \nSi elles souhaitent exercer ce droit et obtenir communication des informations vous concernant, elles devront s'adresse adresser au Secrétariat de l’O.M.S., 121, Avenue de la Libération à Clermont-Ferrand ou par mail : oms.clermont@gmail.com ";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.activity_mentions, container,false);
		TextView conditions=(TextView) v.findViewById(R.id.txt_conditions);
		conditions.setText(conditionsGeneralesUtilisation);
		TextView editeur=(TextView) v.findViewById(R.id.txt_editeur);
		editeur.setText(informationsEditeur);
		TextView technique=(TextView) v.findViewById(R.id.txt_technique);
		technique.setText(informationsTechnique);
		TextView juridique=(TextView) v.findViewById(R.id.txt_juridique);
		juridique.setText(informationsJuridique);
		return v;
	}
}

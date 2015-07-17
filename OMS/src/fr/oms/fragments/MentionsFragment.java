package fr.oms.fragments;

import fr.oms.activities.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MentionsFragment extends Fragment {
	
	private static final String conditionsGeneralesUtilisation ="Par le biais de cette base de donn�es r�alis�e gr�ce au soutien de la Ville de Clermont-Ferrand, l'Office Municipal du Sport de Clermont-Ferrand dispose d�une application qui permette � tout(e)s les habitant(e)s de l�agglom�ration clermontoise de trouver les informations rassembl�es dans le guide papier � Sport � Clermont �. Cette application permet de faire conna�tre le sport � Clermont-Ferrand, ses lieux de pratique, ses clubs et de retrouver en ligne l�annuaire des associations sportives de Clermont-Ferrand.\nToute utilisation/reproduction/modification pour une utilisation dans le cadre d'un produit payant (prestation vendue, �dition d'un livre, etc) ou � objectif publicitaire/promotionnel (site web, flyer, prospectus, etc) ou de toute action � but lucratif en g�n�ral est strictement interdite sans l'accord pr�alable de l'Office Municipal du Sport de Clermont-Ferrand.";
	private static final String informationsEditeur = "Cette application est la propri�t� de l'Office Municipal du Sport de Clermont-Ferrand dont le si�ge social est situ� :\nPavillon du Stade Philippe MARCOMBES - 121 Avenue de la Lib�ration\n63 000 Clermont-Ferrand\nT�l : 04 73 35 15 53\nLe Directeur de la publication du site Internet est Monsieur le Pr�sident de l'OMS\nLes mises � jour sont assur�es par la Commission Promotion et Nouvelles Technologie de l'O.M.S. \nPour toute suggestion, information, r�action, n�h�sitez pas � �crire au webmaster via oms.clermont@gmail.com.";
	private static final String informationsTechnique ="- Conception et r�alisation de l�application : K�vin MALLERET et Quentin SEPTIER, �tudiants en Licence Professionnelle D�veloppement d�Applications sur Plateformes Mobiles � l�IUT de Clermont-Ferrand. Avec la participation de Guillaume VRILLIAUX.";
	private static final String informationsJuridique = "L�utilisateur s�engage pour sa part � respecter les r�gles suivantes :\n\n- Clause de non responsabilit�\nLa pr�sente application ne peut garantir l�exactitude de toutes les informations contenues. Cependant, l��diteur s�efforcera de diffuser des informations exactes et � jour, ainsi que de corriger les erreurs qui lui seront signal�es.\n\n- Cr�dits iconographiques\nLe logo de l'Office Municipal du Sport de Clermont-Ferrand, ainsi que toute iconographie qui la caract�rise, ne peut et ne doit �tre utilis� sans l�accord explicite de l'O.M.S.\n\n- Traitement des donn�es personnelles\nConcernant les associations sportives mentionn�es, les informations recueillies sont n�cessaires pour votre adh�sion. Elles font l'objet d'un traitement informatique et sont destin�es au secr�tariat de l'association et � l�actualisation du site internet de l�O.M.S. de Clermont-Ferrand. En application des articles 39 et suivants de la loi du 6 janvier 1978 modifi�e, les associations b�n�ficient d'un droit d'acc�s et de rectification aux informations qui les concernent. \nSi elles souhaitent exercer ce droit et obtenir communication des informations vous concernant, elles devront s'adresse adresser au Secr�tariat de l�O.M.S., 121, Avenue de la Lib�ration � Clermont-Ferrand ou par mail : oms.clermont@gmail.com ";

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

package fr.oms.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MentionsActivity extends Activity {

	private static final String conditionsGeneralesUtilisation ="Par le biais de cette base de donn�es r�alis�e gr�ce au soutien de la Ville de Clermont-Ferrand, l'Office Municipal du Sport de Clermont-Ferrand dispose d'un site internet qui permette � tout(e)s les habitant(e)s de l�agglom�ration clermontoise de trouver les informations rassembl�es dans le guide papier � Sport � Clermont �. Ce site permet de faire conna�tre le sport � Clermont-Ferrand, ses lieux de pratique, ses clubs et de retrouver en ligne l�annuaire des associations sportives de Clermont-Ferrand.\nToute utilisation/reproduction/modification pour une utilisation dans le cadre d'un produit payant (prestation vendue, �dition d'un livre, etc) ou � objectif publicitaire/promotionnel (site web, flyer, prospectus, etc) ou de toute action � but lucratif en g�n�ral est strictement interdite sans l'accord pr�alable de l'Office Municipal du Sport de Clermont-Ferrand.";
	private static final String informationsEditeur = "Ce site Internet est la propri�t� de l'Office Municipal du Sport de Clermont-Ferrand dont le si�ge social est situ� :\nPavillon du Stade Philippe MARCOMBES - 121 Avenue de la Lib�ration\n63 000 Clermont-Ferrand\nT�l : 04 73 35 15 53\nLe Directeur de la publication du site Internet est Monsieur le Pr�sident de l'OMS\nLes mises � jour sont assur�es par la Commission Promotion et Nouvelles Technologie de l'O.M.S. \nPour toute suggestion, information, r�action concernant ce site, n�h�sitez pas � �crire au webmestre via le formulaire de contact.";

				@Override
				protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_mentions);
				TextView conditions=(TextView) findViewById(R.id.txt_conditions);
				conditions.setText(conditionsGeneralesUtilisation);
				TextView editeur=(TextView) findViewById(R.id.txt_editeur);
				editeur.setText(informationsEditeur);
			}

			@Override
			public boolean onCreateOptionsMenu(Menu menu) {
				// Inflate the menu; this adds items to the action bar if it is present.
				getMenuInflater().inflate(R.menu.mentions, menu);
				return true;
			}

			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				// Handle action bar item clicks here. The action bar will
				// automatically handle clicks on the Home/Up button, so long
				// as you specify a parent activity in AndroidManifest.xml.
				int id = item.getItemId();
				if (id == R.id.action_settings) {
					return true;
				}
				return super.onOptionsItemSelected(item);
			}
}

package fr.oms.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MentionsActivity extends Activity {

	private static final String conditionsGeneralesUtilisation ="Par le biais de cette base de données réalisée grâce au soutien de la Ville de Clermont-Ferrand, l'Office Municipal du Sport de Clermont-Ferrand dispose d'un site internet qui permette à tout(e)s les habitant(e)s de l’agglomération clermontoise de trouver les informations rassemblées dans le guide papier « Sport à Clermont ». Ce site permet de faire connaître le sport à Clermont-Ferrand, ses lieux de pratique, ses clubs et de retrouver en ligne l’annuaire des associations sportives de Clermont-Ferrand.\nToute utilisation/reproduction/modification pour une utilisation dans le cadre d'un produit payant (prestation vendue, édition d'un livre, etc) ou à objectif publicitaire/promotionnel (site web, flyer, prospectus, etc) ou de toute action à but lucratif en général est strictement interdite sans l'accord préalable de l'Office Municipal du Sport de Clermont-Ferrand.";
	private static final String informationsEditeur = "Ce site Internet est la propriété de l'Office Municipal du Sport de Clermont-Ferrand dont le siège social est situé :\nPavillon du Stade Philippe MARCOMBES - 121 Avenue de la Libération\n63 000 Clermont-Ferrand\nTél : 04 73 35 15 53\nLe Directeur de la publication du site Internet est Monsieur le Président de l'OMS\nLes mises à jour sont assurées par la Commission Promotion et Nouvelles Technologie de l'O.M.S. \nPour toute suggestion, information, réaction concernant ce site, n’hésitez pas à écrire au webmestre via le formulaire de contact.";

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

package fr.oms.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ParametresActivity extends Activity {

	private CheckBox box;
	private Button effectuerMaj;
	private TextView redem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parametres);
		final SharedPreferences prefs = getSharedPreferences(
				"fr.oms.activities", Context.MODE_PRIVATE);
		
		effectuerMaj=(Button) findViewById(R.id.effectuer_maj);
		redem=(TextView) findViewById(R.id.redemar);
		box=(CheckBox) findViewById(R.id.chek_maj);
		box.setChecked(prefs.getBoolean("MAJ", true));
		if(!prefs.getBoolean("MAJ", true)){
			effectuerMaj.setVisibility(View.VISIBLE);
			redem.setVisibility(View.VISIBLE);
		}else{
			effectuerMaj.setVisibility(View.GONE);
			redem.setVisibility(View.GONE);
		}
		box.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {				

				
				prefs.edit().putBoolean("MAJ", isChecked).apply();
				if(!isChecked){
					effectuerMaj.setVisibility(View.VISIBLE);
					redem.setVisibility(View.VISIBLE);
				}else{
					effectuerMaj.setVisibility(View.GONE);
					redem.setVisibility(View.GONE);
				}
			}
		});
		clicEffectueMAJ();
	}
	
	private void clicEffectueMAJ(){
		effectuerMaj.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ParametresActivity.this, Activity_Chargement.class);
				i.putExtra("MAJ", true);
				startActivity(i);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parametres, menu);
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

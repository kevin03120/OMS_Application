package fr.oms.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
	}
}

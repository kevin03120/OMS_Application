package fr.oms.activities;

import fr.oms.metier.Evenement;
import fr.oms.modele.DownloadImageTask;
import fr.oms.modele.Manager;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;

public class ZoomImage extends Activity {

	private Evenement evenement;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.zoom_image);
        int pos = getIntent().getExtras().getInt("id");
        for(Evenement e : Manager.getInstance().getListEvenements()){
        	if(e.getId() == pos){
        		evenement = e;
        	}
        }
        ImageView image = (ImageView)findViewById(R.id.image);
		new DownloadImageTask(image).execute(evenement.getLienImage()+"=?reqwidth=1000");
	}
}

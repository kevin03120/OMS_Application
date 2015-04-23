package fr.oms.dataloader;

import java.io.File;
import java.io.InputStream;

import org.json.JSONObject;

import android.content.Context;

public interface iLoadData {
	
	void loadAllFileFromServer(Context context);
	
	JSONObject LoadFile(File f);
	
	String convertStreamToString(InputStream is);	

}

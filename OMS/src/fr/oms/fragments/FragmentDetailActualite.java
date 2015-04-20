package fr.oms.fragments;

import fr.oms.activities.R;
import fr.oms.metier.Actualite;
import fr.oms.modele.DownloadImageTask;
import fr.oms.modele.Manager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentDetailActualite extends Fragment {

	private Actualite actualite;
	
	public static FragmentDetailActualite newInstance(Actualite a) {
		Bundle extras = new Bundle();
		extras.putInt("id", a.getId());
		FragmentDetailActualite fragment = new FragmentDetailActualite();
		fragment.setArguments(extras);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 View v = inflater.inflate(R.layout.detail_actualite, container, false);
		 int pos = getArguments().getInt("id");
			for(Actualite a : Manager.getInstance().getListeActualite()){
				if(a.getId() == pos){
					setActualite(a);
				}
			}
			recupererToutesViews(v);
			getActivity().setTitle(getResources().getString(R.string.titreDetailActualite));
	     return v;
	}

	private void recupererToutesViews(View v){
		TextView txtTitre = (TextView)v.findViewById(R.id.txtTitreActu);
		txtTitre.setText(actualite.getTitre());
		ImageView image = (ImageView)v.findViewById(R.id.imgActu);
		new DownloadImageTask(image).execute(actualite.getLienImage()+"=?reqwidth=40");
		TextView txtDetailActu = (TextView)v.findViewById(R.id.txtDetailActu);
		txtDetailActu.setText(actualite.getDescription());
		TextView txtAssoc = (TextView)v.findViewById(R.id.txtAssociationConcerne);
		txtAssoc.setText("Association Concernée : " + actualite.getAssociationConcernee().getNom());
	}
	
	public Actualite getActualite() {
		return actualite;
	}

	public void setActualite(Actualite actualite) {
		this.actualite = actualite;
	}
}

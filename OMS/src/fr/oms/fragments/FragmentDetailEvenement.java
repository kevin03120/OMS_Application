package fr.oms.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import fr.oms.activities.FragmentAssociationActivity;
import fr.oms.activities.FragmentEquipementActivity;
import fr.oms.activities.R;
import fr.oms.metier.Equipement;
import fr.oms.metier.Evenement;
import fr.oms.modele.DownloadImageTask;
import fr.oms.modele.Manager;

public class FragmentDetailEvenement extends Fragment {

	private Evenement evenement;
	
	public static FragmentDetailEvenement newInstance(Evenement e) {
		Bundle extras = new Bundle();
		extras.putInt("id", e.getId());
		FragmentDetailEvenement fragment = new FragmentDetailEvenement();
		fragment.setArguments(extras);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.detail_evenement, container, false);
		 int pos = getArguments().getInt("id");
			for(Evenement e : Manager.getInstance().getListEvenements()){
				if(e.getId() == pos){
					setEvenement(e);
				}
			}
			recupererToutesViews(v);
			getActivity().setTitle(getResources().getString(R.string.titreDetailEvenement));
	     return v;
	}
	
	private void recupererToutesViews(View v){
		TextView txtTitre = (TextView)v.findViewById(R.id.txtTitreEvent);
		txtTitre.setText(evenement.getTitre());
		ImageView image = (ImageView)v.findViewById(R.id.imgEvent);
		new DownloadImageTask(image).execute(evenement.getLienImage()+"=?reqwidth=40");
		TextView txtDetailActu = (TextView)v.findViewById(R.id.txtDetailEvent);
		txtDetailActu.setText(Html.fromHtml(evenement.getDescription()));
		txtDetailActu.setMovementMethod(LinkMovementMethod.getInstance());
		if(evenement.getAssociationConcernee() != null){
			TextView txtAssoc = (TextView)v.findViewById(R.id.txtAssociationConcerne);
			txtAssoc.setText("Association Concernée : " + evenement.getAssociationConcernee().getNom());
			touchAssoc(txtAssoc);
			txtAssoc.setVisibility(0);
		}
		if(!evenement.getLieu1().equals("")){
			TextView txtLieu1 = (TextView)v.findViewById(R.id.txtLieu1);
			txtLieu1.setText("Lieu 1 : " + evenement.getLieu1());
			txtLieu1.setVisibility(0);
		}
		if(evenement.getLieu2() != null){
			TextView txtLieu2 = (TextView)v.findViewById(R.id.txtLieu2);
			txtLieu2.setText("Lieu 2 : " + evenement.getLieu2().getNom());
			touchLieu2(txtLieu2);
			txtLieu2.setVisibility(0);
		}
	}
	
	private void touchAssoc(TextView v){
		v.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), FragmentAssociationActivity.class);
				intent.putExtra("position", getEvenement().getAssociationConcernee().getId());
				intent.putExtra("adherents", true);
				intent.putExtra("nonAdherents", true);
				intent.putExtra("sport", false);
				intent.putExtra("idSport", 0);
				startActivity(intent);
			}
		});
	}
	
	private void touchLieu2(TextView v){
		v.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Equipement lieu = evenement.getLieu2();
				int position = lieu.getUid();
				Intent intent = new Intent(getActivity(), FragmentEquipementActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
	}

	public Evenement getEvenement() {
		return evenement;
	}

	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}
}

package fr.oms.fragments;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import fr.oms.activities.FragmentActuActivity;
import fr.oms.activities.FragmentAssociationActivity;
import fr.oms.activities.R;
import fr.oms.dataloader.URLImageParser;
import fr.oms.metier.Actualite;
import fr.oms.modele.DownloadImageTask;
import fr.oms.modele.Manager;

public class FragmentDetailActualite extends Fragment {

	private Actualite actualite;
	private ImageView image;
	private ImageView arrow_left;
	private ImageView arrow_right;
	
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
			for(Actualite a : Manager.getInstance().getListActualites()){
				if(a.getId() == pos){
					setActualite(a);
				}
			}
			recupererToutesViews(v);
			getActivity().setTitle(getResources().getString(R.string.titreDetailActualite));
			clicFlecheLeft();
			clicFlecheRight();
			return v;
	}

	public void clicFlecheLeft(){
		arrow_left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				List<Actualite> actus = Manager.getInstance().getListActualites();
				ViewPager pager = FragmentActuActivity.fragmentActuActivity.getPager();
				if(actus.indexOf(actualite) != 0){
					pager.setCurrentItem(pager.getCurrentItem()-1);
				}
				else{
					pager.setCurrentItem(actus.size()-1);
				}
			}
		});
	}
	
	public void clicFlecheRight(){
		arrow_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<Actualite> actus = Manager.getInstance().getListActualites();
				ViewPager pager = FragmentActuActivity.fragmentActuActivity.getPager();
				if(actus.indexOf(actualite) != actus.size()-1){
					pager.setCurrentItem(pager.getCurrentItem()+1);
				}
				else{
					pager.setCurrentItem(0);
				}
			}
		});
	}
	
	private void recupererToutesViews(View v){
		TextView txtTitre = (TextView)v.findViewById(R.id.txtTitreActu);
		txtTitre.setText(getActualite().getTitre());
		image = (ImageView)v.findViewById(R.id.imgActu);
		new DownloadImageTask(image).execute(getActualite().getLienImage()+"=?reqwidth=200");
		TextView txtDetailActu = (TextView)v.findViewById(R.id.txtDetailActu);
		String textHtml = getActualite().getDescription();
		
		URLImageParser p = new URLImageParser(txtDetailActu, this.getActivity());
		Spanned htmlSpan = Html.fromHtml(textHtml, p, null);
		
		txtDetailActu.setText(htmlSpan);
		
		txtDetailActu.setMovementMethod(LinkMovementMethod.getInstance());
		TextView txtAssoc = (TextView)v.findViewById(R.id.txtAssociationConcerne);
		TextView txtPasAssoc = (TextView)v.findViewById(R.id.txtPasAssocActu);
		if(getActualite().getAssociationConcernee() != null){
			txtAssoc.setText(getActualite().getAssociationConcernee().getNom());
			touchAssoc(txtAssoc);
		}
		else{
			txtAssoc.setVisibility(View.GONE);
			txtPasAssoc.setVisibility(View.VISIBLE);
		}
		arrow_left = (ImageView)v.findViewById(R.id.arrow_l_actu);
		arrow_right = (ImageView)v.findViewById(R.id.arrow_r_actu);
	}
	
	public Actualite getActualite() {
		return actualite;
	}

	public void setActualite(Actualite actualite) {
		this.actualite = actualite;
	}
	
	private void touchAssoc(TextView v){
		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), FragmentAssociationActivity.class);
				intent.putExtra("position", getActualite().getAssociationConcernee().getId());
				intent.putExtra("adherents", true);
				intent.putExtra("nonAdherents", true);
				intent.putExtra("sport", false);
				intent.putExtra("idSport", 0);
				startActivity(intent);
			}
		});
	}
}

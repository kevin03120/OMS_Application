package fr.oms.fragments;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import fr.oms.activities.FragmentActuActivity;
import fr.oms.activities.FragmentEventActivity;
import fr.oms.activities.R;
import fr.oms.adapter.ActualiteAdapter;
import fr.oms.adapter.EvenementAdapter;
import fr.oms.metier.Actualite;
import fr.oms.metier.Evenement;
import fr.oms.modele.Manager;

public class FragmentEvents extends Fragment{

	private ListView listEvenements;
	private EditText editRechercher;
	private TextView failRecherche;
	private EvenementAdapter eventAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View v = inflater.inflate(R.layout.fragment_layout, container, false);
        listEvenements = (ListView)v.findViewById(R.id.listActualiteEvenement);
        editRechercher = (EditText)v.findViewById(R.id.rechercheActuEvent);
        failRecherche = (TextView)v.findViewById(R.id.failRecherche);
        editRechercher.setHint(getActivity().getResources().getString(R.string.recherche_event));
        eventAdapter = new EvenementAdapter(getActivity(), 0, Manager.getInstance().getListEvenements());
        listEvenements.setAdapter(eventAdapter);
        touchEvent();
        ajouterRecherche();

		editRechercher.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		});
		return v;
	}

	private void ajouterRecherche() {

		editRechercher.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				ArrayList<Evenement> listeRecherche=new ArrayList<Evenement>();

				for(Evenement a : Manager.getInstance().getListEvenements()){
					if(a.getTitre().toLowerCase(Locale.FRENCH).contains(s)){						
						listeRecherche.add(a);
						eventAdapter = new EvenementAdapter(getActivity(), 0, listeRecherche);
						eventAdapter.notifyDataSetChanged();
						listEvenements.setVisibility(View.VISIBLE);
						failRecherche.setVisibility(View.GONE);
						listEvenements.setAdapter(eventAdapter);
						touchEventFiltre(listeRecherche);
					}
				}
				if (listeRecherche.size()==0) {
					listEvenements.setVisibility(View.GONE);
					failRecherche.setVisibility(View.VISIBLE);
					failRecherche.setText("Aucun résulat trouvé");
				}


			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if(s.length()==0){
					System.out.println("il est passé par ici");
					eventAdapter = new EvenementAdapter(getActivity(), 0, Manager.getInstance().getListEvenements());
					eventAdapter.notifyDataSetChanged();
					listEvenements.setAdapter(eventAdapter);
					touchEvent();
				}

			}
		});

	}

	private void touchEventFiltre(final ArrayList<Evenement> liste){
		listEvenements.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Evenement event = liste.get(position);
				Intent intent = new Intent(getActivity(), FragmentEventActivity.class);
				intent.putExtra("position", event.getId());
				startActivity(intent);
			}
		}); 
	}
	
    private void touchEvent(){
    	listEvenements.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Evenement event = Manager.getInstance().getListEvenements().get(position);
				Intent intent = new Intent(getActivity(), FragmentEventActivity.class);
				intent.putExtra("position", event.getId());
				startActivity(intent);
			}
		}); 
	}
}

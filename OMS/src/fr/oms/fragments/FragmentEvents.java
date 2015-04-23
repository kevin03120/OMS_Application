package fr.oms.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import fr.oms.activities.FragmentEventActivity;
import fr.oms.activities.R;
import fr.oms.adapter.EvenementAdapter;
import fr.oms.modele.Manager;

public class FragmentEvents extends Fragment{

	private ListView listEvenements;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View v = inflater.inflate(R.layout.fragment_layout, container, false);
        listEvenements = (ListView)v.findViewById(R.id.listActualiteEvenement);
        listEvenements.setAdapter(new EvenementAdapter(getActivity(), 0, Manager.getInstance().getListEvenements()));
        touchEvent();
        return v;
    }
    
    private void touchEvent(){
    	listEvenements.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), FragmentEventActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		}); 
	}
}

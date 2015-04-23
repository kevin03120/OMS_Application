package fr.oms.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import fr.oms.activities.FragmentActuActivity;
import fr.oms.activities.R;
import fr.oms.adapter.ActualiteAdapter;
import fr.oms.modele.Manager;

public class FragmentActus extends Fragment{
	
	private ListView listActualite;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        listActualite = (ListView)v.findViewById(R.id.listActualiteEvenement);
        listActualite.setAdapter(new ActualiteAdapter(getActivity(), 0, Manager.getInstance().getListActualites()));
        touchActu();
        return v;
    }
    
    private void touchActu(){
    	listActualite.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), FragmentActuActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		}); 
	}

}

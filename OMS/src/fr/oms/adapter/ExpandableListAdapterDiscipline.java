package fr.oms.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import fr.oms.activities.R;
import fr.oms.fragments.AnnuaireFragment;

public class ExpandableListAdapterDiscipline extends BaseExpandableListAdapter{

	private Context _context;
	private List<String> _listDataHeader; 
	private HashMap<String, List<String>> _listDataChild;
	private ArrayList<String> child;

	public ExpandableListAdapterDiscipline(Context context, List<String> listDataHeader,
			HashMap<String, List<String>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		child=(ArrayList<String>) _listDataChild.get(getGroup(groupPosition));
		TextView textView = null;

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.expandable_list_item,parent,false);
		}
		textView = (TextView) convertView.findViewById(R.id.lblListItem);
		textView.setText(child.get(childPosition));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				String nom = ExpandableListAdapterDiscipline.this.child.get(childPosition);
				Fragment fragment = new AnnuaireFragment(0);
				Bundle args = new Bundle();
				args.putString("nomSport", nom);
				fragment.setArguments(args);
				FragmentManager fragmentManager = ((FragmentActivity) _context).getSupportFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
			}
		});
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.expandable_list_group,parent,false);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		if(groupPosition%2==0){
			lblListHeader.setBackgroundColor(_context.getResources().getColor(R.color.BleuClair));
		}else{
			lblListHeader.setBackgroundColor(_context.getResources().getColor(R.color.VertOms));
		}
		
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	

	

}

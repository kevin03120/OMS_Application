package fr.oms.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.oms.activities.R;
import fr.oms.adapter.AnnuairePagerAdapter;
import fr.oms.ressources.SlidingTabLayout;

public class AnnuaireFragment extends Fragment{
	
	public static final String ARG_PAGE = "ARG_PAGE";
	
	private int mPage;
	public static AnnuairePagerAdapter sp;
	
	public AnnuaireFragment(int page) {
		this.mPage=page;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String nomSport = null;
		if(getArguments()!=null){
			nomSport = getArguments().getString("nomSport");
		}
		View view = inflater.inflate(R.layout.pager, container, false);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new AnnuairePagerAdapter(getFragmentManager(),this.getActivity(), nomSport));
        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);
        viewPager.setCurrentItem(mPage,true);
        slidingTabLayout.setViewPager(viewPager);
		return view;
	}

	
	
	
	
	public int getmPage() {
		return mPage;
	}

	public void setmPage(int mPage) {
		this.mPage = mPage;
	}
	
}

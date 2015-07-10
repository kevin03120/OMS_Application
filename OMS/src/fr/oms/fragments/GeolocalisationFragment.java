package fr.oms.fragments;

import fr.oms.activities.R;
import fr.oms.adapter.GeolocalisationPagerAdapter;
import fr.oms.ressources.SlidingTabLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GeolocalisationFragment extends Fragment {
	
	public static final String ARG_PAGE = "ARG_PAGE";
	
	private int mPage;
	public static GeolocalisationPagerAdapter sp;
	
	public GeolocalisationFragment(int page) {
		this.mPage=page;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pager, container, false);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
		viewPager.setAdapter(new GeolocalisationPagerAdapter(getFragmentManager(),this.getActivity()));
		viewPager.setCurrentItem(mPage,true);
		SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
		slidingTabLayout.setDistributeEvenly(true);
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

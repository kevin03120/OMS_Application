package fr.oms.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.oms.activities.R;
import fr.oms.adapter.SampleFragmentPagerAdapter;
import fr.oms.ressources.SlidingTabLayout;

public class AnnuaireFragment extends Fragment{
	public static final String ARG_PAGE = "ARG_PAGE";

	private int mPage;

	public static Fragment newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		android.support.v4.app.Fragment fragment = new AnnuaireFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pager, container, false);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getFragmentManager(), 
            getActivity()));
        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
		return view;
	}
}
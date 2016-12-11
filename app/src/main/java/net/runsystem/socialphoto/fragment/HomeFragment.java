package net.runsystem.socialphoto.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.runsystem.socialphoto.Adapter.HomePagerAdapter;
import net.runsystem.socialphoto.Constant.HeaderOption;
import net.runsystem.socialphoto.MainActivity;
import net.runsystem.socialphoto.R;

import butterknife.BindView;

public class HomeFragment extends HeaderFragment {
    @BindView(R.id.vpPager)
    ViewPager viewPager;


    @BindView(R.id.layout_tab)
    TabLayout tabLayout;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
        HomePagerAdapter homePagerAdapter;
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_MENU;
    }

    @Override
    protected int getRightIcon() {
        return HeaderOption.RIGHT_NO_OPTION;
    }

    @Override
    public String getScreenTitle() {
        return "Home";
    }


    MenuFragment menuFragment;

    @Override
    protected void initView(View root) {
        super.initView(root);
        ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_HOME);
        menuFragment =(MenuFragment)
                getActivity().getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}

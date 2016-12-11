package net.runsystem.socialphoto.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.runsystem.socialphoto.Bean.DataTut;
import net.runsystem.socialphoto.fragment.PagerTutFragment;

/**
 * Created by admin on 10/21/2016.
 */

public class TutViewPagerAdapter extends FragmentStatePagerAdapter {
    int pageNum = 4;
    DataTut getedData;



    public TutViewPagerAdapter(FragmentManager fm,DataTut dataTut) {
        super(fm);
        getedData = dataTut;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return PagerTutFragment.newInstance(getedData.tutorial.get(0),getedData.user);
            case 1:
                return PagerTutFragment.newInstance(getedData.tutorial.get(1),getedData.user);
            case 2:
                return PagerTutFragment.newInstance(getedData.tutorial.get(2),getedData.user);
            case 3:
                return PagerTutFragment.newInstance(getedData.tutorial.get(3),getedData.user);
            default:
                return PagerTutFragment.newInstance(getedData.tutorial.get(0),getedData.user);
        }

    }

    @Override
    public int getCount() {
        return pageNum;
    }
}

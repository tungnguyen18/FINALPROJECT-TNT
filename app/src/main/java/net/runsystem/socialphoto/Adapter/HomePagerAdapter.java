package net.runsystem.socialphoto.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.runsystem.socialphoto.Constant.ApiType;
import net.runsystem.socialphoto.fragment.NewFragment;

/**
 *
 */

public class HomePagerAdapter extends FragmentPagerAdapter{

    private String tabTitles[] = new String[] { "New", "Follow" };

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NewFragment.newInstance(ApiType.NEW);
            case 1:
                return NewFragment.newInstance(ApiType.FOLLOW);
            default:
                return NewFragment.newInstance(ApiType.NEW);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}

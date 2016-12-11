package net.runsystem.socialphoto.fragment;

import android.os.Bundle;
import android.view.View;

import net.runsystem.socialphoto.Bean.HeaderControlBean;
import net.runsystem.socialphoto.Constant.HeaderOption;

import vn.app.base.fragment.BaseSwipeRefreshFragment;

/**
 * Created on 7/23/2016.
 */
public abstract class BaseHeaderListFragment extends BaseSwipeRefreshFragment {

    @Override
    protected void initView(View root) {
        if (commonListener != null) {
            commonListener.onCommonUIHandle(showHeaderBundle());
        }
    }

    private Bundle showHeaderBundle() {
        Bundle bundle = new Bundle();
        HeaderControlBean headerControlBean = new HeaderControlBean(getScreenTitle());
        headerControlBean.setHeaderOption(HeaderOption.SHOW_HEADER, getLeftIcon(), getRightIcon());
        bundle.putParcelable(HeaderOption.HEADER_CONTROL, headerControlBean);
        return bundle;
    }

    protected int getLeftIcon() {
        return 0;
    }

    protected int getRightIcon() {
        return 0;
    }

    protected boolean isHeaderTransparent() {
        return false;
    }
}

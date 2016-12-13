package net.runsystem.socialphoto;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.runsystem.socialphoto.Bean.HeaderControlBean;
import net.runsystem.socialphoto.Bean.NewsBean;
import net.runsystem.socialphoto.Bean.User;
import net.runsystem.socialphoto.Constant.ApiConstance;
import net.runsystem.socialphoto.Constant.HeaderOption;
import net.runsystem.socialphoto.fragment.HomeFragment;
import net.runsystem.socialphoto.fragment.LoginFragment;
import net.runsystem.socialphoto.fragment.MenuFragment;
import net.runsystem.socialphoto.manager.UserManager;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.activity.CommonActivity;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.StringUtil;
import vn.app.base.util.UiUtil;

public class MainActivity extends CommonActivity implements MenuFragment.NavigationDrawerCallbacks {

    @BindView(R.id.toolbar)
    RelativeLayout rlToolbar;

    @BindView(R.id.headerMenu)
    ImageView ivMenu;

    @BindView(R.id.headerBack)
    ImageView ivBack;

    @BindView(R.id.tv_delete)
    TextView tvDelete;

    @BindView(R.id.tv_update)
    TextView tvUpdate;

    @BindView(R.id.headerTitle)
    TextView tvTitle;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    MenuFragment menuFragment;
    User currentUser;

    int iScreenNo = 0;
    NewsBean selectHomeBean;

    @Override
    protected String getNoConnectionMessage() {
        return getString(R.string.dialog_error_no_connection);
    }

    @Override
    protected String getErrorAPIMessage() {
        return getString(R.string.dialog_error_no_connection);
    }

    @Override
    protected String getTimeOutMessage() {
        return getString(R.string.dialog_error_timeout);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        currentUser = UserManager.getCurrentUser();
        if (currentUser == null) {
            setUpInitScreen(LoginFragment.newInstance(), null);
        } else {
            setUpInitScreen(HomeFragment.newInstance(), ApiConstance.TAGHOME);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onCommonDataHandle(Bundle bundle) {

    }

    @Override
    public void onCommonUIHandle(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (bundle.containsKey(HeaderOption.HEADER_CONTROL)) {
            HeaderControlBean headerControlBean = bundle.getParcelable(HeaderOption.HEADER_CONTROL);
            if (headerControlBean != null) {
                handleHeaderUI(headerControlBean);
            }
        }

    }
    private void handleHeaderUI(HeaderControlBean headerControlBean) {
        if (headerControlBean.headerOptions != null && headerControlBean.headerOptions.length > 0) {
            if (headerControlBean.headerOptions[0] == HeaderOption.SHOW_HEADER) {
                UiUtil.showView(rlToolbar);
            } else if (headerControlBean.headerOptions[0] == HeaderOption.HIDE_HEADER) {
                UiUtil.hideView(rlToolbar,true);
            }
            if (headerControlBean.headerOptions.length > 1 && headerControlBean.headerOptions[1] > 0) {
                handleLeftIcon(headerControlBean.headerOptions[1]);
                if (headerControlBean.headerOptions.length > 2 && headerControlBean.headerOptions[2] > 0) {
                    handleRightIcon(headerControlBean.headerOptions[2]);
                } else {
                    showAndHideIconRight(null);
                }
            } else {
                showAndHideIconLeft(ivMenu);
            }
        }
        if (StringUtil.checkStringValid(headerControlBean.title)) {
            StringUtil.displayText(headerControlBean.title, tvTitle);
        }
    }
    private void handleLeftIcon(int value) {
        switch (value) {
            case HeaderOption.LEFT_NO_OPTION:
                showAndHideIconLeft(null);
                break;
            case HeaderOption.LEFT_MENU:
                showAndHideIconLeft(ivMenu);
                handleMenuSlide();
                break;
            case HeaderOption.LEFT_BACK:
                showAndHideIconLeft(ivBack);
                break;
        }
    }

    private void handleRightIcon(int value) {
        switch (value) {
            case HeaderOption.RIGHT_NO_OPTION:
                showAndHideIconRight(null);
                break;
            case HeaderOption.RIGHT_DELETE:
                showAndHideIconRight(tvDelete);
                break;
            case HeaderOption.RIGHT_UPDATE:
                showAndHideIconRight(tvUpdate);
                break;
        }
    }
    private void showAndHideIconLeft(View target) {
        showOrHide(ivMenu, target);
        showOrHide(ivBack, target);
    }

    private void showAndHideIconRight(View target) {
        showOrHide(tvDelete, target);
        showOrHide(tvUpdate, target);
    }

    protected void showOrHide(View subject, View target) {
        subject.setVisibility(subject == target ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.headerBack)
    public void onBack() {
        FragmentUtil.popBackStack(this);
    }
    private void handleMenuSlide() {
        menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        menuFragment.setUp(R.id.navigation_drawer, drawerLayout);
    }
    @OnClick(R.id.tv_delete)
    public void onDelete() {
        if (fragmentListener != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(ApiConstance.ISDELCLICK, true);
            fragmentListener.onFragmentUIHandle(bundle);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
    public void setToolbar(int screenNo) {

        rlToolbar.setVisibility(View.GONE);
        ivBack.setVisibility(View.GONE);
        ivMenu.setVisibility(View.GONE);
       // tvTitle.setVisibility(View.GONE);
        tvDelete.setVisibility(View.GONE);
        switch (screenNo) {
            case HeaderOption.MENU_HOME:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            case HeaderOption.MENU_DETAIL_USER:
                rlToolbar.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Detail");
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            case HeaderOption.MENU_DELETE:
                rlToolbar.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Detail");
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                tvDelete.setVisibility(View.VISIBLE);
                tvTitle.setText("Delete");
        }


        iScreenNo = screenNo;
        if (screenNo == HeaderOption.MENU_HOME) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            menuFragment = (MenuFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

            menuFragment.setUp(
                    R.id.navigation_drawer,
                    drawerLayout);

        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }
    @OnClick(R.id.headerBack)
    public void clickHeaderBack(){
        FragmentUtil.popBackStack(this);
    }
}

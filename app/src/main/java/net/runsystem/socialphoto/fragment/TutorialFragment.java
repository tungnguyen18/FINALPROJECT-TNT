package net.runsystem.socialphoto.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.Toast;

import net.runsystem.socialphoto.API.Request.TutRequest;
import net.runsystem.socialphoto.API.Response.TutorialResponse;
import net.runsystem.socialphoto.Adapter.TutViewPagerAdapter;
import net.runsystem.socialphoto.Bean.DataTut;
import net.runsystem.socialphoto.R;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.FragmentUtil;

/**
 * Created by Veteran Commander on 10/21/2016.
 */

public class TutorialFragment extends NoHeaderFragment {
    DataTut dataTut;
    TutViewPagerAdapter tutViewPagerAdapter;

    public static TutorialFragment newInstance() {
        TutorialFragment newFragment = new TutorialFragment();
        return newFragment;
    }

    @BindView(R.id.vp_tut)
    ViewPager viewPager;
    @BindView(R.id.btn_tut_skip)
    Button btnSkip;
    @BindView(R.id.tut_indicator)
    CircleIndicator indicator;

    @OnClick(R.id.btn_tut_skip)
    public void onSkip() {
        Toast.makeText(getActivity(), btnSkip.getText().toString(), Toast.LENGTH_SHORT).show();
        FragmentUtil.replaceFragment(getActivity(),HomeFragment.newInstance(),null);
        //go to home screen
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tut_screen;
    }

    @Override
    protected boolean isStartWithLoading() {
        return dataTut == null;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
        if (dataTut == null) {
            getDatatutorial();
        } else {
            handleTutData(dataTut);
        }

    }

    private void getDatatutorial() {
        TutRequest tutRequest = new TutRequest();
        tutRequest.setRequestCallBack(new ApiObjectCallBack<TutorialResponse>() {
            @Override
            public void onSuccess(TutorialResponse data) {
                initialResponseHandled();
                handleTutData(data.data);
            }

            @Override
            public void onFail(int failCode, String message) {

            }
        });
        tutRequest.execute();
    }

    private void handleTutData(DataTut getedData) {
        tutViewPagerAdapter = new TutViewPagerAdapter(getChildFragmentManager(), getedData);
        viewPager.setAdapter(tutViewPagerAdapter);
        indicator.setViewPager(viewPager);
    }


}

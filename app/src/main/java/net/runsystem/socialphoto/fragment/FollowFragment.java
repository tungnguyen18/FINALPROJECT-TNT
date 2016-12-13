package net.runsystem.socialphoto.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import net.runsystem.socialphoto.API.Request.FollowRequest;
import net.runsystem.socialphoto.API.Response.FollowResponse;
import net.runsystem.socialphoto.Adapter.FollowListAdapter;
import net.runsystem.socialphoto.Bean.FollowDataBean;
import net.runsystem.socialphoto.Constant.HeaderOption;
import net.runsystem.socialphoto.R;

import java.util.List;

import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.callback.OnRecyclerViewItemClick;
import vn.app.base.util.DebugLog;

/**
 *
 */

public class FollowFragment extends BaseHeaderListFragment implements OnRecyclerViewItemClick {
    List<FollowDataBean> followDataBeanList;
    FollowListAdapter followListAdapter;

    public static FollowFragment newInstance(){
        FollowFragment newFragment = new FollowFragment();
        return newFragment;
    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
        if (followDataBeanList == null) {
            getFollowlistdata();
        }
    }

    @Override
    protected boolean isSkipGenerateBaseLayout() {
        return true;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        setCanRefresh(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow_screen;
    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_BACK;
    }

    @Override
    protected int getRightIcon() {
        return HeaderOption.RIGHT_NO_OPTION;
    }

    @Override
    public String getScreenTitle() {
        return "Follow";
    }

    private void getFollowlistdata(){
        FollowRequest followRequest = new FollowRequest();
        followRequest.setRequestCallBack(new ApiObjectCallBack<FollowResponse>() {
            @Override
            public void onSuccess(FollowResponse data) {
                if (data.followDatas != null) {
                    setData(data.followDatas);
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
                DebugLog.e("Error" + failCode + message);
            }
        });
        followRequest.execute();
    }
    private void setData(List<FollowDataBean> followData) {
        followDataBeanList = followData;
        followListAdapter = new FollowListAdapter(followDataBeanList);
        rvList.setAdapter(followListAdapter);
        followListAdapter.setOnRecyclerViewItemClick(this);
    }
//    private void handleFollowlistdata(List<FollowDataBean> dataFollowList){
//        followListAdapter = new FollowListAdapter(dataFollowList);
//        followListAdapter.setOnRecyclerViewItemClick(new OnRecyclerViewItemClick() {
//            @Override
//            public void onItemClick(View view, int position) {
//                int realpos = position + 1;
//                Toast.makeText(getActivity(),realpos + "" ,Toast.LENGTH_SHORT).show();
//            }
//        });
//        rvList.setAdapter(followListAdapter);
//
//    }


    @Override
    public void onItemClick(View view, int position) {
    //   FollowDataBean followDataBean = followDataBeanList.get(position);
//        if (followDataBean != null) {
//            FragmentUtil.pushFragment(getActivity(), ProfileUserFragment.newInstance(followDataBean.user.id),null, "ProfileUserFragment");
//        }

    }
}

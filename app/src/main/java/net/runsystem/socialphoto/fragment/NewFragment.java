package net.runsystem.socialphoto.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.runsystem.socialphoto.API.Request.FavouritesRequest;
import net.runsystem.socialphoto.API.Request.FollowHomeRequest;
import net.runsystem.socialphoto.API.Request.NewsRequest;
import net.runsystem.socialphoto.API.Response.NewsResponse;
import net.runsystem.socialphoto.Adapter.NewListAdapter;
import net.runsystem.socialphoto.Bean.NewsBean;
import net.runsystem.socialphoto.Constant.ApiConstance;
import net.runsystem.socialphoto.R;
import net.runsystem.socialphoto.callback.OnNewItemClick;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.customview.endlessrecycler.EndlessRecyclerOnScrollListener;
import vn.app.base.util.DebugLog;
import vn.app.base.util.FragmentUtil;

public class NewFragment extends BaseHeaderListFragment implements OnNewItemClick {

    public static final String HOME_TYPE = "HOME_TYPE";

    @BindView(R.id.rvNew)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.fabCamera)
    FloatingActionButton fabCamera;

    NewListAdapter newListAdapter;

    List<NewsBean> newBeanList;
    NewsBean newsBean;

    int type;
    String last_query_timestamp;
    int num;
    int home_type;

    public static NewFragment newInstance(int type) {
        NewFragment fragmentNew = new NewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(HOME_TYPE, type);
        fragmentNew.setArguments(bundle);
    return fragmentNew;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new;
    }

    @Override
    protected void onRefreshData() {
        getNews(true);
        hideCoverNetworkLoading();
    }

    @Override
    protected void getArgument(Bundle bundle) {
        home_type = bundle.getInt(HOME_TYPE);
    }


    @Override
    protected void initView(View root) {
        super.initView(root);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(true);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                getNews(true);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        if (newBeanList == null) {
            getNews(false);
        } else {
            handleNewsData(newBeanList);
        }
    }

    @OnClick(R.id.fabCamera)
    public void clickFabCamera() {
        // Di chuyển đến màn hình Detail
        FragmentUtil.pushFragment(getActivity(), FragmentImageUpload.newInstance(), null);
    }

    private void getNews(final boolean isRefresh) {
        showCoverNetworkLoading();
        NewsRequest newsRequest;
        if (!isRefresh) {
            newsRequest = new NewsRequest(home_type, "", 0);
        } else {
            String str_last_time;
            newsRequest = new NewsRequest(home_type, last_query_timestamp, 0);
        }
        newsRequest.setRequestCallBack(new ApiObjectCallBack<NewsResponse>() {
            @Override
            public void onSuccess(NewsResponse data) {
                initialResponseHandled();
                handleNewsData(data.data);
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        newsRequest.execute();
    }



    private void handleNewsData(List<NewsBean> inNewsBean) {
        this.newBeanList = inNewsBean;
        newListAdapter = new NewListAdapter(newBeanList);
        recyclerView.setAdapter(newListAdapter);
        newListAdapter.setOnNewItemClick(this);
        DebugLog.i(inNewsBean.toString());
        getLasttime(newBeanList);
    }
    public void getLasttime(List<NewsBean> inNewsBean) {
        if (inNewsBean != null) {
            int size = inNewsBean.size();
            if (size > 0) {
                NewsBean newsBeanLast = inNewsBean.get(size - 1);
                last_query_timestamp = newsBeanLast.image.createdAt;
            } else {
                last_query_timestamp = "";
            }
        }
    }

    @Override
    public void onRefresh() {
        getNews(true);
    }

    @Override
    protected void initialResponseHandled() {
        super.initialResponseHandled();
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onFavouriteClick(final String imageId, final int favourite) {
        FavouritesRequest favouritesRequest = new FavouritesRequest(imageId, favourite);
        favouritesRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                hideCoverNetworkLoading();
                if (data.status == 1) {
                    changeFavouriteLocal(imageId,favourite);
                    newListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(int failCode, String message) {

            }
        });
        favouritesRequest.execute();
    }

    @Override
    public void onFollowClick(final String userId, final int follow) {
        FollowHomeRequest followHomeRequest = new FollowHomeRequest(userId, follow);
        followHomeRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data.status == 1) {
                    setChangeFollow(userId,follow);
                    newListAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
                DebugLog.i(message);
            }
        });
        followHomeRequest.execute();

    }

    @Override
    public void onAvatarClick(String userId) {
     //   FragmentUtil.pushFragment(getActivity(), ProfileUserFragment.newInstance(userId),null, "ProfileUserFragment");
    }

    @Override
    public void onPictureClick(NewsBean imageProfile) {
        FragmentUtil.pushFragment(getActivity(), ImageDetailFragment.newInstance(imageProfile),null, "ImageDetailFragment");

    }

    @Override
    public void onPinMapClick(NewsBean newsBean) {

    }
    private void setChangeFollow(String userId, int follow) {
        int size = newBeanList.size();
        for (int i = 0; i < size; i++) {
             newsBean = newBeanList.get(i);
            if ((newsBean != null) && (newsBean.user.id.equals(userId))) {
                if (follow == 0) {
                    newBeanList.get(i).user.isFollowing = false;
                } else if (follow == 1) {
                    newBeanList.get(i).user.isFollowing = true;
                }
            }
        }
    }
    private void changeFavouriteLocal(String imageId, int status) {
        int size = newBeanList.size();
        for (int i = 0; i < size; i++) {
            newsBean = newBeanList.get(i);
            if (newsBean != null && newsBean.image.id.equals(imageId)) {
                if (status == ApiConstance.UN_FAVOURITE) {
                    newsBean.image.isFavourite = false;
                } else {
                    newsBean.image.isFavourite = true;
                }

            }
        }
    }
}

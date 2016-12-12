package net.runsystem.socialphoto.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import net.runsystem.socialphoto.API.Request.FavouriteListRequest;
import net.runsystem.socialphoto.API.Request.FavouritesRequest;
import net.runsystem.socialphoto.API.Request.FollowHomeRequest;
import net.runsystem.socialphoto.API.Response.NewsResponse;
import net.runsystem.socialphoto.Adapter.NewListAdapter;
import net.runsystem.socialphoto.Bean.NewsBean;
import net.runsystem.socialphoto.Constant.ApiConstance;
import net.runsystem.socialphoto.Constant.HeaderOption;
import net.runsystem.socialphoto.R;
import net.runsystem.socialphoto.callback.OnNewItemClick;
import net.runsystem.socialphoto.manager.UserManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import butterknife.BindView;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.IntentUtil;

/**
 * Created by admin on 12/12/2016.
 */

public class FragmentFavourite extends BaseHeaderListFragment {
    private static final String USER_ID = "USER_ID";

    @BindView(R.id.recycerList)
    RecyclerView rvList;

    private List<NewsBean> newsBeanList;
    String userId;
    private NewsBean newsBean;
    private NewListAdapter mAdapter;
    private LatLng pictureLocation;

    public FragmentFavourite() {

    }

    public static FragmentFavourite newInstance(String userId) {
        FragmentFavourite fragmentFavourite = new FragmentFavourite();
        Bundle bundle = new Bundle();
        bundle.putString(USER_ID, userId);
        fragmentFavourite.setArguments(bundle);
        return fragmentFavourite;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.swipe_refresh_layout;
    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_BACK;
    }

    @Override
    public String getScreenTitle() {
        return "Favourite";
    }
    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void getArgument(Bundle bundle) {
        userId = bundle.getString(USER_ID);
    }
    @Override
    protected void initView(View root) {
        super.initView(root);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        if (newsBeanList == null) {
            getFavouriteList(false);
        } else {
            handleFavouriteListData(newsBeanList);
        }
    }
    private void getFavouriteList(boolean isRefresh) {
        FavouriteListRequest favouriteListRequest;
        if (userId.equals(UserManager.getCurrentUser().id)) {
            favouriteListRequest = new FavouriteListRequest("");
        } else {
            favouriteListRequest = new FavouriteListRequest(userId);
        }
        favouriteListRequest.setRequestCallBack(new ApiObjectCallBack<NewsResponse>() {
            @Override
            public void onSuccess(NewsResponse data) {
                initialResponseHandled();
                handleFavouriteListData(data.data);

            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        favouriteListRequest.execute();
    }
    private void handleFavouriteListData(List<NewsBean> inNewsBeanList) {
        this.newsBeanList = inNewsBeanList;
        mAdapter = new NewListAdapter(newsBeanList);
        rvList.setAdapter(mAdapter);
        mAdapter.setOnNewItemClick(new OnNewItemClick() {
            @Override
            public void onFavouriteClick(final String imageId, final int favourite) {
                FavouritesRequest favouritesRequest = new FavouritesRequest(imageId, favourite);
                favouritesRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse data) {
                        hideCoverNetworkLoading();
                        if (data.status == 1) {
                            changeFavouriteLocal(imageId,favourite);
                            mAdapter.notifyDataSetChanged();
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
                            changeFollowLocal(userId,follow);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFail(int failCode, String message) {
                    }
                });
                followHomeRequest.execute();

            }

            @Override
            public void onAvatarClick(String userId) {
              //  FragmentUtil.pushFragment(getActivity(), FragmentProfile.newInstance(userId), null, "ProfileUser");

            }

            @Override
            public void onPictureClick(NewsBean imageProfile) {
                FragmentUtil.pushFragment(getActivity(), ImageDetailFragment.newInstance(imageProfile), null, "DetailImage");

            }

            @Override
            public void onPinMapClick(NewsBean newsBean) {
                goToMapAddress(newsBean);

            }
//            @Override
//            public void onPhotoClick(HomeBean homeBean) {
//                FragmentUtil.pushFragmentWithAnimation(getActivity(), FragmentDetail.newInstance(homeBean), null);
//            }
        });
    }
    private void changeFavouriteLocal(String imageId, int favourite) {
        int size = newsBeanList.size();
        for (int i = 0; i < size; i++) {
            newsBean = newsBeanList.get(i);
            if (newsBean != null && newsBean.image.id.equals(imageId)) {
                if (favourite == ApiConstance.UN_FAVOURITE) {
                    newsBean.image.isFavourite = false;
                } else {
                    newsBean.image.isFavourite = true;
                }

            }
        }
    }
    private void changeFollowLocal(String userId, int follow) {
        Log.e("changeFollowLocal", "===>" + follow);
        int size = newsBeanList.size();
        for (int i = 0; i < size; i++) {
            newsBean = newsBeanList.get(i);
            if (newsBean != null && newsBean.user.id.equals(userId)) {
                if (follow == ApiConstance.FOLLOW) {
                    newsBean.user.isFollowing = true;
                } else {
                    newsBean.user.isFollowing = false;
                }
            }
        }
    }
    private void goToMapAddress(NewsBean newsBean) {
        Uri mapUri;
        if (pictureLocation != null) {
            mapUri = Uri.parse("geo:" + pictureLocation.latitude + "," + pictureLocation.longitude + "?q=" + +pictureLocation.latitude + "," + pictureLocation.longitude + "&z=15");
        } else {
            mapUri = Uri.parse("geo:0,0?z=15&q=" + getDecodeAddress(newsBean.image.location));
        }
        IntentUtil.openMap(getActivity(), mapUri);
    }

    private String getDecodeAddress(String location) {
        try {
            return URLDecoder.decode(location, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return location.replace(" ", "+");
        }
    }
}

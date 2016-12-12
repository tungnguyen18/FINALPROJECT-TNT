package net.runsystem.socialphoto.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

import net.runsystem.socialphoto.API.Request.CommentListRequest;
import net.runsystem.socialphoto.API.Request.CommentRequest;
import net.runsystem.socialphoto.API.Request.FavouritesRequest;
import net.runsystem.socialphoto.API.Request.FollowHomeRequest;
import net.runsystem.socialphoto.API.Response.CommentListResponse;
import net.runsystem.socialphoto.Adapter.ImageDetailListAdapter;
import net.runsystem.socialphoto.Bean.CommentBean;
import net.runsystem.socialphoto.Bean.DataLoginBean;
import net.runsystem.socialphoto.Bean.NewsBean;
import net.runsystem.socialphoto.Constant.HeaderOption;
import net.runsystem.socialphoto.MainActivity;
import net.runsystem.socialphoto.R;
import net.runsystem.socialphoto.callback.OnDetailClicked;
import net.runsystem.socialphoto.manager.UserManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.adapter.DividerItemDecoration;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.DebugLog;
import vn.app.base.util.IntentUtil;


public class ImageDetailFragment extends  BaseHeaderListFragment{

    public static final String IMAGE = "image";
    FollowHomeRequest followRequest;
    FavouritesRequest favouritesRequest;

    ImageDetailListAdapter imageDetailListAdapter;
    LatLng pictureLocation;

    NewsBean selectHomeBean;
    List<CommentBean> commentList;

    DataLoginBean loginUser;

    @BindView(R.id.edt_send_cm)
    EditText edtSendCm;

    @BindView(R.id.img_send)
    ImageView imgSend;

    @OnClick(R.id.img_send)
    public void onSendClicked() {
        if (edtSendCm.getText().toString() != "") {
            CommentRequest commentRequest = new CommentRequest(selectHomeBean.image.id, edtSendCm.getText().toString());
            commentRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                @Override
                public void onSuccess(BaseResponse data) {
                    DebugLog.i(data.message);
                    getCommentList();
                }


                @Override
                public void onFail(int failCode, String message) {
                    DebugLog.e(message);
                }
            });
            commentRequest.execute();
        }
        edtSendCm.setText("");

    }

    public static ImageDetailFragment newInstance(NewsBean imageDetailData) {
        ImageDetailFragment imageDetailFragment = new ImageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(IMAGE, imageDetailData);
        imageDetailFragment.setArguments(bundle);
        return imageDetailFragment;
    }


    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_BACK;
    }
    @Override
    protected int getRightIcon() {
        if (selectHomeBean.user.username.equals(UserManager.getCurrentUser().username)) {
            return HeaderOption.RIGHT_DELETE;
        } else return HeaderOption.RIGHT_NO_OPTION;

    }

    @Override
    public String getScreenTitle() {
        return "Detail";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_detail;
    }

    private void getCommentList() {
        showCoverNetworkLoading();
        commentList.clear();
        CommentListRequest commentListRequest = new CommentListRequest(selectHomeBean.image.id);
        commentListRequest.setRequestCallBack(new ApiObjectCallBack<CommentListResponse>() {
            @Override
            public void onSuccess(CommentListResponse data) {
                commentList = data.data;
                initialResponseHandled();
                handleDetailImage(commentList);
            }

            @Override
            public void onFail(int failCode, String message) {
                DebugLog.e(message);
            }
        });
        commentListRequest.execute();
    }
    private void handleDetailImage(List<CommentBean> dataList) {
        imageDetailListAdapter = new ImageDetailListAdapter();
        imageDetailListAdapter.setHeader(selectHomeBean);
        imageDetailListAdapter.setItems(dataList);
        imageDetailListAdapter.setOnDetailClicked(new OnDetailClicked() {
            @Override
            public void onFollowDetailClick(NewsBean newsBean) {
                if (newsBean.user.isFollowing) {
                    //Goi unfollow
                    followRequest = new FollowHomeRequest(newsBean.user.id, 0);
                    followRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse data) {
                            if (data.status == 1) {
                                selectHomeBean.user.isFollowing = false;
                                imageDetailListAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFail(int failCode, String message) {
                        }
                    });
                } else {
                    //Goi follow
                    followRequest = new FollowHomeRequest(newsBean.user.id, 1);
                    followRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse data) {
                            if (data.status == 1) {
                                selectHomeBean.user.isFollowing = true;
                                imageDetailListAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFail(int failCode, String message) {

                        }
                    });
                }
                followRequest.execute();
            }


            @Override
            public void onFavouriteDetailClick(NewsBean newsBean) {
                if (newsBean.image.isFavourite) {
                    //Goi unfavorite
                    favouritesRequest = new FavouritesRequest(newsBean.image.id, 0);
                    favouritesRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse data) {
                            if (data.status == 1) {
                                selectHomeBean.image.isFavourite = false;
                                imageDetailListAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFail(int failCode, String message) {

                        }
                    });

                } else {
                    //Goi favourite
                    favouritesRequest = new FavouritesRequest(newsBean.image.id, 1);
                    favouritesRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse data) {
                            if (data.status == 1) {
                                selectHomeBean.image.isFavourite = true;
                                imageDetailListAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFail(int failCode, String message) {

                        }
                    });
                }
                favouritesRequest.execute();
            }

            @Override
            public void onMapClick(NewsBean newsBean) {
                goToMapAddress(newsBean);
            }

            @Override
            public void onAvatarClicked(NewsBean newsBean) {
                //TODO: go to profile
               // FragmentUtil.pushFragmentWithAnimation(getActivity(),FragmentProfile.newInstance(selectHomeBean.user.id),null);

            }
        });
        rvList.setAdapter(imageDetailListAdapter);
    }

    @Override
    protected void onRefreshData() {
        getCommentList();
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

    @Override
    protected void initView(View root) {
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        setCanRefresh(false);
        ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_DETAIL_USER);
    }

    @Override
    protected void getArgument(Bundle bundle) {
        selectHomeBean = bundle.getParcelable(IMAGE);
    }
    @Override
    protected void setLoadMore() {
        super.setLoadMore();
    }

    @Override
    protected void initData() {
        commentList = new ArrayList<>();
        getCommentList();

    }

}

package net.runsystem.socialphoto.Adapter.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.runsystem.socialphoto.Bean.NewsBean;
import net.runsystem.socialphoto.R;
import net.runsystem.socialphoto.Utinity.RoundedCornersTransformation;
import net.runsystem.socialphoto.callback.OnDetailClicked;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 * Created by admin on 11/21/2016.
 */

public class ImageDetailHeaderViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.item_image_detail_header;

    public static int sCorner = 15;
    public static int sMargin = 2;

    OnDetailClicked onDetailClicked;
    NewsBean selectedHomeBean;
    int hashtash;
    boolean mFollow, mFavourites;

    @BindView(R.id.imgAvatar_detail)
    ImageView ivAvatar;
    @OnClick(R.id.imgAvatar)
    public void gotoProfile(){
        if (onDetailClicked != null) {
            onDetailClicked.onAvatarClicked(selectedHomeBean);
        }
    }

    @BindView(R.id.txtUser_detail)
    TextView tvUserName;

    @BindView(R.id.btnFollow_detail)
    Button btnFollow;

    @BindView(R.id.imgPicture_detail)
    ImageView ivPicture;

    @BindView(R.id.txtPinMap_detail)
    TextView tvPinMap;
    @OnClick(R.id.txtPinMap_detail)
    public void openMapDetailScreen() {
        if (onDetailClicked != null) {
            onDetailClicked.onMapClick(selectedHomeBean);
        }
    }

    @BindView(R.id.txtCaption_detail)
    TextView tvCaption;

    @BindView(R.id.txtHashtag_detail)
    TextView tvHashtag;

    @BindView(R.id.imgFavourite_detail)
    CircleImageView cvFavourite;


    public ImageDetailHeaderViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(NewsBean newsBean,OnDetailClicked onDetailClicked) {
        selectedHomeBean = newsBean;
        this.onDetailClicked = onDetailClicked;
        ImageLoader.loadImage(itemView.getContext(),newsBean.image.url,ivPicture);
       // ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, newsBean.user.avatar, ivAvatar);
        Glide.with(itemView.getContext()).load(newsBean.user.avatar)
                .bitmapTransform(new RoundedCornersTransformation(itemView.getContext(),sCorner,sMargin)).into(ivAvatar);
        hashtash = newsBean.image.hashtag.size();
        for (int i = 0; i < hashtash; i++) {
            StringUtil.displayText(newsBean.image.hashtag.get(i), tvHashtag);
        }

        StringUtil.displayText(newsBean.image.location, tvPinMap);
        StringUtil.displayText(newsBean.user.username, tvUserName);
        StringUtil.displayText(newsBean.image.caption, tvCaption);

        if (newsBean.image.isFavourite) {
            cvFavourite.setImageResource(R.drawable.icon_favourite);
        } else {
            cvFavourite.setImageResource(R.drawable.icon_no_favourite);
        }
        mFavourites = newsBean.image.isFavourite;

        if (newsBean.user.isFollowing) {
            btnFollow.setBackgroundResource(R.drawable.button_follow_bg);
            btnFollow.setText("Following");
        } else {
            btnFollow.setBackgroundResource(R.drawable.button_unfollow_bg);
            btnFollow.setText("Follow");
        }
        mFollow = newsBean.user.isFollowing;

    }

    @OnClick(R.id.btnFollow_detail)
    public void onFollowClick(){
        if (onDetailClicked != null){
            onDetailClicked.onFollowDetailClick(selectedHomeBean);
        }
    }
    @OnClick(R.id.imgFavourite_detail)
    public void onFavouriteClick(){
        if (onDetailClicked != null){
            onDetailClicked.onFavouriteDetailClick(selectedHomeBean);
        }
    }

//        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, newsBean.user.avatar, ivAvatar);
//        //ivAvatar.setImageResource(R.drawable.dummy_avatar);
//        StringUtil.displayText(newsBean.user.username,tvUserName);
//
//        if (newsBean.user.isFollowing) {
//            btnFollow.setBackgroundResource(R.drawable.button_follow_bg);
//            btnFollow.setText("Following");
//        }else {
//            btnFollow.setBackgroundResource(R.drawable.button_unfollow_bg);
//            btnFollow.setText("Follow");
//        }
//
//        StringUtil.displayText(newsBean.image.caption, tvCaption);
//
//        int size = newsBean.image.hashtag.size();
//        String strHashtag ="";
//        for (int i = 0; i < size; i++) {
//            strHashtag += newsBean.image.hashtag.get(i);
//            strHashtag += " ";
//        }
//        StringUtil.displayText(strHashtag, tvHashtag);
//        StringUtil.displayText(newsBean.image.location, tvPinMap);
//
//        if (newsBean.image.isFavourite) {
//            cvFavourite.setImageResource(R.drawable.icon_favourite);
//        }else {
//            cvFavourite.setImageResource(R.drawable.icon_no_favourite);
//        }
//
//        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, newsBean.image.url, ivPicture);
//        //ivPicture.setImageResource(R.drawable.placeholer_image_1600);
    }
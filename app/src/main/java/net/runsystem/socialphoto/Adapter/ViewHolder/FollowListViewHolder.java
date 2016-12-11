package net.runsystem.socialphoto.Adapter.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;

import net.runsystem.socialphoto.Bean.FollowDataBean;
import net.runsystem.socialphoto.R;

import butterknife.BindView;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 * Created by admin on 10/20/2016.
 */

public class FollowListViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.item_list_follow;

    @BindView(R.id.avatar_follow)
    public RoundedImageView ivAvatar;

    @BindView(R.id.tv_name_follow)
    public TextView tvFollowName;

    @BindView(R.id.btn_follow)
    public Button btnFollow;
//    @OnClick(R.id.btn_follow)
//    public void clickFollow(Button btnFollow){
//        btnFollow.setText("UnFollow");
//    }


    public FollowListViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(FollowDataBean followDataBean) {
        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, followDataBean.user.avatar, ivAvatar);
        StringUtil.displayText(followDataBean.user.username,tvFollowName);
        if (followDataBean.user.isFollowing) {
            btnFollow.setBackgroundResource(R.drawable.button_follow_bg);
            btnFollow.setText("Following");
        }else {
            btnFollow.setBackgroundResource(R.drawable.button_unfollow_bg);
            btnFollow.setText("Follow");
        }
    }
}

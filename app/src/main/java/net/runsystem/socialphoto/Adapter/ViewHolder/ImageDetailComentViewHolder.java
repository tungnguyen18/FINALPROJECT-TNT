package net.runsystem.socialphoto.Adapter.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.runsystem.socialphoto.Bean.CommentBean;
import net.runsystem.socialphoto.R;
import net.runsystem.socialphoto.Utinity.RoundedCornersTransformation;

import butterknife.BindView;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.util.StringUtil;

/**
 * Created by admin on 11/21/2016.
 */

public class ImageDetailComentViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.item_list_coment;

    public static int sCorner = 15;
    public static int sMargin = 2;

    @BindView(R.id.imgUser)
    ImageView ivAvatar;

    @BindView(R.id.txtCmt)
    TextView tvComment;


    public ImageDetailComentViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(CommentBean commentBean) {
        if (commentBean.user == null) {
            ivAvatar.setImageResource(R.drawable.dummy_avatar);
        } else {
            Glide.with(itemView.getContext()).load(commentBean.user.avatar)
                    .bitmapTransform(new RoundedCornersTransformation(itemView.getContext(), sCorner, sMargin)).into(ivAvatar);
        }
        StringUtil.displayText(commentBean.comment, tvComment);
    }
}

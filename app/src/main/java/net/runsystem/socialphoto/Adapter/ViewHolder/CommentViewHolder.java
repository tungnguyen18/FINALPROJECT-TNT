package net.runsystem.socialphoto.Adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.runsystem.socialphoto.Bean.CommentBean;
import net.runsystem.socialphoto.R;

/**
 * Created by admin on 12/6/2016.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder {

    ImageView ivUser;
    TextView tvCmt;

    public CommentViewHolder(View itemView) {
        super(itemView);
        ivUser = (ImageView)itemView.findViewById(R.id.imgUser);
        tvCmt = (TextView)itemView.findViewById(R.id.txtCmt);
    }

    public void bind(CommentBean commentBean) {
    }
}

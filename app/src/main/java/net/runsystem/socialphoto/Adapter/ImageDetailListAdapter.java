package net.runsystem.socialphoto.Adapter;

import android.view.ViewGroup;

import net.runsystem.socialphoto.Adapter.ViewHolder.ImageDetailComentViewHolder;
import net.runsystem.socialphoto.Adapter.ViewHolder.ImageDetailHeaderViewHolder;
import net.runsystem.socialphoto.Bean.CommentBean;
import net.runsystem.socialphoto.Bean.NewsBean;
import net.runsystem.socialphoto.callback.OnDetailClicked;

import vn.app.base.adapter.HeaderAdapterWithItemClick;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.util.UiUtil;

/**
 * Created by admin on 12/5/2016.
 */

public class ImageDetailListAdapter extends HeaderAdapterWithItemClick<OnClickViewHolder, NewsBean, CommentBean, String> {

    OnDetailClicked onDetailClicked;

    public void setOnDetailClicked(OnDetailClicked onDetailClicked) {
        this.onDetailClicked = onDetailClicked;
    }
    @Override
    protected OnClickViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new ImageDetailHeaderViewHolder(UiUtil.inflate(parent, ImageDetailHeaderViewHolder.LAYOUT_ID, false));
    }

    @Override
    protected OnClickViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ImageDetailComentViewHolder(UiUtil.inflate(parent, ImageDetailComentViewHolder.LAYOUT_ID, false));
    }

    @Override
    protected void onBindHeaderViewHolder(OnClickViewHolder holder, int position) {
        super.onBindHeaderViewHolder(holder, position);
        ((ImageDetailHeaderViewHolder)holder).bind(getHeader(),onDetailClicked);
    }

    @Override
    protected void onBindItemViewHolder(OnClickViewHolder holder, int position) {
        super.onBindItemViewHolder(holder, position);
        CommentBean commentBean = getItem(position);
        ((ImageDetailComentViewHolder)holder).bind(commentBean);
    }
}

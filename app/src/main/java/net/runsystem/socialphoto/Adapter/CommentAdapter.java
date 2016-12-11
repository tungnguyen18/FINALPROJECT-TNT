package net.runsystem.socialphoto.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import net.runsystem.socialphoto.Adapter.ViewHolder.CommentViewHolder;
import net.runsystem.socialphoto.Bean.CommentBean;
import net.runsystem.socialphoto.R;

import java.util.List;

/**
 * Created by admin on 12/6/2016.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    List<CommentBean> commentBeanList;

    public CommentAdapter(List<CommentBean> commentBeanList) {
        this.commentBeanList = commentBeanList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CommentViewHolder(layoutInflater.inflate(R.layout.item_list_coment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.bind(commentBeanList.get(position));

    }

    @Override
    public int getItemCount() {
        return commentBeanList.size();
    }
}

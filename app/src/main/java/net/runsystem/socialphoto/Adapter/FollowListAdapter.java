package net.runsystem.socialphoto.Adapter;

import android.view.ViewGroup;

import net.runsystem.socialphoto.Adapter.ViewHolder.FollowListViewHolder;
import net.runsystem.socialphoto.Bean.FollowDataBean;

import java.util.List;

import vn.app.base.adapter.AdapterWithItemClick;
import vn.app.base.util.UiUtil;

/**
 *
 */

public class FollowListAdapter extends AdapterWithItemClick<FollowListViewHolder> {
    public List<FollowDataBean> followDataBeanList;

    public FollowListAdapter(List<FollowDataBean> followDataBeanList) {
        this.followDataBeanList = followDataBeanList;
    }

    @Override
    public FollowListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowListViewHolder(UiUtil.inflate(parent, FollowListViewHolder.LAYOUT_ID, false));
    }

    @Override
    public int getItemCount() {
        return followDataBeanList.size();
    }

    @Override
    public void onBindViewHolder(FollowListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(followDataBeanList.get(position));
    }
}

package net.runsystem.socialphoto.Adapter;

import android.view.ViewGroup;

import net.runsystem.socialphoto.Adapter.ViewHolder.NewListViewHolder;
import net.runsystem.socialphoto.Bean.NewsBean;
import net.runsystem.socialphoto.callback.OnNewItemClick;

import java.util.List;

import vn.app.base.adapter.AdapterWithItemClick;
import vn.app.base.util.UiUtil;


/**
 * Created by admin on 10/17/2016.
 */

public class NewListAdapter extends AdapterWithItemClick<NewListViewHolder> {

    public List<NewsBean> newBeanList;

    OnNewItemClick onNewItemClick;




    public void setOnNewItemClick(OnNewItemClick onNewItemClick) {
        this.onNewItemClick = onNewItemClick;
    }


    public NewListAdapter(List<NewsBean> newBeanList) {
        this.newBeanList = newBeanList;
    }

    @Override
    public NewListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewListViewHolder(UiUtil.inflate(parent, NewListViewHolder.LAYOUT_ID, false));
    }

    @Override
    public int getItemCount() {
        return newBeanList.size();
    }

    @Override
    public void onBindViewHolder(NewListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(newBeanList.get(position), onNewItemClick);
    }
}

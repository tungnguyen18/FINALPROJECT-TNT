package net.runsystem.socialphoto.callback;

import net.runsystem.socialphoto.Bean.NewsBean;

/**
 * Created by admin on 12/11/2016.
 */

public interface OnDetailClicked {
    void onFollowDetailClick(NewsBean newsBean);
    void onFavouriteDetailClick(NewsBean newsBean);
    void onMapClick(NewsBean newsBean);
    void onAvatarClicked(NewsBean newsBean);
}

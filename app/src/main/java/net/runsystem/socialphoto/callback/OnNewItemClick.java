package net.runsystem.socialphoto.callback;

import net.runsystem.socialphoto.Bean.NewsBean;

/**
 * Created by admin on 12/7/2016.
 */

public interface OnNewItemClick {
    void onFavouriteClick(String imageId, int favourite);

    void onFollowClick(String userId, int follow);

    void onAvatarClick(String userId);

    void onPictureClick(NewsBean imageProfile);

    void onPinMapClick(NewsBean newsBean);
}

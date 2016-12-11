package net.runsystem.socialphoto.callback;

/**
 * Created by admin on 12/6/2016.
 */

public interface OnUserEdit {
    void onChangePhoto(int position);

    void onClickPhoto(String url);
}

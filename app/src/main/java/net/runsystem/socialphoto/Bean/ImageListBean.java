package net.runsystem.socialphoto.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 12/11/2016.
 */

public class ImageListBean {
    @SerializedName("user")
    @Expose
    public UserBean user;
    @SerializedName("image")
    @Expose
    public ImageBean image;
}

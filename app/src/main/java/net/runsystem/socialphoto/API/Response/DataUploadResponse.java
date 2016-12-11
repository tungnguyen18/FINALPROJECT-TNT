package net.runsystem.socialphoto.API.Response;

/**
 * Created by admin on 10/24/2016.
 */
import com.google.gson.annotations.SerializedName;

import net.runsystem.socialphoto.Bean.ImageBean;
import net.runsystem.socialphoto.Bean.UserBean;

public class DataUploadResponse {

    @SerializedName("user")
    public UserBean user;

    @SerializedName("image")
    public ImageBean image;
}

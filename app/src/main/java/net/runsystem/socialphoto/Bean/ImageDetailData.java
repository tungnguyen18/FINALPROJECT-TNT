package net.runsystem.socialphoto.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 12/5/2016.
 */

public class ImageDetailData {
    @SerializedName("user")
    public UserBean user;

    @SerializedName("comment")
    public String comment;

    @SerializedName("created_at")
    public Integer createdAt;
}

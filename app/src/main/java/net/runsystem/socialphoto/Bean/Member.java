package net.runsystem.socialphoto.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 11/17/2016.
 */

public class Member {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("avatar")
    @Expose
    public String avatar;
    @SerializedName("is_following")
    @Expose
    public Boolean isFollowing;
}

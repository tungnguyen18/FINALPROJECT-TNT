package net.runsystem.socialphoto.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 12/6/2016.
 */

public class CommentBean {
    @SerializedName("user")
    @Expose
    public Member user;
    @SerializedName("comment")
    @Expose
    public String comment;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    }


package net.runsystem.socialphoto.API.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.runsystem.socialphoto.Bean.User;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by admin on 11/2/2016.
 */

public class LoginResponse extends BaseResponse {
    public String token;
    @SerializedName("data")
    @Expose
    public User data;
}

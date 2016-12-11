package net.runsystem.socialphoto.API.Response;

import com.google.gson.annotations.SerializedName;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by Toi on 10/24/2016.
 */

public class UploadImageResponse extends BaseResponse {

    @SerializedName("data")
    public DataUploadResponse data;
}

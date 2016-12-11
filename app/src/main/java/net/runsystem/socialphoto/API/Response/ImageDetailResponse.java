package net.runsystem.socialphoto.API.Response;

import com.google.gson.annotations.SerializedName;

import net.runsystem.socialphoto.Bean.ImageListBean;

import java.util.ArrayList;
import java.util.List;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by admin on 12/5/2016.
 */

public class ImageDetailResponse extends BaseResponse {

    @SerializedName("data")
    public List<ImageListBean> data = new ArrayList<ImageListBean>();

}

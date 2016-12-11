package net.runsystem.socialphoto.API.Response;

import com.google.gson.annotations.SerializedName;

import net.runsystem.socialphoto.Bean.FollowDataBean;

import java.util.ArrayList;
import java.util.List;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by admin on 11/17/2016.
 */

public class FollowResponse extends BaseResponse {
    @SerializedName("data")
    public List<FollowDataBean> followDatas = new ArrayList<FollowDataBean>();
}

package net.runsystem.socialphoto.API.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.runsystem.socialphoto.Bean.CommentBean;

import java.util.ArrayList;
import java.util.List;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by admin on 12/11/2016.
 */

public class CommentListResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public List<CommentBean> data = new ArrayList<CommentBean>();
}

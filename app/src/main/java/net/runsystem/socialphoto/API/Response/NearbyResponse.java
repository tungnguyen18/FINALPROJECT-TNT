package  net.runsystem.socialphoto.API.Response;

/**
 *
 */

import com.google.gson.annotations.SerializedName;

import net.runsystem.socialphoto.Bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

import vn.app.base.api.response.BaseResponse;

public class NearbyResponse extends BaseResponse{
    @SerializedName("data")
    public List<NewsBean> data = new ArrayList<NewsBean>();
}

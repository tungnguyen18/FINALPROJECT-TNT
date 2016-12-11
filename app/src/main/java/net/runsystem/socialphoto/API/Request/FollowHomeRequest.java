package net.runsystem.socialphoto.API.Request;

import com.android.volley.Request;

import net.runsystem.socialphoto.Constant.ApiConstance;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by admin on 11/22/2016.
 */

public class FollowHomeRequest extends ObjectApiRequest<BaseResponse>{
    public static final String URLFOLLOW = "https://polar-plains-86888.herokuapp.com/api/follow";

    String userId;
    int isFollow = 2;

    public FollowHomeRequest(String userId, int isFollow) {
        this.userId = userId;
        this.isFollow = isFollow;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {

         return URLFOLLOW;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> header = new HashMap<>();
        header.put(ApiConstance.TOKEN, SharedPrefUtils.getAccessToken());
        return header;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String,String> params = new HashMap<>();
        if (!userId.isEmpty()) {
            params.put(ApiConstance.USER_ID, userId);
        }

        if (isFollow != 2) {
            params.put(ApiConstance.IS_FOLLOW, Integer.toString(isFollow));
        }

        return params;
    }

    @Override
    public Class<BaseResponse> getResponseClass() {
        return BaseResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}

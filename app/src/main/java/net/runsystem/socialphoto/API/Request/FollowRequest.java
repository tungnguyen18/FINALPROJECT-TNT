package net.runsystem.socialphoto.API.Request;

import com.android.volley.Request;

import net.runsystem.socialphoto.API.Response.FollowResponse;
import net.runsystem.socialphoto.Constant.ApiConstance;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by admin on 11/17/2016.
 */

public class FollowRequest extends ObjectApiRequest<FollowResponse> {

    public static final String URLFOLLOWLIST = "https://polar-plains-86888.herokuapp.com/api/followlist";


    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return URLFOLLOWLIST;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }
    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> newHeader = new HashMap<>();
        newHeader.put(ApiConstance.TOKEN, SharedPrefUtils.getAccessToken());
        return newHeader;
    }

    @Override
    public Map<String, String> getRequestParams() {
        return null;
    }

    @Override
    public Class<FollowResponse> getResponseClass() {
        return FollowResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.GET;
    }
}

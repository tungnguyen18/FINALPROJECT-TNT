package net.runsystem.socialphoto.API.Request;

import com.android.volley.Request;

import net.runsystem.socialphoto.API.Response.NewsResponse;
import net.runsystem.socialphoto.Constant.ApiConstance;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.constant.ApiParam;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by admin on 12/12/2016.
 */

public class FavouriteListRequest extends ObjectApiRequest<NewsResponse> {

    public static final String FAVOURITE_LIST="https://polar-plains-86888.herokuapp.com/api/favouritelist";
    String userId;

    public FavouriteListRequest(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return FAVOURITE_LIST;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String,String> param = new HashMap<>();
        if (userId != null){
            param.put(ApiConstance.USERID , userId);
            return param;
        }else {
            return null;
        }
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> newHeader = new HashMap<>();
        newHeader.put(ApiParam.TOKEN, SharedPrefUtils.getAccessToken());
        return newHeader;
    }

    @Override
    public Class<NewsResponse> getResponseClass() {
        return NewsResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}

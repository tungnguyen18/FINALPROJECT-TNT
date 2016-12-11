package net.runsystem.socialphoto.API.Request;

import com.android.volley.Request;

import net.runsystem.socialphoto.API.Response.NewsResponse;
import net.runsystem.socialphoto.Constant.ApiConstance;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by admin on 10/25/2016.
 */

public class NewsRequest extends ObjectApiRequest<NewsResponse>{

    int type;
    String last_query_timestamp;
    int num;

    public NewsRequest(int type, String last_query_timestamp, int num) {
        this.type = type;
        this.last_query_timestamp = last_query_timestamp;
        this.num = num;
   }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public String getRequestURL() {
        String url = "https://polar-plains-86888.herokuapp.com/api/home";
        return url;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {

        Map<String, String> params = new HashMap<>();
        params.put(ApiConstance.HOME_TYPE, Integer.toString(type));
        if (!last_query_timestamp.isEmpty()) {
            params.put(ApiConstance.LAST_TIMESTAMP, last_query_timestamp);
        }
      //  params.put("last_query_timestamp", Long.toString(last_query_timestamp));
     //   params.put("num", Integer.toString(num));
        return params;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> header = new HashMap<>();
        header.put(ApiConstance.TOKEN, SharedPrefUtils.getAccessToken());   //"token", SharedPrefUtils.getAccessToken()
        return header;
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

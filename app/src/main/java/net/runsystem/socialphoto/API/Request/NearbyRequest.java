package net.runsystem.socialphoto.API.Request;

import com.android.volley.Request;

import net.runsystem.socialphoto.API.Response.NearbyResponse;
import net.runsystem.socialphoto.Constant.ApiConstance;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 *
 */

public class NearbyRequest extends ObjectApiRequest<NearbyResponse>{

    public static final String NEARBY = "https://polar-plains-86888.herokuapp.com/api/nearby";

    double dbLat;
    double dbLong;

    public NearbyRequest(double dbLat, double dbLong) {
        this.dbLat = dbLat;
        this.dbLong = dbLong;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return NEARBY;
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

        Map<String, String> params = new HashMap<>();
        params.put("lat", Double.toString(dbLat));
        params.put("long", Double.toString(dbLong));
        return params;
    }

    @Override
    public Class<NearbyResponse> getResponseClass() {
        return NearbyResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}

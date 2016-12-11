package net.runsystem.socialphoto.API.Request;

import com.android.volley.Request;

import net.runsystem.socialphoto.API.Response.ImageDetailResponse;
import net.runsystem.socialphoto.Constant.ApiConstance;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by admin on 12/5/2016.
 */

public class ImageDetailRequest extends ObjectApiRequest<ImageDetailResponse> {
    String imageId;
//
//    public ImageDetailRequest(String imageId) {
//        this.imageId = imageId;
//    }
    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        String url = "https://polar-plains-86888.herokuapp.com/api/image/list";
        return url;
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
        if (imageId != null) {
            params.put(ApiConstance.IMAGE_ID, imageId);
            return params;
        } else {
            return null;
        }
    }

    @Override
    public Class<ImageDetailResponse> getResponseClass() {
        return ImageDetailResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}

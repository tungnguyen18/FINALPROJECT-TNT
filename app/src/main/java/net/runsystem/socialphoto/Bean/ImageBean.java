package net.runsystem.socialphoto.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toi on 10/25/2016.
 */

public class ImageBean implements Parcelable {

    @SerializedName("_id")
    @Expose
    public String id;

    @SerializedName("public_id")
    @Expose
    public String publicId;

    @SerializedName("version")
    public Integer version;

    @SerializedName("width")
    public Integer width;

    @SerializedName("height")
    public Integer height;

    @SerializedName("format")
    public String format;

    @SerializedName("resource_type")
    public String resourceType;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("bytes")
    public Integer bytes;

    @SerializedName("type")
    public String type;

    @SerializedName("url")
    public String url;

    @SerializedName("secure_url")
    public String secureUrl;

    @SerializedName("user_id")
    public String userId;

    @SerializedName("caption")
    public String caption;

    @SerializedName("location")
    public String location;
    @SerializedName("lat")
    public String lat;

    @SerializedName("long")
    public String _long;

    @SerializedName("hashtag")
    public List<String> hashtag = new ArrayList<String>();

    @SerializedName("is_favourite")
    public Boolean isFavourite;

    protected ImageBean(Parcel in) {
        id = in.readString();
        publicId = in.readString();
        format = in.readString();
        resourceType = in.readString();
        createdAt = in.readString();
        type = in.readString();
        url = in.readString();
        secureUrl = in.readString();
        userId = in.readString();
        caption = in.readString();
        location = in.readString();
        lat = in.readString();
        _long = in.readString();
        hashtag = in.createStringArrayList();
    }

    public static final Parcelable.Creator<ImageBean> CREATOR = new Parcelable.Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel in) {
            return new ImageBean(in);
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(publicId);
        dest.writeString(format);
        dest.writeString(resourceType);
        dest.writeString(createdAt);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(secureUrl);
        dest.writeString(userId);
        dest.writeString(caption);
        dest.writeString(location);
        dest.writeString(lat);
        dest.writeString(_long);
        dest.writeStringList(hashtag);
    }
}

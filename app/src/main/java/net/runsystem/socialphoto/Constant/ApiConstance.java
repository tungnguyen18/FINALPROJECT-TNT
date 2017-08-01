package net.runsystem.socialphoto.Constant;

/**
 * Created by admin on 11/28/2016.
 */

public class ApiConstance {
    public static final String API_URL = "https://polar-plains-86888.herokuapp.com/api/";
    public static final String IMAGE_UPLOAD = API_URL + "image/upload";
    public static final String REGISTER = API_URL + "regist";


    public static final int REQUEST_CODE_TAKEPHOTO = 100;
    public static final int CAMERA_REQQUEST_CROP = 927;
    public static final String PHOTO_FILE_NAME = "photo.jpg";
    public static final String TOKEN = "token";
    public static final String TOKEN_CODE = "a46f90def2e9ac297c0df00cc64b0fb1";

    //ImageUpload Request
    public static final String UNKNOW_ERROR = "UNKNOW_ERROR";
    public static final String NO_CONNECTION_ERROR = "NO_CONNECTION_ERROR";
    public static final String CAPTION = "CAPTION";
    public static final String LOCATION = "LOCATION";
    public static final String LAT = "lat";
    public static final String LONG = "long";
    public static final String HASHTAG = "HASHTAG";
    public static final String IMAGE = "IMAGE";

    // Delete Image
    public static final String IMAGE_ID = "imageId";

    // Home
    public static final String HOME_TYPE = "type";
    public static final String LAST_TIMESTAMP = "last_query_timestamp";

    // Image List
    public static final String USER_ID = "userId";
    // Favourite
    public static final String IS_FAVOURITE = "isFavourite";
    public static final String IS_FOLLOW = "isFollow";

    //Login + register
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final int REQUEST_CODE_PICKPHOTO = 188;
    public static final String ISLOGINYET = "isloginyet";
    //TAG for fragment
    public static final String TAGHOME = "taghome";
    // Upload image
    public static final String FILE_IMAGE = "image";
    public static final String FILE_CAPTION = "caption";
    public static final String FILE_LOCATION = "location";
    public static final String FILE_HASHTAG = "hashtag";
    //Detail
    public static final String URLCOMMENTLIST = "https://polar-plains-86888.herokuapp.com/api/commentlist";
    public static final String URLCOMMENT = "https://polar-plains-86888.herokuapp.com/api/comment";
    public static final String URLDELETE = "https://polar-plains-86888.herokuapp.com/api/image/delete";
    public static final String IMAGEID = "imageId";
    public static final String COMMENT = "comment";
    public static final String USERID = "userId";
    public static final String ISDELCLICK = "isdeleteclicked";

    //Follow & Favourite
    public static final String FAVOURITE_STATUS = "isFavourite";
    public static final String FOLLOW_STATUS = "isFollow";
    public static final int FOLLOW = 1;
    public static final int UN_FOLLOW = 0;
    public static final int UN_FAVOURITE = 0;
}

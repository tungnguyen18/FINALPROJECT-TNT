package net.runsystem.socialphoto.manager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.runsystem.socialphoto.Bean.User;

import vn.app.base.util.SharedPrefUtils;
import vn.app.base.util.StringUtil;

/**
 * Created on 7/19/2016.
 */
public class UserManager {

    private static Gson gson = new Gson();

    public static final String USER_DATA = "USER_DATA";

    public static void saveCurrentUser(User user) {
        String userData = gson.toJson(user, User.class);
        SharedPrefUtils.putString(USER_DATA, userData);
    }

    public static User getCurrentUser() {
        String userData = SharedPrefUtils.getString(USER_DATA, null);
        if (StringUtil.checkStringValid(userData)) {
            try {
                return gson.fromJson(userData, User.class);
            } catch (JsonSyntaxException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void clearUserData() {
        SharedPrefUtils.removeKey(USER_DATA);
        SharedPrefUtils.removeKey("TOKEN");
    }
}

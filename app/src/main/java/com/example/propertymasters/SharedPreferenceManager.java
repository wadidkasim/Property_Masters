package com.example.propertymasters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.example.propertymasters.activities.LoginActivity;
import com.example.propertymasters.activities.WelcomeActivity;
import com.example.propertymasters.models.User;


public class SharedPreferenceManager {

  private static final String SHARED_PREF_NAME = "user_login";

  private static final String KEY_USERID = "keyuserid";
  private static final String KEY_USERNAME = "keyusername";
  private static final String KEY_EMAIL = "keyemail";
  private static final String KEY_PHONE = "keyphone";
  private static final String KEY_PROFILE_PICTURE = "keyprofilepicture";
  private static final String KEY_ROLE_ID = "keyroleid";
  private static final String KEY_ROLE_NAME = "keyrolename";



  private static SharedPreferenceManager mInstance;
  private static Context ctx;

  private SharedPreferenceManager(Context context) {
    ctx = context;
  }

  public static synchronized SharedPreferenceManager getInstance(Context context) {
    if (mInstance == null) {
      mInstance = new SharedPreferenceManager(context);
    }
    return mInstance;
  }

  //this method will store the user data in shared preferences
  public void userLogin(User user) {
    SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(KEY_USERID, user.getUserId());
    editor.putString(KEY_USERNAME, user.getUserName());
    editor.putString(KEY_EMAIL, user.getEmail());
    editor.putString(KEY_PHONE, user.getPhone());
    editor.putString(KEY_PROFILE_PICTURE, user.getProfilePicture());
    editor.putInt(KEY_ROLE_ID, user.getRoleId());
    editor.putString(KEY_ROLE_NAME, user.getRoleName());

    editor.apply();
  }

  //this method will checker whether student is already logged in or not
  public boolean isLoggedIn() {
    SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getInt(KEY_USERID, 0) != 0;
  }

  public boolean isSuperAdmin() {
    SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getInt(KEY_USERID, 0) != 0;
  }

  //this method will give the logged in student
  public User getUser() {
    SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return new User(
            sharedPreferences.getInt(KEY_USERID, 0),
            sharedPreferences.getString(KEY_USERNAME, null),
            sharedPreferences.getString(KEY_EMAIL, null),
            sharedPreferences.getString(KEY_PHONE, null),
            sharedPreferences.getString(KEY_PROFILE_PICTURE, null),
            sharedPreferences.getInt(KEY_ROLE_ID, 0),
            sharedPreferences.getString(KEY_ROLE_NAME, null)
    );
  }

  //this method will logout the user
  public void logout() {
    SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.clear();
    editor.apply();

    Intent intent = new Intent(ctx, WelcomeActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    ctx.startActivity(intent);

  }

  public String getProfileImageUrl() {
    SharedPreferences preferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return preferences.getString(KEY_PROFILE_PICTURE, "hello");
  }

  public void setProfileImageUrl(String url) {
    SharedPreferences.Editor editor = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
    editor.putString(KEY_PROFILE_PICTURE, url);
    editor.apply();
  }

  public boolean isAdmin() {
    SharedPreferences preferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    if(preferences.getInt(KEY_ROLE_ID, 0)==1){
      return true;
    } else {
      return false;
    }
  }

}
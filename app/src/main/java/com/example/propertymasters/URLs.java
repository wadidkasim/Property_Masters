package com.example.propertymasters;

public class URLs {

  private static final String ROOT_URL = "http://192.168.0.128/property-masters-api/api/";

  private static final String USER_API = "user/";
  private static final String PROPERTY_API = "property/";
//  private static final String ROOT_URL_000WEBHOSTAPP = "https://iusuapp.000webhostapp.com/iusu_app_conn_v4/login_signup_api.php/?apicall=";
//  private static final String ROOT_URL_000WEBHOSTAPP_PROFILE_PIC_UPLOAD = "https://iusuapp.000webhostapp.com/iusu_app_conn_v4/insert_profile_image_api.php/?apicall=";
//  private static final String ROOT_URL_000WEBHOSTAPP_NEWS_UPLOAD = "https://iusuapp.000webhostapp.com/iusu_app_conn_v4/news_api.php/?apicall=";
//  private static final String ROOT_URL_000WEBHOSTAPP_ANNOUNCEMENT_UPLOAD = "https://iusuapp.000webhostapp.com/iusu_app_conn_v4/announcement_api.php?apicall=";
//  private static final String ROOT_URL_000WEBHOSTAPP_EVENT_UPLOAD = "https://iusuapp.000webhostapp.com/iusu_app_conn_v4/event_api.php?apicall=";
//  private static final String ROOT_URL_000WEBHOSTAPP_GUILD = "https://iusuapp.000webhostapp.com/iusu_app_conn_v4/guild_official_api.php/?apicall=";
//

  public static final String URL_LOGIN = ROOT_URL+USER_API+"login.php";
  public static final String URL_SIGNUP = ROOT_URL+USER_API+"signup.php";
  public static final String URL_READ_USERS = ROOT_URL+USER_API+"read.php";
  public static final String URL_READ_SINGLE_USER = ROOT_URL+USER_API+"read_single.php";
  public static final String URL_DELETE_USER = ROOT_URL+USER_API+"delete.php";
  public static final String URL_UPDATE_USER = ROOT_URL+USER_API+"update.php";



//  public static final String URL_NEWS_UPLOAD=ROOT_URL_000WEBHOSTAPP_NEWS_UPLOAD+"make_post";
//  public static final String URL_NEWS_FETCH=ROOT_URL_000WEBHOSTAPP_NEWS_UPLOAD+"getnews";
//  public static final String URL_LATEST_NEWS_FETCH=ROOT_URL_000WEBHOSTAPP_NEWS_UPLOAD+"latest_news";
//
//  public static final String URL_ANN_UPLOAD=ROOT_URL_000WEBHOSTAPP_ANNOUNCEMENT_UPLOAD+"make_announcement";
//  public static final String URL_ANN_GET_ANN=ROOT_URL_000WEBHOSTAPP_ANNOUNCEMENT_UPLOAD+"getannouncements";
//  public static final String URL_LATEST_ANNOUNCEMENTS=ROOT_URL_000WEBHOSTAPP_ANNOUNCEMENT_UPLOAD+"latest_announcements";
//
// public static final String URL_EVENT_UPLOAD=ROOT_URL_000WEBHOSTAPP_EVENT_UPLOAD+"make_post";
//  public static final String URL_GET_EVENT=ROOT_URL_000WEBHOSTAPP_EVENT_UPLOAD+"getevents";
//  public static final String URL_LATEST_EVENT=ROOT_URL_000WEBHOSTAPP_EVENT_UPLOAD+"latest_events";
//
//
//  //guild official
//
//  public static final String URL_ADD_GUILD_OFFICIAL=ROOT_URL_000WEBHOSTAPP_GUILD+"add_guild_official";
//  public static final String URL_FETCH_GUILD_OFFICIAL=ROOT_URL_000WEBHOSTAPP_GUILD+"fetch_guild_officials";
//  public static final String URL_UPDATE_GUILD_OFFICIAL=ROOT_URL_000WEBHOSTAPP_GUILD+"update_guild_officials";


}

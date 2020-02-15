package com.marty.track.Constants;

public class Constant {

    public static String BASE_URL = "http://codecell.in/schools/Admin/";
    public static String Track_URL = "http://codecell.in/schools/Admin/location/";
    public static String Route_URL = "http://codecell.in/schools/Admin/studentlist_pickloc/";

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(BASE_URL);
        sb.append("location/");
        Track_URL = sb.toString();
    }

}

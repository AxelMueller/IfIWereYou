package com.ifiwereyou.objects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by D060426 on 23.04.2015.
 */
@ParseClassName("Friendship")
public class Friendship extends ParseObject{

    public static final String KEY_FRIENDA = "friendA";
    public static final String KEY_FRIENDB = "friendB";

    public String getFriendA() {
        return getString(KEY_FRIENDA);
    }

    public String getFriendB() {
        return getString(KEY_FRIENDB);
    }

    public static ParseQuery<Friendship> getQuery(){
        return ParseQuery.getQuery("Friendship");
    }

}

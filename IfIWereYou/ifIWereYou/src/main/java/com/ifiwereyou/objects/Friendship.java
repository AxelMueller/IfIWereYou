package com.ifiwereyou.objects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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

    public ParseUser getFriend() {
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        return (getParseObject(KEY_FRIENDA).getObjectId().equals(currentUserId) && !getParseObject(KEY_FRIENDB).getObjectId().equals(currentUserId)) ? (ParseUser) getParseObject(KEY_FRIENDA)
                : (getParseObject(KEY_FRIENDB).getObjectId().equals(currentUserId) && !getParseObject(KEY_FRIENDA).getObjectId().equals(currentUserId)) ? (ParseUser) getParseObject(KEY_FRIENDB)
                    : null;
    }

    public static ParseQuery<Friendship> getQuery(){
        return ParseQuery.getQuery("Friendship");
    }

}

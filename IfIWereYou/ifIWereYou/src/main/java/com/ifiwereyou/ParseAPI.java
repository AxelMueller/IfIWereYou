package com.ifiwereyou;

import android.content.Context;

import com.parse.Parse;
import com.parse.PushService;

/**
 * Created by D060426 on 09.06.2015.
 */
public class ParseAPI {
    public void init(Context context, String key, String id) {
        // Add your initialization code here
        Parse.initialize(context, key,
                id);
    }

    public void initPush(Context context, Class pushCallbackClass) {
        // Specify an Activity to handle all pushes by default.
        PushService.setDefaultPushCallback(context, pushCallbackClass);
    }
}

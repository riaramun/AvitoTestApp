package com.avito.avitotestapp.helper;

import android.content.Context;

import com.avito.avitotestapp.model.UserModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Roman on 9/25/2015.
 */
public class HttpHelper {

    public static void getData(Context context)
    {
        Ion.with(context)
                .load("https://api.github.com/users")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if(result != null) {
                            for (int i = 0; i < result.size(); i++) {
                                JsonObject jsonObject = (JsonObject) result.get(i);
                                UserModel userModel = new UserModel();
                                userModel.avatar_url = jsonObject.get("avatar_url").getAsString();
                                userModel.login = jsonObject.get("login").getAsString();
                                userModel.save();
                            }
                        }
                    }
                });
    }
}

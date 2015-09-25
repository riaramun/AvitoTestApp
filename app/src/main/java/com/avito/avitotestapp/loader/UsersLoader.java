package com.avito.avitotestapp.loader;

import android.content.Context;
import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Select;
import com.avito.avitotestapp.model.UserModel;

/**
 * Created by Roman Lebedenko.
 * fisher3421@gmail.com
 */

public class UsersLoader extends CursorRiaLoader {

    public UsersLoader(Context context) {
        super(context);
        setSubscription(ContentProvider.createUri(UserModel.class, null));
    }

    @Override
    protected Cursor loadCursor() throws Exception {
        String select = new Select().from(UserModel.class).toSql();
        return Cache.openDatabase().rawQuery(select, null);
    }
}

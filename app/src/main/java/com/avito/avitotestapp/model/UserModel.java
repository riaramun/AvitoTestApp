package com.avito.avitotestapp.model;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Roman on 7/9/2015.
 */

@Table(name = "UserModel", id = BaseColumns._ID)
public class UserModel extends Model {

    @Column(name = "login",unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String login;


    @Column(name = "avatar_url")
    public String avatar_url;

    public UserModel() {
        super();
    }

    /*public List<RosterEntryModel> items() {
        return getMany(RosterEntryModel.class, "ChatRoomModel");
    }*/
}

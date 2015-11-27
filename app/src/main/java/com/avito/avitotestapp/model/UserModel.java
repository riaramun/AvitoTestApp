package com.avito.avitotestapp.model;

import android.os.Parcel;
import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;

/**
 * Created by Roman on 7/9/2015.
 */

@Table(name = "UserModel", id = BaseColumns._ID)
public class UserModel extends Model implements AsymmetricItem {

    private int columnSpan;
    private int rowSpan;

    @Column(name = "login", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String login;


    @Column(name = "avatar_url")
    public String avatar_url;

    public void setSpans(int rowSpan, int columnSpan) {
        this.columnSpan = columnSpan;
        this.rowSpan = rowSpan;
    }

    public UserModel() {
        super();
        columnSpan = 1;
        rowSpan = 1;
    }

    @Override
    public int getColumnSpan() {
        return columnSpan;
    }

    @Override
    public int getRowSpan() {
        return rowSpan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    /*public List<RosterEntryModel> items() {
        return getMany(RosterEntryModel.class, "ChatRoomModel");
    }*/
}

package com.avito.avitotestapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avito.avitotestapp.R;
import com.avito.avitotestapp.helper.DbHelper;
import com.avito.avitotestapp.model.UserModel;
import com.koushikdutta.ion.Ion;

/**
 * Created by Roman on 6/30/2015.
 */
public class UsersAdapter extends CursorRecyclerViewAdapter {

    boolean mListIsEmpty = false;
    static final int LIST_EMPTY_ITEMS_COUNT = 1;

    public UsersAdapter(Context context) {
        super(context, null);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        Cursor cursor = getCursor();
        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
        }
        if (count == 0) {
            mListIsEmpty = true;
            count = LIST_EMPTY_ITEMS_COUNT;//empty view
        } else {
            mListIsEmpty = false;
        }
        return count;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_EMPTY_ITEM:
                break;
            case VIEW_TYPE_CONTENT:
                final UserModel userModel = DbHelper.getModelByCursor(cursor, UserModel.class);
                final UserViewHolder contactViewHolder = (UserViewHolder) viewHolder;
                contactViewHolder.userTextView.setText(userModel.login);
                Ion.with(contactViewHolder.userImageView)
                        .load(userModel.avatar_url);
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        int resType = -1;
        // final int count = getItemCount();
        if (mListIsEmpty) {
            resType = VIEW_TYPE_EMPTY_ITEM;
        } else {
            resType = VIEW_TYPE_CONTENT;
        }
        return resType;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        RecyclerView.ViewHolder vh = null;
        switch (viewType) {
            case VIEW_TYPE_CONTENT:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
                vh = new UserViewHolder(itemView);
                break;
            case VIEW_TYPE_EMPTY_ITEM:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_empty, parent, false);
                itemView.setLayoutParams(new RecyclerView.LayoutParams(parent.getWidth(), parent.getHeight()));
                vh = new EmptyViewHolder(itemView);
                break;
        }
        return vh;
    }

    public UserModel getItem(int index) {
        UserModel UserModel = null;
        Cursor cursor = getCursor();
        if (!cursor.isClosed() && cursor.moveToPosition(index)) {
            UserModel = DbHelper.getModelByCursor(cursor, UserModel.class);
        }
        return UserModel;

    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        public final TextView userTextView;
        public final ImageView userImageView;

        public UserViewHolder(View itemView) {
            super(itemView);
            userTextView = (TextView) itemView.findViewById(R.id.user_text_view);
            userImageView = (ImageView) itemView.findViewById(R.id.user_image_view);
        }
    }
    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

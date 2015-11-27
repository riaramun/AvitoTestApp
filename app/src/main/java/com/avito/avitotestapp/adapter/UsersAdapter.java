package com.avito.avitotestapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

    public UsersAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
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


    public UserModel getItem(int index) {
        UserModel UserModel = null;
        Cursor cursor = getCursor();
        if (!cursor.isClosed() && cursor.moveToPosition(index)) {
            UserModel = DbHelper.getModelByCursor(cursor, UserModel.class);
        }
        return UserModel;

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View itemView = null;

        if (mListIsEmpty) {
            EmptyView view = new EmptyView(context);
            view.init(null);
            itemView = view;
            itemView.setLayoutParams(new RecyclerView.LayoutParams(parent.getWidth(), parent.getHeight()));
        } else {
            ItemView view = new ItemView(context);
            view.init(R.layout.list_item_user);
            itemView = view;
            //itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        }
        return itemView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if (mListIsEmpty) {
            EmptyViewHolder emptyViewHolder = new EmptyViewHolder(view);

        } else {
            final UserModel userModel = DbHelper.getModelByCursor(cursor, UserModel.class);
            int pos = cursor.getPosition();
            if (pos == 0){
                userModel.setSpans(2, 2);
            }
            if (pos == 3){
                userModel.setSpans(2, 3);
            }
            if (cursor.getPosition() % 2 == 0 ) {
                //userModel.setSpans(2, 2);
            }/*else if (cursor.getPosition() % 3 == 0) {
                userModel.setSpans(6, 3);
            } else if (cursor.getPosition() % 5 == 0) {
                userModel.setSpans(2, 3);
            }*/ else {
              //  userModel.setSpans(1, 1);
            }

            ItemView itemView = (ItemView) view;
            if (userModel != null && !TextUtils.isEmpty(userModel.avatar_url) && itemView != null) {
                //final UserViewHolder contactViewHolder = new UserViewHolder(view);
                //   contactViewHolder.userTextView.setText(userModel.login);
               Ion.with(itemView.userImageView).load(userModel.avatar_url);
            } else {
           //     itemView.userImageView.setImageResource(R.mipmap.ic_launcher);
            }
            itemView.textView.setText("" + cursor.getPosition());
        }
    }

    class EmptyView extends View {

        public EmptyView(Context context) {
            super(context);
        }

        public void init(ViewGroup parent) {
            inflate(getContext(), R.layout.list_item_empty, null);
        }
    }

    class ItemView extends RelativeLayout {

        int resId = R.layout.list_item_user;
        public ImageView userImageView;
        public TextView textView;

        public ItemView(Context context) {
            super(context);
            inflate();
        }

        public ItemView(Context context, AttributeSet attrs) {
            super(context, attrs);
            inflate();
        }

        public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            inflate();
        }


        public void init(int resId) {
            this.resId = resId;
        }

        public void inflate() {
            LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflator.from(getContext()).inflate(resId, null, true);
            this.addView(view);
            userImageView = (ImageView) view.findViewById(R.id.user_image_view);
            textView = (TextView) view.findViewById(R.id.user_text_view);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (mCursor == null || !mCursor.moveToPosition(position)) {
            EmptyView emptyView = new EmptyView(mContext);
            emptyView.init(parent);
            v = emptyView;
        } else {
            if (convertView == null) {
                v = newView(mContext, mCursor, parent);
            } else {
                v = convertView;
            }
            bindView(v, mContext, mCursor);
        }
        return v;
    }


    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

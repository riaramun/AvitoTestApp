package com.avito.avitotestapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;

import com.avito.avitotestapp.adapter.UsersAdapter;
import com.avito.avitotestapp.helper.HttpHelper;
import com.avito.avitotestapp.loader.CursorRiaLoader;
import com.avito.avitotestapp.loader.UsersLoader;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<CursorRiaLoader.LoaderResult<Cursor>> {

    final int USERS_LOADER = 1;
    UsersAdapter chatsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsymmetricGridView recyclerView = (AsymmetricGridView) findViewById(R.id.recycler_view);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        chatsAdapter = new UsersAdapter(this, null, true);
        //recyclerView.setAdapter(chatsAdapter);
        //recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setItemAnimator(itemAnimator);


        // Choose your own preferred column width
        //recyclerView.setRequestedColumnWidth(Utils.dpToPx(this, 120));
      // final List<AsymmetricItem> items = new ArrayList<>();

        // initialize your items array
        //adapter = new ListAdapter(this, listView, items);
        AsymmetricGridViewAdapter asymmetricAdapter =
                new AsymmetricGridViewAdapter<>(this, recyclerView, chatsAdapter);
        recyclerView.setAdapter(asymmetricAdapter);

        recyclerView.setRequestedColumnCount(3);
        recyclerView.setRequestedHorizontalSpacing(Utils.dpToPx(this, 3));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<CursorRiaLoader.LoaderResult<Cursor>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case USERS_LOADER:
                return new UsersLoader(this);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<CursorRiaLoader.LoaderResult<Cursor>> loader, CursorRiaLoader.LoaderResult<Cursor> data) {
        chatsAdapter.changeCursor(data.result);
    }

    @Override
    public void onLoaderReset(Loader<CursorRiaLoader.LoaderResult<Cursor>> loader) {

    }

    public void onResume() {
        super.onResume();
        initOrRestartLoader(USERS_LOADER, null, this);
        HttpHelper.getData(this);
    }

    void initOrRestartLoader(int loaderId, Bundle bundle, LoaderManager.LoaderCallbacks<CursorRiaLoader.LoaderResult<Cursor>> callback) {
        if (getSupportLoaderManager().getLoader(loaderId) == null) {
            getSupportLoaderManager().initLoader(loaderId, bundle, callback);
        } else {
            getSupportLoaderManager().restartLoader(loaderId, bundle, callback);
        }
    }
}

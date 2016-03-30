package com.example.ivansv.softomatetest;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * Created by ivansv on 29.03.2016.
 */
public class ListFragment extends Fragment implements LoaderCallbacks<Cursor> {
    private DataAdapter dataAdapter;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(MainActivity.textLanguages);
        dataAdapter = new DataAdapter(getContext(), null, 0);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listView.setAdapter(dataAdapter);
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
//        recyclerView.setLayoutManager(linearLayoutManager);
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DataContract.Data.CONTENT_URI,
                DataContract.Data.DEFAULT_PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        dataAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        dataAdapter.swapCursor(null);
    }
}

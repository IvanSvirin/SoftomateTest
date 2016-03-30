package com.example.ivansv.softomatetest;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ivansv on 30.03.2016.
 */
public class DataAdapter extends CursorAdapter {
    private LayoutInflater inflater;

    public DataAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View root = inflater.inflate(R.layout.fragment_list_item, parent, false);
        ViewHolder holder = new ViewHolder();
        TextView userTextTextView = (TextView)root.findViewById(R.id.userText);
        TextView userTextLanguageTextView = (TextView)root.findViewById(R.id.userTextLanguage);
        holder.userTextTextView = userTextTextView;
        holder.userTextLanguageTextView = userTextLanguageTextView;
        root.setTag(holder);
        return root;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(DataContract.Data._ID));
        String text = cursor.getString(cursor.getColumnIndex(DataContract.Data.COLUMN_NAME_TEXT));
        String language = cursor.getString(cursor.getColumnIndex(DataContract.Data.COLUMN_NAME_LANGUAGE));
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder != null) {
            holder.userTextTextView.setText(text);
            holder.userTextLanguageTextView.setText(language);
            holder.id = id;
        }
    }

    static class ViewHolder {
        View itemView;
        TextView userTextTextView;
        TextView userTextLanguageTextView;
        TextLanguage item;
        long id;
    }
}

package com.example.ivansv.softomatetest;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by ivansv on 30.03.2016.
 */
public class DataProvider extends ContentProvider {
    private static final int DATABASE_VERSION = 1;
    private static HashMap<String, String> sDataProjectionMap;
    private static final int DATA = 1;
    private static final int DATA_ID = 2;
    private static final UriMatcher sUriMatcher;
    private static DBHelper dbHelper;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(DataContract.AUTHORITY, "data", DATA);
        sUriMatcher.addURI(DataContract.AUTHORITY, "data/#", DATA_ID);
        sDataProjectionMap = new HashMap<>();
        for (int i = 0; i < DataContract.Data.DEFAULT_PROJECTION.length; i++) {
            sDataProjectionMap.put(
                    DataContract.Data.DEFAULT_PROJECTION[i],
                    DataContract.Data.DEFAULT_PROJECTION[i]);
        }
    }

    public static class DBHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "database";
        public static final String DATABASE_TABLE = DataContract.Data.TABLE_NAME;
        public static final String KEY_ROW_ID = "_id";
        public static final String KEY_TEXT = "text";
        public static final String KEY_LANGUAGE = "language";
        private static final String DATABASE_CREATE_TABLE =
                "create table " + DATABASE_TABLE + " ("
                        + KEY_ROW_ID + " integer primary key autoincrement, "
                        + KEY_TEXT + " string , "
                        + KEY_LANGUAGE + " string );";

        private Context context;

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, factory, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), DBHelper.DATABASE_TABLE, new SQLiteDatabase.CursorFactory() {
            @Override
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
                return null;
            }
        }, DBHelper.DATABASE_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String orderBy;
        switch (sUriMatcher.match(uri)) {
            case DATA:
                qb.setTables(DataContract.Data.TABLE_NAME);
                qb.setProjectionMap(sDataProjectionMap);
                orderBy = sortOrder == null ? DataContract.Data.DEFAULT_SORT_ORDER : sortOrder;
                break;
            case DATA_ID:
                qb.setTables(DataContract.Data.TABLE_NAME);
                qb.setProjectionMap(sDataProjectionMap);
                qb.appendWhere(DataContract.Data._ID + "=" + uri.getPathSegments().get(DataContract.Data.DATA_ID_PATH_POSITION));
                orderBy = sortOrder == null ? DataContract.Data.DEFAULT_SORT_ORDER : sortOrder;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return qb.query(db, projection, selection, selectionArgs, null, null, orderBy);    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case DATA:
                return DataContract.Data.CONTENT_TYPE;
            case DATA_ID:
                return DataContract.Data.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (sUriMatcher.match(uri) != DATA) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
        long rowId;
        Uri rowUri = Uri.EMPTY;
        if (!values.containsKey(DataContract.Data.COLUMN_NAME_TEXT)) {
            values.put(DataContract.Data.COLUMN_NAME_TEXT, "");
        }
        if (!values.containsKey(DataContract.Data.COLUMN_NAME_LANGUAGE)) {
            values.put(DataContract.Data.COLUMN_NAME_LANGUAGE, "");
        }
        rowId = db.insert(DataContract.Data.TABLE_NAME,
                DataContract.Data.COLUMN_NAME_TEXT,
                values);
        if (rowId > 0) {
            rowUri = ContentUris.withAppendedId(DataContract.Data.CONTENT_ID_URI_BASE, rowId);
        }
        return rowUri;    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

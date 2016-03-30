package com.example.ivansv.softomatetest;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ivansv on 30.03.2016.
 */
public final class DataContract {
    public static final String AUTHORITY = "com.example.ivansv.softomatetest.DataContract";
    private DataContract() {}
    public static final class Data implements BaseColumns {
        private Data() {}
        public static final String TABLE_NAME = "simpletable";
        private static final String SCHEME = "content://";
        private static final String PATH_DATA = "/data";
        private static final String PATH_DATA_ID = "/data/";
        public static final int DATA_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + AUTHORITY + PATH_DATA);
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_DATA_ID);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.org.example.data";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.org.example.data";
        public static final String DEFAULT_SORT_ORDER = "_id ASC";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_LANGUAGE = "language";
        public static final String[] DEFAULT_PROJECTION = new String[] {
                DataContract.Data._ID,
                Data.COLUMN_NAME_TEXT,
                Data.COLUMN_NAME_LANGUAGE
        };
    }
}

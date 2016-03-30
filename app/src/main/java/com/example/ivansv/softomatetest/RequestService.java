package com.example.ivansv.softomatetest;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ivansv on 30.03.2016.
 */
public class RequestService extends IntentService {
    public static final String LANGUAGE = "language";
    private String url =
            "http://gateway-a.watsonplatform.net/calls/text/TextGetLanguage?apikey=4978e60252ae102dfe1341146bb8cc3ec4bbbd78&text=";
    private String text;
    private HttpURLConnection urlConnection;
    private BufferedInputStream inputStream;
    private String language;
    private ResultReceiver resultReceiver;

    public RequestService() {
        super("RequestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        language = null;
        resultReceiver = intent.getParcelableExtra(DataResultReceiver.RECEIVER);
        text = intent.getStringExtra(TextFragment.TEXT);
        loadXml(url + text);
    }

    private void loadXml(String urlString) {
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("language")) {
                    parser.next();
                    language = parser.getText();
                    break;
                }
                parser.next();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
            sendResult(language);
        }
    }

    private void sendResult(String language) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TEXT, text);
        contentValues.put(DBHelper.KEY_LANGUAGE, language);
        MainActivity.dbHelper.getWritableDatabase().insert(DBHelper.DATABASE_TABLE, null, contentValues);
        MainActivity.dbHelper.close();
        TextLanguage textLanguage = new TextLanguage();
        textLanguage.setText(text);
        textLanguage.setLanguage(language);
        MainActivity.textLanguages.add(textLanguage);
        ListFragment.adapter.notifyDataSetChanged();

        Bundle bundle = new Bundle();
        bundle.putString(LANGUAGE, language);
        resultReceiver.send(DataResultReceiver.RESULT, bundle);
    }
}

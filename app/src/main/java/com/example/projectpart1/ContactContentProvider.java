package com.example.projectpart1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class ContactContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.projectpart1";
    private static final String BASE_PATH = "contacts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CONTACTS = 1;
    private static final int CONTACT = 2;

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, CONTACTS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", CONTACT);
    }

    SQLiteDatabase db;

    public ContactContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int delCount = 0;
        switch (uriMatcher.match(uri)) {
            case CONTACTS:
                delCount = db.delete(ContactDatabase.TABLE, selection, selectionArgs);
                break;
            case CONTACT:
                delCount = db.delete(ContactDatabase.TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return delCount;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case 1:
                return "vnd.android.cursor.dir/contacts";
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i("insert uri: ", uri.toString());
        long id = db.insert(ContactDatabase.TABLE, null, values);

        if (id > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Insertion Failed for URI: " + uri);
    }

    @Override
    public boolean onCreate() {
        db = new ContactDatabase(getContext()).getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Log.i("query uri: ", uri.toString());
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case 1:
                cursor = db.query(ContactDatabase.TABLE, ContactDatabase.ALL_COLUMNS, selection
                        , null, null, null, ContactDatabase.NAME + " ASC");
                break;
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updCount = 0;
        switch (uriMatcher.match(uri)) {
            case 1:
                updCount = db.update(ContactDatabase.TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updCount;
    }
}
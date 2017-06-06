package com.myapplication.monitor.Utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.myapplication.monitor.Model.BrowserHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 06/05/2017.
 */

public class BrowserHelper {
    private static final String TAG = "BROWSER_HELPER" ;
    private Context mContext;
    private SessionManager sessionManager;
    private static final String[] BOOKMARK_PROJECTION = {
            "title", // Browser.BookmarkColumns.TITLE
            "url", // Browser.BookmarkColumns.URL
    };
    // Copied from android.provider.Browser.BOOKMARKS_URI:
    private static final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");

    private static final String BOOKMARK_SELECTION = "bookmark = 0 AND url IS NOT NULL";// 0 = history,
                                                                                        // 1 = bookmark

    public BrowserHelper(Context mContext) {
        this.mContext = mContext;
        sessionManager = new SessionManager(mContext);
    }

    public List<BrowserHistory> getBrowserHist() {
        List<BrowserHistory> resultList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(BOOKMARKS_URI, BOOKMARK_PROJECTION,
                BOOKMARK_SELECTION, null, null);
        if (cursor == null) {
            Log.w(TAG, "No cursor returned for bookmark query");
            return null;
        }
        try {
            while (cursor.moveToNext()) {
                BrowserHistory history = new BrowserHistory();
                history.setUserId(sessionManager.getUserId());
                history.setUrl(cursor.getString(0));
                history.setUrl(cursor.getString(1));
                resultList.add(history);
            }
        } finally {
            cursor.close();
        }
        /*cursor.moveToFirst();
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                resultList.add(new String[]{cursor.getString(0),cursor.getString(1)});
                Log.v("titleIdx", cursor
                        .getString(0));
                Log.v("urlIdx", cursor
                        .getString(1));
                cursor.moveToNext();
            }
        }
        cursor.close();*/
        return resultList;
    }

}

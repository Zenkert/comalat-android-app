package org.sakaiproject.sakai;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * Created by Kostas on 27-Oct-16.
 * Javascript Interface to pass username and password
 * to the webview of 'Site Browser'
 */

public class JavaScriptInterface {
    Context mContext;
    JavaScriptInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public String getWebviewUsername() {
        return Global.userName;
    }
    @JavascriptInterface
    public String getWebviewPassword() {
        return Global.passWord;
    }
}

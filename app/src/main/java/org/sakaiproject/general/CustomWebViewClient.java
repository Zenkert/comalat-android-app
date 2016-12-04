package org.sakaiproject.general;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by George Kakarontzas on 4/12/2016.
 */

public class CustomWebViewClient extends WebViewClient {
    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        final Uri uri = Uri.parse(url);
        return handleUri(uri);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        final Uri uri = request.getUrl();
        return handleUri(uri);
    }

    private boolean handleUri(final Uri uri) {
        //Log.i(TAG, "Uri =" + uri);
        System.out.println(uri);
        final String host = uri.getHost();
        final String scheme = uri.getScheme();
        // Based on some condition you need to determine if you are going to load the url
        // in your web view itself or in a browser.
        // You can use `host` or `scheme` or any part of the `uri` to decide.
        //if (/* any condition */) {
            // Returning false means that you are going to load this url in the webView itself
            return false;
        //} else {
            // Returning true means that you need to handle what to do with the url
            // e.g. open web page in a Browser
           // final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
           // startActivity(intent);
           // return true;
    }
}

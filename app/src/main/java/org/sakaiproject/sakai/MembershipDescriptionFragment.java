package org.sakaiproject.sakai;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.sakaiproject.api.memberships.SiteData;
import org.sakaiproject.api.pojos.membership.SitePage;
import org.sakaiproject.api.user.User;
import org.sakaiproject.customviews.rich_textview.RichTextView;
import org.sakaiproject.general.CustomWebViewClient;
import org.sakaiproject.helpers.ActionsHelper;

import java.util.List;

/**
 * Created by vasilis on 1/26/16.
 */
public class MembershipDescriptionFragment extends DialogFragment {

    private String ownerShortName;
    private String email;
    private String shortDescription;
    private String description;
    private SiteData data;
    private WebView mWebView;

    public MembershipDescriptionFragment() {
    }

    /**
     * get the owner data
     *
     * @param ownerShortName   the owner full name
     * @param email            the owner email
     * @param shortDescription the short description from the site
     * @param description      the description from the site
     * @return the fragment with the data
     */
    public MembershipDescriptionFragment getData(String ownerShortName, String email, String shortDescription, String description, SiteData data) {
        MembershipDescriptionFragment membershipDescriptionFragment = new MembershipDescriptionFragment();
        Bundle b = new Bundle();
        b.putString("ownerShortName", ownerShortName);
        b.putString("email", email);
        b.putString("shortDescription", shortDescription);
        b.putString("description", description);
        b.putSerializable("data", data);
        membershipDescriptionFragment.setArguments(b);
        return membershipDescriptionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_membership_description, container, false);
        mWebView = (WebView) v.findViewById(R.id.membership_webview);
        data = (SiteData) getArguments().getSerializable("data");
        String comalat_guide_url = getString(R.string.portal_site)+data.getId()+getString(R.string.comalat_guide_tool_postfix);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().acceptThirdPartyCookies(mWebView);
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView,true);
        }
        String postdata = "eid="+Global.userName+"&pw="+Global.passWord;
        mWebView.postUrl(comalat_guide_url,postdata.getBytes());
        mWebView.setWebChromeClient(new WebChromeClient());

        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setLoadsImagesAutomatically(true);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(true);
        webSettings.setCacheMode(
             WebSettings.LOAD_DEFAULT);
        webSettings.setEnableSmoothTransition(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setPluginState(WebSettings.PluginState.ON);
        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new CustomWebViewClient());

        return v;
    }

}

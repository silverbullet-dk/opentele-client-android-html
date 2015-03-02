package dk.silverbullet.telemed.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import dk.silverbullet.telemed.Util;

/**
 * Takes a <code>WebView</code> and initializes it.
 *
 * @author Peter Urbak
 * @version 2014-10-22
 */
public class WebViewHelper {

    // --*-- Constructors --*--

    /**
     * Constructs <code>WebViewHelper</code>
     */
    public WebViewHelper() {
    }

    // --*-- Methods --*--

    /**
     * Initializes a <code>WebView</code>
     */
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    public void initializeWebView(Activity activity, WebView webView, String openTeleUrl) {
        webView.setWebViewClient(new EmbeddedWebViewClient());

        // Settings
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(1);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);

        // Console debugging
        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cm) {
                Log.d("OpenTele:Console", cm.message() + " -- From line "
                        + cm.lineNumber() + " of "
                        + cm.sourceId());
                return true;
            }
        });

        // Add interface between Android and Javascript
        final JavaScriptInterface javaScriptInterface =
                new JavaScriptInterface(activity, webView, openTeleUrl);
        webView.addJavascriptInterface(javaScriptInterface,
                Util.JAVASCRIPT_INTERFACE_NAME);

    }

}

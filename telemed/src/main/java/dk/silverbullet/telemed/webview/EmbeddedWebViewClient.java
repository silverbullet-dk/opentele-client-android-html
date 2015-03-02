package dk.silverbullet.telemed.webview;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import dk.silverbullet.telemed.Util;

/**
 * Embeds the content of a URL into a View.
 *
 * @author Peter Urbak
 * @version 2014-10-14
 */
public class EmbeddedWebViewClient extends WebViewClient {

    // --*-- Methods --*--

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        webView.loadUrl(url);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onReceivedError(WebView webView, int errorCode,
                                String description, String failingUrl) {
        try {
            webView.stopLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (webView.canGoBack()) {
            webView.goBack();
        }
        webView.loadUrl(Util.ERROR_URL);
        super.onReceivedError(webView, errorCode, description, failingUrl);
    }
}

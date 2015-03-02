package dk.silverbullet.telemed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import java.util.Properties;

import dk.silverbullet.telemed.webview.WebViewHelper;

/**
 * Loads the OpenTele client HTML5 page and handles any errors in doing so.
 *
 * @author Peter Urbak
 * @version 2014-10-22
 */
public class MainActivity extends Activity {

    // --*-- Fields --*--

    private static boolean hasBeenCreated;
    private String openTeleUrl;
    private WebView webView;

    // --*-- Methods --*--

    /**
     * {@inheritDoc}
     */
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hasBeenCreated = true;

        Properties config = new ConfigProperties(this.getApplicationContext()).getConfig();
        this.openTeleUrl = config.getProperty("OpenTeleUrl");
        webView = (WebView) findViewById(R.id.webView);
        WebViewHelper webViewHelper = new WebViewHelper();
        webViewHelper.initializeWebView(this, webView, openTeleUrl);
        startWebView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startWebView();
    }

    @Override
    public void onBackPressed() {
        if(shouldExit(webView)) {
            super.onBackPressed();
        } else {
            webView.goBack();
        }
    }

    private boolean shouldExit(WebView webView) {
        return !webView.canGoBack() || webView.getUrl().endsWith("#/menu");
    }

    public static boolean hasBeenCreated() {
        return hasBeenCreated;
    }

    /**
     * Instantiates the WebView and attempts to load the OpenTele HTML5 client.
     */
    private void startWebView() {
        adjustScreenOrientation();
        webView.loadUrl(openTeleUrl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustScreenOrientation();
    }

    /**
     * Adjusts the screen orientation depending on whether it is a tablet or
     * a phone. Any device with a screen size greater than or equal to 720x960
     * dp units will be in landscape mode.
     */
    private void adjustScreenOrientation() {
        Context context = this.getApplicationContext();
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        if (xlarge) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        return false; // disable menu button
    }

}

package dk.silverbullet.telemed.webview;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.webkit.WebView;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import dk.silverbullet.telemed.Util;

/**
 * Loads a URL but displays a custom OpenTele error page instead of the
 * browser's 404/500 error page.
 */
public class ErrorHandlingWebView extends WebView {

    // --*-- Constructors --*--

    public ErrorHandlingWebView(Context context) {
        super(context);
    }

    public ErrorHandlingWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ErrorHandlingWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // --*-- Methods --*--

    @Override
    public void loadUrl(final String url) {
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected void onPostExecute(Boolean isAvailable) {
                if (isAvailable) {
                    ErrorHandlingWebView.super.loadUrl(url);
                }
                else {
                    ErrorHandlingWebView.super.loadUrl(Util.ERROR_URL);
                }
            }

            @Override
            protected Boolean doInBackground(String... params) {
                return isClientAvailable(params[0]);
            }
        }.execute(url);
    }

    private boolean isClientAvailable(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpParams httpParameters = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
        HttpConnectionParams.setSoTimeout(httpParameters, 3000);

        HttpGet httpGet = new HttpGet(url);

        try {
            httpClient.execute(httpGet, new BasicResponseHandler());
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

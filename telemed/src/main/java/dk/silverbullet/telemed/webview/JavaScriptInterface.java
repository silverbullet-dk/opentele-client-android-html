package dk.silverbullet.telemed.webview;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONObject;
import java.util.Map;

import dk.silverbullet.telemed.DeviceInformation;
import dk.silverbullet.telemed.R;
import dk.silverbullet.telemed.reminder.ReminderService;

/**
 * Allows the Javascript in assets/www/missing-page.html to call a select number
 * of methods in the Android application code.
 *
 * @author Peter Urbak
 * @version 2014-11-06
 */
public class JavaScriptInterface {

    // --*-- Fields --*--

    private Activity mainActivity;
    private WebView webView;
    private String openTeleUrl;

    // --*-- Constructors --*--

    /**
     * Constructs a <code>JavaScriptInterface</code>.
     *
     * @param mainActivity - the main activity.
     * @param openTeleUrl -
     */
    public JavaScriptInterface(Activity mainActivity, WebView webView, String openTeleUrl) {
        this.mainActivity = mainActivity;
        this.webView = webView;
        this.openTeleUrl = openTeleUrl;
    }

    // --*-- Methods --*--

    /**
     * Called from missing-page.html
     */
    @JavascriptInterface
    public void reloadUrl() {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                webView.loadUrl(openTeleUrl);
            }
        });
    }

    /**
     * Called from missing-page.html
     */
    @JavascriptInterface
    public String getTitleText() {
        return mainActivity.getString(R.string.app_name);
    }

    /**
     * Called from missing-page.html
     */
    @JavascriptInterface
    public String getErrorHeaderText() {
        return mainActivity.getString(R.string.error_header_text);
    }

    /**
     * Called from missing-page.html
     */
    @JavascriptInterface
    public String getErrorMessageText() {
        return mainActivity.getString(R.string.error_message_text);
    }

    /**
     * Called from missing-page.html
     */
    @JavascriptInterface
    public String getReloadButtonText() {
        return mainActivity.getString(R.string.reload_button_text);
    }

    /**
     * Called from the HTML5 application.
     *
     * @param json - a stringified representation of the reminders JSON objects.
     */
    @JavascriptInterface
    public void sendReminders(String json) {
        ReminderService.setRemindersTo(mainActivity.getApplicationContext(), json);
    }

    /**
     * Called from the HTML5 application.
     *
     * @param questionnaireName -
     */
    @JavascriptInterface
    public void clearRemindersForQuestionnaire(String questionnaireName) {
        ReminderService.clearRemindersForQuestionnaire(mainActivity.getApplicationContext(), questionnaireName);
    }

    /**
     * Called from the HTML5 application.
     *
     * @return a stringified representation of the questionnaire names JSON array.
     */
    @JavascriptInterface
    public String getQuestionnairesToHighlight() {
        return ReminderService.getQuestionnairesToHighlight().toString();
    }

    /**
     * Called from the HTML5 application.
     *
     * @return a stringified representation of the JSON object describing the
     * device information.
     */
    @JavascriptInterface
    public String getDeviceInformation() {
        Map<String, String> deviceInformation = DeviceInformation.getDeviceInformation(mainActivity);
        return new JSONObject(deviceInformation).toString();
    }

}

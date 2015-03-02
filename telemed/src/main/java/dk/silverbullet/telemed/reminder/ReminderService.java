package dk.silverbullet.telemed.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dk.silverbullet.telemed.Util;

/**
 * In charge of setting, updating, and removing reminders.
 */
public class ReminderService {

    // --*-- Fields --*--

    private static final String TAG = Util.getTag(ReminderService.class);
    private static final String
            SHARED_PREFERENCES_QUESTIONNAIRE_SCHEDULES = "PREF_QUESTIONNAIRE_SCHEDULES",
            SHARED_PREFERENCES_QUESTIONNAIRE_SCHEDULES_BASELINE = "PREF_QUESTIONNAIRE_SCHEDULES_BASELINE";
    private static final Set<String> QUESTIONNAIRES_TO_HIGHLIGHT = new HashSet<String>();

    // --*-- Methods --*--

    /**
     * Sets reminders to the specified list of reminders, stores them, and sets up the required alarms.
     *
     * @param context -
     * @param json -
     */
    public static void setRemindersTo(Context context, String json) {
        Gson gson = new Gson();
        try {
            List<ReminderBean> reminderBeans = Arrays.asList(gson.fromJson(json, ReminderBean[].class));
            ReminderService.setRemindersTo(context, reminderBeans);
        } catch (JsonParseException e) {
            Log.e(TAG, "Could not deserialize reminder beans from JavaScript");
        }
    }

    /**
     * Sets reminders to the specified list of reminders, stores them, and sets up the required alarms.
     *
     * @param context -
     * @param reminderBeans -
     */
    public static void setRemindersTo(Context context, List<ReminderBean> reminderBeans) {
        Date now = new Date();
        UpcomingReminders upcomingReminders = new UpcomingReminders(now, reminderBeans);
        saveUpcomingReminders(context, upcomingReminders);
        setupReminders(context, now);
    }

    /**
     * For initializing the application. Loads stored reminders.
     *
     * @param context -
     */
    public static void setupReminders(Context context) {
        setupReminders(context, new Date());
    }

    /**
     * For initializing the application. Loads stored reminders.
     *
     * @param context -
     * @param now -
     */
    private static void setupReminders(Context context, Date now) {
        cancelUpcomingAlarm(context);
        UpcomingReminders upcomingReminders = getUpcomingReminders(context);
        upcomingReminders.removeRemindersBeforeOrAt(now);
        saveUpcomingReminders(context, upcomingReminders);
        if (upcomingReminders.hasMoreReminders()) {
            setAlarm(context, upcomingReminders.nextReminder());
        }
    }

    /**
     * Used when an alarm has been issued and the reminders need to be updated accordingly.
     *
     * @param context -
     */
    public static void updateReminders(Context context) {
        Date now = new Date();
        UpcomingReminders upcomingReminders = getUpcomingReminders(context);
        QUESTIONNAIRES_TO_HIGHLIGHT.addAll(upcomingReminders.remindedQuestionnairesAt(now));
        setupReminders(context, now);
    }

    /**
     * Saves upcoming reminders to shared preferences.
     *
     * @param context -
     * @param upcomingReminders -
     */
    private static void saveUpcomingReminders(Context context, UpcomingReminders upcomingReminders) {
        Gson gson = new Gson();
        String reminderBeansAsJson = gson.toJson(upcomingReminders.getReminderBeans());
        long baselineDate = upcomingReminders.getBaselineDateAsLong();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(SHARED_PREFERENCES_QUESTIONNAIRE_SCHEDULES, reminderBeansAsJson);
        editor.putLong(SHARED_PREFERENCES_QUESTIONNAIRE_SCHEDULES_BASELINE, baselineDate);
        editor.apply();
    }

    /**
     * Returns the upcoming reminders.
     *
     * @param context -
     * @return -
     */
    private static UpcomingReminders getUpcomingReminders(Context context) {
        Gson gson = new Gson();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String reminderBeansAsJson = preferences.getString(SHARED_PREFERENCES_QUESTIONNAIRE_SCHEDULES, null);
        long baselineDate = preferences.getLong(SHARED_PREFERENCES_QUESTIONNAIRE_SCHEDULES_BASELINE, 0);

        if (reminderBeansAsJson != null && baselineDate != 0) {
            List<ReminderBean> reminderBeans;
            try {
                reminderBeans = Arrays.asList(gson.fromJson(reminderBeansAsJson, ReminderBean[].class));
                return new UpcomingReminders(baselineDate, reminderBeans);
            } catch (JsonParseException e) {
                Log.e(TAG, "Could not deserialize reminder beans from shared preferences");
                return noReminders();
            }
        }
        return noReminders();
    }

    /**
     * Used when the user has started filling in a questionnaire, since it makes
     * no sense to still issue reminders for that questionnaire.
     *
     * @param context -
     * @param questionnaireName -
     */
    public static void clearRemindersForQuestionnaire(Context context, String questionnaireName) {
        UpcomingReminders upcomingReminders = getUpcomingReminders(context);
        QUESTIONNAIRES_TO_HIGHLIGHT.remove(questionnaireName);
        upcomingReminders.removeQuestionnaire(questionnaireName);
        saveUpcomingReminders(context, upcomingReminders);
    }

    /**
     * Returns the list of names of questionnaires to highlight.
     *
     * @return -
     */
    public static List<String> getQuestionnairesToHighlight() {
        return new ArrayList<String>(QUESTIONNAIRES_TO_HIGHLIGHT);
    }

    /**
     * Returns an empty set of upcoming reminders.
     *
     * @return -
     */
    private static UpcomingReminders noReminders() {
        return new UpcomingReminders(new Date(), new ArrayList<ReminderBean>());
    }

    /**
     * Returns the alarm manager.
     *
     * @param context -
     * @return -
     */
    private static AlarmManager getAlarmManager(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * Sets an alarm.
     *
     * @param context -
     * @param alarmTime -
     */
    private static void setAlarm(Context context, Date alarmTime) {
        Log.d(TAG, "Setting alarm: " + alarmTime);
        long timeInMillis = alarmTime.getTime();
        AlarmManager alarmManager = getAlarmManager(context);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, getPendingIntent(context));
    }

    /**
     * Cancel an upcoming alarm.
     *
     * @param context -
     */
    private static void cancelUpcomingAlarm(Context context) {
        AlarmManager alarmManager = getAlarmManager(context);
        alarmManager.cancel(getPendingIntent(context));
    }

    /**
     * Returns the pending intent.
     *
     * @param context -
     * @return -
     */
    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, OnAlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

}
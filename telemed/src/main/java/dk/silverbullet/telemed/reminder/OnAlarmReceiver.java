package dk.silverbullet.telemed.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import dk.silverbullet.telemed.R;
import dk.silverbullet.telemed.Util;

/**
 * Displays next reminder notification when alarm received.
 */
public class OnAlarmReceiver extends BroadcastReceiver {

    // --*-- Fields --*--

    private static final String TAG = Util.getTag(OnAlarmReceiver.class);

    // --*-- Methods --*--

    /**
     * {@inheritDoc}
     */
    @Override
    public final void onReceive(Context context, Intent intent) {
        Log.d(TAG, "OnAlarmReceiver...");
        displayNotification(context, context.getString(R.string.alarm_user_prompt, context));
        ReminderService.updateReminders(context);
    }

    /**
     * Displays the given <code>message</code> notification.
     *
     * @param context -
     * @param message -
     */
    private void displayNotification(Context context, String message) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, ReminderActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(contentIntent)
                .setContentTitle(context.getString(R.string.alarm_alarm))
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentText(message)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .getNotification();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

}

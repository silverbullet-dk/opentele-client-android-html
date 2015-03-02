package dk.silverbullet.telemed.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import dk.silverbullet.telemed.Util;

/**
 * Tries to setup reminders when device has booted.
 */
public class OnBootReceiver extends BroadcastReceiver {

    // --*-- Fields --*--

    private static final String TAG = Util.getTag(OnBootReceiver.class);

    // --*-- Methods --*--

    /**
     * {@inheritDoc}
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "...onReceive...");
        ReminderService.setupReminders(context);
    }

}
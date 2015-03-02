package dk.silverbullet.telemed.reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import dk.silverbullet.telemed.MainActivity;

/**
 * Pseudo activity for starting the MainActivity if it isn't already alive.
 */
public class ReminderActivity extends Activity {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!MainActivity.hasBeenCreated()) {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}


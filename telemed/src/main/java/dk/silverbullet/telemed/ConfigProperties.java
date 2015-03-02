package dk.silverbullet.telemed;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Rene Andersen
 * @version 2014-10-22
 */
public class ConfigProperties {

    private final Context context;
    private final Properties properties;

    public ConfigProperties(Context context) {
        this.context = context;
        this.properties = new Properties();
    }

    public Properties getConfig() {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            Log.e("ConfigProperties", e.toString());
        }
        return properties;
    }
}

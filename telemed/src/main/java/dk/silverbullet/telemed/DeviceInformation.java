package dk.silverbullet.telemed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the device information.
 *
 * @author Peter Urbak
 * @version 2014-11-07
 */
public class DeviceInformation {

    // --*-- Methods --*--

    /**
     * @return a <code>Map</code> of device information.
     */
    @SuppressLint("NewApi")
    public static Map<String, String> getDeviceInformation(Activity activity) {
        Map<String, String> deviceMap = new HashMap<String, String>();

        // General
        deviceMap.put("model", Build.MODEL);
        deviceMap.put("serial", Build.SERIAL);
        deviceMap.put("brand", Build.BRAND);
        deviceMap.put("androidVersion", Build.VERSION.RELEASE);

        // Memory
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Activity.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.JELLY_BEAN) {
            deviceMap.put("totalMemory", String.valueOf(memoryInfo.totalMem));
            deviceMap.put("availableMemory", String.valueOf(memoryInfo.availMem));
        }

        // Network
        deviceMap.put("bluetooth", "N/A");
        WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        deviceMap.put("wifi", String.valueOf(wifiManager.isWifiEnabled()));

        return deviceMap;
    }
}


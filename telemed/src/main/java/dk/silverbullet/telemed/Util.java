package dk.silverbullet.telemed;

/**
 * Contains OpenTele-specific constants.
 *
 * @author Peter Urbak
 * @version 2014-10-14
 */
public final class Util {

    // Constants
    public static final String ERROR_URL =
            "file:///android_asset/www/missing-page.html";
    public static final String JAVASCRIPT_INTERFACE_NAME = "Native";

    // Helper functions

    public static String getTag(Class<?> cls) {
        return cls.getName().substring(cls.getName().lastIndexOf(".") + 1);
    }

}


package net.gottsolutions.translations.utils;

import android.os.Environment;

import java.io.File;

/**
 * This class contains device related utilities.
 */

public class DeviceUtils {

    private static final String TAG = "DeviceUtils";

    /**
     * Get the launcher default public cache directory.
     *
     * @return Returns a File object.
     */
    public static File getLauncherPublicAppCacheDir() {
        File folder = new File(Environment.getExternalStorageDirectory(), Constants.GottPackage.LAUNCHERV2);
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }

}

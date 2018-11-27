package net.gottsolutions.translations.services;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.util.Log;

import net.gottsolutions.translations.models.AppResourcesTranslation;
import net.gottsolutions.translations.models.Translations;
import net.gottsolutions.translations.utils.Constants;
import net.gottsolutions.translations.utils.DeviceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class contains all methods needed to load and show translated text strings.
 */
public class Translation {

    private static final String TAG = "Translation";
    private static final String UNDEFINED = "undefined";

    /**
     * Will contain the app translations for the current language id in session.
     */
    private static HashMap<String, String> mTranslations = new HashMap<>();

    /**
     * Will contain the app translations for the fallback language.
     * For some reason, if a given translation is not found in a given language, we must load
     * the translation of the fallback language.
     */
    private static HashMap<String, String> mFallbackTranslations = new HashMap<>();

    private static OnTranslationsLoadedListener mOnTranslationsLoadedListener;

    /**
     * The id of the fallback language.
     */
    private static int fallbackIdAppLanguage = 2;//English

    /**
     * Loads synchronously the translations of a given language id, obtained from
     * a list of AppResourcesTranslation objects.
     *
     * @param idAppLanguage
     * @param appResourcesTranslations
     */
    public static void loadTranslations(int idAppLanguage, List<AppResourcesTranslation> appResourcesTranslations) {
        mTranslations.clear();
        for (AppResourcesTranslation translation : appResourcesTranslations) {
            if (translation.getIdAppLanguage() == idAppLanguage) {
                mTranslations.put(translation.getTag(), sanitize(translation.getText()));
            }
            if (translation.getIdAppLanguage() == fallbackIdAppLanguage) {
                mFallbackTranslations.put(translation.getTag(), sanitize(translation.getText()));
            }
        }
    }

    /**
     * Loads asynchronously the translations of a given language id
     * obtained from the launcher's public cache file AppResourcesTranslations.
     *
     * @param idAppLanguage
     */
    public static void loadTranslations(final int idAppLanguage) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                List<AppResourcesTranslation> appResourcesTranslations = getAppResourcesTranslations();
                loadTranslations(idAppLanguage, appResourcesTranslations);
                setTranslationsLoaded(appResourcesTranslations);
            }
        };
        handler.post(runnable);
    }

    /**
     * Load synchronously the app with the minimal translations.
     *
     * @param context
     */
    public static void loadTranslations(Context context, int idAppLanguage) {
        File file = new File(DeviceUtils.getLauncherPublicAppCacheDir().getPath() + "/" + Constants.AppResourcesTranslations);
        if (!file.exists()) {
            // Translations file does not exist so load the file from assets containing the essential translations
            copyFallbackAppResourcesTranslations(context);
            List<AppResourcesTranslation> appResourcesTranslations = getAppResourcesTranslations();
            loadTranslations(fallbackIdAppLanguage, appResourcesTranslations);
        } else {
            List<AppResourcesTranslation> appResourcesTranslations = getAppResourcesTranslations();
            loadTranslations(idAppLanguage, appResourcesTranslations);
        }
    }

    /**
     * Get a translation text by tag.
     *
     * @param tag
     * @return
     */
    public static String get(String tag) {
        if (mTranslations.containsKey(tag)) {
            return mTranslations.get(tag);
        } else {

            // Try to get the translation in the fallback language (English)
            if (mFallbackTranslations.containsKey(tag)) {
                return mFallbackTranslations.get(tag);
            }

            return UNDEFINED;
        }
    }

    /**
     * Get from launcher's public cache, the file containing the translations json and
     * convert it to a list of AppResourcesTranslation objects.
     */
    private static List<AppResourcesTranslation> getAppResourcesTranslations() {

        List<AppResourcesTranslation> appResourcesTranslations = new ArrayList<>();

        try {

            String strLine = "";
            StringBuilder text = new StringBuilder();

            File file = new File(DeviceUtils.getLauncherPublicAppCacheDir().getPath() + "/" + Constants.AppResourcesTranslations);

            if (file.exists()) {

                FileReader fReader = new FileReader(file);

                BufferedReader bReader = new BufferedReader(fReader);

                while ((strLine = bReader.readLine()) != null) {
                    text.append(strLine);
                }

                com.google.gson.Gson mGson = new com.google.gson.Gson();
                Translations translations = mGson.fromJson(text.toString(), Translations.class);
                appResourcesTranslations = translations.getAppResourcesTranslations();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return appResourcesTranslations;
    }

    /**
     * Remove from the text any xml formatting characters and other.
     *
     * @param text
     * @return
     */
    private static String sanitize(String text) {
        text = text.replace("\\n", System.getProperty("line.separator"));
        text = text.replace("\\'", "'");
        return text;
    }

    /**
     * Copy from assets to sdcard, the fallback translations file containing the essential translations.
     *
     * @param context
     */
    public static void copyFallbackAppResourcesTranslations(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream in = null;
            OutputStream out = null;
            in = assetManager.open(Constants.AppResourcesTranslations);
            out = new FileOutputStream(DeviceUtils.getLauncherPublicAppCacheDir().getPath() + "/" + Constants.AppResourcesTranslations);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception ex) {
            Log.e(TAG, "copyFallbackAppResourcesTranslations", ex);
        }
    }

    public interface OnTranslationsLoadedListener {
        void onTranslationsLoaded(List<AppResourcesTranslation> appResourcesTranslations);
    }

    private static void setTranslationsLoaded(List<AppResourcesTranslation> appResourcesTranslations) {
        if (mOnTranslationsLoadedListener != null) {
            mOnTranslationsLoadedListener.onTranslationsLoaded(appResourcesTranslations);
        }
    }

    public static void setOnTranslationsLoadedListener(OnTranslationsLoadedListener eventListener) {
        mOnTranslationsLoadedListener = eventListener;
    }
}

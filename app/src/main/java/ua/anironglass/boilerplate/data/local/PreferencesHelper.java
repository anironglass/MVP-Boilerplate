package ua.anironglass.boilerplate.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntRange;

import javax.inject.Inject;
import javax.inject.Singleton;

import ua.anironglass.boilerplate.injection.ApplicationContext;


@Singleton
public class PreferencesHelper {

    private static final String PREF_FILE_NAME = "anironglass_pref_file";

    private static final String PREF_ALBUM_ID = "pref_album_id";

    public static final int DEFAULT_ALBUM_ID = 1;

    private final SharedPreferences mPref;

    @Inject
    @SuppressWarnings("WeakerAccess")
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public int getAlbumId() {
        return mPref.getInt(PREF_ALBUM_ID, DEFAULT_ALBUM_ID);
    }

    public void setAlbumId(@IntRange(from = 1, to = 100) int albumId) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putInt(PREF_ALBUM_ID, albumId);
        editor.apply();
    }

    @SuppressWarnings("unused")
    public void clear() {
        mPref.edit().clear().apply();
    }

}
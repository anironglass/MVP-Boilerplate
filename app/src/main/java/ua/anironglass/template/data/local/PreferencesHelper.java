package ua.anironglass.template.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ua.anironglass.template.injection.ApplicationContext;


@Singleton
public final class PreferencesHelper {

    private static final String PREF_FILE_NAME = "anironglass_pref_file";

    private static final String PREF_SOMETHING = "pref_something";

    public static final String DEFAULT_SOMETHING = "default_something";

    private final SharedPreferences mPref;

    @Inject
    @SuppressWarnings("WeakerAccess")  // Used in global singleton
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public String getSomething() {
        return mPref.getString(PREF_SOMETHING, DEFAULT_SOMETHING);
    }

    public void setSomething(@NonNull String something) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(PREF_SOMETHING, something);
        editor.apply();
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

}
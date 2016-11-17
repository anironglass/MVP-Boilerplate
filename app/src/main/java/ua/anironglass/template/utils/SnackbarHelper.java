package ua.anironglass.template.utils;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ua.anironglass.template.R;
import ua.anironglass.template.injection.ActivityContext;
import ua.anironglass.template.injection.PerActivity;

@PerActivity
public final class SnackBarHelper {

    private final Context mContext;

    @Inject
    @SuppressWarnings("WeakerAccess")  // Used in DI
    public SnackBarHelper(@ActivityContext Context context) {
        mContext = context;
    }

    public void showShort(@NonNull View view, @NonNull String message) {
        createSnackBar(view, message, Snackbar.LENGTH_SHORT)
                .show();
    }

    public void showLong(@NonNull View view, @NonNull String message) {
        createSnackBar(view, message, Snackbar.LENGTH_LONG)
                .show();
    }

    @NonNull
    private Snackbar createSnackBar(@NonNull View view, @NonNull String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        ViewGroup group = (ViewGroup) snackbar.getView();
        int color = ContextCompat.getColor(mContext, R.color.primary);
        group.setBackgroundColor(color);
        return snackbar;
    }

}
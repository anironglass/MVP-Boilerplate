package ua.anironglass.boilerplate.runner;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import ua.anironglass.boilerplate.TestApp;


/**
 * Extension of AndroidJUnitRunner that replaces application with test application
 */
class CustomAppAndroidJUnitRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader loader, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(loader, TestApp.class.getName(), context);
    }

}
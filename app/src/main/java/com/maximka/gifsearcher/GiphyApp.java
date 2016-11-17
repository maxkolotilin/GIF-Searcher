package com.maximka.gifsearcher;

import android.app.Application;

import com.maximka.gifsearcher.DI.DaggerGiphyAppComponent;
import com.maximka.gifsearcher.DI.GiphyAppComponent;

import proxypref.ProxyPreferences;

/**
 * Created by maximka on 15.11.16.
 */

public class GiphyApp extends Application {
    private static GiphyAppComponent component;
    private static Preferences preferences;

    public static GiphyAppComponent getInjector() {
        return component;
    }

    public static Preferences getPreferences() {
        return preferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerGiphyAppComponent.create();
        preferences = ProxyPreferences.build(Preferences.class,
                getSharedPreferences("preferences", 0));
    }
}

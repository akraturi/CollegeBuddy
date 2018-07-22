package drunkcoder.com.collegebuddy;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;


public class MyApplication extends Application
{
    private Gson gson;
    private static MyApplication sAppInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create();

        sAppInstance=this;

        ActiveAndroid.initialize(this);
    }

    public static  MyApplication getApp()
    {
        return sAppInstance;
    }

    public Gson getGson()
    {
        return gson;
    }




}
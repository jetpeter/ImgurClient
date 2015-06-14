package me.jefferey.imgur;

import android.app.Application;

public class ImgurApplication extends Application {


    private MainComponent mMainComponent;

    public void onCreate() {
        super.onCreate();
        mMainComponent = DaggerMainComponent.create();
    }


    public MainComponent getMainComponent() {
        return mMainComponent;
    }

}

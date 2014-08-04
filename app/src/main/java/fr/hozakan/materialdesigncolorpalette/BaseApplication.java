package fr.hozakan.materialdesigncolorpalette;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by gimbert on 2014-07-31.
 */
public abstract class BaseApplication extends Application {
    protected ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        mObjectGraph = ObjectGraph.create(getModules());
    }

    public <T> void inject(T object) {
        mObjectGraph.inject(object);
    }

    protected abstract Object[] getModules();
}

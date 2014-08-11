package fr.hozakan.materialdesigncolorpalette.dagger;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by gimbert on 2014-08-11.
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

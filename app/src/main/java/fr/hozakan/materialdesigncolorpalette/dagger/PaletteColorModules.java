package fr.hozakan.materialdesigncolorpalette.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import fr.hozakan.materialdesigncolorpalette.BaseApplication;
import fr.hozakan.materialdesigncolorpalette.NavigationDrawerFragment;
import fr.hozakan.materialdesigncolorpalette.services.PaletteColorService;

/**
 * Created by gimbert on 2014-07-31.
 */
@Module(injects = {
        NavigationDrawerFragment.class
},
library = true)
public class PaletteColorModules {
    private BaseApplication mBaseApp;

    public PaletteColorModules(BaseApplication app) {
        this.mBaseApp = app;
    }

    @Provides
    public Context provideAppContext() {
        return mBaseApp.getApplicationContext();
    }

    @Provides
    public PaletteColorService providePaletteColorService(Context context) {
        return new PaletteColorService(context);
    }
}

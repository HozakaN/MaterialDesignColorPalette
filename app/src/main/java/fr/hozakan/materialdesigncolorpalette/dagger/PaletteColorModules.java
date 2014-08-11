package fr.hozakan.materialdesigncolorpalette.dagger;

import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.hozakan.materialdesigncolorpalette.ColorPaletteActivity;
import fr.hozakan.materialdesigncolorpalette.PaletteFragment;
import fr.hozakan.materialdesigncolorpalette.service.PaletteService;

/**
 * Created by gimbert on 2014-08-11.
 */
@Module(injects = {ColorPaletteActivity.class,
        PaletteFragment.class},
library = true)
public class PaletteColorModules {

    private final BaseApplication mApp;

    public PaletteColorModules(BaseApplication app) {
        mApp = app;
    }

    @Provides
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    public PaletteService providePaletteService(Context context) {
        return new PaletteService(context);
    }

    @Provides
    @Singleton
    public Bus provideBus() {
        return new Bus(ThreadEnforcer.ANY);
    }

}

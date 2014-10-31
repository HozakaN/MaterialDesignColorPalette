package fr.hozakan.materialdesigncolorpalette.dagger;

import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.hozakan.materialdesigncolorpalette.color_list.ColorPaletteActivity;
import fr.hozakan.materialdesigncolorpalette.color_list.PaletteFragment;
import fr.hozakan.materialdesigncolorpalette.preview.PreviewFragment;
import fr.hozakan.materialdesigncolorpalette.service.PaletteService;

/**
 * Created by gimbert on 2014-08-11.
 */
@Module(injects = {
        ColorPaletteActivity.class,
        PaletteFragment.class,
        PreviewFragment.class
    },
library = true)
public class PaletteColorModule {

    private final BaseApplication mApp;

    public PaletteColorModule(BaseApplication app) {
        mApp = app;
    }

    @Provides
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    public PaletteService providePaletteService(Context context, Bus bus) {
        return new PaletteService(context, bus);
    }

    @Provides
    @Singleton
    public Bus provideBus() {
        return new Bus(ThreadEnforcer.ANY);
    }

}

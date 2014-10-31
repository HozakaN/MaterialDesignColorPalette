package fr.hozakan.materialdesigncolorpalette;

import java.util.Arrays;

import fr.hozakan.materialdesigncolorpalette.dagger.BaseApplication;
import fr.hozakan.materialdesigncolorpalette.dagger.PaletteColorModule;
import fr.hozakan.materialdesigncolorpalette.otto.AddActionbarPreviewColorEvent;

/**
 * Created by gimbert on 2014-08-11.
 */
public class PaletteColorApplication extends BaseApplication {
    @Override
    protected Object[] getModules() {
        return Arrays.asList(new PaletteColorModule(this)).toArray();
    }

    private void foo(AddActionbarPreviewColorEvent event) {
        event.color.setActionBarPreviewColor(true);
    }
}

package fr.hozakan.materialdesigncolorpalette;

import java.util.Arrays;

import fr.hozakan.materialdesigncolorpalette.dagger.PaletteColorModules;

/**
 * Created by gimbert on 2014-07-31.
 */
public class PaletteColorApplication extends BaseApplication {
    @Override
    protected Object[] getModules() {
        return Arrays.asList(new PaletteColorModules(this)).toArray();
    }
}

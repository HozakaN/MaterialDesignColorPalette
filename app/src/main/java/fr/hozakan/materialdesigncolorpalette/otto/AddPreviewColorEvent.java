package fr.hozakan.materialdesigncolorpalette.otto;

import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;

/**
 * Created by gImbert on 12/08/2014.
 */
public class AddPreviewColorEvent {
    public final PaletteColor color;

    public AddPreviewColorEvent(PaletteColor color) {
        this.color = color;
    }
}

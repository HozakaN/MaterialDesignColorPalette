package fr.hozakan.materialdesigncolorpalette.otto;

import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;

/**
 * Created by gImbert on 12/08/2014.
 */
public class AddActionbarPreviewColorEvent {
    public final PaletteColor color;

    public AddActionbarPreviewColorEvent(PaletteColor color) {
        this.color = color;
    }
}

package fr.hozakan.materialdesigncolorpalette.otto;

import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;

/**
 * Created by gImbert on 12/08/2014.
 */
public class RemoveActionbarPreviewColorEvent {
    public final PaletteColor color;

    public RemoveActionbarPreviewColorEvent(PaletteColor color) {
        this.color = color;
    }
}

package fr.hozakan.materialdesigncolorpalette.otto;

import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;

/**
 * Created by gImbert on 12/08/2014.
 */
public class ActionbarPreviewColorRemovedEvent {
    public final PaletteColor color;

    public ActionbarPreviewColorRemovedEvent(PaletteColor color) {
        this.color = color;
    }
}

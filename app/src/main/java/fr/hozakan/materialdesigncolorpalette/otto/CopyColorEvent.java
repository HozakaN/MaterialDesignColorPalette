package fr.hozakan.materialdesigncolorpalette.otto;

import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;

/**
 * Created by gimbert on 2014-08-11.
 */
public class CopyColorEvent {
    public PaletteColor mColor;
    private String mColorName;

    public CopyColorEvent(String colorName, PaletteColor color) {
        mColor = color;
        mColorName = colorName;
    }

    public PaletteColor getColor() {
        return mColor;
    }

    public String getColorName() {
        return mColorName;
    }
}

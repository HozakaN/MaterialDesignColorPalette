package fr.hozakan.materialdesigncolorpalette.model;

/**
 * Created by gimbert on 2014-07-18.
 */
public class PaletteColor {

    private int colorId;
    private String nameId;
    private String hexaId;
    private String parentColorName;

    public PaletteColor(int colorId, String parentColorName, String nameId, String hexaId) {
        this.colorId = colorId;
        this.parentColorName = parentColorName;
        this.nameId = nameId;
        this.hexaId = hexaId;
    }

    public int getColorId() {
        return colorId;
    }

    public String getParentColorName() {
        return parentColorName;
    }

    public String getNameId() {
        return nameId;
    }

    public String getHexaId() {
        return hexaId;
    }
}

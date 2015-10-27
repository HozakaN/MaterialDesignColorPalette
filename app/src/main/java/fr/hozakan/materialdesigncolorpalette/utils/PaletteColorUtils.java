package fr.hozakan.materialdesigncolorpalette.utils;

import android.text.TextUtils;

import java.util.List;

import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;

/**
 * Created by gimbert on 14-12-22.
 */
public class PaletteColorUtils {

    public static PaletteColor findPaletteByHexColor(List<PaletteColor> paletteColorList, String hexString) {
        if (paletteColorList == null) {
            throw new IllegalArgumentException("paletteColorList must not be null");
        }
        if (TextUtils.isEmpty(hexString)) {
            throw new IllegalArgumentException("hexString must not be null or empty");
        }
        PaletteColor foundPalette = null;
        for (PaletteColor paletteColor : paletteColorList) {
            if (hexString.equals(paletteColor.getHexString())) {
                foundPalette = paletteColor;
                break;
            }
        }
        return foundPalette;
    }

    /**
     *
     * @param paletteColorList
     * @param hexString
     * @return the position of hexString in the list
     */
    public static int findPaletteIndexByHexColor(List<PaletteColor> paletteColorList, String hexString) {
        if (paletteColorList == null) {
            throw new IllegalArgumentException("paletteColorList must not be null");
        }
        if (TextUtils.isEmpty(hexString)) {
            throw new IllegalArgumentException("hexString must not be null or empty");
        }
        int index = -1;
        for (int i = 0; i < paletteColorList.size(); i++) {
            if (hexString.equals(paletteColorList.get(i).getHexString())) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static int findPaletteSectionIndexByPalette(List<PaletteColorSection> sections, PaletteColor color) {
        if (sections == null) {
            throw new IllegalArgumentException("sections must not be null");
        }
        if (color == null) {
            throw new IllegalArgumentException("color must not be null");
        }
        for (int i = 0; i < sections.size(); i++) {
            PaletteColorSection section = sections.get(i);
            if (section.getPaletteColorList().contains(color)) {
                return i;
            }
        }
        return -1;
    }

    public static PaletteColorSection findPaletteSectionByPalette(List<PaletteColorSection> sections, PaletteColor color) {
        PaletteColorSection section = null;
        if (sections == null) {
            throw new IllegalArgumentException("sections must not be null");
        }
        if (color == null) {
            throw new IllegalArgumentException("color must not be null");
        }
        for (PaletteColorSection paletteColorSection : sections) {
            if (paletteColorSection.getPaletteColorList().contains(color)) {
                section = paletteColorSection;
                break;
            }
        }
        return section;
    }
}

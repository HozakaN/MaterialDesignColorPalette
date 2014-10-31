package fr.hozakan.materialdesigncolorpalette.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;
import fr.hozakan.materialdesigncolorpalette.otto.ActionbarPreviewColorRemovedEvent;

/**
 * Created by gimbert on 2014-08-11.
 */
public class PaletteService {

    private static final String SHARED_PREFERENCES_ID = "fr.hozakan.materialdesigncolorpalette.SharedPreferences";
    private static final String ACTIONBAR_PREVIEW_COLOR = "fr.hozakan.materialdesigncolorpalette.ActionBarPreviewColor";
    private static final String PREVIEW_COLORS = "fr.hozakan.materialdesigncolorpalette.PreviewColors";

    private static PaletteColor mActionBarPreviewColor;
    private static List<PaletteColor> mPreviewColors;
    private static List<PaletteColorSection> mColorList;
    private static boolean initialized = false;

    private final Context mContext;
    private final Bus mBus;

    public PaletteService(Context context, Bus bus) {
        mContext = context;
        mBus = bus;

        if (!initialized) {
            mPreviewColors = new ArrayList<PaletteColor>();

            final SharedPreferences prefs = getSharedPreferences();

            String actionbarPreviewColor = prefs.getString(ACTIONBAR_PREVIEW_COLOR, "");
            String previewColors = prefs.getString(PREVIEW_COLORS, "");

            if (actionbarPreviewColor.length() > 0) {
                PaletteColor color = findColor(actionbarPreviewColor);
                if (color != null) {
                    color.setActionBarPreviewColor(true);
                    mActionBarPreviewColor = color;
                }
            }

            if (previewColors.length() > 0) {
                String[] previews = previewColors.split(";");
                for (String preview : previews) {
                    PaletteColor color = findColor(preview);
                    if (color != null) {
                        color.setPreviewColor(true);
                        mPreviewColors.add(color);
                    }
                }
            }
            initialized = true;
        }

    }

    private PaletteColor findColor(String colorHex) {
        final List<PaletteColorSection> colors = getPaletteColorSectionsList();
        for (PaletteColorSection section : colors) {
            for (PaletteColor color : section.getPaletteColorList()) {
                if (color.getHexString().equals(colorHex)) {
                    return color;
                }
            }
        }
        return null;
    }

    public List<PaletteColorSection> getPaletteColorSectionsList () {
        if (mColorList == null) {
            mColorList = buildColorList();
        }
        return mColorList;
    }

    private List<PaletteColorSection> buildColorList() {
        final String[] colorSectionsNames
                = mContext.getResources().getStringArray(R.array.color_sections_names);
        final int[] colorSectionsValues
                = mContext.getResources().getIntArray(R.array.color_sections_colors);
        final int[] darkColorSectionsValues
                = mContext.getResources().getIntArray(R.array.dark_color_sections_colors);

        final String[][] baseColorNames = getBaseColorNames(colorSectionsNames);
        final int[][] colorValues = getColorValues(colorSectionsNames);

        if (colorSectionsNames.length != colorSectionsValues.length
                || colorSectionsNames.length != darkColorSectionsValues.length
                || colorSectionsNames.length != baseColorNames.length
                || colorSectionsNames.length != colorValues.length) {
            throw new RuntimeException("Must supply one-to-one parameters.");
        }
        mColorList = new ArrayList<PaletteColorSection>();
        for (int i = 0; i < colorSectionsNames.length; i++) {

            if (baseColorNames[i].length != colorValues[i].length) {
                throw new RuntimeException("Must supply one-to-one base names with colors.  "
                        + colorSectionsNames[i] + " has " + baseColorNames[i].length + " base names and "
                        + colorValues[i].length + " colors.");
            }
            List<PaletteColor> paletteColorList = new ArrayList<PaletteColor>();
            for (int j = 0; j < baseColorNames[i].length; j++) {
                paletteColorList.add(new PaletteColor(colorSectionsNames[i], colorValues[i][j], baseColorNames[i][j], false, false));
            }
            mColorList.add(new PaletteColorSection(colorSectionsNames[i], colorSectionsValues[i], darkColorSectionsValues[i], paletteColorList));
        }
        return mColorList;
    }

    private boolean isPreviewColor(int hex) {
        List<PaletteColor> colors = getPreviewColors();
        if (colors == null || colors.size() == 0) return false;
        for (PaletteColor color : colors) {
            if (color.getHexString().equals(PaletteColor.intToStringHex(hex))) {
                return true;
            }
        }
        return false;
    }

    private boolean isActionBarPreviewColor(int hex) {
        PaletteColor actionbarPreviewColor = getActionbarPreviewColor();
        if (actionbarPreviewColor == null) {
            return false;
        }
        return actionbarPreviewColor.getHexString().equals(PaletteColor.intToStringHex(hex));
    }


    private String[][] getBaseColorNames(String[] colorSectionsNames) {
        String[][] strArr = new String[colorSectionsNames.length][];
        for (int i = 0; i < colorSectionsNames.length; i++) {
            strArr[i] = getBaseColorNames(colorSectionsNames[i]);
        }
        return strArr;
    }

    private int[][] getColorValues(String[] colorSectionsNames) {
        int[][] intArr = new int[colorSectionsNames.length][];
        for (int i = 0; i < colorSectionsNames.length; i++) {
            intArr[i] = getColorValues(colorSectionsNames[i]);
        }
        return intArr;
    }
    private String[] getBaseColorNames(String colorSectionName) {
        Resources res = mContext.getResources();
        if (res.getString(R.string.red).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.pink).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.purple).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.deep_purple).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.indigo).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.blue).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.light_blue).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.cyan).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.teal).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.green).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.light_green).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.lime).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.yellow).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.amber).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.orange).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.deep_orange).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (res.getString(R.string.brown).equals(colorSectionName)) {
            return res.getStringArray(R.array.base_color_list_to_900);
        } else if (res.getString(R.string.grey).equals(colorSectionName)) {
            return res.getStringArray(R.array.base_color_list_greys);
        } else if (res.getString(R.string.blue_grey).equals(colorSectionName)) {
            return res.getStringArray(R.array.base_color_list_to_900);
        } else {
            throw new RuntimeException("Invalid color section: " + colorSectionName);
        }
    }

    private int[] getColorValues(String colorSectionName) {
        Resources res = mContext.getResources();
        if (res.getString(R.string.red).equals(colorSectionName)) {
            return res.getIntArray(R.array.reds);
        } else if (res.getString(R.string.pink).equals(colorSectionName)) {
            return res.getIntArray(R.array.pinks);
        } else if (res.getString(R.string.purple).equals(colorSectionName)) {
            return res.getIntArray(R.array.purples);
        } else if (res.getString(R.string.deep_purple).equals(colorSectionName)) {
            return res.getIntArray(R.array.deep_purples);
        } else if (res.getString(R.string.indigo).equals(colorSectionName)) {
            return res.getIntArray(R.array.indigos);
        } else if (res.getString(R.string.blue).equals(colorSectionName)) {
            return res.getIntArray(R.array.blues);
        } else if (res.getString(R.string.light_blue).equals(colorSectionName)) {
            return res.getIntArray(R.array.light_blues);
        } else if (res.getString(R.string.cyan).equals(colorSectionName)) {
            return res.getIntArray(R.array.cyans);
        } else if (res.getString(R.string.teal).equals(colorSectionName)) {
            return res.getIntArray(R.array.teals);
        } else if (res.getString(R.string.green).equals(colorSectionName)) {
            return res.getIntArray(R.array.greens);
        } else if (res.getString(R.string.light_green).equals(colorSectionName)) {
            return res.getIntArray(R.array.light_greens);
        } else if (res.getString(R.string.lime).equals(colorSectionName)) {
            return res.getIntArray(R.array.limes);
        } else if (res.getString(R.string.yellow).equals(colorSectionName)) {
            return res.getIntArray(R.array.yellows);
        } else if (res.getString(R.string.amber).equals(colorSectionName)) {
            return res.getIntArray(R.array.ambers);
        } else if (res.getString(R.string.orange).equals(colorSectionName)) {
            return res.getIntArray(R.array.oranges);
        } else if (res.getString(R.string.deep_orange).equals(colorSectionName)) {
            return res.getIntArray(R.array.deep_oranges);
        } else if (res.getString(R.string.brown).equals(colorSectionName)) {
            return res.getIntArray(R.array.browns);
        } else if (res.getString(R.string.grey).equals(colorSectionName)) {
            return res.getIntArray(R.array.greys);
        } else if (res.getString(R.string.blue_grey).equals(colorSectionName)) {
            return res.getIntArray(R.array.blue_greys);
        } else {
            throw new RuntimeException("Invalid color section: " + colorSectionName);
        }
    }

    public PaletteColor getActionbarPreviewColor() {
        return mActionBarPreviewColor;
    }

    public List<PaletteColor> getPreviewColors() {
        return mPreviewColors;
    }

    public boolean setActionBarPreviewColor(PaletteColor color) {
        final SharedPreferences prefs = getSharedPreferences();
        PaletteColor oldAbPreviewColor = mActionBarPreviewColor;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ACTIONBAR_PREVIEW_COLOR, color.getHexString());
        editor.commit();
        color.setActionBarPreviewColor(true);
        this.mActionBarPreviewColor = color;

        if (oldAbPreviewColor != null) {
            oldAbPreviewColor.setActionBarPreviewColor(false);
            mBus.post(new ActionbarPreviewColorRemovedEvent(oldAbPreviewColor));
        }
        return true;
    }

    public boolean resetActionBarPreviewColor() {

        if (mActionBarPreviewColor != null) {
            final SharedPreferences prefs = getSharedPreferences();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(ACTIONBAR_PREVIEW_COLOR, "");
            editor.commit();
            mActionBarPreviewColor.setActionBarPreviewColor(false);
            mActionBarPreviewColor = null;
            return true;
        }
        return false;
    }

    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(SHARED_PREFERENCES_ID, Context.MODE_PRIVATE);
    }

    public boolean addPreviewColor(PaletteColor color) {
        final SharedPreferences prefs = getSharedPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        String previewColors = prefs.getString(PREVIEW_COLORS, "");
        if (previewColors.length() > 0) {
            previewColors += ";";
        }
        previewColors += color.getHexString();
        editor.putString(PREVIEW_COLORS, previewColors);
        editor.commit();

        color.setPreviewColor(true);
        mPreviewColors.add(color);
        return true;
    }

    public boolean removePreviewColor(PaletteColor color) {
        final SharedPreferences prefs = getSharedPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        String previewColors = prefs.getString(PREVIEW_COLORS, "");
        if (previewColors.contains(color.getHexString())) {
            previewColors = previewColors.replace(color.getHexString(), "");
            previewColors = previewColors.replace(";;", ";");
            editor.putString(PREVIEW_COLORS, previewColors);
            editor.commit();

            color.setPreviewColor(false);
            mPreviewColors.remove(color);
            return true;
        }
        return false;
    }
}

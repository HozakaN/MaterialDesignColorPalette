package fr.hozakan.materialdesigncolorpalette.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;

import com.squareup.otto.Bus;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;

/**
 * Created by gimbert on 2014-08-11.
 */
public class PaletteService {

    private static final String SHARED_PREFERENCES_ID = "fr.hozakan.materialdesigncolorpalette.SharedPreferences";
    private static final String PRIMARY_COLOR = "fr.hozakan.materialdesigncolorpalette.PrimaryColor";
    private static final String PRIMARY_DARK_COLOR = "fr.hozakan.materialdesigncolorpalette.PrimaryDarkColor";
    private static final String ACCENT_COLOR = "fr.hozakan.materialdesigncolorpalette.AccentColor";
    private static final String PREVIEW_COLORS = "fr.hozakan.materialdesigncolorpalette.PreviewColors";

    public static final int SPARSE_PRIMARY_COLOR_KEY = 0;
    public static final int SPARSE_PRIMARY_DARK_COLOR_KEY = 1;
    public static final int SPARSE_ACCENT_COLOR_KEY = 2;

    private static PaletteColor mPrimaryColor;
    private static PaletteColor mPrimaryDarkColor;
    private static PaletteColor mAccentColor;
    private static List<PaletteColor> mPreviewColors;
    private static List<PaletteColorSection> mColorList;
    private static boolean initialized = false;

    private final Context mContext;
    private final Bus mBus;

    public PaletteService(Context context, Bus bus) {
        mContext = context;
        mBus = bus;

        if (!initialized) {
            mPreviewColors = new ArrayList<>();

            final SharedPreferences prefs = getSharedPreferences();

            String primaryColor = prefs.getString(PRIMARY_COLOR, "");
            String primaryDarkColor = prefs.getString(PRIMARY_DARK_COLOR, "");
            String accentColor = prefs.getString(ACCENT_COLOR, "");
            String previewColors = prefs.getString(PREVIEW_COLORS, "");

            if (!TextUtils.isEmpty(primaryColor)) {
                PaletteColor color = findColor(primaryColor);
                if (color != null) {
                    color.setPrimaryColor(true);
                    mPrimaryColor = color;
                }
            }

            if (!TextUtils.isEmpty(primaryDarkColor)) {
                PaletteColor color = findColor(primaryDarkColor);
                if (color != null) {
                    color.setPrimaryDarkColor(true);
                    mPrimaryDarkColor = color;
                }
            }

            if (!TextUtils.isEmpty(accentColor)) {
                PaletteColor color = findColor(accentColor);
                if (color != null) {
                    color.setAccentColor(true);
                    mAccentColor = color;
                }
            }

            if (previewColors.length() > 0) {
                String[] previews = previewColors.split(";");
                for (String preview : previews) {
                    PaletteColor color = findColor(preview);
                    if (color != null) {
                        color.setPrimaryDarkColor(true);
                        mPreviewColors.add(color);
                    }
                }
            }
            initialized = true;
        }

    }

    public PaletteColor getPrimaryColor() {
        return mPrimaryColor;
    }

    public PaletteColor getPrimaryDarkColor() {
        return mPrimaryDarkColor;
    }

    public PaletteColor getAccentColor() {
        return mAccentColor;
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
                paletteColorList.add(new PaletteColor(colorSectionsNames[i], colorValues[i][j], baseColorNames[i][j], false, false, false));
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
        return mPrimaryColor;
    }

    public List<PaletteColor> getPreviewColors() {
        return mPreviewColors;
    }

    public SparseArray<PaletteColor> getChosenColors() {
        SparseArray<PaletteColor> colors = new SparseArray<>();
        if (mPrimaryColor != null) {
            colors.put(SPARSE_PRIMARY_COLOR_KEY, mPrimaryColor);
        }
        if (mPrimaryDarkColor != null) {
            colors.put(SPARSE_PRIMARY_DARK_COLOR_KEY, mPrimaryDarkColor);
        }
        if (mAccentColor != null) {
            colors.put(SPARSE_ACCENT_COLOR_KEY, mAccentColor);
        }
        return colors;
    }

    public interface SetPrimaryColorCallback {
        public void onPrimaryColorRemoved(final PaletteColor oldPrimaryColor);
        public void onPrimaryColorAdded(final PaletteColor newPrimaryColor);
        public void onPrimaryColorChanged(PaletteColor oldPrimaryColor, PaletteColor newPrimaryColor);
    }

    public interface SetPrimaryDarkColorCallback {
        public void onPrimaryDarkColorRemoved(final PaletteColor oldPrimaryDarkColor);
        public void onPrimaryDarkColorAdded(final PaletteColor newPrimaryDarkColor);
        public void onPrimaryDarkColorChanged(PaletteColor oldPrimaryDarkColor, PaletteColor newPrimaryDarkColor);
    }

    public interface SetAccentColorCallback {
        public void onAccentColorRemoved(final PaletteColor oldAccentColor);
        public void onAccentColorAdded(final PaletteColor newAccentColor);
        public void onAccentColorChanged(PaletteColor oldAccentColor, PaletteColor newAccentColor);
    }

    public void setPrimaryColor(final PaletteColor primaryColor, final SetPrimaryColorCallback callback) {
        final WeakReference<SetPrimaryColorCallback> cback = new WeakReference<>(callback);

        new Thread(new Runnable() {
            @Override
            public void run() {

                final SharedPreferences prefs = getSharedPreferences();
                final PaletteColor oldPrimary = mPrimaryColor;
                SharedPreferences.Editor editor = prefs.edit();

                if (oldPrimary != null && primaryColor.getHexString().equals(oldPrimary.getHexString())) {
                    mPrimaryColor = null;
                    editor.putString(PRIMARY_COLOR, "");
                    editor.commit();
                } else {
                    primaryColor.setPrimaryColor(true);
                    mPrimaryColor = primaryColor;
                    editor.putString(PRIMARY_COLOR, primaryColor.getHexString());
                    editor.commit();
                }


                if (oldPrimary != null) {
                    oldPrimary.setPrimaryColor(false);
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (cback.get() != null) {
                            if (mPrimaryColor != null && oldPrimary != null) {
                                cback.get().onPrimaryColorChanged(oldPrimary, primaryColor);
                            } else if (mPrimaryColor != null) {
                                cback.get().onPrimaryColorAdded(primaryColor);
                            } else if (oldPrimary != null) {
                                cback.get().onPrimaryColorRemoved(oldPrimary);
                            }
                        }
                    }
                });
            }
        }).start();
    }

    public void setPrimaryDarkColor(final PaletteColor primaryDarkColor, final SetPrimaryDarkColorCallback callback) {
        final WeakReference<SetPrimaryDarkColorCallback> cback = new WeakReference<>(callback);

        new Thread(new Runnable() {
            @Override
            public void run() {

                final SharedPreferences prefs = getSharedPreferences();
                final PaletteColor oldPrimaryDark = mPrimaryDarkColor;
                SharedPreferences.Editor editor = prefs.edit();

                if (oldPrimaryDark != null && primaryDarkColor.getHexString().equals(oldPrimaryDark.getHexString())) {
                    mPrimaryDarkColor = null;
                    editor.putString(PRIMARY_DARK_COLOR, "");
                    editor.commit();
                } else {
                    primaryDarkColor.setPrimaryDarkColor(true);
                    mPrimaryDarkColor = primaryDarkColor;
                    editor.putString(PRIMARY_DARK_COLOR, primaryDarkColor.getHexString());
                    editor.commit();
                }


                if (oldPrimaryDark != null) {
                    oldPrimaryDark.setPrimaryDarkColor(false);
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (cback.get() != null) {
                            if (mPrimaryDarkColor != null && oldPrimaryDark != null) {
                                cback.get().onPrimaryDarkColorChanged(oldPrimaryDark, primaryDarkColor);
                            } else if (mPrimaryDarkColor != null) {
                                cback.get().onPrimaryDarkColorAdded(primaryDarkColor);
                            } else if (oldPrimaryDark != null) {
                                cback.get().onPrimaryDarkColorRemoved(oldPrimaryDark);
                            }
                        }
                    }
                });
            }
        }).start();
    }

    public void setAccentColor(final PaletteColor accentColor, final SetAccentColorCallback callback) {
        final WeakReference<SetAccentColorCallback> cback = new WeakReference<>(callback);

        new Thread(new Runnable() {
            @Override
            public void run() {

                final SharedPreferences prefs = getSharedPreferences();
                final PaletteColor oldAccent = mAccentColor;
                SharedPreferences.Editor editor = prefs.edit();

                if (oldAccent != null && accentColor.getHexString().equals(oldAccent.getHexString())) {
                    mAccentColor = null;
                    editor.putString(ACCENT_COLOR, "");
                    editor.commit();
                } else {
                    accentColor.setAccentColor(true);
                    mAccentColor = accentColor;
                    editor.putString(ACCENT_COLOR, accentColor.getHexString());
                    editor.apply();
                }


                if (oldAccent != null) {
                    oldAccent.setAccentColor(false);
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (cback.get() != null) {
                            if (mAccentColor != null && oldAccent != null) {
                                cback.get().onAccentColorChanged(oldAccent, accentColor);
                            } else if (mAccentColor != null) {
                                cback.get().onAccentColorAdded(accentColor);
                            } else if (oldAccent != null) {
                                cback.get().onAccentColorRemoved(oldAccent);
                            }
                        }
                    }
                });
            }
        }).start();
    }

    public boolean resetActionBarPreviewColor() {
        if (mPrimaryColor != null) {
            final SharedPreferences prefs = getSharedPreferences();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PRIMARY_COLOR, "");
            editor.commit();
            mPrimaryColor.setPrimaryColor(false);
            mPrimaryColor = null;
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

        color.setPrimaryDarkColor(true);
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

            color.setPrimaryDarkColor(false);
            mPreviewColors.remove(color);
            return true;
        }
        return false;
    }
}

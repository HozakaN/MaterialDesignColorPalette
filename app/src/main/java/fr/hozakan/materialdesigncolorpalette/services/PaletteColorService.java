package fr.hozakan.materialdesigncolorpalette.services;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;

/**
 * Created by gimbert on 2014-07-18.
 */
public class PaletteColorService {

    private Context mContext;
    private static List<PaletteColor> redPaletteColors;
    private static List<PaletteColor> pinkPaletteColors;
    private static List<PaletteColor> purplePaletteColors;
    private static List<PaletteColor> deepPurplePaletteColors;
    private static List<PaletteColor> indigoPaletteColors;
    private static List<PaletteColor> bluePaletteColors;
    private static List<PaletteColor> lightBluePaletteColors;
    private static List<PaletteColor> cyanPaletteColors;
    private static List<PaletteColor> tealPaletteColors;
    private static List<PaletteColor> greenPaletteColors;
    private static List<PaletteColor> lightGreenPaletteColors;
    private static List<PaletteColor> limePaletteColors;
    private static List<PaletteColor> yellowPaletteColors;
    private static List<PaletteColor> amberPaletteColors;
    private static List<PaletteColor> orangePaletteColors;
    private static List<PaletteColor> deepOrangePaletteColors;
    private static List<PaletteColor> brownPaletteColors;
    private static List<PaletteColor> greyPaletteColors;
    private static List<PaletteColor> blueGreyPaletteColors;

    public PaletteColorService(Context context) {
        mContext = context;
    }


    private static List<String> sections;

    public final List<String> getSections() {
        if (sections == null) {
            Resources res = mContext.getResources();
            sections = Arrays.asList(new String[] {res.getString(R.string.red),
                    res.getString(R.string.pink),
                    res.getString(R.string.purple),
                    res.getString(R.string.deep_purple),
                    res.getString(R.string.indigo),
                    res.getString(R.string.blue),
                    res.getString(R.string.light_blue),
                    res.getString(R.string.cyan),
                    res.getString(R.string.teal),
                    res.getString(R.string.green),
                    res.getString(R.string.light_green),
                    res.getString(R.string.lime),
                    res.getString(R.string.yellow),
                    res.getString(R.string.amber),
                    res.getString(R.string.orange),
                    res.getString(R.string.deep_orange),
                    res.getString(R.string.brown),
                    res.getString(R.string.grey),
                    res.getString(R.string.blue_grey)});
        }
        return sections;
    }

    public final String[] getSectionsAsArray() {
        return getSections().toArray(new String[]{});
    }

    public List<PaletteColor> getPaletteColors(int primaryColor) {
        List<PaletteColor> colors = null;
        switch (primaryColor) {
            case R.string.red:
                colors = getRedPaletteColors();
                break;
            case R.string.pink:
                colors = getPinkPaletteColors();
                break;
            case R.string.purple:
                colors = getPurplePaletteColors();
                break;
            case R.string.deep_purple:
                colors = getDeepPurplePaletteColors();
                break;
            case R.string.indigo:
                colors = getIndigoPaletteColors();
                break;
            case R.string.blue:
                colors = getBluePaletteColors();
                break;
            case R.string.light_blue:
                colors = getLightBluePaletteColors();
                break;
            case R.string.cyan:
                colors = getCyanPaletteColors();
                break;
            case R.string.teal:
                colors = getTealPaletteColors();
                break;
            case R.string.green:
                colors = getGreenPaletteColors();
                break;
            case R.string.light_green:
                colors = getLightGreenPaletteColors();
                break;
            case R.string.lime:
                colors = getLimePaletteColors();
                break;
            case R.string.yellow:
                colors = getYellowPaletteColors();
                break;
            case R.string.amber:
                colors = getAmberPaletteColors();
                break;
            case R.string.orange:
                colors = getOrangePaletteColors();
                break;
            case R.string.deep_orange:
                colors = getDeepOrangePaletteColors();
                break;
            case R.string.brown:
                colors = getBrownPaletteColors();
                break;
            case R.string.grey:
                colors = getGreyPaletteColors();
                break;
            case R.string.blue_grey:
                colors = getBlueGreyPaletteColors();
                break;
            default:
                colors = new ArrayList<PaletteColor>();
                break;
        }
        return colors;
    }

    public List<PaletteColor> getRedPaletteColors() {
        if (redPaletteColors == null) {
            Resources res = mContext.getResources();
            redPaletteColors = Arrays.asList(new PaletteColor(R.color.md_red_500, "red", "500", res.getString(R.color.md_red_500)),
                                             new PaletteColor(R.color.md_red_50, "red", "50", res.getString(R.color.md_red_50)),
                                             new PaletteColor(R.color.md_red_100, "red", "100", res.getString(R.color.md_red_100)),
                                             new PaletteColor(R.color.md_red_200, "red", "200", res.getString(R.color.md_red_200)),
                                             new PaletteColor(R.color.md_red_300, "red", "300", res.getString(R.color.md_red_300)),
                                             new PaletteColor(R.color.md_red_400, "red", "400", res.getString(R.color.md_red_400)),
                                             new PaletteColor(R.color.md_red_600, "red", "600", res.getString(R.color.md_red_600)),
                                             new PaletteColor(R.color.md_red_700, "red", "700", res.getString(R.color.md_red_700)),
                                             new PaletteColor(R.color.md_red_800, "red", "800", res.getString(R.color.md_red_800)),
                                             new PaletteColor(R.color.md_red_900, "red", "900", res.getString(R.color.md_red_900)),
                                             new PaletteColor(R.color.md_red_A100, "red", "A100", res.getString(R.color.md_red_A100)),
                                             new PaletteColor(R.color.md_red_A200, "red", "A200", res.getString(R.color.md_red_A200)),
                                             new PaletteColor(R.color.md_red_A400, "red", "A400", res.getString(R.color.md_red_A400)),
                                             new PaletteColor(R.color.md_red_A700, "red", "A700", res.getString(R.color.md_red_A700)));
        }
        return redPaletteColors;
    }

    public List<PaletteColor> getPinkPaletteColors() {
        if (pinkPaletteColors == null) {
            Resources res = mContext.getResources();
            pinkPaletteColors = Arrays.asList(new PaletteColor(R.color.md_pink_500, "pink", "500", res.getString(R.color.md_pink_500)),
                                             new PaletteColor(R.color.md_pink_50, "pink", "50", res.getString(R.color.md_pink_50)),
                                             new PaletteColor(R.color.md_pink_100, "pink", "100", res.getString(R.color.md_pink_100)),
                                             new PaletteColor(R.color.md_pink_200, "pink", "200", res.getString(R.color.md_pink_200)),
                                             new PaletteColor(R.color.md_pink_300, "pink", "300", res.getString(R.color.md_pink_300)),
                                             new PaletteColor(R.color.md_pink_400, "pink", "400", res.getString(R.color.md_pink_400)),
                                             new PaletteColor(R.color.md_pink_600, "pink", "600", res.getString(R.color.md_pink_600)),
                                             new PaletteColor(R.color.md_pink_700, "pink", "700", res.getString(R.color.md_pink_700)),
                                             new PaletteColor(R.color.md_pink_800, "pink", "800", res.getString(R.color.md_pink_800)),
                                             new PaletteColor(R.color.md_pink_900, "pink", "900", res.getString(R.color.md_pink_900)),
                                             new PaletteColor(R.color.md_pink_A100, "pink", "A100", res.getString(R.color.md_pink_A100)),
                                             new PaletteColor(R.color.md_pink_A200, "pink", "A200", res.getString(R.color.md_pink_A200)),
                                             new PaletteColor(R.color.md_pink_A400, "pink", "A400", res.getString(R.color.md_pink_A400)),
                                             new PaletteColor(R.color.md_pink_A700, "pink", "A700", res.getString(R.color.md_pink_A700)));
        }
        return pinkPaletteColors;
    }

    public List<PaletteColor> getPurplePaletteColors() {
        if (purplePaletteColors == null) {
            Resources res = mContext.getResources();
            purplePaletteColors = Arrays.asList(new PaletteColor(R.color.md_purple_500, "purple", "500", res.getString(R.color.md_purple_500)),
                                             new PaletteColor(R.color.md_purple_50, "purple", "50", res.getString(R.color.md_purple_50)),
                                             new PaletteColor(R.color.md_purple_100, "purple", "100", res.getString(R.color.md_purple_100)),
                                             new PaletteColor(R.color.md_purple_200, "purple", "200", res.getString(R.color.md_purple_200)),
                                             new PaletteColor(R.color.md_purple_300, "purple", "300", res.getString(R.color.md_purple_300)),
                                             new PaletteColor(R.color.md_purple_400, "purple", "400", res.getString(R.color.md_purple_400)),
                                             new PaletteColor(R.color.md_purple_600, "purple", "600", res.getString(R.color.md_purple_600)),
                                             new PaletteColor(R.color.md_purple_700, "purple", "700", res.getString(R.color.md_purple_700)),
                                             new PaletteColor(R.color.md_purple_800, "purple", "800", res.getString(R.color.md_purple_800)),
                                             new PaletteColor(R.color.md_purple_900, "purple", "900", res.getString(R.color.md_purple_900)),
                                             new PaletteColor(R.color.md_purple_A100, "purple", "A100", res.getString(R.color.md_purple_A100)),
                                             new PaletteColor(R.color.md_purple_A200, "purple", "A200", res.getString(R.color.md_purple_A200)),
                                             new PaletteColor(R.color.md_purple_A400, "purple", "A400", res.getString(R.color.md_purple_A400)),
                                             new PaletteColor(R.color.md_purple_A700, "purple", "A700", res.getString(R.color.md_purple_A700)));
        }
        return purplePaletteColors;
    }

    public List<PaletteColor> getDeepPurplePaletteColors() {
        if (deepPurplePaletteColors == null) {
            Resources res = mContext.getResources();
            deepPurplePaletteColors = Arrays.asList(new PaletteColor(R.color.md_deep_purple_500, "deep purple", "500", res.getString(R.color.md_deep_purple_500)),
                                             new PaletteColor(R.color.md_deep_purple_50, "deep purple", "50", res.getString(R.color.md_deep_purple_50)),
                                             new PaletteColor(R.color.md_deep_purple_100, "deep purple", "100", res.getString(R.color.md_deep_purple_100)),
                                             new PaletteColor(R.color.md_deep_purple_200, "deep purple", "200", res.getString(R.color.md_deep_purple_200)),
                                             new PaletteColor(R.color.md_deep_purple_300, "deep purple", "300", res.getString(R.color.md_deep_purple_300)),
                                             new PaletteColor(R.color.md_deep_purple_400, "deep purple", "400", res.getString(R.color.md_deep_purple_400)),
                                             new PaletteColor(R.color.md_deep_purple_600, "deep purple", "600", res.getString(R.color.md_deep_purple_600)),
                                             new PaletteColor(R.color.md_deep_purple_700, "deep purple", "700", res.getString(R.color.md_deep_purple_700)),
                                             new PaletteColor(R.color.md_deep_purple_800, "deep purple", "800", res.getString(R.color.md_deep_purple_800)),
                                             new PaletteColor(R.color.md_deep_purple_900, "deep purple", "900", res.getString(R.color.md_deep_purple_900)),
                                             new PaletteColor(R.color.md_deep_purple_A100, "deep purple", "A100", res.getString(R.color.md_deep_purple_A100)),
                                             new PaletteColor(R.color.md_deep_purple_A200, "deep purple", "A200", res.getString(R.color.md_deep_purple_A200)),
                                             new PaletteColor(R.color.md_deep_purple_A400, "deep purple", "A400", res.getString(R.color.md_deep_purple_A400)),
                                             new PaletteColor(R.color.md_deep_purple_A700, "deep purple", "A700", res.getString(R.color.md_deep_purple_A700)));
        }
        return deepPurplePaletteColors;
    }

    public List<PaletteColor> getIndigoPaletteColors() {
        if (indigoPaletteColors == null) {
            Resources res = mContext.getResources();
            indigoPaletteColors = Arrays.asList(new PaletteColor(R.color.md_indigo_500, "indigo", "500", res.getString(R.color.md_indigo_500)),
                                             new PaletteColor(R.color.md_indigo_50, "indigo", "50", res.getString(R.color.md_indigo_50)),
                                             new PaletteColor(R.color.md_indigo_100, "indigo", "100", res.getString(R.color.md_indigo_100)),
                                             new PaletteColor(R.color.md_indigo_200, "indigo", "200", res.getString(R.color.md_indigo_200)),
                                             new PaletteColor(R.color.md_indigo_300, "indigo", "300", res.getString(R.color.md_indigo_300)),
                                             new PaletteColor(R.color.md_indigo_400, "indigo", "400", res.getString(R.color.md_indigo_400)),
                                             new PaletteColor(R.color.md_indigo_600, "indigo", "600", res.getString(R.color.md_indigo_600)),
                                             new PaletteColor(R.color.md_indigo_700, "indigo", "700", res.getString(R.color.md_indigo_700)),
                                             new PaletteColor(R.color.md_indigo_800, "indigo", "800", res.getString(R.color.md_indigo_800)),
                                             new PaletteColor(R.color.md_indigo_900, "indigo", "900", res.getString(R.color.md_indigo_900)),
                                             new PaletteColor(R.color.md_indigo_A100, "indigo", "A100", res.getString(R.color.md_indigo_A100)),
                                             new PaletteColor(R.color.md_indigo_A200, "indigo", "A200", res.getString(R.color.md_indigo_A200)),
                                             new PaletteColor(R.color.md_indigo_A400, "indigo", "A400", res.getString(R.color.md_indigo_A400)),
                                             new PaletteColor(R.color.md_indigo_A700, "indigo", "A700", res.getString(R.color.md_indigo_A700)));
        }
        return indigoPaletteColors;
    }

    public List<PaletteColor> getBluePaletteColors() {
        if (bluePaletteColors == null) {
            Resources res = mContext.getResources();
            bluePaletteColors = Arrays.asList(new PaletteColor(R.color.md_blue_500, "blue", "500", res.getString(R.color.md_blue_500)),
                                             new PaletteColor(R.color.md_blue_50, "blue", "50", res.getString(R.color.md_blue_50)),
                                             new PaletteColor(R.color.md_blue_100, "blue", "100", res.getString(R.color.md_blue_100)),
                                             new PaletteColor(R.color.md_blue_200, "blue", "200", res.getString(R.color.md_blue_200)),
                                             new PaletteColor(R.color.md_blue_300, "blue", "300", res.getString(R.color.md_blue_300)),
                                             new PaletteColor(R.color.md_blue_400, "blue", "400", res.getString(R.color.md_blue_400)),
                                             new PaletteColor(R.color.md_blue_600, "blue", "600", res.getString(R.color.md_blue_600)),
                                             new PaletteColor(R.color.md_blue_700, "blue", "700", res.getString(R.color.md_blue_700)),
                                             new PaletteColor(R.color.md_blue_800, "blue", "800", res.getString(R.color.md_blue_800)),
                                             new PaletteColor(R.color.md_blue_900, "blue", "900", res.getString(R.color.md_blue_900)),
                                             new PaletteColor(R.color.md_blue_A100, "blue", "A100", res.getString(R.color.md_blue_A100)),
                                             new PaletteColor(R.color.md_blue_A200, "blue", "A200", res.getString(R.color.md_blue_A200)),
                                             new PaletteColor(R.color.md_blue_A400, "blue", "A400", res.getString(R.color.md_blue_A400)),
                                             new PaletteColor(R.color.md_blue_A700, "blue", "A700", res.getString(R.color.md_blue_A700)));
        }
        return bluePaletteColors;
    }

    public List<PaletteColor> getLightBluePaletteColors() {
        if (lightBluePaletteColors == null) {
            Resources res = mContext.getResources();
            lightBluePaletteColors = Arrays.asList(new PaletteColor(R.color.md_light_blue_500, "light blue", "500", res.getString(R.color.md_light_blue_500)),
                                             new PaletteColor(R.color.md_light_blue_50, "light blue", "50", res.getString(R.color.md_light_blue_50)),
                                             new PaletteColor(R.color.md_light_blue_100, "light blue", "100", res.getString(R.color.md_light_blue_100)),
                                             new PaletteColor(R.color.md_light_blue_200, "light blue", "200", res.getString(R.color.md_light_blue_200)),
                                             new PaletteColor(R.color.md_light_blue_300, "light blue", "300", res.getString(R.color.md_light_blue_300)),
                                             new PaletteColor(R.color.md_light_blue_400, "light blue", "400", res.getString(R.color.md_light_blue_400)),
                                             new PaletteColor(R.color.md_light_blue_600, "light blue", "600", res.getString(R.color.md_light_blue_600)),
                                             new PaletteColor(R.color.md_light_blue_700, "light blue", "700", res.getString(R.color.md_light_blue_700)),
                                             new PaletteColor(R.color.md_light_blue_800, "light blue", "800", res.getString(R.color.md_light_blue_800)),
                                             new PaletteColor(R.color.md_light_blue_900, "light blue", "900", res.getString(R.color.md_light_blue_900)),
                                             new PaletteColor(R.color.md_light_blue_A100, "light blue", "A100", res.getString(R.color.md_light_blue_A100)),
                                             new PaletteColor(R.color.md_light_blue_A200, "light blue", "A200", res.getString(R.color.md_light_blue_A200)),
                                             new PaletteColor(R.color.md_light_blue_A400, "light blue", "A400", res.getString(R.color.md_light_blue_A400)),
                                             new PaletteColor(R.color.md_light_blue_A700, "light blue", "A700", res.getString(R.color.md_light_blue_A700)));
        }
        return lightBluePaletteColors;
    }

    public List<PaletteColor> getCyanPaletteColors() {
        if (cyanPaletteColors == null) {
            Resources res = mContext.getResources();
            cyanPaletteColors = Arrays.asList(new PaletteColor(R.color.md_cyan_500, "cyan", "500", res.getString(R.color.md_cyan_500)),
                                             new PaletteColor(R.color.md_cyan_50, "cyan", "50", res.getString(R.color.md_cyan_50)),
                                             new PaletteColor(R.color.md_cyan_100, "cyan", "100", res.getString(R.color.md_cyan_100)),
                                             new PaletteColor(R.color.md_cyan_200, "cyan", "200", res.getString(R.color.md_cyan_200)),
                                             new PaletteColor(R.color.md_cyan_300, "cyan", "300", res.getString(R.color.md_cyan_300)),
                                             new PaletteColor(R.color.md_cyan_400, "cyan", "400", res.getString(R.color.md_cyan_400)),
                                             new PaletteColor(R.color.md_cyan_600, "cyan", "600", res.getString(R.color.md_cyan_600)),
                                             new PaletteColor(R.color.md_cyan_700, "cyan", "700", res.getString(R.color.md_cyan_700)),
                                             new PaletteColor(R.color.md_cyan_800, "cyan", "800", res.getString(R.color.md_cyan_800)),
                                             new PaletteColor(R.color.md_cyan_900, "cyan", "900", res.getString(R.color.md_cyan_900)),
                                             new PaletteColor(R.color.md_cyan_A100, "cyan", "A100", res.getString(R.color.md_cyan_A100)),
                                             new PaletteColor(R.color.md_cyan_A200, "cyan", "A200", res.getString(R.color.md_cyan_A200)),
                                             new PaletteColor(R.color.md_cyan_A400, "cyan", "A400", res.getString(R.color.md_cyan_A400)),
                                             new PaletteColor(R.color.md_cyan_A700, "cyan", "A700", res.getString(R.color.md_cyan_A700)));
        }
        return cyanPaletteColors;
    }

    public List<PaletteColor> getTealPaletteColors() {
        if (tealPaletteColors == null) {
            Resources res = mContext.getResources();
            tealPaletteColors = Arrays.asList(new PaletteColor(R.color.md_teal_500, "teal", "500", res.getString(R.color.md_teal_500)),
                                             new PaletteColor(R.color.md_teal_50, "teal", "50", res.getString(R.color.md_teal_50)),
                                             new PaletteColor(R.color.md_teal_100, "teal", "100", res.getString(R.color.md_teal_100)),
                                             new PaletteColor(R.color.md_teal_200, "teal", "200", res.getString(R.color.md_teal_200)),
                                             new PaletteColor(R.color.md_teal_300, "teal", "300", res.getString(R.color.md_teal_300)),
                                             new PaletteColor(R.color.md_teal_400, "teal", "400", res.getString(R.color.md_teal_400)),
                                             new PaletteColor(R.color.md_teal_600, "teal", "600", res.getString(R.color.md_teal_600)),
                                             new PaletteColor(R.color.md_teal_700, "teal", "700", res.getString(R.color.md_teal_700)),
                                             new PaletteColor(R.color.md_teal_800, "teal", "800", res.getString(R.color.md_teal_800)),
                                             new PaletteColor(R.color.md_teal_900, "teal", "900", res.getString(R.color.md_teal_900)),
                                             new PaletteColor(R.color.md_teal_A100, "teal", "A100", res.getString(R.color.md_teal_A100)),
                                             new PaletteColor(R.color.md_teal_A200, "teal", "A200", res.getString(R.color.md_teal_A200)),
                                             new PaletteColor(R.color.md_teal_A400, "teal", "A400", res.getString(R.color.md_teal_A400)),
                                             new PaletteColor(R.color.md_teal_A700, "teal", "A700", res.getString(R.color.md_teal_A700)));
        }
        return tealPaletteColors;
    }

    public List<PaletteColor> getGreenPaletteColors() {
        if (greenPaletteColors == null) {
            Resources res = mContext.getResources();
            greenPaletteColors = Arrays.asList(new PaletteColor(R.color.md_green_500, "green", "500", res.getString(R.color.md_green_500)),
                                             new PaletteColor(R.color.md_green_50, "green", "50", res.getString(R.color.md_green_50)),
                                             new PaletteColor(R.color.md_green_100, "green", "100", res.getString(R.color.md_green_100)),
                                             new PaletteColor(R.color.md_green_200, "green", "200", res.getString(R.color.md_green_200)),
                                             new PaletteColor(R.color.md_green_300, "green", "300", res.getString(R.color.md_green_300)),
                                             new PaletteColor(R.color.md_green_400, "green", "400", res.getString(R.color.md_green_400)),
                                             new PaletteColor(R.color.md_green_600, "green", "600", res.getString(R.color.md_green_600)),
                                             new PaletteColor(R.color.md_green_700, "green", "700", res.getString(R.color.md_green_700)),
                                             new PaletteColor(R.color.md_green_800, "green", "800", res.getString(R.color.md_green_800)),
                                             new PaletteColor(R.color.md_green_900, "green", "900", res.getString(R.color.md_green_900)),
                                             new PaletteColor(R.color.md_green_A100, "green", "A100", res.getString(R.color.md_green_A100)),
                                             new PaletteColor(R.color.md_green_A200, "green", "A200", res.getString(R.color.md_green_A200)),
                                             new PaletteColor(R.color.md_green_A400, "green", "A400", res.getString(R.color.md_green_A400)),
                                             new PaletteColor(R.color.md_green_A700, "green", "A700", res.getString(R.color.md_green_A700)));
        }
        return greenPaletteColors;
    }

    public List<PaletteColor> getLightGreenPaletteColors() {
        if (lightGreenPaletteColors == null) {
            Resources res = mContext.getResources();
            lightGreenPaletteColors = Arrays.asList(new PaletteColor(R.color.md_light_green_500, "light green", "500", res.getString(R.color.md_light_green_500)),
                                             new PaletteColor(R.color.md_light_green_50, "light green", "50", res.getString(R.color.md_light_green_50)),
                                             new PaletteColor(R.color.md_light_green_100, "light green", "100", res.getString(R.color.md_light_green_100)),
                                             new PaletteColor(R.color.md_light_green_200, "light green", "200", res.getString(R.color.md_light_green_200)),
                                             new PaletteColor(R.color.md_light_green_300, "light green", "300", res.getString(R.color.md_light_green_300)),
                                             new PaletteColor(R.color.md_light_green_400, "light green", "400", res.getString(R.color.md_light_green_400)),
                                             new PaletteColor(R.color.md_light_green_600, "light green", "600", res.getString(R.color.md_light_green_600)),
                                             new PaletteColor(R.color.md_light_green_700, "light green", "700", res.getString(R.color.md_light_green_700)),
                                             new PaletteColor(R.color.md_light_green_800, "light green", "800", res.getString(R.color.md_light_green_800)),
                                             new PaletteColor(R.color.md_light_green_900, "light green", "900", res.getString(R.color.md_light_green_900)),
                                             new PaletteColor(R.color.md_light_green_A100, "light green", "A100", res.getString(R.color.md_light_green_A100)),
                                             new PaletteColor(R.color.md_light_green_A200, "light green", "A200", res.getString(R.color.md_light_green_A200)),
                                             new PaletteColor(R.color.md_light_green_A400, "light green", "A400", res.getString(R.color.md_light_green_A400)),
                                             new PaletteColor(R.color.md_light_green_A700, "light green", "A700", res.getString(R.color.md_light_green_A700)));
        }
        return lightGreenPaletteColors;
    }

    public List<PaletteColor> getLimePaletteColors() {
        if (limePaletteColors == null) {
            Resources res = mContext.getResources();
            limePaletteColors = Arrays.asList(new PaletteColor(R.color.md_lime_500, "lime", "500", res.getString(R.color.md_lime_500)),
                                             new PaletteColor(R.color.md_lime_50, "lime", "50", res.getString(R.color.md_lime_50)),
                                             new PaletteColor(R.color.md_lime_100, "lime", "100", res.getString(R.color.md_lime_100)),
                                             new PaletteColor(R.color.md_lime_200, "lime", "200", res.getString(R.color.md_lime_200)),
                                             new PaletteColor(R.color.md_lime_300, "lime", "300", res.getString(R.color.md_lime_300)),
                                             new PaletteColor(R.color.md_lime_400, "lime", "400", res.getString(R.color.md_lime_400)),
                                             new PaletteColor(R.color.md_lime_600, "lime", "600", res.getString(R.color.md_lime_600)),
                                             new PaletteColor(R.color.md_lime_700, "lime", "700", res.getString(R.color.md_lime_700)),
                                             new PaletteColor(R.color.md_lime_800, "lime", "800", res.getString(R.color.md_lime_800)),
                                             new PaletteColor(R.color.md_lime_900, "lime", "900", res.getString(R.color.md_lime_900)),
                                             new PaletteColor(R.color.md_lime_A100, "lime", "A100", res.getString(R.color.md_lime_A100)),
                                             new PaletteColor(R.color.md_lime_A200, "lime", "A200", res.getString(R.color.md_lime_A200)),
                                             new PaletteColor(R.color.md_lime_A400, "lime", "A400", res.getString(R.color.md_lime_A400)),
                                             new PaletteColor(R.color.md_lime_A700, "lime", "A700", res.getString(R.color.md_lime_A700)));
        }
        return limePaletteColors;
    }

    public List<PaletteColor> getYellowPaletteColors() {
        if (yellowPaletteColors == null) {
            Resources res = mContext.getResources();
            yellowPaletteColors = Arrays.asList(new PaletteColor(R.color.md_yellow_500, "yellow", "500", res.getString(R.color.md_yellow_500)),
                                             new PaletteColor(R.color.md_yellow_50, "yellow", "50", res.getString(R.color.md_yellow_50)),
                                             new PaletteColor(R.color.md_yellow_100, "yellow", "100", res.getString(R.color.md_yellow_100)),
                                             new PaletteColor(R.color.md_yellow_200, "yellow", "200", res.getString(R.color.md_yellow_200)),
                                             new PaletteColor(R.color.md_yellow_300, "yellow", "300", res.getString(R.color.md_yellow_300)),
                                             new PaletteColor(R.color.md_yellow_400, "yellow", "400", res.getString(R.color.md_yellow_400)),
                                             new PaletteColor(R.color.md_yellow_600, "yellow", "600", res.getString(R.color.md_yellow_600)),
                                             new PaletteColor(R.color.md_yellow_700, "yellow", "700", res.getString(R.color.md_yellow_700)),
                                             new PaletteColor(R.color.md_yellow_800, "yellow", "800", res.getString(R.color.md_yellow_800)),
                                             new PaletteColor(R.color.md_yellow_900, "yellow", "900", res.getString(R.color.md_yellow_900)),
                                             new PaletteColor(R.color.md_yellow_A100, "yellow", "A100", res.getString(R.color.md_yellow_A100)),
                                             new PaletteColor(R.color.md_yellow_A200, "yellow", "A200", res.getString(R.color.md_yellow_A200)),
                                             new PaletteColor(R.color.md_yellow_A400, "yellow", "A400", res.getString(R.color.md_yellow_A400)),
                                             new PaletteColor(R.color.md_yellow_A700, "yellow", "A700", res.getString(R.color.md_yellow_A700)));
        }
        return yellowPaletteColors;
    }

    public List<PaletteColor> getAmberPaletteColors() {
        if (amberPaletteColors == null) {
            Resources res = mContext.getResources();
            amberPaletteColors = Arrays.asList(new PaletteColor(R.color.md_amber_500, "amber", "500", res.getString(R.color.md_amber_500)),
                                             new PaletteColor(R.color.md_amber_50, "amber", "50", res.getString(R.color.md_amber_50)),
                                             new PaletteColor(R.color.md_amber_100, "amber", "100", res.getString(R.color.md_amber_100)),
                                             new PaletteColor(R.color.md_amber_200, "amber", "200", res.getString(R.color.md_amber_200)),
                                             new PaletteColor(R.color.md_amber_300, "amber", "300", res.getString(R.color.md_amber_300)),
                                             new PaletteColor(R.color.md_amber_400, "amber", "400", res.getString(R.color.md_amber_400)),
                                             new PaletteColor(R.color.md_amber_600, "amber", "600", res.getString(R.color.md_amber_600)),
                                             new PaletteColor(R.color.md_amber_700, "amber", "700", res.getString(R.color.md_amber_700)),
                                             new PaletteColor(R.color.md_amber_800, "amber", "800", res.getString(R.color.md_amber_800)),
                                             new PaletteColor(R.color.md_amber_900, "amber", "900", res.getString(R.color.md_amber_900)),
                                             new PaletteColor(R.color.md_amber_A100, "amber", "A100", res.getString(R.color.md_amber_A100)),
                                             new PaletteColor(R.color.md_amber_A200, "amber", "A200", res.getString(R.color.md_amber_A200)),
                                             new PaletteColor(R.color.md_amber_A400, "amber", "A400", res.getString(R.color.md_amber_A400)),
                                             new PaletteColor(R.color.md_amber_A700, "amber", "A700", res.getString(R.color.md_amber_A700)));
        }
        return amberPaletteColors;
    }

    public List<PaletteColor> getOrangePaletteColors() {
        if (orangePaletteColors == null) {
            Resources res = mContext.getResources();
            orangePaletteColors = Arrays.asList(new PaletteColor(R.color.md_orange_500, "orange", "500", res.getString(R.color.md_orange_500)),
                                             new PaletteColor(R.color.md_orange_50, "orange", "50", res.getString(R.color.md_orange_50)),
                                             new PaletteColor(R.color.md_orange_100, "orange", "100", res.getString(R.color.md_orange_100)),
                                             new PaletteColor(R.color.md_orange_200, "orange", "200", res.getString(R.color.md_orange_200)),
                                             new PaletteColor(R.color.md_orange_300, "orange", "300", res.getString(R.color.md_orange_300)),
                                             new PaletteColor(R.color.md_orange_400, "orange", "400", res.getString(R.color.md_orange_400)),
                                             new PaletteColor(R.color.md_orange_600, "orange", "600", res.getString(R.color.md_orange_600)),
                                             new PaletteColor(R.color.md_orange_700, "orange", "700", res.getString(R.color.md_orange_700)),
                                             new PaletteColor(R.color.md_orange_800, "orange", "800", res.getString(R.color.md_orange_800)),
                                             new PaletteColor(R.color.md_orange_900, "orange", "900", res.getString(R.color.md_orange_900)),
                                             new PaletteColor(R.color.md_orange_A100, "orange", "A100", res.getString(R.color.md_orange_A100)),
                                             new PaletteColor(R.color.md_orange_A200, "orange", "A200", res.getString(R.color.md_orange_A200)),
                                             new PaletteColor(R.color.md_orange_A400, "orange", "A400", res.getString(R.color.md_orange_A400)),
                                             new PaletteColor(R.color.md_orange_A700, "orange", "A700", res.getString(R.color.md_orange_A700)));
        }
        return orangePaletteColors;
    }

    public List<PaletteColor> getDeepOrangePaletteColors() {
        if (deepOrangePaletteColors == null) {
            Resources res = mContext.getResources();
            deepOrangePaletteColors = Arrays.asList(new PaletteColor(R.color.md_deep_orange_500, "deep orange", "500", res.getString(R.color.md_deep_orange_500)),
                                             new PaletteColor(R.color.md_deep_orange_50, "deep orange", "50", res.getString(R.color.md_deep_orange_50)),
                                             new PaletteColor(R.color.md_deep_orange_100, "deep orange", "100", res.getString(R.color.md_deep_orange_100)),
                                             new PaletteColor(R.color.md_deep_orange_200, "deep orange", "200", res.getString(R.color.md_deep_orange_200)),
                                             new PaletteColor(R.color.md_deep_orange_300, "deep orange", "300", res.getString(R.color.md_deep_orange_300)),
                                             new PaletteColor(R.color.md_deep_orange_400, "deep orange", "400", res.getString(R.color.md_deep_orange_400)),
                                             new PaletteColor(R.color.md_deep_orange_600, "deep orange", "600", res.getString(R.color.md_deep_orange_600)),
                                             new PaletteColor(R.color.md_deep_orange_700, "deep orange", "700", res.getString(R.color.md_deep_orange_700)),
                                             new PaletteColor(R.color.md_deep_orange_800, "deep orange", "800", res.getString(R.color.md_deep_orange_800)),
                                             new PaletteColor(R.color.md_deep_orange_900, "deep orange", "900", res.getString(R.color.md_deep_orange_900)),
                                             new PaletteColor(R.color.md_deep_orange_A100, "deep orange", "A100", res.getString(R.color.md_deep_orange_A100)),
                                             new PaletteColor(R.color.md_deep_orange_A200, "deep orange", "A200", res.getString(R.color.md_deep_orange_A200)),
                                             new PaletteColor(R.color.md_deep_orange_A400, "deep orange", "A400", res.getString(R.color.md_deep_orange_A400)),
                                             new PaletteColor(R.color.md_deep_orange_A700, "deep orange", "A700", res.getString(R.color.md_deep_orange_A700)));
        }
        return deepOrangePaletteColors;
    }

    public List<PaletteColor> getBrownPaletteColors() {
        if (brownPaletteColors == null) {
            Resources res = mContext.getResources();
            brownPaletteColors = Arrays.asList(new PaletteColor(R.color.md_brown_500, "brown", "500", res.getString(R.color.md_brown_500)),
                                             new PaletteColor(R.color.md_brown_50, "brown", "50", res.getString(R.color.md_brown_50)),
                                             new PaletteColor(R.color.md_brown_100, "brown", "100", res.getString(R.color.md_brown_100)),
                                             new PaletteColor(R.color.md_brown_200, "brown", "200", res.getString(R.color.md_brown_200)),
                                             new PaletteColor(R.color.md_brown_300, "brown", "300", res.getString(R.color.md_brown_300)),
                                             new PaletteColor(R.color.md_brown_400, "brown", "400", res.getString(R.color.md_brown_400)),
                                             new PaletteColor(R.color.md_brown_600, "brown", "600", res.getString(R.color.md_brown_600)),
                                             new PaletteColor(R.color.md_brown_700, "brown", "700", res.getString(R.color.md_brown_700)),
                                             new PaletteColor(R.color.md_brown_800, "brown", "800", res.getString(R.color.md_brown_800)),
                                             new PaletteColor(R.color.md_brown_900, "brown", "900", res.getString(R.color.md_brown_900)));
        }
        return brownPaletteColors;
    }

    public List<PaletteColor> getGreyPaletteColors() {
        if (greyPaletteColors == null) {
            Resources res = mContext.getResources();
            greyPaletteColors = Arrays.asList(new PaletteColor(R.color.md_grey_500, "grey", "500", res.getString(R.color.md_grey_500)),
                                             new PaletteColor(R.color.md_grey_50, "grey", "50", res.getString(R.color.md_grey_50)),
                                             new PaletteColor(R.color.md_grey_100, "grey", "100", res.getString(R.color.md_grey_100)),
                                             new PaletteColor(R.color.md_grey_200, "grey", "200", res.getString(R.color.md_grey_200)),
                                             new PaletteColor(R.color.md_grey_300, "grey", "300", res.getString(R.color.md_grey_300)),
                                             new PaletteColor(R.color.md_grey_400, "grey", "400", res.getString(R.color.md_grey_400)),
                                             new PaletteColor(R.color.md_grey_600, "grey", "600", res.getString(R.color.md_grey_600)),
                                             new PaletteColor(R.color.md_grey_700, "grey", "700", res.getString(R.color.md_grey_700)),
                                             new PaletteColor(R.color.md_grey_800, "grey", "800", res.getString(R.color.md_grey_800)),
                                             new PaletteColor(R.color.md_grey_900, "grey", "900", res.getString(R.color.md_grey_900)),
                                             new PaletteColor(R.color.md_black_1000, "grey", "1000", res.getString(R.color.md_black_1000)),
                                             new PaletteColor(R.color.md_white_1000, "grey", "1000", res.getString(R.color.md_white_1000)));
        }
        return greyPaletteColors;
    }

    public List<PaletteColor> getBlueGreyPaletteColors() {
        if (blueGreyPaletteColors == null) {
            Resources res = mContext.getResources();
            blueGreyPaletteColors = Arrays.asList(new PaletteColor(R.color.md_blue_grey_500, "blue grey", "500", res.getString(R.color.md_blue_grey_500)),
                                             new PaletteColor(R.color.md_blue_grey_50, "blue grey", "50", res.getString(R.color.md_blue_grey_50)),
                                             new PaletteColor(R.color.md_blue_grey_100, "blue grey", "100", res.getString(R.color.md_blue_grey_100)),
                                             new PaletteColor(R.color.md_blue_grey_200, "blue grey", "200", res.getString(R.color.md_blue_grey_200)),
                                             new PaletteColor(R.color.md_blue_grey_300, "blue grey", "300", res.getString(R.color.md_blue_grey_300)),
                                             new PaletteColor(R.color.md_blue_grey_400, "blue grey", "400", res.getString(R.color.md_blue_grey_400)),
                                             new PaletteColor(R.color.md_blue_grey_600, "blue grey", "600", res.getString(R.color.md_blue_grey_600)),
                                             new PaletteColor(R.color.md_blue_grey_700, "blue grey", "700", res.getString(R.color.md_blue_grey_700)),
                                             new PaletteColor(R.color.md_blue_grey_800, "blue grey", "800", res.getString(R.color.md_blue_grey_800)),
                                             new PaletteColor(R.color.md_blue_grey_900, "blue grey", "900", res.getString(R.color.md_blue_grey_900)));
        }
        return blueGreyPaletteColors;
    }


    /**
     * @param position the position of the section whose we need the name
     * @return the name, as a resource id
     */
    public static int getSectionNameAtPosition(int position) {
        int sectionId = -1;
        switch (position) {
            case 0:
                sectionId = R.string.red;
                break;
            case 1:
                sectionId = R.string.pink;
                break;
            case 2:
                sectionId = R.string.purple;
                break;
            case 3:
                sectionId = R.string.deep_purple;
                break;
            case 4:
                sectionId = R.string.indigo;
                break;
            case 5:
                sectionId = R.string.blue;
                break;
            case 6:
                sectionId = R.string.light_blue;
                break;
            case 7:
                sectionId = R.string.cyan;
                break;
            case 8:
                sectionId = R.string.teal;
                break;
            case 9:
                sectionId = R.string.green;
                break;
            case 10:
                sectionId = R.string.light_green;
                break;
            case 11:
                sectionId = R.string.lime;
                break;
            case 12:
                sectionId = R.string.yellow;
                break;
            case 13:
                sectionId = R.string.amber;
                break;
            case 14:
                sectionId = R.string.orange;
                break;
            case 15:
                sectionId = R.string.deep_orange;
                break;
            case 16:
                sectionId = R.string.brown;
                break;
            case 17:
                sectionId = R.string.grey;
                break;
            case 18:
                sectionId = R.string.blue_grey;
                break;
            default:
                break;
        }
        return sectionId;
    }
}

package fr.hozakan.materialdesigncolorpalette.utils;

import android.graphics.Color;

/**
 * Created by gimbert on 14-11-22.
 */
public class ColorUtils {

    public static boolean isColorLight(int hex) {
        // convert hex string to int
        int rgb = Integer.parseInt(String.format("%d", hex), 16);

        int red = Color.red(hex);
        int green = Color.green(hex);
        int blue = Color.blue(hex);

        float[] hsb = new float[3];
        Color.colorToHSV(hex, hsb);
//        float[] hsb = Color.RGBtoHSB(red, green, blue, null);

        float brightness = hsb[2];

        return hsb[2] > 0.5;
    }

}

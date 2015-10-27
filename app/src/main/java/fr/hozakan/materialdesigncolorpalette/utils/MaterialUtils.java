package fr.hozakan.materialdesigncolorpalette.utils;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;

import fr.hozakan.materialdesigncolorpalette.R;

/**
 * Created by gimbert on 14-12-25.
 */
public class MaterialUtils {

    public static void applyBackgroundColor(int hex, View coloredZone, Drawable coloredBackground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            applyMaterialBackgroundColor(hex, coloredBackground);
        } else {
            applySimpleBackgroundColor(hex, coloredZone);
        }
    }

    private static void applySimpleBackgroundColor(int hex, View coloredZone) {
        coloredZone.setBackgroundColor(hex);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void applyMaterialBackgroundColor(int hex, Drawable coloredBackground) {
        RippleDrawable rd = (RippleDrawable) coloredBackground;
        ColorDrawable cd = new ColorDrawable(hex);
        rd.setDrawableByLayerId(R.id.color, cd);
    }

}

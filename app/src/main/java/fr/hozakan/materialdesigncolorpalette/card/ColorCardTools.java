package fr.hozakan.materialdesigncolorpalette.card;

import android.content.Context;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;

/**
 * Created by gimbert on 2014-09-15.
 */
public class ColorCardTools {

    public static List<ColorCard> getColorCardList(Context context, Bus bus, String parentName,
                                                   List<PaletteColor> paletteColors) {
        final List<ColorCard> cardList = new ArrayList<ColorCard>(paletteColors.size());
        for (PaletteColor paletteColor : paletteColors) {
            cardList.add(new ColorCard(context, bus, parentName, paletteColor));
        }
        return cardList;
    }

    public static List<PreviewColorCard> getColorCardList(Context context, Bus bus,
                                                   List<PaletteColor> paletteColors) {
        final List<PreviewColorCard> cardList = new ArrayList<PreviewColorCard>(paletteColors.size());
        for (PaletteColor paletteColor : paletteColors) {
            cardList.add(new PreviewColorCard(context, bus, paletteColor.getBaseName(), paletteColor));
        }
        return cardList;
    }
}

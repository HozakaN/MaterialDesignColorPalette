package fr.hozakan.materialdesigncolorpalette.card;

import android.content.Context;

import com.squareup.otto.Bus;

import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by gimbert on 2014-09-15.
 */
public class PreviewColorCard extends ColorCard {

    public PreviewColorCard(Context context, Bus bus, String colorParentName, PaletteColor color) {
        super(context, bus, colorParentName, color);
    }

    @Override
    protected CardHeader initHeader() {
        final CardHeader header = new CardHeader(mContext);
        header.setTitle(String.format("%s %s", mColor.getColorSectionName(), mColor.getBaseName()));
        return header;
    }
}

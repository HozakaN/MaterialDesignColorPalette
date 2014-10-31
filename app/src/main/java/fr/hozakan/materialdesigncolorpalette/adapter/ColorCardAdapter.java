package fr.hozakan.materialdesigncolorpalette.adapter;

import android.content.Context;

import java.util.List;

import fr.hozakan.materialdesigncolorpalette.card.ColorCard;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;

/**
 * Created by gimbert on 2014-07-18.
 */
public class ColorCardAdapter<T extends Card> extends CardArrayAdapter {
    private final List<T> mCards;

    public ColorCardAdapter(Context context, List<T> cards) {
        super(context, (List<Card>) cards);
        mCards = cards;
    }

    public void updateColorCardElement(PaletteColor color) {
        ColorCard colorCard = null;
        for (T card : mCards) {
            ColorCard c = (ColorCard) card;
            if (c.getColorHex().equals(color.getHexString())) {
                c.setAppropriateMenu();
                break;
            }
        }
    }

    public ColorCard findItemByColor(PaletteColor color) {
        for (T card : mCards) {
            ColorCard c = (ColorCard) card;
            if (c.getColorHex().equals(color.getHexString())) {
                return c;
            }
        }
        return null;
    }
}

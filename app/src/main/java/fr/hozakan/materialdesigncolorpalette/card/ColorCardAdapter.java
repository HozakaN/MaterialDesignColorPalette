package fr.hozakan.materialdesigncolorpalette.card;

import android.content.Context;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;

/**
 * Created by gimbert on 2014-07-18.
 */
public class ColorCardAdapter<T extends Card> extends CardArrayAdapter {
    public ColorCardAdapter(Context context, List<T> cards) {
        super(context, (List<Card>) cards);
    }

}

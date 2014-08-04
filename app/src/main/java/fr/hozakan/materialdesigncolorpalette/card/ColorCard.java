package fr.hozakan.materialdesigncolorpalette.card;

import android.content.Context;
import android.view.MenuItem;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

/**
 * Created by gimbert on 2014-07-18.
 */
public class ColorCard extends Card {

    private final PaletteColor mColor;
    private final ColorCardCallback mCallback;

    public ColorCard(Context context, PaletteColor color, ColorCardCallback callback) {
        super(context);
        mColor = color;
        mCallback = callback;

        CardHeader header = new CardHeader(context);
        header.setTitle(mColor.getNameId());
        addCardHeader(header);

        setBackgroundResourceId(mColor.getColorId());
        setTitle(mColor.getHexaId());

        header.setPopupMenu(R.menu.color_card_popup_menu, new CardHeader.OnClickCardHeaderPopupMenuListener() {

            @Override
            public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {
                menuClicked(menuItem.getItemId());
            }

        });
    }

    private void menuClicked(int itemId) {
        switch(itemId) {
            case R.id.card_menu_copy_color:
                copyColorToClipboard();
                break;
            default:
                break;
        }
    }

    private void copyColorToClipboard() {
        if (mCallback != null) {
            mCallback.copyColorToClipboard(mColor.getParentColorName(), mColor.getNameId(), mColor.getHexaId());
        }
    }

    public interface ColorCardCallback {
        void copyColorToClipboard(String parentColorName, String colorName, String colorHexa);
    }

}

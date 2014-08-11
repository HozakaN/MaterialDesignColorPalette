package fr.hozakan.materialdesigncolorpalette.card;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.otto.CopyColorEvent;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

public class ColorCard extends Card {

    private final Context mContext;
    private final Bus mBus;
    private final String mColorParentName;
    private final PaletteColor mColor;

    private final CardHeader.OnClickCardHeaderPopupMenuListener mCardMenuClick
            = new CardHeader.OnClickCardHeaderPopupMenuListener() {
        @Override
        public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {
            switch(menuItem.getItemId()) {
                case R.id.card_menu_copy_color:
//                    copyColorToClipboard(mColorParentName, mColor.getBaseName(),
//                            mColor.getHexString());
                    mBus.post(new CopyColorEvent(mColorParentName, mColor));
                    break;
                default:
                    break;
            }
        }
    };

    public ColorCard(Context context, Bus bus, String colorParentName, PaletteColor color) {
        super(context);
        mContext = context;
        mBus = bus;
        mColorParentName = colorParentName;
        mColor = color;

        final CardHeader header = new CardHeader(context);
        header.setTitle(mColor.getBaseName());
        addCardHeader(header);

        setBackgroundResource(new ColorDrawable(mColor.getHex()));
        setTitle(mColor.getHexString());

        header.setPopupMenu(R.menu.color_card_popup_menu, mCardMenuClick);
    }

    public void copyColorToClipboard(String parentColorName, String colorBaseName, String colorHex) {
        final ClipboardManager clipboard
                = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipData clip
                = ClipData.newPlainText(mContext.getString(R.string.color_clipboard, parentColorName, colorBaseName), colorHex);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(mContext, mContext.getString(R.string.color_copied, colorHex),
                Toast.LENGTH_SHORT).show();
    }

    public static List<ColorCard> getColorCardList(Context context, Bus bus, String parentName,
                                                   List<PaletteColor> paletteColors) {
        final List<ColorCard> cardList = new ArrayList<ColorCard>(paletteColors.size());
        for (PaletteColor paletteColor : paletteColors) {
            cardList.add(new ColorCard(context, bus, parentName, paletteColor));
        }
        return cardList;
    }
}

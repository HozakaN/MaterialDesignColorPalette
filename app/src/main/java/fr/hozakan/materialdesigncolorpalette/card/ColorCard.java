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
import fr.hozakan.materialdesigncolorpalette.otto.AddActionbarPreviewColorEvent;
import fr.hozakan.materialdesigncolorpalette.otto.AddPreviewColorEvent;
import fr.hozakan.materialdesigncolorpalette.otto.CopyColorEvent;
import fr.hozakan.materialdesigncolorpalette.otto.RemoveActionbarPreviewColorEvent;
import fr.hozakan.materialdesigncolorpalette.otto.RemovePreviewColorEvent;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardView;

public class ColorCard extends Card {

    protected final Context mContext;
    private final Bus mBus;
    private final String mColorParentName;
    protected PaletteColor mColor;

    private final CardHeader.OnClickCardHeaderPopupMenuListener mCardMenuClick
            = new CardHeader.OnClickCardHeaderPopupMenuListener() {
        @Override
        public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {
            switch(menuItem.getItemId()) {
                case R.id.card_menu_copy_color:
                    mBus.post(new CopyColorEvent(mColorParentName, mColor));
                    break;
                case R.id.card_menu_actionbar_color_preview:
                    mBus.post(new AddActionbarPreviewColorEvent(mColor));
                    mColor.setActionBarPreviewColor(true);
                    setAppropriateMenu();
                    break;
                case R.id.card_menu_color_preview:
                    mBus.post(new AddPreviewColorEvent(mColor));
                    mColor.setPreviewColor(true);
                    setAppropriateMenu();
                    break;
                case R.id.card_menu_remove_actionbar_color_preview:
                    mBus.post(new RemoveActionbarPreviewColorEvent(mColor));
                    mColor.setActionBarPreviewColor(false);
                    setAppropriateMenu();
                    break;
                case R.id.card_menu_remove_color_preview:
                    mBus.post(new RemovePreviewColorEvent(mColor));
                    mColor.setPreviewColor(false);
                    setAppropriateMenu();
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

        init();
    }

    private void init() {
        addCardHeader(initHeader());

        setBackgroundResource(new ColorDrawable(mColor.getHex()));

        initTitle();

        setAppropriateMenu();
    }

    protected CardHeader initHeader() {
        final CardHeader header = new CardHeader(mContext);
        header.setTitle(mColor.getBaseName());
        return header;
    }

    protected void initTitle() {
        setTitle(mColor.getHexString());
    }

    public void setAppropriateMenu() {
//        if (mColor.isActionBarPreviewColor()) {
//            if (mColor.isPreviewColor()) {
//                getCardHeader().setPopupMenu(R.menu.color_card_popup_menu_both, mCardMenuClick);
//            } else {
//                getCardHeader().setPopupMenu(R.menu.color_card_popup_menu_actionbar_color_preview, mCardMenuClick);
//            }
//        } else if (mColor.isPreviewColor()) {
//            getCardHeader().setPopupMenu(R.menu.color_card_popup_menu_color_preview, mCardMenuClick);
//        } else {
//            getCardHeader().setPopupMenu(R.menu.color_card_popup_menu_unused, mCardMenuClick);
//        }
        getCardHeader().setPopupMenu(R.menu.color_card_popup_menu, mCardMenuClick);
    }

    public String getColorHex() {
        return mColor.getHexString();
    }

    public void setColor(PaletteColor color) {
        this.mColor = color;
    }
}

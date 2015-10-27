package fr.hozakan.materialdesigncolorpalette.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.utils.ColorUtils;
import fr.hozakan.materialdesigncolorpalette.utils.MaterialUtils;

/**
 * Created by gimbert on 14-11-10.
 */
public class ColorCardRecyclerAdapter extends RecyclerView.Adapter<ColorCardRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private final ColorCardRecyclerAdapterCallback mCallback;
    private final LayoutInflater mInflater;
    private List<PaletteColor> mCards;

    public ColorCardRecyclerAdapter(Context context, List<PaletteColor> mCards, ColorCardRecyclerAdapterCallback callback) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mCards = mCards;
        this.mCallback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.card_color, parent, false);
        ViewHolder holder = new ViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final PaletteColor paletteColor = mCards.get(position);

        MaterialUtils.applyBackgroundColor(paletteColor.getHex(), viewHolder.coloredZone, viewHolder.coloredBackground);
//        ColorDrawable drawable = new ColorDrawable(paletteColor.getHex());
//        drawable.setAlpha(100);
//        viewHolder.coloredZone.setBackgroundDrawable(drawable);
        viewHolder.title.setText(paletteColor.getBaseName());
        viewHolder.content.setText(paletteColor.getHexString());
        if (ColorUtils.isColorLight(paletteColor.getHex())) {
//            viewHolder.copyColor.setImageResource(R.drawable.ic_content_copy_black_24dp);
            viewHolder.primaryColor.setImageResource(R.drawable.button_primary_color_black_24dp);
            viewHolder.primaryDarkColor.setImageResource(R.drawable.button_primary_dark_color_black_24dp);
            viewHolder.accentColor.setImageResource(R.drawable.button_accent_color_black_24dp);
            viewHolder.title.setTextColor(mContext.getResources().getColor(R.color.color_card_title_dark_color));
            viewHolder.content.setTextColor(mContext.getResources().getColor(R.color.color_card_content_dark_color));
        } else {
//            viewHolder.copyColor.setImageResource(R.drawable.ic_content_copy_white_24dp);
            viewHolder.primaryColor.setImageResource(R.drawable.button_primary_color_white_24dp);
            viewHolder.primaryDarkColor.setImageResource(R.drawable.button_primary_dark_color_white_24dp);
            viewHolder.accentColor.setImageResource(R.drawable.button_accent_color_white_24dp);
            viewHolder.title.setTextColor(mContext.getResources().getColor(R.color.color_card_title_light_color));
            viewHolder.content.setTextColor(mContext.getResources().getColor(R.color.color_card_content_light_color));
        }
        viewHolder.primaryColor.setSelected(paletteColor.isPrimaryColor());
        viewHolder.primaryDarkColor.setSelected(paletteColor.isPrimaryDarkColor());
        viewHolder.accentColor.setSelected(paletteColor.isAccentColor());
        viewHolder.coloredZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.onCopyColorClicked(paletteColor);
                }
            }
        });

        final int pos = position;
        viewHolder.primaryColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                onPrimaryColorClicked(pos);
            }
        });
        viewHolder.primaryDarkColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                onPrimaryDarkColorClicked(pos);
            }
        });
        viewHolder.accentColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                onAccentColorClicked(pos);
            }
        });
    }

    private void onPrimaryColorClicked(int position) {
        if (mCallback != null) {
            mCallback.onPrimaryColorClicked(mCards.get(position), position);
        }
    }

    private void onPrimaryDarkColorClicked(int position) {
        if (mCallback != null) {
            mCallback.onPrimaryDarkColorClicked(mCards.get(position), position);
        }
    }

    private void onAccentColorClicked(int position) {
        if (mCallback != null) {
            mCallback.onAccentColorClicked(mCards.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    public void refreshPaletteColor(PaletteColor paletteColor, int position) {
        final PaletteColor color = mCards.get(position);
        if (color != null) {
            color.setPrimaryColor(paletteColor.isPrimaryColor());
            color.setPrimaryDarkColor(paletteColor.isPrimaryDarkColor());
            color.setAccentColor(paletteColor.isAccentColor());
            notifyItemChanged(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View coloredZone;
        public final TextView title;
        public final TextView content;
//        public final ImageButton copyColor;
        public final ImageButton primaryColor;
        public final ImageButton primaryDarkColor;
        public final ImageButton accentColor;
        public final Drawable coloredBackground;
//        public final View selectableZone;

        public ViewHolder(View itemView) {
            super(itemView);
            coloredZone = itemView.findViewById(R.id.card_view);
            title = (TextView) itemView.findViewById(R.id.color_title);
            content = (TextView) itemView.findViewById(R.id.color_content);
//            copyColor = (ImageButton) itemView.findViewById(R.id.color_copy);
            primaryColor = (ImageButton) itemView.findViewById(R.id.primary_color);
            primaryDarkColor = (ImageButton) itemView.findViewById(R.id.primary_dark_color);
            accentColor = (ImageButton) itemView.findViewById(R.id.accent_color);
//            selectableZone = itemView.findViewById(R.id.selectable_zone);
            coloredBackground = coloredZone.getBackground();
        }
    }

    public interface ColorCardRecyclerAdapterCallback {
        public void onCopyColorClicked(PaletteColor paletteColor);
        public void onPrimaryColorClicked(PaletteColor paletteColor, int position);
        public void onPrimaryDarkColorClicked(PaletteColor paletteColor, int position);
        public void onAccentColorClicked(PaletteColor paletteColor, int position);
    }

}

package fr.hozakan.materialdesigncolorpalette.adapter;

import android.content.Context;
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
        viewHolder.coloredZone.setBackgroundColor(paletteColor.getHex());
        viewHolder.title.setText(paletteColor.getBaseName());
        viewHolder.content.setText(paletteColor.getHexString());
        if (ColorUtils.isColorLight(paletteColor.getHex())) {
            viewHolder.copyColor.setImageResource(R.drawable.ic_content_copy_black_24dp);
            viewHolder.title.setTextColor(mContext.getResources().getColor(R.color.color_card_title_dark_color));
            viewHolder.content.setTextColor(mContext.getResources().getColor(R.color.color_card_content_dark_color));
//            viewHolder.separator.setBackgroundResource(R.color.color_car_separator_dark_color);
        } else {
            viewHolder.copyColor.setImageResource(R.drawable.ic_content_copy_white_24dp);
            viewHolder.title.setTextColor(mContext.getResources().getColor(R.color.color_card_title_light_color));
            viewHolder.content.setTextColor(mContext.getResources().getColor(R.color.color_card_content_light_color));
//            viewHolder.separator.setBackgroundResource(R.color.color_car_separator_light_color);
        }
//        viewHolder.pc.setText(paletteColor.isPrimaryColor() ? "true" : "false");
        viewHolder.copyColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.onCopyColorClicked(paletteColor);
//                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View coloredZone;
        public final TextView title;
        public final TextView content;
//        public final TextView pc;
        public final ImageButton copyColor;
//        public final View separator;

        public ViewHolder(View itemView) {
            super(itemView);
            coloredZone = itemView.findViewById(R.id.card_view);
            title = (TextView) itemView.findViewById(R.id.color_title);
            content = (TextView) itemView.findViewById(R.id.color_content);
//            pc = (TextView) itemView.findViewById(R.id.is_primary);
            copyColor = (ImageButton) itemView.findViewById(R.id.color_copy);
//            separator = itemView.findViewById(R.id.actions_separator);
        }
    }

    public interface ColorCardRecyclerAdapterCallback {
        public void onPrimaryColorClicked(PaletteColor paletteColor);
        public void onCopyColorClicked(PaletteColor paletteColor);
    }

}

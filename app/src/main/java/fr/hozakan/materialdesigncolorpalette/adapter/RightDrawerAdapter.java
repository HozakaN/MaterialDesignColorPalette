package fr.hozakan.materialdesigncolorpalette.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.service.PaletteService;
import fr.hozakan.materialdesigncolorpalette.utils.ColorUtils;

/**
 * Created by gimbert on 14-12-26.
 */
public class RightDrawerAdapter extends RecyclerView.Adapter<RightDrawerAdapter.ViewHolder> {

    public interface PreviewColorClickListener {
        public void onPreviewColorClicked(PaletteColor paletteColor);
    }

    private final PreviewColorClickListener mListener;
    private final LayoutInflater mInflater;
    private Context mContext;
    private SparseArray<PaletteColor> mColors = null;

    public RightDrawerAdapter(Context context, SparseArray<PaletteColor> colors, PreviewColorClickListener listener) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        mColors = colors;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.preview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int key = mColors.keyAt(position);
        final PaletteColor color = mColors.get(key);

        switch (key) {
            case PaletteService.SPARSE_PRIMARY_COLOR_KEY:
                holder.colorCircle.setText(R.string.primary_color_initial);
                break;
            case PaletteService.SPARSE_PRIMARY_DARK_COLOR_KEY:
                holder.colorCircle.setText(R.string.primary_dark_color_initial);
                break;
            case PaletteService.SPARSE_ACCENT_COLOR_KEY:
                holder.colorCircle.setText(R.string.accent_color_initial);
                break;
        }
        if (ColorUtils.isColorLight(color.getHex())) {
            holder.colorCircle.setTextColor(mContext.getResources().getColor(R.color.color_card_title_dark_color));
        } else {
            holder.colorCircle.setTextColor(mContext.getResources().getColor(R.color.color_card_title_light_color));
        }
        holder.colorCircle.getBackground().setColorFilter(color.getHex(), PorterDuff.Mode.SRC_ATOP);
        holder.colorText1.setText(String.format("%s %s", color.getColorSectionName(), color.getBaseName()));
        holder.colorText2.setText(color.getHexString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onPreviewColorClicked(color);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mColors.size();
    }

    public void colorRemoved(int sparseKey) {
        int index = mColors.indexOfKey(sparseKey);
        if (index >= 0) {
            mColors.remove(sparseKey);
            notifyItemRemoved(index);
        }
    }

    public void colorAdded(int sparseKey, PaletteColor color) {
        int index = mColors.indexOfKey(sparseKey);
        mColors.put(sparseKey, color);
        if (index >= 0) {
            notifyItemChanged(index);
        } else {
            notifyItemInserted(mColors.indexOfKey(sparseKey));
        }
    }

    public void colorChanged(int sparseKey, PaletteColor color) {
        colorAdded(sparseKey, color);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView colorCircle;
        public final TextView colorText1;
        public final TextView colorText2;

        public ViewHolder(View itemView) {
            super(itemView);
            colorCircle = (TextView) itemView.findViewById(R.id.color_circle);
            colorText1 = (TextView) itemView.findViewById(R.id.color_text_1);
            colorText2 = (TextView) itemView.findViewById(R.id.color_text_2);
        }
    }
}

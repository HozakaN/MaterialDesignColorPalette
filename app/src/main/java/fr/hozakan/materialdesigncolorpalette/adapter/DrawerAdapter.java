package fr.hozakan.materialdesigncolorpalette.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;

public class DrawerAdapter extends ArrayAdapter<PaletteColorSection> {

    private static final int LAYOUT_RESOURCE = R.layout.nav_item;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<PaletteColorSection> mColorList;

    public DrawerAdapter(Context context, List<PaletteColorSection> colorList) {
        super(context, LAYOUT_RESOURCE, colorList);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mColorList = colorList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(LAYOUT_RESOURCE, parent, false);
            holder = new ViewHolder((TextView) convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final PaletteColorSection paletteColorSection = mColorList.get(position);
        final String colorName = paletteColorSection.getColorSectionName();
        holder.textView.setText(colorName);
        final StateListDrawable sld = new StateListDrawable();
        final Drawable d = new ColorDrawable(paletteColorSection.getColorSectionValue());
        sld.addState(new int[] { android.R.attr.state_pressed }, d);
        sld.addState(new int[] { android.R.attr.state_checked }, d);
        holder.textView.setBackground(sld);
        return convertView;
    }

    private static final class ViewHolder {
        private final TextView textView;
        private ViewHolder(TextView textView) {
            this.textView = textView;
        }
    }
}

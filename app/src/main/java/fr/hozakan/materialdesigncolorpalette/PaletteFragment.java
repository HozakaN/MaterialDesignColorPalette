package fr.hozakan.materialdesigncolorpalette;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import fr.hozakan.materialdesigncolorpalette.model.ColorCard;
import fr.hozakan.materialdesigncolorpalette.adapter.ColorCardAdapter;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;

public class PaletteFragment extends Fragment {

    public static final String ARG_COLOR_SECTION = "COLOR_SECTION";

    private ListView mListView;
    private CardArrayAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mListView = (ListView) inflater.inflate(R.layout.fragment_color_palette, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final PaletteColorSection paletteColorSection = getArguments().getParcelable(ARG_COLOR_SECTION);
        final List<ColorCard> colorCardList = ColorCard.getColorCardList(getActivity(),
                paletteColorSection.getColorSectionName(),
                paletteColorSection.getPaletteColorList());
        mAdapter = new ColorCardAdapter<ColorCard>(getActivity(), colorCardList);
        mListView.setAdapter(mAdapter);
    }
}

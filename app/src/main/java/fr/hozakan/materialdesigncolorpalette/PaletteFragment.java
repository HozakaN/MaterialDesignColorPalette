package fr.hozakan.materialdesigncolorpalette;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import fr.hozakan.materialdesigncolorpalette.card.ColorCard;
import fr.hozakan.materialdesigncolorpalette.adapter.ColorCardAdapter;
import fr.hozakan.materialdesigncolorpalette.dagger.BaseApplication;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;

public class PaletteFragment extends Fragment {

    public static final String ARG_COLOR_SECTION = "COLOR_SECTION";

    private static final String SECTION_KEY = "SECTION_KEY";
    private static final int SCROLL_TO_TOP_MILLIS = 300;

    private ListView mListView;
    private CardArrayAdapter mAdapter;
    private PaletteColorSection mPaletteColorSection = null;

    @Inject
    protected Bus mBus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mListView = (ListView) inflater.inflate(R.layout.fragment_color_palette, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TAAAAAAAG", "DEBUUUUUUG");
        if (savedInstanceState != null) {
            mPaletteColorSection = savedInstanceState.getParcelable(SECTION_KEY);
        }
        if (mPaletteColorSection == null) {
            mPaletteColorSection = getArguments().getParcelable(ARG_COLOR_SECTION);
        }
        final List<ColorCard> colorCardList = getColorCardList();
        mAdapter = new ColorCardAdapter<ColorCard>(getActivity(), colorCardList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SECTION_KEY, mPaletteColorSection);
        super.onSaveInstanceState(outState);
    }

    public void replaceColorCardList(PaletteColorSection paletteColorSection) {
        mPaletteColorSection = paletteColorSection;
        mAdapter.clear();
        mAdapter.addAll(getColorCardList());
        mAdapter.notifyDataSetChanged();
        if (mAdapter.getCount() > 0) {
            mListView.setSelection(0);
        }
    }

    public void scrollToTop() {
        mListView.smoothScrollToPositionFromTop(0, 0, SCROLL_TO_TOP_MILLIS);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseApplication)activity.getApplication()).inject(this);
    }

    private List<ColorCard> getColorCardList() {
        return ColorCard.getColorCardList(getActivity().getApplicationContext(),
                mBus,
                mPaletteColorSection.getColorSectionName(),
                mPaletteColorSection.getPaletteColorList());
    }
}

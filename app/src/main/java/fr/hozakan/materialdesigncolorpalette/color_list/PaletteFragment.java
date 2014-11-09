package fr.hozakan.materialdesigncolorpalette.color_list;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.adapter.ColorCardAdapter;
import fr.hozakan.materialdesigncolorpalette.card.ColorCard;
import fr.hozakan.materialdesigncolorpalette.card.ColorCardTools;
import fr.hozakan.materialdesigncolorpalette.dagger.BaseApplication;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;
import fr.hozakan.materialdesigncolorpalette.service.PaletteService;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class PaletteFragment extends Fragment {

    public static final String ARG_COLOR_SECTION = "COLOR_SECTION";

    private static final String SECTION_KEY = "SECTION_KEY";
    private static final int SCROLL_TO_TOP_MILLIS = 300;

    private CardRecyclerView mListView;
    private CardArrayRecyclerViewAdapter mAdapter;
    private PaletteColorSection mPaletteColorSection = null;

    @Inject
    protected Bus mBus;

    @Inject
    protected PaletteService mService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return mListView = (CardRecyclerView) inflater.inflate(R.layout.fragment_color_palette, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.palette_color_menu, menu);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            mPaletteColorSection = savedInstanceState.getParcelable(SECTION_KEY);
        }
        if (mPaletteColorSection == null) {
            mPaletteColorSection = getArguments().getParcelable(ARG_COLOR_SECTION);
        }
        final List<ColorCard> colorCardList = getColorCardList();
        mAdapter = new ColorCardAdapter<ColorCard>(getActivity(), colorCardList);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        if (mAdapter.getItemCount() > 0) {
//            mListView.setSelection(0);
        }
    }

    public void scrollToTop() {
//        mListView.smoothScrollToPositionFromTop(0, 0, SCROLL_TO_TOP_MILLIS);
        mListView.smoothScrollToPosition(0);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseApplication)activity.getApplication()).inject(this);
    }

    private List<ColorCard> getColorCardList() {
        return ColorCardTools.getColorCardList(getActivity().getApplicationContext(),
                mBus,
                mPaletteColorSection.getColorSectionName(),
                mPaletteColorSection.getPaletteColorList());
    }

    public void updateColorCardElement(PaletteColor color) {
        ColorCard card = ((ColorCardAdapter)mListView.getAdapter()).findItemByColor(color);
        if (card != null) {
            card.setColor(color);
            card.setAppropriateMenu();
            ((ColorCardAdapter) mListView.getAdapter()).notifyDataSetChanged();
        }
    }

//    public void updateColorCardElement(PaletteColor color) {
//        ColorCard card = ((ColorCardAdapter)mListView.getAdapter()).findItemByColor(color);
//        if (card != null) {
//            card.setAppropriateMenu();
//        }
//    }
}

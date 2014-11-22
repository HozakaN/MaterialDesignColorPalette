package fr.hozakan.materialdesigncolorpalette.color_list;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.adapter.ColorCardRecyclerAdapter;
import fr.hozakan.materialdesigncolorpalette.dagger.BaseApplication;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;
import fr.hozakan.materialdesigncolorpalette.service.PaletteService;

public class PaletteFragment extends Fragment implements ColorCardRecyclerAdapter.ColorCardRecyclerAdapterCallback {

    public static final String ARG_COLOR_SECTION = "COLOR_SECTION";

    private static final String SECTION_KEY = "SECTION_KEY";
    private static final int SCROLL_TO_TOP_MILLIS = 300;

    private RecyclerView mListView;
    private ColorCardRecyclerAdapter mRecyclerAdapter;
    private PaletteColorSection mPaletteColorSection = null;

    @Inject
    protected Bus mBus;

    @Inject
    protected PaletteService mService;

    @Inject
    protected ClipboardManager mClipboardManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_color_palette, container, false);
        mListView = (RecyclerView) v;
        return mListView;
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
//        final List<ColorCard> colorCardList = getColorCardList();

        mRecyclerAdapter = new ColorCardRecyclerAdapter(getActivity(), mPaletteColorSection.getPaletteColorList(), this);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SECTION_KEY, mPaletteColorSection);
        super.onSaveInstanceState(outState);
    }

    public void replaceColorCardList(PaletteColorSection paletteColorSection) {
        mPaletteColorSection = paletteColorSection;
//        mAdapter.clear();
//        mAdapter.addAll(getColorCardList());
//        mAdapter.notifyDataSetChanged();
//        if (mAdapter.getItemCount() > 0) {
////            mListView.setSelection(0);
//        }
        mRecyclerAdapter = new ColorCardRecyclerAdapter(getActivity(),
                mPaletteColorSection.getPaletteColorList(), this);
        mListView.setAdapter(mRecyclerAdapter);
//        if (mAdapter.getItemCount() > 0) {
//            mListView.setSelection(0);
//        }
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

//    private List<ColorCard> getColorCardList() {
//        return ColorCardTools.getColorCardList(getActivity().getApplicationContext(),
//                mBus,
//                mPaletteColorSection.getColorSectionName(),
//                mPaletteColorSection.getPaletteColorList());
//    }

//    public void updateColorCardElement(PaletteColor color) {
//        ColorCard card = ((ColorCardAdapter)mListView.getAdapter()).findItemByColor(color);
//        if (card != null) {
//            card.setColor(color);
//            card.setAppropriateMenu();
//            ((ColorCardAdapter) mListView.getAdapter()).notifyDataSetChanged();
//        }
//    }

    @Override
    public void onPrimaryColorClicked(PaletteColor paletteColor) {
        paletteColor.setPrimaryColor(mService.setPrimaryColor(paletteColor));
//        mRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCopyColorClicked(PaletteColor paletteColor) {
        final ClipData clip
                = ClipData.newPlainText(getString(R.string.color_clipboard, paletteColor.getColorSectionName(),
                paletteColor.getBaseName()), paletteColor.getHexString());
        mClipboardManager.setPrimaryClip(clip);

        Toast.makeText(getActivity(), getString(R.string.color_copied, paletteColor.getHexString()),
                Toast.LENGTH_SHORT).show();
    }

//    public void updateColorCardElement(PaletteColor color) {
//        ColorCard card = ((ColorCardAdapter)mListView.getAdapter()).findItemByColor(color);
//        if (card != null) {
//            card.setAppropriateMenu();
//        }
//    }
}

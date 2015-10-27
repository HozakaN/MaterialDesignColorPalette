package fr.hozakan.materialdesigncolorpalette.color_list;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.adapter.ColorCardRecyclerAdapter;
import fr.hozakan.materialdesigncolorpalette.dagger.BaseApplication;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;
import fr.hozakan.materialdesigncolorpalette.service.PaletteService;
import fr.hozakan.materialdesigncolorpalette.utils.PaletteColorUtils;

public class PaletteFragment extends Fragment implements ColorCardRecyclerAdapter.ColorCardRecyclerAdapterCallback {

    public interface PaletteFragmentCallback {
        void onPrimaryColorRemoved(PaletteColor color);
        void onPrimaryDarkColorRemoved(PaletteColor color);
        void onAccentColorRemoved(PaletteColor color);
        void onPrimaryColorAdded(PaletteColor color);
        void onPrimaryDarkColorAdded(PaletteColor color);
        void onAccentColorAdded(PaletteColor color);
        void onPrimaryColorChanged(PaletteColor oldPrimaryColor, PaletteColor newPrimaryColor);
        void onPrimaryDarkColorChanged(PaletteColor oldPrimaryDarkColor, PaletteColor newPrimaryDarkColor);
        void onAccentColorChanged(PaletteColor oldAccentColor, PaletteColor newAccentColor);
    }

    private static final String TAG = PaletteFragment.class.getSimpleName();
    private PaletteFragmentCallback mCallback;

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

    public void replaceColorCardList(PaletteColorSection paletteColorSection, int colorIndex) {
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
        if (colorIndex >= 0) {
            ((LinearLayoutManager)mListView.getLayoutManager()).scrollToPositionWithOffset(colorIndex, 0);
        }
    }

    public void scrollToTop(int colorIndex) {
//        mListView.smoothScrollToPositionFromTop(0, 0, SCROLL_TO_TOP_MILLIS);
        if (colorIndex < 0) {
            colorIndex = 0;
        }
        mListView.smoothScrollToPosition(colorIndex);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseApplication)activity.getApplication()).inject(this);

        try {
            mCallback = (PaletteFragmentCallback) activity;
        } catch(ClassCastException e) {
            final String message = String.format("%s should implement %s", activity.getClass().getSimpleName(),
                    PaletteFragmentCallback.class.getSimpleName());
            Log.e(TAG, message);
            throw new IllegalArgumentException(message, e);
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
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
    public void onPrimaryColorClicked(PaletteColor paletteColor, int position) {
        mService.setPrimaryColor(paletteColor, new PaletteService.SetPrimaryColorCallback() {
            @Override
            public void onPrimaryColorRemoved(final PaletteColor oldPrimaryColor) {
                refreshPaletteColor(oldPrimaryColor);
                if (mCallback != null) {
                    mCallback.onPrimaryColorRemoved(oldPrimaryColor);
                }
            }

            @Override
            public void onPrimaryColorAdded(final PaletteColor newPrimaryColor) {
                refreshPaletteColor(newPrimaryColor);
                if (mCallback != null) {
                    mCallback.onPrimaryColorAdded(newPrimaryColor);
                }
            }

            @Override
            public void onPrimaryColorChanged(PaletteColor oldPrimaryColor, PaletteColor newPrimaryColor) {
                refreshPaletteColor(oldPrimaryColor);
                refreshPaletteColor(newPrimaryColor);
                if (mCallback != null) {
                    mCallback.onPrimaryColorChanged(oldPrimaryColor, newPrimaryColor);
                }
            }
        });
    }

    @Override
    public void onPrimaryDarkColorClicked(PaletteColor paletteColor, int position) {
        mService.setPrimaryDarkColor(paletteColor, new PaletteService.SetPrimaryDarkColorCallback() {
            @Override
            public void onPrimaryDarkColorRemoved(final PaletteColor oldPrimaryDarkColor) {
                refreshPaletteColor(oldPrimaryDarkColor);
                if (mCallback != null) {
                    mCallback.onPrimaryDarkColorRemoved(oldPrimaryDarkColor);
                }
            }

            @Override
            public void onPrimaryDarkColorAdded(final PaletteColor newPrimaryDarkColor) {
                refreshPaletteColor(newPrimaryDarkColor);
                if (mCallback != null) {
                    mCallback.onPrimaryDarkColorAdded(newPrimaryDarkColor);
                }
            }

            @Override
            public void onPrimaryDarkColorChanged(PaletteColor oldPrimaryDarkColor, PaletteColor newPrimaryDarkColor) {
                refreshPaletteColor(oldPrimaryDarkColor);
                refreshPaletteColor(newPrimaryDarkColor);
                if (mCallback != null) {
                    mCallback.onPrimaryDarkColorChanged(oldPrimaryDarkColor, newPrimaryDarkColor);
                }
            }
        });
    }

    @Override
    public void onAccentColorClicked(PaletteColor paletteColor, int position) {
        mService.setAccentColor(paletteColor, new PaletteService.SetAccentColorCallback() {
            @Override
            public void onAccentColorRemoved(final PaletteColor oldAccentColor) {
                refreshPaletteColor(oldAccentColor);
                if (mCallback != null) {
                    mCallback.onAccentColorRemoved(oldAccentColor);
                }
            }

            @Override
            public void onAccentColorAdded(final PaletteColor newAccentColor) {
                refreshPaletteColor(newAccentColor);
                if (mCallback != null) {
                    mCallback.onAccentColorAdded(newAccentColor);
                }
            }

            @Override
            public void onAccentColorChanged(PaletteColor oldAccentColor, PaletteColor newAccentColor) {
                refreshPaletteColor(oldAccentColor);
                refreshPaletteColor(newAccentColor);
                if (mCallback != null) {
                    mCallback.onAccentColorChanged(oldAccentColor, newAccentColor);
                }
            }
        });
    }

    private void refreshPaletteColor(final PaletteColor paletteColor) {
        if (paletteColor
                != null) {
            final int colorPosition = PaletteColorUtils.findPaletteIndexByHexColor(
                    mPaletteColorSection.getPaletteColorList(),
                    paletteColor
                            .getHexString());
            if (colorPosition >= 0) {
                mRecyclerAdapter.refreshPaletteColor(paletteColor, colorPosition);
            }
        }
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

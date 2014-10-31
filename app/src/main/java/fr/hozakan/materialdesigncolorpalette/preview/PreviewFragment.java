package fr.hozakan.materialdesigncolorpalette.preview;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import fr.hozakan.materialdesigncolorpalette.card.PreviewColorCard;
import fr.hozakan.materialdesigncolorpalette.dagger.BaseApplication;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.service.PaletteService;

/**
 * Created by gImbert on 12/08/2014.
 */
public class PreviewFragment extends Fragment {

    @Inject
    protected PaletteService mService;

    @Inject
    protected Bus mBus;

    private View mScreenBackground;
    private View mActionBarPreview;
    private ListView mListView;
    private ColorCardAdapter<PreviewColorCard> mAdapter;


    public static PreviewFragment newInstance() {
        return new PreviewFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseApplication)activity.getApplication()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return mListView = (ListView) inflater.inflate(R.layout.fragment_color_palette, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActivity().getActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mScreenBackground = view.findViewById(R.id.screen_background_preview);
//        mActionBarPreview = view.findViewById(R.id.action_bar_preview);

        PaletteColor actionbarColor = mService.getActionbarPreviewColor();
        List<PaletteColor> previewColors = mService.getPreviewColors();
        mAdapter = new ColorCardAdapter<PreviewColorCard>(getActivity(), getColorCardList(previewColors));
        mListView.setAdapter(mAdapter);

        final Drawable d = new ColorDrawable(actionbarColor.getHex());
        getActivity().getActionBar().setBackgroundDrawable(d);
    }


    private List<PreviewColorCard> getColorCardList(List<PaletteColor> previewColors) {
        return ColorCardTools.getColorCardList(getActivity().getApplicationContext(),
                mBus,
                previewColors);
    }

}

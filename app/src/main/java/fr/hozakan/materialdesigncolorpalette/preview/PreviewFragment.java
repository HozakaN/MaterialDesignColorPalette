package fr.hozakan.materialdesigncolorpalette.preview;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import fr.hozakan.materialdesigncolorpalette.R;
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
    private EditText mEditText;

    private PaletteColor mPrimaryColor;
    private PaletteColor mPrimaryDarkColor;
    private PaletteColor mAccentColor;
//    private ColorCardAdapter<PreviewColorCard> mAdapter;

    public static PreviewFragment newInstance() {
        return new PreviewFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseApplication)activity.getApplication()).inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrimaryColor = mService.getPrimaryColor();
        mPrimaryDarkColor = mService.getPrimaryDarkColor();
        mAccentColor = mService.getAccentColor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_preview, container, false);
        mEditText = (EditText) rootView.findViewById(R.id.edit_text);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mAccentColor != null) {
            mEditText.getBackground().setColorFilter(mAccentColor.getHex(), PorterDuff.Mode.SRC_IN);
        }
    }

}

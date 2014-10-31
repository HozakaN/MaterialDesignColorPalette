package fr.hozakan.materialdesigncolorpalette.preview;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import javax.inject.Inject;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.color_list.PaletteFragment;
import fr.hozakan.materialdesigncolorpalette.dagger.BaseApplication;
import fr.hozakan.materialdesigncolorpalette.service.PaletteService;

/**
 * Created by gImbert on 12/08/2014.
 */
public class PreviewActivity extends Activity {

    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    private static final String FRAGMENT_KEY = "FRAGMENT_KEY";

    private PreviewFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preview);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState != null) {
            mFragment = (PreviewFragment) getFragmentManager()
                    .getFragment(savedInstanceState, FRAGMENT_KEY);
        }

        if (mFragment == null) {
            mFragment = PreviewFragment.newInstance();
        }

        getFragmentManager().beginTransaction().replace(R.id.container, mFragment,
                FRAGMENT_TAG).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getFragmentManager().putFragment(outState, FRAGMENT_KEY, mFragment);
        super.onSaveInstanceState(outState);
    }
}

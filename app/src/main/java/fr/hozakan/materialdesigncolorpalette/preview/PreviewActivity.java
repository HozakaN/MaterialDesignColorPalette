package fr.hozakan.materialdesigncolorpalette.preview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import fr.hozakan.materialdesigncolorpalette.BuildConfig;
import fr.hozakan.materialdesigncolorpalette.R;

/**
 * Created by gImbert on 12/08/2014.
 */
public class PreviewActivity extends ActionBarActivity {

    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    private static final String FRAGMENT_KEY = "FRAGMENT_KEY";

    private PreviewFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preview);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.preview_screen_title);

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

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, PreviewActivity.class);
        return intent;
    }
}

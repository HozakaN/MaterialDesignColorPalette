package fr.hozakan.materialdesigncolorpalette;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import fr.hozakan.materialdesigncolorpalette.adapter.DrawerAdapter;
import fr.hozakan.materialdesigncolorpalette.dagger.BaseApplication;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;
import fr.hozakan.materialdesigncolorpalette.otto.CopyColorEvent;
import fr.hozakan.materialdesigncolorpalette.service.PaletteService;

public class ColorPaletteActivity extends Activity {

    private static final String FIRST_RUN = "FIRST_RUN";
    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    private static final String FRAGMENT_KEY = "FRAGMENT_KEY";
    private static final String POSITION_KEY = "POSITION_KEY";
    private static final String DRAWER_TITLE_KEY = "DRAWER_TITLE_KEY";
    private static final String TITLE_KEY = "TITLE_KEY";

    @Inject
    protected PaletteService mService;

    @Inject
    protected Bus mBus;

    private PaletteFragment mFragment = null;
    private CharSequence mDrawerTitle = null;
    private CharSequence mTitle = null;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<PaletteColorSection> mColorList;
    private int mPosition = 0;

    private final ListView.OnItemClickListener drawerClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(final AdapterView parent, final View view,
                                final int position, final long id) {
            selectItem(position);
        }
    };

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseApplication)getApplication()).inject(this);
        mBus.register(this);

        setContentView(R.layout.activity_color_palette);

        mColorList = mService.getPaletteColorSectionsList();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navigation_drawer);
        setupNavigationDrawer();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState != null) {
            mFragment = (PaletteFragment) getFragmentManager()
                    .getFragment(savedInstanceState, FRAGMENT_KEY);
            mPosition = savedInstanceState.getInt(POSITION_KEY);
            mDrawerTitle = savedInstanceState.getCharSequence(DRAWER_TITLE_KEY);
            mTitle = savedInstanceState.getCharSequence(TITLE_KEY);
        }
        if (mDrawerTitle == null) {
            mDrawerTitle = getTitle();
        }
        if (mTitle == null) {
            mTitle = getTitle();
        }

        selectItem(mPosition, mFragment, false);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean firstRun
                = prefs.getBoolean(FIRST_RUN, true);
        if (firstRun) {
            prefs.edit().putBoolean(FIRST_RUN, false).apply();
            mDrawerLayout.openDrawer(mDrawerList);
        }
    }

    @Override
    protected void onDestroy() {
        mBus.unregister(this);
        super.onDestroy();
    }

    private void setupNavigationDrawer() {
        mDrawerList.setAdapter(new DrawerAdapter(this, mColorList));
        mDrawerList.setOnItemClickListener(drawerClickListener);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                doDrawerClosed();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                doDrawerOpened();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void doDrawerClosed() {
        getActionBar().setTitle(mTitle);
        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }

    private void doDrawerOpened() {
        getActionBar().setTitle(mDrawerTitle);
        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }

    /* Swaps fragments in the main content view */
    private void selectItem(final int position, final PaletteFragment fragment,
                            boolean fromClick) {
        final PaletteColorSection paletteColorSection = mColorList.get(position);
        final String sectionName = paletteColorSection.getColorSectionName();
        final int sectionValue = paletteColorSection.getColorSectionValue();
        final Bundle bundle = new Bundle();
        bundle.putParcelable(PaletteFragment.ARG_COLOR_SECTION, paletteColorSection);
        if (mPosition == position && fromClick) {
            mFragment.scrollToTop();
        } else if (fromClick) {
            mPosition = position;
            mFragment.replaceColorCardList(paletteColorSection);
        } else {
            mPosition = position;
            if (fragment == null) {
                mFragment = new PaletteFragment();
                mFragment.setArguments(bundle);
            } else {
                mFragment = fragment;
            }
            getFragmentManager().beginTransaction().replace(R.id.container, mFragment,
                    FRAGMENT_TAG).commit();
        }
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(mPosition, true);
        setTitle(sectionName);
        getActionBar().setBackgroundDrawable(new ColorDrawable(sectionValue));
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void selectItem(final int position) {
        selectItem(position, null, true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();

        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            doDrawerOpened();
        } else {
            doDrawerClosed();
        }
    }

    @Subscribe
    public void onCopyColor(CopyColorEvent event) {
        final PaletteColor color = event.getColor();
        final ClipboardManager clipboard
                = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipData clip
                = ClipData.newPlainText(getString(R.string.color_clipboard, event.getColorName(),
                    color.getBaseName()), color.getHexString());
        clipboard.setPrimaryClip(clip);

        Toast.makeText(this, getString(R.string.color_copied, color.getHexString()),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Called on invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        final boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getFragmentManager().putFragment(outState, FRAGMENT_KEY, mFragment);
        outState.putInt(POSITION_KEY, mPosition);
        outState.putCharSequence(DRAWER_TITLE_KEY, mDrawerTitle);
        outState.putCharSequence(TITLE_KEY, mTitle);
        super.onSaveInstanceState(outState);
    }
}

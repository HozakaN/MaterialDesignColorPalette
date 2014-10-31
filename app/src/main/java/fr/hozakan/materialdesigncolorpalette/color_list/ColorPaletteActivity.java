package fr.hozakan.materialdesigncolorpalette.color_list;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
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

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.adapter.DrawerAdapter;
import fr.hozakan.materialdesigncolorpalette.dagger.BaseApplication;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;
import fr.hozakan.materialdesigncolorpalette.otto.ActionbarPreviewColorRemovedEvent;
import fr.hozakan.materialdesigncolorpalette.otto.AddActionbarPreviewColorEvent;
import fr.hozakan.materialdesigncolorpalette.otto.AddPreviewColorEvent;
import fr.hozakan.materialdesigncolorpalette.otto.CopyColorEvent;
import fr.hozakan.materialdesigncolorpalette.otto.RemoveActionbarPreviewColorEvent;
import fr.hozakan.materialdesigncolorpalette.otto.RemovePreviewColorEvent;
import fr.hozakan.materialdesigncolorpalette.service.PaletteService;

public class ColorPaletteActivity extends ActionBarActivity {

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
        getSupportActionBar().setTitle(mTitle);
    }

//    @Override
//    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//        Intent intent = new Intent();
//        switch (item.getItemId()) {
//            case R.id.menu_go_to_preview:
//                intent.setClass(getApplicationContext(), PreviewActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.menu_go_to_special:
//                intent.setClass(getApplicationContext(), SpecialActivity.class);
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
//        return super.onMenuItemSelected(featureId, item);
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseApplication)getApplication()).inject(this);
        mBus.register(this);

        setContentView(R.layout.activity_color_palette);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mColorList = mService.getPaletteColorSectionsList();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navigation_drawer);
        setupNavigationDrawer(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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

    private void setupNavigationDrawer(Toolbar toolbar) {
        mDrawerList.setAdapter(new DrawerAdapter(this, mColorList));
        mDrawerList.setOnItemClickListener(drawerClickListener);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
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
        getSupportActionBar().setTitle(mTitle);
        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }

    private void doDrawerOpened() {
        getSupportActionBar().setTitle(mDrawerTitle);
        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }

    /* Swaps fragments in the main content view */
    private void selectItem(final int position, final PaletteFragment fragment,
                            boolean fromClick) {
        final PaletteColorSection paletteColorSection = mColorList.get(position);
        final String sectionName = paletteColorSection.getColorSectionName();
        final int sectionValue = paletteColorSection.getColorSectionValue();
        final int darkColorSectionValue = paletteColorSection.getDarkColorSectionsValue();
        if (mPosition == position && fromClick) {
            mFragment.scrollToTop();
        } else if (fromClick) {
            mPosition = position;
            mFragment.replaceColorCardList(paletteColorSection);
        } else {
            mPosition = position;
            if (fragment == null) {
                final Bundle bundle = new Bundle();
                bundle.putParcelable(PaletteFragment.ARG_COLOR_SECTION, paletteColorSection);
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
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(sectionValue));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(darkColorSectionValue);
        }

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

    @Subscribe
    public void onAddActionbarPreviewColor(AddActionbarPreviewColorEvent event) {
        if (mService.setActionBarPreviewColor(event.color)) {
            event.color.setActionBarPreviewColor(true);
            mFragment.updateColorCardElement(event.color);

        }
    }

    @Subscribe
    public void onRemoveActionBarPreviewColor(RemoveActionbarPreviewColorEvent event) {
        if (mService.resetActionBarPreviewColor()) {
            event.color.setActionBarPreviewColor(false);
            mFragment.updateColorCardElement(event.color);
        }
    }

    @Subscribe
    public void onActionBarPreviewColorRemoved(ActionbarPreviewColorRemovedEvent event) {
        event.color.setActionBarPreviewColor(false);
        mFragment.updateColorCardElement(event.color);
    }

    @Subscribe
    public void onAddPreviewColor(AddPreviewColorEvent event) {
        if (mService.addPreviewColor(event.color)) {
            event.color.setPreviewColor(true);
            mFragment.updateColorCardElement(event.color);
        }
    }

    @Subscribe
    public void onRemovePreviewColor(RemovePreviewColorEvent event) {
        if (mService.removePreviewColor(event.color)) {
            event.color.setPreviewColor(false);
            mFragment.updateColorCardElement(event.color);
        }
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

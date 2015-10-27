package fr.hozakan.materialdesigncolorpalette.color_list;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.adapter.DrawerAdapter;
import fr.hozakan.materialdesigncolorpalette.adapter.RightDrawerAdapter;
import fr.hozakan.materialdesigncolorpalette.dagger.BaseApplication;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;
import fr.hozakan.materialdesigncolorpalette.otto.AddActionbarPreviewColorEvent;
import fr.hozakan.materialdesigncolorpalette.otto.AddPreviewColorEvent;
import fr.hozakan.materialdesigncolorpalette.otto.CopyColorEvent;
import fr.hozakan.materialdesigncolorpalette.otto.PrimaryColorRemovedEvent;
import fr.hozakan.materialdesigncolorpalette.otto.RemoveActionbarPreviewColorEvent;
import fr.hozakan.materialdesigncolorpalette.otto.RemovePreviewColorEvent;
import fr.hozakan.materialdesigncolorpalette.preview.PreviewActivity;
import fr.hozakan.materialdesigncolorpalette.preview.PreviewFragment;
import fr.hozakan.materialdesigncolorpalette.service.PaletteService;
import fr.hozakan.materialdesigncolorpalette.test.TestActivity;
import fr.hozakan.materialdesigncolorpalette.utils.PaletteColorUtils;

public class ColorPaletteActivity extends ActionBarActivity implements PaletteFragment.PaletteFragmentCallback, RightDrawerAdapter.PreviewColorClickListener {

    private static final String FIRST_RUN = "FIRST_RUN";
    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    private static final String FRAGMENT_KEY = "FRAGMENT_KEY";
//    private static final String PREVIEW_FRAGMENT_KEY = "PREVIEW_FRAGMENT_KEY";
    private static final String POSITION_KEY = "POSITION_KEY";
    private static final String DRAWER_TITLE_KEY = "DRAWER_TITLE_KEY";
    private static final String TITLE_KEY = "TITLE_KEY";
    private static final String CHOSEN_COLORS_KEY = "CHOSEN_COLORS_KEY";

    @Inject
    protected PaletteService mService;

    @Inject
    protected Bus mBus;

    private PaletteFragment mFragment = null;
//    private PreviewFragment mPreviewFragment = null;
    private CharSequence mDrawerTitle = null;
    private CharSequence mTitle = null;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<PaletteColorSection> mColorList;
    private int mPosition = 0;
//    private DrawerLayout mRightDrawerLayout;
    private RecyclerView mRightDrawerList;
    private RightDrawerAdapter mChosenColorsAdapter;

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

//        mRightDrawerLayout = (DrawerLayout) findViewById(R.id.right_drawer_layout);
        mRightDrawerList = (RecyclerView) findViewById(R.id.chosen_colors);
        setupRightNavigationDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState != null) {
            mFragment = (PaletteFragment) getFragmentManager()
                    .getFragment(savedInstanceState, FRAGMENT_KEY);
//            mPreviewFragment = (PreviewFragment) getFragmentManager()
//                    .getFragment(savedInstanceState, PREVIEW_FRAGMENT_KEY);
            mPosition = savedInstanceState.getInt(POSITION_KEY);
            mDrawerTitle = savedInstanceState.getCharSequence(DRAWER_TITLE_KEY);
            mTitle = savedInstanceState.getCharSequence(TITLE_KEY);
        } /*else {
            mPreviewFragment = PreviewFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.preview_container, mPreviewFragment)
                    .commit();
        }*/
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
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setupRightNavigationDrawer() {
        SparseArray<PaletteColor> colors = mService.getChosenColors();
        SparseArray<PaletteColor> chosenColorProxy = new SparseArray<>(colors.size());
        for (int i = 0; i < colors.size(); i++) {
            chosenColorProxy.put(colors.keyAt(i), colors.get(colors.keyAt(i)));
        }
        mChosenColorsAdapter = new RightDrawerAdapter(this, chosenColorProxy, this);
        mRightDrawerList.setLayoutManager(new LinearLayoutManager(this));
        mRightDrawerList.setAdapter(mChosenColorsAdapter);

        findViewById(R.id.button_preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreviewButtonClicked();
            }
        });

        findViewById(R.id.button_test_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testThemeClicked();
            }
        });
//        mRightDrawerList.setOnItemClickListener(drawerClickListener);
//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                doDrawerClosed();
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                doDrawerOpened();
//            }
//        };
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void doDrawerClosed() {
//        getSupportActionBar().setTitle(mTitle);
//        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }

    private void doDrawerOpened() {
//        getSupportActionBar().setTitle(mDrawerTitle);
//        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }

    /* Swaps fragments in the main content view */
    private void selectItem(final int position, final PaletteFragment fragment,
                            boolean fromClick) {
        selectItem(position, fragment, fromClick, -1);
    }

    /* Swaps fragments in the main content view */
    private void selectItem(final int position, final PaletteFragment fragment,
                            boolean fromClick, int colorIndex) {
        final PaletteColorSection paletteColorSection = mColorList.get(position);
        final String sectionName = paletteColorSection.getColorSectionName();
        final int sectionValue = paletteColorSection.getColorSectionValue();
        final int darkColorSectionValue = paletteColorSection.getDarkColorSectionsValue();
        if (mPosition == position && fromClick) {
            mFragment.scrollToTop(colorIndex);
        } else if (fromClick) {
            mPosition = position;
            mFragment.replaceColorCardList(paletteColorSection, colorIndex);
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
//            mDrawerLayout.setStatusBarBackgroundColor(darkColorSectionValue);
        }

//        mDrawerLayout.closeDrawer(mDrawerList);
        mDrawerLayout.closeDrawers();
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
//        if (mService.setPrimaryColor(event.color)) {
//            event.color.setPrimaryColor(true);
//            mFragment.updateColorCardElement(event.color);

//        }
    }

    @Subscribe
    public void onRemoveActionBarPreviewColor(RemoveActionbarPreviewColorEvent event) {
        if (mService.resetActionBarPreviewColor()) {
            event.color.setPrimaryColor(false);
//            mFragment.updateColorCardElement(event.color);
        }
    }

    @Subscribe
    public void onPrimaryColorRemoved(PrimaryColorRemovedEvent event) {
        event.color.setPrimaryColor(false);
//        mFragment.updateColorCardElement(event.color);
    }

    @Subscribe
    public void onAddPreviewColor(AddPreviewColorEvent event) {
        if (mService.addPreviewColor(event.color)) {
            event.color.setPrimaryDarkColor(true);
//            mFragment.updateColorCardElement(event.color);
        }
    }

    @Subscribe
    public void onRemovePreviewColor(RemovePreviewColorEvent event) {
        if (mService.removePreviewColor(event.color)) {
            event.color.setPrimaryDarkColor(false);
//            mFragment.updateColorCardElement(event.color);
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
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.color_card_popup_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    private void onPreviewButtonClicked() {
        Intent intent = PreviewActivity.createIntent(this);
        startActivity(intent);
    }

    private void testThemeClicked() {
        Intent intent = TestActivity.createIntent(this);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getFragmentManager().putFragment(outState, FRAGMENT_KEY, mFragment);
//        getFragmentManager().putFragment(outState, PREVIEW_FRAGMENT_KEY, mPreviewFragment);
        outState.putInt(POSITION_KEY, mPosition);
        outState.putCharSequence(DRAWER_TITLE_KEY, mDrawerTitle);
        outState.putCharSequence(TITLE_KEY, mTitle);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPrimaryColorRemoved(PaletteColor color) {
        mChosenColorsAdapter.colorRemoved(PaletteService.SPARSE_PRIMARY_COLOR_KEY);
    }

    @Override
    public void onPrimaryDarkColorRemoved(PaletteColor color) {
        mChosenColorsAdapter.colorRemoved(PaletteService.SPARSE_PRIMARY_DARK_COLOR_KEY);
    }

    @Override
    public void onAccentColorRemoved(PaletteColor color) {
        mChosenColorsAdapter.colorRemoved(PaletteService.SPARSE_ACCENT_COLOR_KEY);
    }

    @Override
    public void onPrimaryColorAdded(PaletteColor color) {
        mChosenColorsAdapter.colorAdded(PaletteService.SPARSE_PRIMARY_COLOR_KEY, color);
    }

    @Override
    public void onPrimaryDarkColorAdded(PaletteColor color) {
        mChosenColorsAdapter.colorAdded(PaletteService.SPARSE_PRIMARY_DARK_COLOR_KEY, color);
    }

    @Override
    public void onAccentColorAdded(PaletteColor color) {
        mChosenColorsAdapter.colorAdded(PaletteService.SPARSE_ACCENT_COLOR_KEY, color);
    }

    @Override
    public void onPrimaryColorChanged(PaletteColor oldPrimaryColor, PaletteColor newPrimaryColor) {
        mChosenColorsAdapter.colorChanged(PaletteService.SPARSE_PRIMARY_COLOR_KEY, newPrimaryColor);
    }

    @Override
    public void onPrimaryDarkColorChanged(PaletteColor oldPrimaryDarkColor, PaletteColor newPrimaryDarkColor) {
        mChosenColorsAdapter.colorChanged(PaletteService.SPARSE_PRIMARY_DARK_COLOR_KEY, newPrimaryDarkColor);
    }

    @Override
    public void onAccentColorChanged(PaletteColor oldAccentColor, PaletteColor newAccentColor) {
        mChosenColorsAdapter.colorChanged(PaletteService.SPARSE_ACCENT_COLOR_KEY, newAccentColor);
    }

    @Override
    public void onPreviewColorClicked(PaletteColor paletteColor) {
        int colorIndex = -1;
        PaletteColorSection section = PaletteColorUtils.findPaletteSectionByPalette(mColorList, paletteColor);
        if (section != null) {
            int sectionIndex = mColorList.indexOf(section);
            colorIndex = PaletteColorUtils.findPaletteIndexByHexColor(section.getPaletteColorList(), paletteColor.getHexString());
            selectItem(sectionIndex, mFragment, true, colorIndex);
        }
    }
}

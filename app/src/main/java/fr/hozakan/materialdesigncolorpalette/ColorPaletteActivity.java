package fr.hozakan.materialdesigncolorpalette;

import android.app.Activity;
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

import java.util.List;

import fr.hozakan.materialdesigncolorpalette.adapter.DrawerAdapter;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColorSection;

public class ColorPaletteActivity extends Activity {

    private static final String FIRST_RUN = "FIRST_RUN";
    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    private static final String FRAGMENT_KEY = "FRAGMENT_KEY";
    private static final String POSITION_KEY = "POSITION_KEY";
    private static final String DRAWER_TITLE_KEY = "DRAWER_TITLE_KEY";
    private static final String TITLE_KEY = "TITLE_KEY";

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
        setContentView(R.layout.activity_color_palette);

        final String[] colorSectionsNames
                = getResources().getStringArray(R.array.color_sections_names);
        final int[] colorSectionsValues
                = getResources().getIntArray(R.array.color_sections_colors);

        mColorList = PaletteColorSection.getPaletteColorSectionsList(colorSectionsNames,
                colorSectionsValues, getBaseColorNames(colorSectionsNames),
                getColorValues(colorSectionsNames));
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
            mFragment.scollToTop();
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

    private String[][] getBaseColorNames(String[] colorSectionsNames) {
        String[][] strArr = new String[colorSectionsNames.length][];
        for (int i = 0; i < colorSectionsNames.length; i++) {
            strArr[i] = getBaseColorNames(colorSectionsNames[i]);
        }
        return strArr;
    }

    private int[][] getColorValues(String[] colorSectionsNames) {
        int[][] intArr = new int[colorSectionsNames.length][];
        for (int i = 0; i < colorSectionsNames.length; i++) {
            intArr[i] = getColorValues(colorSectionsNames[i]);
        }
        return intArr;
    }

    private String[] getBaseColorNames(String colorSectionName) {
        Resources res = getResources();
        if (getString(R.string.red).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.pink).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.purple).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.deep_purple).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.indigo).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.blue).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.light_blue).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.cyan).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.teal).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.green).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.light_green).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.lime).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.yellow).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.amber).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.orange).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.deep_orange).equals(colorSectionName)) {
            return res.getStringArray(R.array.full_base_color_list);
        } else if (getString(R.string.brown).equals(colorSectionName)) {
            return res.getStringArray(R.array.base_color_list_to_900);
        } else if (getString(R.string.grey).equals(colorSectionName)) {
            return res.getStringArray(R.array.base_color_list_greys);
        } else if (getString(R.string.blue_grey).equals(colorSectionName)) {
            return res.getStringArray(R.array.base_color_list_to_900);
        } else {
            throw new RuntimeException("Invalid color section: " + colorSectionName);
        }
    }

    private int[] getColorValues(String colorSectionName) {Resources res = getResources();
        if (getString(R.string.red).equals(colorSectionName)) {
            return res.getIntArray(R.array.reds);
        } else if (getString(R.string.pink).equals(colorSectionName)) {
            return res.getIntArray(R.array.pinks);
        } else if (getString(R.string.purple).equals(colorSectionName)) {
            return res.getIntArray(R.array.purples);
        } else if (getString(R.string.deep_purple).equals(colorSectionName)) {
            return res.getIntArray(R.array.deep_purples);
        } else if (getString(R.string.indigo).equals(colorSectionName)) {
            return res.getIntArray(R.array.indigos);
        } else if (getString(R.string.blue).equals(colorSectionName)) {
            return res.getIntArray(R.array.blues);
        } else if (getString(R.string.light_blue).equals(colorSectionName)) {
            return res.getIntArray(R.array.light_blues);
        } else if (getString(R.string.cyan).equals(colorSectionName)) {
            return res.getIntArray(R.array.cyans);
        } else if (getString(R.string.teal).equals(colorSectionName)) {
            return res.getIntArray(R.array.teals);
        } else if (getString(R.string.green).equals(colorSectionName)) {
            return res.getIntArray(R.array.greens);
        } else if (getString(R.string.light_green).equals(colorSectionName)) {
            return res.getIntArray(R.array.light_greens);
        } else if (getString(R.string.lime).equals(colorSectionName)) {
            return res.getIntArray(R.array.limes);
        } else if (getString(R.string.yellow).equals(colorSectionName)) {
            return res.getIntArray(R.array.yellows);
        } else if (getString(R.string.amber).equals(colorSectionName)) {
            return res.getIntArray(R.array.ambers);
        } else if (getString(R.string.orange).equals(colorSectionName)) {
            return res.getIntArray(R.array.oranges);
        } else if (getString(R.string.deep_orange).equals(colorSectionName)) {
            return res.getIntArray(R.array.deep_oranges);
        } else if (getString(R.string.brown).equals(colorSectionName)) {
            return res.getIntArray(R.array.browns);
        } else if (getString(R.string.grey).equals(colorSectionName)) {
            return res.getIntArray(R.array.greys);
        } else if (getString(R.string.blue_grey).equals(colorSectionName)) {
            return res.getIntArray(R.array.blue_greys);
        } else {
            throw new RuntimeException("Invalid color section: " + colorSectionName);
        }
    }
}

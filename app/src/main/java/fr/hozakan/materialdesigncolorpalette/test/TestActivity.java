package fr.hozakan.materialdesigncolorpalette.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.color_list.ColorPaletteActivity;

/**
 * Created by gimbert on 15-04-13.
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_preview);



    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, TestActivity.class);
        return intent;
    }
}

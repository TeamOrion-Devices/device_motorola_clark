/*
 * Copyright (C) 2015 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cyanogenmod.settings.device;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.DisplayInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class TouchscreenGestureSettings extends PreferenceActivity {
    private static final String CATEGORY_AMBIENT_DISPLAY = "ambient_display_key";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.gesture_panel);
        PreferenceCategory ambientDisplayCat = (PreferenceCategory)
                findPreference(CATEGORY_AMBIENT_DISPLAY);
        if (ambientDisplayCat != null) {
            ambientDisplayCat.setEnabled(CMActionsSettings.isDozeEnabled(getContentResolver()));
        }
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // If running on a phone, remove padding around the listview
        if (!isTablet(this)) {
            getListView().setPadding(0, 0, 0, 0);
        }
    }

    private static boolean isTablet(Context con) {
        WindowManager wm = (WindowManager) con.getSystemService(Context.WINDOW_SERVICE);
        DisplayInfo outDisplayInfo = new DisplayInfo();
        wm.getDefaultDisplay().getDisplayInfo(outDisplayInfo);
        int shortSize = Math.min(outDisplayInfo.logicalHeight, outDisplayInfo.logicalWidth);
        int shortSizeDp =
            shortSize * DisplayMetrics.DENSITY_DEFAULT / outDisplayInfo.logicalDensityDpi;
        return shortSizeDp > 720;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}

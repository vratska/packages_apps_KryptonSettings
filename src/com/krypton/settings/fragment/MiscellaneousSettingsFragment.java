/*
 * Copyright (C) 2021 AOSP-Krypton Project
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
package com.krypton.settings.fragment;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.preference.Preference;

import com.android.settings.R;
import com.krypton.settings.fragment.AccentPickerFragment;
import com.krypton.settings.preference.AccentPickerPreference;
import com.krypton.settings.preference.SettingSwitchPreference;

public class MiscellaneousSettingsFragment extends BaseFragment {
    private static final String TAG = "MiscellaneousSettingsFragment";
    private Context mContext;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.miscellaneous_settings);
        mContext = getContext();
        SettingSwitchPreference volumePanelSwitch = (SettingSwitchPreference) findPreference(
            "volume_panel_on_left_switch_preference");
        try {
            Resources res = mContext.getPackageManager().getResourcesForApplication("com.android.systemui");
            int resId = res.getIdentifier("config_audioPanelOnLeftSide", "bool", "com.android.systemui");
            if (resId != 0) {
                volumePanelSwitch.setSettingDefault(res.getBoolean(resId) ? 1 : 0);
                volumePanelSwitch.setChecked(volumePanelSwitch.isChecked());
            }
        } catch(NameNotFoundException e) {
            // Do nothing
        }
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        if (preference instanceof AccentPickerPreference) {
            final AccentPickerFragment fragment = new AccentPickerFragment();
            fragment.setTargetFragment(this, 0);
            fragment.show(getParentFragmentManager(), TAG);
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }
}

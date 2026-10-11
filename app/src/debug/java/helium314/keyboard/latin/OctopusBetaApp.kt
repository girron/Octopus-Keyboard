// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.latin

import android.content.SharedPreferences
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import helium314.keyboard.latin.settings.Defaults
import helium314.keyboard.latin.settings.Settings
import helium314.keyboard.latin.utils.prefs

/** Debug-only crash reporting; never attach typed words or clipboard content. */
class OctopusBetaApp : App() {
    private var crashPrefs: SharedPreferences? = null
    private val prefsListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == Settings.PREF_OCTOPUS_CRASH_REPORTS) {
            updateCrashCollection(verification = true)
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.OCTOPUS_CRASHLYTICS_CONFIGURED || FirebaseApp.getApps(this).isEmpty()) return
        crashPrefs = prefs()
        crashPrefs?.registerOnSharedPreferenceChangeListener(prefsListener)
        updateCrashCollection(verification = false)
    }

    private fun updateCrashCollection(verification: Boolean) {
        val enabled = crashPrefs?.getBoolean(
            Settings.PREF_OCTOPUS_CRASH_REPORTS, Defaults.PREF_OCTOPUS_CRASH_REPORTS
        ) ?: return
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCrashlyticsCollectionEnabled(enabled)
        if (enabled && verification) {
            crashlytics.recordException(IllegalStateException("Octopus beta diagnostics verification"))
        }
    }
}

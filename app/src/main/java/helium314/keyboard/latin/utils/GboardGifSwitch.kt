package helium314.keyboard.latin.utils

import android.content.Context
import android.os.Build
import android.view.inputmethod.InputMethodManager
import android.inputmethodservice.InputMethodService

/**
 * Switch to an enabled Gboard IME using public Android APIs.
 *
 * Returns false when Gboard is unavailable or the platform cannot perform
 * a direct switch. Caller can then show the standard IME picker.
 *
 * This helper does not attempt to open Gboard's private GIF interface.
 */
object GboardGifSwitch {
    private const val GBOARD_PACKAGE = "com.google.android.inputmethod.latin"

    @JvmStatic
    fun switchToGboard(service: InputMethodService): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) return false
        val imm = service.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            ?: return false
        val gboard = imm.enabledInputMethodList.firstOrNull {
            it.packageName == GBOARD_PACKAGE
        } ?: return false
        return try {
            service.switchInputMethod(gboard.id)
            true
        } catch (_: RuntimeException) {
            false
        }
    }

    @JvmStatic
    fun showPicker(service: InputMethodService) {
        (service.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.showInputMethodPicker()
    }
}

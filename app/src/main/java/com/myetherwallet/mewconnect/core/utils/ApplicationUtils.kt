package com.myetherwallet.mewconnect.core.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.AttrRes
import android.util.DisplayMetrics
import android.util.Size
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import com.myetherwallet.mewconnect.content.data.Network
import com.myetherwallet.mewconnect.core.persist.prefenreces.PreferencesManager

/**
 * Created by BArtWell on 21.08.2018.
 */

object ApplicationUtils {

    fun getAttrDimension(context: Context, @AttrRes resId: Int): Int {
        val typedValue = TypedValue()
        if (context.theme.resolveAttribute(resId, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, context.resources.displayMetrics)
        }
        return 0
    }

    fun pxToDp(px: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val dp = px / (metrics.densityDpi / 160f)
        return Math.round(dp).toFloat()
    }

    fun dpToPx(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return Math.round(px).toFloat()
    }

    fun getDisplaySize(context: Context): Size {
        val displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay
                .getMetrics(displayMetrics)
        return Size(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }

    fun getStatusBarHeight(context: Context?): Int {
        context?.let {
            val resources = context.resources
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                return resources.getDimensionPixelSize(resourceId)
            }
        }
        return ApplicationUtils.dpToPx(if (VERSION.SDK_INT >= VERSION_CODES.M) 24f else 25f).toInt()
    }

    fun getToolbarMargin(view: View?): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            view?.rootWindowInsets?.displayCutout?.let {
                if (it.boundingRects.isNotEmpty()) {
                    return it.boundingRects[0].height()
                }
            }
        }
        return ApplicationUtils.getStatusBarHeight(view?.context)
    }

    fun removeAllData(context: Context?, preferences: PreferencesManager) {
        context?.let { CardBackgroundHelper.remove(it, preferences.applicationPreferences.getCurrentNetwork()) }
        preferences.applicationPreferences.removeWalletData()
        for (network in Network.values()) {
            preferences.getWalletPreferences(network).removeAllData()
        }
    }
}
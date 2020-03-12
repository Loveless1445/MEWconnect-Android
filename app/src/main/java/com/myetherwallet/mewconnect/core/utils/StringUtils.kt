package com.myetherwallet.mewconnect.core.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.StringRes

object StringUtils {

    fun replaceBreaks(source: String) = source
            .replace("\r", "")
            .replace("\n", "<br/>")

    fun fromHtml(source: String): Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(source)
    }

    fun fromHtml(context: Context, @StringRes strRes: Int) = fromHtml(context.getString(strRes))
}
package com.nb.core.test.main

import android.content.Context

fun dip2px(context: Context, dpValue: Float): Int {
    return (dpValue * context.resources.displayMetrics.density + 0.5f).toInt()
}
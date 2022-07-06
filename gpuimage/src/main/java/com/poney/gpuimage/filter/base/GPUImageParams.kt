package com.poney.gpuimage.filter.base

import android.content.Context

object GPUImageParams {
    @JvmField
    var sContext: Context? = null

    fun init(context: Context?) {
        sContext = context
    }
}
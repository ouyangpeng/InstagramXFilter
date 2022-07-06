package com.poney.gpuimage.filter.blend

import com.poney.gpuimage.utils.OpenGlUtils.readShaderFromRawResource
import com.poney.gpuimage.filter.base.GPUImageBlendFilter
import com.poney.gpuimage.R
import android.opengl.GLES20

class GPUImageSketchFilter :
    GPUImageBlendFilter(NO_FILTER_VERTEX_SHADER, readShaderFromRawResource(R.raw.sketch)!!) {
    private var mSingleStepOffsetLocation = 0

    //0.0 - 1.0
    private var mStrengthLocation = 0
    override fun onInit() {
        super.onInit()
        mSingleStepOffsetLocation = GLES20.glGetUniformLocation(program, "singleStepOffset")
        mStrengthLocation = GLES20.glGetUniformLocation(program, "strength")
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setTexelSize(w: Float, h: Float) {
        setFloatVec2(mSingleStepOffsetLocation, floatArrayOf(1.0f / w, 1.0f / h))
    }

    override fun onInitialized() {
        super.onInitialized()
        setFloat(mStrengthLocation, 0.5f)
    }

    override fun onOutputSizeChanged(width: Int, height: Int) {
        super.onOutputSizeChanged(width, height)
        setTexelSize(width.toFloat(), height.toFloat())
    }
}
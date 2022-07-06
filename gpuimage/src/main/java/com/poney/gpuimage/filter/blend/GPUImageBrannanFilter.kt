package com.poney.gpuimage.filter.blend

import com.poney.gpuimage.utils.OpenGlUtils.readShaderFromRawResource
import com.poney.gpuimage.utils.OpenGlUtils.loadTexture
import com.poney.gpuimage.filter.base.GPUImageBlendFilter
import com.poney.gpuimage.utils.OpenGlUtils
import com.poney.gpuimage.R
import android.opengl.GLES20
import com.poney.gpuimage.filter.base.GPUImageParams

class GPUImageBrannanFilter :
    GPUImageBlendFilter(NO_FILTER_VERTEX_SHADER, readShaderFromRawResource(R.raw.brannan)!!) {
    private val inputTextureHandles = intArrayOf(-1, -1, -1, -1, -1)
    private val inputTextureUniformLocations = intArrayOf(-1, -1, -1, -1, -1)
    private var mGLStrengthLocation = 0
    override fun onDestroy() {
        super.onDestroy()
        GLES20.glDeleteTextures(inputTextureHandles.size, inputTextureHandles, 0)
        for (i in inputTextureHandles.indices) inputTextureHandles[i] = -1
    }

    public override fun onDrawArraysAfter() {
        var i = 0
        while (i < inputTextureHandles.size
            && inputTextureHandles[i] != OpenGlUtils.NO_TEXTURE
        ) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + (i + 3))
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
            i++
        }
    }

    public override fun onDrawArraysPre() {
        var i = 0
        while (i < inputTextureHandles.size
            && inputTextureHandles[i] != OpenGlUtils.NO_TEXTURE
        ) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + (i + 3))
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, inputTextureHandles[i])
            GLES20.glUniform1i(inputTextureUniformLocations[i], i + 3)
            i++
        }
    }

    override fun onInit() {
        super.onInit()
        for (i in inputTextureUniformLocations.indices) inputTextureUniformLocations[i] =
            GLES20.glGetUniformLocation(program, "inputImageTexture" + (2 + i))
        mGLStrengthLocation = GLES20.glGetUniformLocation(
            program,
            "strength"
        )
    }

    override fun onInitialized() {
        super.onInitialized()
        setFloat(mGLStrengthLocation, 1.0f)
        runOnDraw {
            inputTextureHandles[0] =
                loadTexture(GPUImageParams.sContext!!, "filter/brannan_process.png")
            inputTextureHandles[1] =
                loadTexture(GPUImageParams.sContext!!, "filter/brannan_blowout.png")
            inputTextureHandles[2] =
                loadTexture(GPUImageParams.sContext!!, "filter/brannan_contrast.png")
            inputTextureHandles[3] =
                loadTexture(GPUImageParams.sContext!!, "filter/brannan_luma.png")
            inputTextureHandles[4] =
                loadTexture(GPUImageParams.sContext!!, "filter/brannan_screen.png")
        }
    }
}
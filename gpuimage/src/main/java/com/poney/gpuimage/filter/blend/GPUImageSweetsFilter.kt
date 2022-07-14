package com.poney.gpuimage.filter.blend

import com.poney.gpuimage.utils.OpenGlUtils.readShaderFromRawResource
import com.poney.gpuimage.utils.OpenGlUtils.loadTexture
import com.poney.gpuimage.filter.base.GPUImageBlendFilter
import com.poney.gpuimage.R
import android.opengl.GLES20
import com.poney.gpuimage.filter.base.GPUImageParams
import java.nio.ByteBuffer

class GPUImageSweetsFilter :
    GPUImageBlendFilter(NO_FILTER_VERTEX_SHADER, readShaderFromRawResource(R.raw.sweets)!!) {
    private val mToneCurveTexture = intArrayOf(-1)
    private var mToneCurveTextureUniformLocation = 0
    private var mMaskGrey1TextureId = -1
    private var mMaskGrey1UniformLocation = 0
    private var mLowPerformanceUniformLocation = 0
    override fun onDestroy() {
        super.onDestroy()
        GLES20.glDeleteTextures(2, intArrayOf(mToneCurveTexture[0], mMaskGrey1TextureId), 0)
        mToneCurveTexture[0] = -1
        mMaskGrey1TextureId = -1
    }

    public override fun onDrawArraysAfter() {
        if (mToneCurveTexture[0] != -1) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE3)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        }
        if (mMaskGrey1TextureId != -1) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE4)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        }
    }

    public override fun onDrawArraysPre() {
        if (mToneCurveTexture[0] != -1) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE3)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mToneCurveTexture[0])
            GLES20.glUniform1i(mToneCurveTextureUniformLocation, 3)
        }
        if (mMaskGrey1TextureId != -1) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE4)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mMaskGrey1TextureId)
            GLES20.glUniform1i(mMaskGrey1UniformLocation, 4)
        }
    }

    override fun onInit() {
        super.onInit()
        mToneCurveTextureUniformLocation = GLES20.glGetUniformLocation(program, "curve")
        mMaskGrey1UniformLocation = GLES20.glGetUniformLocation(program, "grey1Frame")
        mLowPerformanceUniformLocation = GLES20.glGetUniformLocation(program, "lowPerformance")
        setInteger(mLowPerformanceUniformLocation, 1)
    }

    override fun onInitialized() {
        super.onInitialized()
        runOnDraw {
            GLES20.glGenTextures(1, mToneCurveTexture, 0)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mToneCurveTexture[0])
            GLES20.glTexParameterf(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat()
            )
            GLES20.glTexParameterf(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR.toFloat()
            )
            GLES20.glTexParameterf(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE.toFloat()
            )
            GLES20.glTexParameterf(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE.toFloat()
            )
            val arrayOfByte = ByteArray(1024)

            val arrayOfInt = intArrayOf( 0, 1, 2, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10, 10, 11, 12, 13, 14, 14, 15, 16, 17, 18, 19, 19, 20, 21, 22, 23, 24, 24, 25, 26, 27, 28, 29, 30, 30, 31, 32, 33, 34, 35, 36, 37, 38, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 71, 72, 73, 74, 75, 76, 77, 79, 80, 81, 82, 83, 84, 86, 87, 88, 89, 90, 92, 93, 94, 95, 96, 98, 99, 100, 101, 103, 104, 105, 106, 108, 109, 110, 111, 113, 114, 115, 116, 118, 119, 120, 121, 123, 124, 125, 126, 128, 129, 130, 132, 133, 134, 135, 137, 138, 139, 140, 142, 143, 144, 145, 147, 148, 149, 150, 152, 153, 154, 155, 157, 158, 159, 160, 161, 163, 164, 165, 166, 167, 169, 170, 171, 172, 173, 174, 176, 177, 178, 179, 180, 181, 182, 183, 184, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 209, 210, 211, 212, 213, 214, 215, 216, 217, 217, 218, 219, 220, 221, 222, 222, 223, 224, 225, 226, 227, 227, 228, 229, 230, 230, 231, 232, 233, 234, 234, 235, 236, 237, 237, 238, 239, 240, 240, 241, 242, 243, 243, 244, 245, 246, 246, 247, 248, 248, 249, 250, 251, 251, 252, 253, 254, 254, 255 )

            for (i in 0..255) {
                arrayOfByte[i * 4] = arrayOfInt[i].toByte()
                arrayOfByte[1 + i * 4] = arrayOfInt[i].toByte()
                arrayOfByte[2 + i * 4] = arrayOfInt[i].toByte()
                arrayOfByte[3 + i * 4] = i.toByte()
            }
            GLES20.glTexImage2D(
                GLES20.GL_TEXTURE_2D,
                0,
                GLES20.GL_RGBA,
                256,
                1,
                0,
                GLES20.GL_RGBA,
                GLES20.GL_UNSIGNED_BYTE,
                ByteBuffer.wrap(arrayOfByte)
            )
            mMaskGrey1TextureId = loadTexture(GPUImageParams.sContext!!, "filter/rise_mask2.jpg")
        }
    }
}
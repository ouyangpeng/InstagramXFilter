package com.poney.gpuimage.filter.blend

import com.poney.gpuimage.utils.OpenGlUtils.readShaderFromRawResource
import com.poney.gpuimage.filter.base.GPUImageBlendFilter
import com.poney.gpuimage.R
import android.opengl.GLES20
import java.nio.ByteBuffer

class GPUImageRomanceFilter :
    GPUImageBlendFilter(NO_FILTER_VERTEX_SHADER, readShaderFromRawResource(R.raw.romance)!!) {
    private val mToneCurveTexture = intArrayOf(-1)
    private var mToneCurveTextureUniformLocation = 0
    override fun onDestroy() {
        super.onDestroy()
        GLES20.glDeleteTextures(1, mToneCurveTexture, 0)
        mToneCurveTexture[0] = -1
    }

    public override fun onDrawArraysAfter() {
        if (mToneCurveTexture[0] != -1) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE3)
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
    }

    override fun onInit() {
        super.onInit()
        mToneCurveTextureUniformLocation = GLES20.glGetUniformLocation(program, "curve")
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
            val romance_arrayOfByte = ByteArray(1024)

            val romance_arrayOfInt1 = intArrayOf( 46, 46, 46, 46, 47, 47, 47, 47, 47, 47, 48, 48, 48, 48, 48, 48, 49, 49, 49, 49, 49, 50, 50, 50, 50, 51, 51, 51, 51, 52, 52, 52, 52, 53, 53, 53, 54, 54, 54, 55, 55, 56, 56, 56, 57, 57, 58, 58, 59, 59, 60, 60, 61, 61, 62, 62, 63, 63, 64, 65, 65, 66, 67, 67, 68, 69, 69, 70, 71, 72, 73, 73, 74, 75, 76, 77, 78, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 100, 101, 102, 103, 104, 105, 106, 107, 109, 110, 111, 112, 113, 114, 116, 117, 118, 119, 120, 122, 123, 124, 125, 127, 128, 129, 130, 131, 133, 134, 135, 136, 138, 139, 140, 141, 143, 144, 145, 146, 148, 149, 150, 151, 153, 154, 155, 156, 158, 159, 160, 161, 162, 164, 165, 166, 167, 169, 170, 171, 172, 173, 175, 176, 177, 178, 179, 180, 182, 183, 184, 185, 186, 187, 188, 189, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 211, 212, 213, 214, 215, 216, 217, 218, 218, 219, 220, 221, 222, 222, 223, 224, 225, 226, 226, 227, 228, 229, 229, 230, 231, 232, 232, 233, 234, 234, 235, 236, 237, 237, 238, 239, 239, 240, 241, 241, 242, 243, 243, 244, 245, 245, 246, 247, 247, 248, 249, 249, 250, 251, 251, 252, 252, 253, 254, 254, 255 )
            val romance_arrayOfInt2 = intArrayOf( 0, 1, 1, 2, 3, 4, 4, 5, 6, 6, 7, 8, 9, 9, 10, 11, 12, 12, 13, 14, 14, 15, 16, 17, 17, 18, 19, 20, 20, 21, 22, 23, 23, 24, 25, 26, 26, 27, 28, 29, 30, 30, 31, 32, 33, 33, 34, 35, 36, 37, 38, 38, 39, 40, 41, 42, 43, 43, 44, 45, 46, 47, 48, 49, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 111, 112, 113, 114, 115, 116, 117, 118, 120, 121, 122, 123, 124, 125, 126, 128, 129, 130, 131, 132, 133, 134, 136, 137, 138, 139, 140, 141, 142, 144, 145, 146, 147, 148, 149, 151, 152, 153, 154, 155, 156, 157, 159, 160, 161, 162, 163, 164, 166, 167, 168, 169, 170, 171, 172, 174, 175, 176, 177, 178, 179, 180, 181, 183, 184, 185, 186, 187, 188, 189, 190, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255 )
            val romance_arrayOfInt3 = intArrayOf( 0, 2, 3, 5, 7, 8, 10, 12, 13, 15, 17, 18, 20, 21, 23, 25, 26, 28, 30, 31, 33, 34, 36, 38, 39, 41, 42, 44, 45, 47, 48, 50, 51, 53, 54, 56, 57, 59, 60, 62, 63, 65, 66, 67, 69, 70, 72, 73, 74, 76, 77, 78, 79, 81, 82, 83, 84, 86, 87, 88, 89, 90, 91, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 106, 107, 108, 109, 110, 111, 112, 112, 113, 114, 115, 116, 116, 117, 118, 119, 119, 120, 121, 122, 122, 123, 124, 124, 125, 126, 126, 127, 128, 128, 129, 130, 130, 131, 131, 132, 133, 133, 134, 134, 135, 136, 136, 137, 137, 138, 139, 139, 140, 140, 141, 141, 142, 143, 143, 144, 144, 145, 146, 146, 147, 147, 148, 149, 149, 150, 150, 151, 152, 152, 153, 154, 154, 155, 155, 156, 157, 157, 158, 159, 159, 160, 161, 162, 162, 163, 164, 164, 165, 166, 167, 168, 168, 169, 170, 171, 172, 172, 173, 174, 175, 176, 177, 177, 178, 179, 180, 181, 182, 183, 184, 185, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 242, 243, 244, 245, 246, 247, 248, 249, 251, 252, 253, 254, 255 )
            val romance_arrayOfInt4 = intArrayOf( 0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 8, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 16, 16, 16, 17, 17, 17, 18, 18, 18, 19, 19, 20, 20, 20, 21, 21, 21, 22, 22, 23, 23, 23, 24, 24, 24, 25, 25, 25, 25, 26, 26, 27, 27, 28, 28, 28, 28, 29, 29, 30, 29, 31, 31, 31, 31, 32, 32, 33, 33, 34, 34, 34, 34, 35, 35, 36, 36, 37, 37, 37, 38, 38, 39, 39, 39, 40, 40, 40, 41, 42, 42, 43, 43, 44, 44, 45, 45, 45, 46, 47, 47, 48, 48, 49, 50, 51, 51, 52, 52, 53, 53, 54, 55, 55, 56, 57, 57, 58, 59, 60, 60, 61, 62, 63, 63, 64, 65, 66, 67, 68, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 88, 89, 90, 91, 93, 94, 95, 96, 97, 98, 100, 101, 103, 104, 105, 107, 108, 110, 111, 113, 115, 116, 118, 119, 120, 122, 123, 125, 127, 128, 130, 132, 134, 135, 137, 139, 141, 143, 144, 146, 148, 150, 152, 154, 156, 158, 160, 163, 165, 167, 169, 171, 173, 175, 178, 180, 182, 185, 187, 189, 192, 194, 197, 199, 201, 204, 206, 209, 211, 214, 216, 219, 221, 224, 226, 229, 232, 234, 236, 239, 241, 245, 247, 250, 252, 255 )

            for (i in 0..255) {
                romance_arrayOfByte[i * 4] = romance_arrayOfInt1[i].toByte()
                romance_arrayOfByte[1 + i * 4] = romance_arrayOfInt2[i].toByte()
                romance_arrayOfByte[2 + i * 4] = romance_arrayOfInt3[i].toByte()
                romance_arrayOfByte[3 + i * 4] = romance_arrayOfInt4[i].toByte()
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
                ByteBuffer.wrap(romance_arrayOfByte)
            )
        }
    }
}
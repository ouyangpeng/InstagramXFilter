package com.poney.gpuimage.filter.adjust.common

import kotlin.jvm.JvmOverloads
import com.poney.gpuimage.filter.base.GPUImageAdjustFilter
import android.opengl.GLES20

/**
 * 对比度是画面黑与白的比值，也就是从黑到白的渐变层次。比值越大，从黑到白的渐变层次就越多，从而色彩表现越丰富。
 *
 * 对比度对视觉效果的影响非常关键，一般来说对比度越大，图像越清晰醒目，色彩也越鲜明艳丽；而对比度小，则会让整个画面都灰蒙蒙的。
 *
 * 简单的说，对比度是像素颜色和某个中值的差，它可以让明亮的颜色更明亮，让灰暗的颜色更灰暗。
 *
 * 这里实现个简单的线性对比度算法：
 *
 * 结果=中值差*对比度+中值
 */
class GPUImageContrastFilter @JvmOverloads constructor(private var contrast: Float = 1.0f) :
    GPUImageAdjustFilter(
        NO_FILTER_VERTEX_SHADER, CONTRAST_FRAGMENT_SHADER
    ) {
    private var contrastLocation = 0
    override fun onInit() {
        super.onInit()
        contrastLocation = GLES20.glGetUniformLocation(program, "contrast")
    }

    override fun onInitialized() {
        super.onInitialized()
        setContrast(contrast)
    }

    fun setContrast(range: Float) {
        contrast = range
        setFloat(contrastLocation, contrast)
    }

    companion object {
        const val CONTRAST_FRAGMENT_SHADER = "" +
                "varying highp vec2 textureCoordinate;\n" +
                " \n" +
                " uniform sampler2D inputImageTexture;\n" +
                " uniform lowp float contrast;\n" +
                " \n" +
                " void main()\n" +
                " {\n" +
                "     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n" +
                "     \n" +
                "     gl_FragColor = vec4(((textureColor.rgb - vec3(0.5)) * contrast + vec3(0.5)), textureColor.w);\n" +
                " }"
    }
}
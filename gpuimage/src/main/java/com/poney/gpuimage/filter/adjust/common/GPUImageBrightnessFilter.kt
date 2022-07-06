package com.poney.gpuimage.filter.adjust.common

import kotlin.jvm.JvmOverloads
import com.poney.gpuimage.filter.base.GPUImageAdjustFilter
import android.opengl.GLES20

/**
 * 亮度（luminance）是表示人眼对发光体或被照射物体表面的发光或反射光强度实际感受的物理量，亮度和光强这两个量在一般的日常用语中往往被混淆使用。简而言之，当任两个物体表面在照相时被拍摄出的最终结果是一样亮、或被眼睛看起来两个表面一样亮，它们就是亮度相同。
 *
 * 国际单位制中规定，“亮度”的符号是B，单位为尼特。
 *
 * 亮度(明度)反应了色彩的明暗程度，它和色相(H)、饱和度(S)共同构成HSL色彩空间。调整亮度只需要RGB色彩空间里面同时加上一个程度值。
 */
class GPUImageBrightnessFilter @JvmOverloads constructor(private var brightness: Float = 0.0f) :
    GPUImageAdjustFilter(
        NO_FILTER_VERTEX_SHADER, CONTRAST_FRAGMENT_SHADER
    ) {
    private var brightnessLocation = 0
    override fun onInit() {
        super.onInit()
        brightnessLocation = GLES20.glGetUniformLocation(program, "brightness")
    }

    override fun onInitialized() {
        super.onInitialized()
        setBrightness(brightness)
    }

    fun setBrightness(range: Float) {
        brightness = range
        setFloat(brightnessLocation, brightness)
    }

    companion object {
        const val CONTRAST_FRAGMENT_SHADER = "" +
                "varying highp vec2 textureCoordinate;\n" +
                " \n" +
                " uniform sampler2D inputImageTexture;\n" +
                " uniform lowp float brightness;\n" +
                " \n" +
                " void main()\n" +
                " {\n" +
                "     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n" +
                "     \n" +
                "     gl_FragColor = vec4(textureColor.rgb + vec3(brightness), textureColor.w);\n" +
                " }"
    }
}
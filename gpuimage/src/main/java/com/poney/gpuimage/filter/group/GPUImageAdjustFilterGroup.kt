package com.poney.gpuimage.filter.group


import com.poney.gpuimage.utils.OpenGlUtils.range
import com.poney.gpuimage.filter.base.GPUImageFilter
import com.poney.gpuimage.filter.adjust.common.GPUImageSharpenFilter
import com.poney.gpuimage.filter.adjust.common.GPUImageHueFilter
import com.poney.gpuimage.filter.adjust.common.GPUImageBrightnessFilter
import com.poney.gpuimage.filter.adjust.common.GPUImageContrastFilter
import com.poney.gpuimage.filter.adjust.common.GPUImageSaturationFilter
import com.poney.gpuimage.filter.adjust.common.GPUImageExposureFilter
import java.util.ArrayList

class GPUImageAdjustFilterGroup : GPUImageFilterGroup(initFilters()) {
    var contrastProgress = 50
        private set
    var saturationProgress = 50
        private set
    var exposureProgress = 50
        private set
    var sharpnessProgress = 50
        private set
    var brightnessProgress = 50
        private set
    var hueProgress = 50
        private set

    fun setSharpness(progress: Int) {
        sharpnessProgress = progress
        (filters!![5] as GPUImageSharpenFilter).setSharpness(range(progress.toFloat(), -4.0f, 4.0f))
    }

    fun setHue(progress: Int) {
        hueProgress = progress
        (filters!![3] as GPUImageHueFilter).setHue(range(progress.toFloat(), -180.0f, 180.0f))
    }

    fun setBrightness(progress: Int) {
        brightnessProgress = progress
        (filters!![1] as GPUImageBrightnessFilter).setBrightness(
            range(
                progress.toFloat(),
                -1.0f,
                1.0f
            )
        )
    }

    fun setContrast(progress: Int) {
        contrastProgress = progress
        (filters!![0] as GPUImageContrastFilter).setContrast(range(progress.toFloat(), 0.0f, 2.0f))
    }

    fun setSaturation(progress: Int) {
        saturationProgress = progress
        (filters!![4] as GPUImageSaturationFilter).setSaturation(
            range(
                progress.toFloat(),
                0.0f,
                2.0f
            )
        )
    }

    fun setExposure(progress: Int) {
        exposureProgress = progress
        (filters!![2] as GPUImageExposureFilter).setExposure(range(progress.toFloat(), -2.0f, 2.0f))
    }

    companion object {
        private fun initFilters(): MutableList<GPUImageFilter> {
            val filters: MutableList<GPUImageFilter> = ArrayList()
            filters.add(GPUImageContrastFilter())
            filters.add(GPUImageBrightnessFilter())
            filters.add(GPUImageExposureFilter())
            filters.add(GPUImageHueFilter())
            filters.add(GPUImageSaturationFilter())
            filters.add(GPUImageSharpenFilter())
            return filters
        }
    }
}
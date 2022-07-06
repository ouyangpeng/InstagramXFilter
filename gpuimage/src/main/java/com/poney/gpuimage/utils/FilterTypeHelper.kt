package com.poney.gpuimage.utils

import com.poney.gpuimage.filter.base.GPUImageFilterType
import com.poney.gpuimage.R
import com.poney.gpuimage.filter.base.GPUImageFilter
import com.poney.gpuimage.filter.blend.GPUImageFairytaleFilter
import com.poney.gpuimage.filter.blend.GPUImageSunRiseFilter
import com.poney.gpuimage.filter.blend.GPUImageWhiteCatFilter
import com.poney.gpuimage.filter.blend.GPUImageBlackCatFilter
import com.poney.gpuimage.filter.blend.GPUImageSkinWhitenFilter
import com.poney.gpuimage.filter.blend.GPUImageRomanceFilter
import com.poney.gpuimage.filter.blend.GPUImageSakuraFilter
import com.poney.gpuimage.filter.blend.GPUImageAmaroFilter
import com.poney.gpuimage.filter.blend.GPUImageWaldenFilter
import com.poney.gpuimage.filter.blend.GPUImageAntiqueFilter
import com.poney.gpuimage.filter.blend.GPUImageCalmFilter
import com.poney.gpuimage.filter.blend.GPUImageBrannanFilter
import com.poney.gpuimage.filter.blend.GPUImageBrooklynFilter
import com.poney.gpuimage.filter.blend.GPUImageEarlyBirdFilter
import com.poney.gpuimage.filter.blend.GPUImageFreudFilter
import com.poney.gpuimage.filter.blend.GPUImageHefeFilter
import com.poney.gpuimage.filter.blend.GPUImageHudsonFilter
import com.poney.gpuimage.filter.blend.GPUImageInkwellFilter
import com.poney.gpuimage.filter.blend.GPUImageKevinFilter
import com.poney.gpuimage.filter.blend.GPUImageLomoFilter
import com.poney.gpuimage.filter.blend.GPUImageN1977Filter
import com.poney.gpuimage.filter.blend.GPUImageNashvilleFilter
import com.poney.gpuimage.filter.blend.GPUImagePixarFilter
import com.poney.gpuimage.filter.blend.GPUImageRiseFilter
import com.poney.gpuimage.filter.blend.GPUImageSierraFilter
import com.poney.gpuimage.filter.blend.GPUImageSutroFilter
import com.poney.gpuimage.filter.blend.GPUImageToasterFilter
import com.poney.gpuimage.filter.blend.GPUImageValenciaFilter
import com.poney.gpuimage.filter.blend.GPUImageXproIIFilter
import com.poney.gpuimage.filter.blend.GPUImageEvergreenFilter
import com.poney.gpuimage.filter.blend.GPUImageHealthyFilter
import com.poney.gpuimage.filter.blend.GPUImageCoolFilter
import com.poney.gpuimage.filter.blend.GPUImageEmeraldFilter
import com.poney.gpuimage.filter.blend.GPUImageLatteFilter
import com.poney.gpuimage.filter.blend.GPUImageWarmFilter
import com.poney.gpuimage.filter.blend.GPUImageTenderFilter
import com.poney.gpuimage.filter.blend.GPUImageSweetsFilter
import com.poney.gpuimage.filter.blend.GPUImageNostalgiaFilter
import com.poney.gpuimage.filter.blend.GPUImageSunsetFilter
import com.poney.gpuimage.filter.blend.GPUImageCrayonFilter
import com.poney.gpuimage.filter.blend.GPUImageSketchFilter
import com.poney.gpuimage.filter.adjust.common.GPUImageBrightnessFilter
import com.poney.gpuimage.filter.adjust.common.GPUImageContrastFilter
import com.poney.gpuimage.filter.adjust.common.GPUImageExposureFilter
import com.poney.gpuimage.filter.adjust.common.GPUImageHueFilter
import com.poney.gpuimage.filter.adjust.common.GPUImageSaturationFilter
import com.poney.gpuimage.filter.adjust.common.GPUImageSharpenFilter

object FilterTypeHelper {
    fun filterType2Color(filterType: GPUImageFilterType?): Int {
        return when (filterType) {
            GPUImageFilterType.NONE -> R.color.filter_color_grey_light
            GPUImageFilterType.WHITECAT, GPUImageFilterType.BLACKCAT, GPUImageFilterType.SUNRISE, GPUImageFilterType.SUNSET -> R.color.filter_color_brown_light
            GPUImageFilterType.COOL -> R.color.filter_color_blue_dark
            GPUImageFilterType.EMERALD, GPUImageFilterType.EVERGREEN -> R.color.filter_color_blue_dark_dark
            GPUImageFilterType.FAIRYTALE -> R.color.filter_color_blue
            GPUImageFilterType.ROMANCE, GPUImageFilterType.SAKURA, GPUImageFilterType.WARM -> R.color.filter_color_pink
            GPUImageFilterType.AMARO, GPUImageFilterType.BRANNAN, GPUImageFilterType.BROOKLYN, GPUImageFilterType.EARLYBIRD, GPUImageFilterType.FREUD, GPUImageFilterType.HEFE, GPUImageFilterType.HUDSON, GPUImageFilterType.INKWELL, GPUImageFilterType.KEVIN, GPUImageFilterType.LOMO, GPUImageFilterType.N1977, GPUImageFilterType.NASHVILLE, GPUImageFilterType.PIXAR, GPUImageFilterType.RISE, GPUImageFilterType.SIERRA, GPUImageFilterType.SUTRO, GPUImageFilterType.TOASTER2, GPUImageFilterType.VALENCIA, GPUImageFilterType.WALDEN, GPUImageFilterType.XPROII -> R.color.filter_color_brown_dark
            GPUImageFilterType.ANTIQUE, GPUImageFilterType.NOSTALGIA -> R.color.filter_color_green_dark
            GPUImageFilterType.SKINWHITEN, GPUImageFilterType.HEALTHY -> R.color.filter_color_red
            GPUImageFilterType.SWEETS -> R.color.filter_color_red_dark
            GPUImageFilterType.CALM, GPUImageFilterType.LATTE, GPUImageFilterType.TENDER -> R.color.filter_color_brown
            else -> R.color.filter_color_grey_light
        }
    }

    fun filterType2Thumb(filterType: GPUImageFilterType?): Int {
        return when (filterType) {
            GPUImageFilterType.NONE -> R.drawable.filter_thumb_original
            GPUImageFilterType.WHITECAT -> R.drawable.filter_thumb_whitecat
            GPUImageFilterType.BLACKCAT -> R.drawable.filter_thumb_blackcat
            GPUImageFilterType.ROMANCE -> R.drawable.filter_thumb_romance
            GPUImageFilterType.SAKURA -> R.drawable.filter_thumb_sakura
            GPUImageFilterType.AMARO -> R.drawable.filter_thumb_amoro
            GPUImageFilterType.BRANNAN -> R.drawable.filter_thumb_brannan
            GPUImageFilterType.BROOKLYN -> R.drawable.filter_thumb_brooklyn
            GPUImageFilterType.EARLYBIRD -> R.drawable.filter_thumb_earlybird
            GPUImageFilterType.FREUD -> R.drawable.filter_thumb_freud
            GPUImageFilterType.HEFE -> R.drawable.filter_thumb_hefe
            GPUImageFilterType.HUDSON -> R.drawable.filter_thumb_hudson
            GPUImageFilterType.INKWELL -> R.drawable.filter_thumb_inkwell
            GPUImageFilterType.KEVIN -> R.drawable.filter_thumb_kevin
            GPUImageFilterType.LOMO -> R.drawable.filter_thumb_lomo
            GPUImageFilterType.N1977 -> R.drawable.filter_thumb_1977
            GPUImageFilterType.NASHVILLE -> R.drawable.filter_thumb_nashville
            GPUImageFilterType.PIXAR -> R.drawable.filter_thumb_piaxr
            GPUImageFilterType.RISE -> R.drawable.filter_thumb_rise
            GPUImageFilterType.SIERRA -> R.drawable.filter_thumb_sierra
            GPUImageFilterType.SUTRO -> R.drawable.filter_thumb_sutro
            GPUImageFilterType.TOASTER2 -> R.drawable.filter_thumb_toastero
            GPUImageFilterType.VALENCIA -> R.drawable.filter_thumb_valencia
            GPUImageFilterType.WALDEN -> R.drawable.filter_thumb_walden
            GPUImageFilterType.XPROII -> R.drawable.filter_thumb_xpro
            GPUImageFilterType.ANTIQUE -> R.drawable.filter_thumb_antique
            GPUImageFilterType.SKINWHITEN -> R.drawable.filter_thumb_beauty
            GPUImageFilterType.CALM -> R.drawable.filter_thumb_calm
            GPUImageFilterType.COOL -> R.drawable.filter_thumb_cool
            GPUImageFilterType.EMERALD -> R.drawable.filter_thumb_emerald
            GPUImageFilterType.EVERGREEN -> R.drawable.filter_thumb_evergreen
            GPUImageFilterType.FAIRYTALE -> R.drawable.filter_thumb_fairytale
            GPUImageFilterType.HEALTHY -> R.drawable.filter_thumb_healthy
            GPUImageFilterType.NOSTALGIA -> R.drawable.filter_thumb_nostalgia
            GPUImageFilterType.TENDER -> R.drawable.filter_thumb_tender
            GPUImageFilterType.SWEETS -> R.drawable.filter_thumb_sweets
            GPUImageFilterType.LATTE -> R.drawable.filter_thumb_latte
            GPUImageFilterType.WARM -> R.drawable.filter_thumb_warm
            GPUImageFilterType.SUNRISE -> R.drawable.filter_thumb_sunrise
            GPUImageFilterType.SUNSET -> R.drawable.filter_thumb_sunset
            GPUImageFilterType.CRAYON -> R.drawable.filter_thumb_crayon
            GPUImageFilterType.SKETCH -> R.drawable.filter_thumb_sketch
            else -> R.drawable.filter_thumb_original
        }
    }

    fun filterType2Name(filterType: GPUImageFilterType?): Int {
        return when (filterType) {
            GPUImageFilterType.NONE -> R.string.filter_none
            GPUImageFilterType.CONTRAST -> R.string.edit_contrast
            GPUImageFilterType.BRIGHTNESS -> R.string.edit_brightness
            GPUImageFilterType.EXPOSURE -> R.string.edit_exposure
            GPUImageFilterType.HUE -> R.string.edit_hue
            GPUImageFilterType.SATURATION -> R.string.edit_saturation
            GPUImageFilterType.SHARPEN -> R.string.edit_sharpness
            GPUImageFilterType.WHITECAT -> R.string.filter_whitecat
            GPUImageFilterType.BLACKCAT -> R.string.filter_blackcat
            GPUImageFilterType.ROMANCE -> R.string.filter_romance
            GPUImageFilterType.SAKURA -> R.string.filter_sakura
            GPUImageFilterType.AMARO -> R.string.filter_amaro
            GPUImageFilterType.BRANNAN -> R.string.filter_brannan
            GPUImageFilterType.BROOKLYN -> R.string.filter_brooklyn
            GPUImageFilterType.EARLYBIRD -> R.string.filter_Earlybird
            GPUImageFilterType.FREUD -> R.string.filter_freud
            GPUImageFilterType.HEFE -> R.string.filter_hefe
            GPUImageFilterType.HUDSON -> R.string.filter_hudson
            GPUImageFilterType.INKWELL -> R.string.filter_inkwell
            GPUImageFilterType.KEVIN -> R.string.filter_kevin
            GPUImageFilterType.LOMO -> R.string.filter_lomo
            GPUImageFilterType.N1977 -> R.string.filter_n1977
            GPUImageFilterType.NASHVILLE -> R.string.filter_nashville
            GPUImageFilterType.PIXAR -> R.string.filter_pixar
            GPUImageFilterType.RISE -> R.string.filter_rise
            GPUImageFilterType.SIERRA -> R.string.filter_sierra
            GPUImageFilterType.SUTRO -> R.string.filter_sutro
            GPUImageFilterType.TOASTER2 -> R.string.filter_toastero
            GPUImageFilterType.VALENCIA -> R.string.filter_valencia
            GPUImageFilterType.WALDEN -> R.string.filter_walden
            GPUImageFilterType.XPROII -> R.string.filter_xproii
            GPUImageFilterType.ANTIQUE -> R.string.filter_antique
            GPUImageFilterType.CALM -> R.string.filter_calm
            GPUImageFilterType.COOL -> R.string.filter_cool
            GPUImageFilterType.EMERALD -> R.string.filter_emerald
            GPUImageFilterType.EVERGREEN -> R.string.filter_evergreen
            GPUImageFilterType.FAIRYTALE -> R.string.filter_fairytale
            GPUImageFilterType.HEALTHY -> R.string.filter_healthy
            GPUImageFilterType.NOSTALGIA -> R.string.filter_nostalgia
            GPUImageFilterType.TENDER -> R.string.filter_tender
            GPUImageFilterType.SWEETS -> R.string.filter_sweets
            GPUImageFilterType.LATTE -> R.string.filter_latte
            GPUImageFilterType.WARM -> R.string.filter_warm
            GPUImageFilterType.SUNRISE -> R.string.filter_sunrise
            GPUImageFilterType.SUNSET -> R.string.filter_sunset
            GPUImageFilterType.SKINWHITEN -> R.string.filter_skinwhiten
            GPUImageFilterType.CRAYON -> R.string.filter_crayon
            GPUImageFilterType.SKETCH -> R.string.filter_sketch
            else -> R.string.filter_none
        }
    }

    fun createGroupFilterBy(filterType: GPUImageFilterType?): GPUImageFilter {
        return when (filterType) {
            GPUImageFilterType.FAIRYTALE -> GPUImageFairytaleFilter()
            GPUImageFilterType.SUNRISE -> GPUImageSunRiseFilter()
            GPUImageFilterType.WHITECAT -> GPUImageWhiteCatFilter()
            GPUImageFilterType.BLACKCAT -> GPUImageBlackCatFilter()
            GPUImageFilterType.SKINWHITEN -> GPUImageSkinWhitenFilter()
            GPUImageFilterType.ROMANCE -> GPUImageRomanceFilter()
            GPUImageFilterType.SAKURA -> GPUImageSakuraFilter()
            GPUImageFilterType.AMARO -> GPUImageAmaroFilter()
            GPUImageFilterType.WALDEN -> GPUImageWaldenFilter()
            GPUImageFilterType.ANTIQUE -> GPUImageAntiqueFilter()
            GPUImageFilterType.CALM -> GPUImageCalmFilter()
            GPUImageFilterType.BRANNAN -> GPUImageBrannanFilter()
            GPUImageFilterType.BROOKLYN -> GPUImageBrooklynFilter()
            GPUImageFilterType.EARLYBIRD -> GPUImageEarlyBirdFilter()
            GPUImageFilterType.FREUD -> GPUImageFreudFilter()
            GPUImageFilterType.HEFE -> GPUImageHefeFilter()
            GPUImageFilterType.HUDSON -> GPUImageHudsonFilter()
            GPUImageFilterType.INKWELL -> GPUImageInkwellFilter()
            GPUImageFilterType.KEVIN -> GPUImageKevinFilter()
            GPUImageFilterType.LOMO -> GPUImageLomoFilter()
            GPUImageFilterType.N1977 -> GPUImageN1977Filter()
            GPUImageFilterType.NASHVILLE -> GPUImageNashvilleFilter()
            GPUImageFilterType.PIXAR -> GPUImagePixarFilter()
            GPUImageFilterType.RISE -> GPUImageRiseFilter()
            GPUImageFilterType.SIERRA -> GPUImageSierraFilter()
            GPUImageFilterType.SUTRO -> GPUImageSutroFilter()
            GPUImageFilterType.TOASTER2 -> GPUImageToasterFilter()
            GPUImageFilterType.VALENCIA -> GPUImageValenciaFilter()
            GPUImageFilterType.XPROII -> GPUImageXproIIFilter()
            GPUImageFilterType.EVERGREEN -> GPUImageEvergreenFilter()
            GPUImageFilterType.HEALTHY -> GPUImageHealthyFilter()
            GPUImageFilterType.COOL -> GPUImageCoolFilter()
            GPUImageFilterType.EMERALD -> GPUImageEmeraldFilter()
            GPUImageFilterType.LATTE -> GPUImageLatteFilter()
            GPUImageFilterType.WARM -> GPUImageWarmFilter()
            GPUImageFilterType.TENDER -> GPUImageTenderFilter()
            GPUImageFilterType.SWEETS -> GPUImageSweetsFilter()
            GPUImageFilterType.NOSTALGIA -> GPUImageNostalgiaFilter()
            GPUImageFilterType.SUNSET -> GPUImageSunsetFilter()
            GPUImageFilterType.CRAYON -> GPUImageCrayonFilter()
            GPUImageFilterType.SKETCH -> GPUImageSketchFilter()
            GPUImageFilterType.BRIGHTNESS -> GPUImageBrightnessFilter()
            GPUImageFilterType.CONTRAST -> GPUImageContrastFilter()
            GPUImageFilterType.EXPOSURE -> GPUImageExposureFilter()
            GPUImageFilterType.HUE -> GPUImageHueFilter()
            GPUImageFilterType.SATURATION -> GPUImageSaturationFilter()
            GPUImageFilterType.SHARPEN -> GPUImageSharpenFilter()
            else -> GPUImageFilter()
        }
    }
}
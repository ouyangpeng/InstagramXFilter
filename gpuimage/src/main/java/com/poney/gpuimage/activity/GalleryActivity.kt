package com.poney.gpuimage.activity

import android.app.Activity
import com.poney.gpuimage.view.GPUImageView
import androidx.recyclerview.widget.RecyclerView
import com.poney.gpuimage.filter.group.GPUImageAdjustFilterGroup
import android.os.Bundle
import com.poney.gpuimage.R
import com.poney.gpuimage.filter.base.GPUImageParams
import com.poney.gpuimage.filter.base.GPUImageFilter
import com.poney.gpuimage.filter.group.GPUImageFilterGroup
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.poney.gpuimage.adapter.FilterAdapter
import com.poney.gpuimage.filter.base.FilterTypeList
import com.poney.gpuimage.filter.base.GPUImageFilterType
import com.poney.gpuimage.utils.FilterTypeHelper
import android.content.Intent
import android.view.View
import android.widget.*
import java.util.ArrayList

class GalleryActivity : Activity(), View.OnClickListener {
    val gpuImageView: GPUImageView by lazy { findViewById(R.id.gpu_image) }

    private val btnCameraFilter: ImageView by lazy { findViewById(R.id.btn_filter) }

    private val btnCameraAdjust: ImageView by lazy { findViewById(R.id.btn_adjust) }

    private val filterListView: RecyclerView by lazy { findViewById(R.id.filter_listView) }

    private val seekBar: SeekBar by lazy { findViewById(R.id.seekBar) }

    private val fragmentAdjustRadiogroup: RadioGroup by lazy { findViewById(R.id.fragment_adjust_radiogroup) }

    private val filterAdjust: LinearLayout by lazy { findViewById(R.id.filter_adjust) }

    private lateinit var gpuImageAdjustFilterGroup: GPUImageAdjustFilterGroup

    private var mCheckedId = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        GPUImageParams.init(this)
        initAction()
        initFilterList()
        startPhotoPicker()
    }

    private fun initAction() {
        btnCameraAdjust.setOnClickListener(this)
        btnCameraFilter.setOnClickListener(this)
        fragmentAdjustRadiogroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, checkedId ->
            if (checkedId == -1) {
                seekBar.visibility = View.GONE
                return@OnCheckedChangeListener
            }
            seekBar.visibility = View.VISIBLE
            val originalFilter = gpuImageView.filter
            if (originalFilter is GPUImageFilterGroup) {
                val filters = originalFilter.filters
                gpuImageAdjustFilterGroup = filters?.get(1) as GPUImageAdjustFilterGroup
                mCheckedId = checkedId
                when (checkedId) {
                    // 对比度
                    R.id.fragment_radio_contrast -> {
                        val contrastProgress: Int = gpuImageAdjustFilterGroup.contrastProgress
                        seekBar.progress = contrastProgress
                        gpuImageAdjustFilterGroup.setContrast(contrastProgress)
                    }
                    // 饱和度
                    R.id.fragment_radio_saturation -> {
                        val saturationProgress: Int = gpuImageAdjustFilterGroup.saturationProgress
                        seekBar.progress = saturationProgress
                        gpuImageAdjustFilterGroup.setSaturation(saturationProgress)
                    }
                    // 曝光
                    R.id.fragment_radio_exposure -> {
                        val exposureProgress: Int = gpuImageAdjustFilterGroup.exposureProgress
                        seekBar.progress = exposureProgress
                        gpuImageAdjustFilterGroup.setExposure(exposureProgress)
                    }
                    // 锐化
                    R.id.fragment_radio_sharpness -> {
                        val sharpnessProgress: Int = gpuImageAdjustFilterGroup.sharpnessProgress
                        seekBar.progress = sharpnessProgress
                        gpuImageAdjustFilterGroup.setSharpness(sharpnessProgress)
                    }
                    // 亮度
                    R.id.fragment_radio_bright -> {
                        val brightnessProgress: Int = gpuImageAdjustFilterGroup.brightnessProgress
                        seekBar.progress = brightnessProgress
                        gpuImageAdjustFilterGroup.setBrightness(brightnessProgress)
                    }
                    // 色调
                    R.id.fragment_radio_hue -> {
                        val hueProgress: Int = gpuImageAdjustFilterGroup.hueProgress
                        seekBar.progress = hueProgress
                        gpuImageAdjustFilterGroup.setHue(hueProgress)
                    }
                }
            }
        })

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                when (mCheckedId) {
                    // 对比度
                    R.id.fragment_radio_contrast -> {
                        gpuImageAdjustFilterGroup.setContrast(progress)
                    }
                    // 饱和度
                    R.id.fragment_radio_saturation -> {
                        gpuImageAdjustFilterGroup.setSaturation(progress)
                    }
                    // 曝光
                    R.id.fragment_radio_exposure -> {
                        gpuImageAdjustFilterGroup.setExposure(progress)
                    }
                    // 锐化
                    R.id.fragment_radio_sharpness -> {
                        gpuImageAdjustFilterGroup.setSharpness(progress)
                    }
                    // 亮度
                    R.id.fragment_radio_bright -> {
                        gpuImageAdjustFilterGroup.setBrightness(progress)
                    }
                    // 色调
                    R.id.fragment_radio_hue -> {
                        gpuImageAdjustFilterGroup.setHue(progress)
                    }
                }
                gpuImageView.requestRender()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    private fun getFilterList(
        originalFilter: GPUImageFilter,
        gpuImageAdjustFilterGroup: GPUImageAdjustFilterGroup
    ): MutableList<GPUImageFilter> {
        val groupFilters: MutableList<GPUImageFilter> = ArrayList(2)
        groupFilters.add(originalFilter)
        groupFilters.add(gpuImageAdjustFilterGroup)
        return groupFilters
    }

    private fun initFilterList() {
        filterListView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val filterAdapter = FilterAdapter(this, FilterTypeList.TYPES)
        filterAdapter.setOnFilterChangeListener(object : FilterAdapter.OnFilterChangeListener {
            override fun onFilterChanged(filterType: GPUImageFilterType?) {
                switchFilterTo(FilterTypeHelper.createGroupFilterBy(filterType))
            }
        })
        filterListView.adapter = filterAdapter
    }

    private fun switchFilterTo(filter: GPUImageFilter) {
        fragmentAdjustRadiogroup.clearCheck()
        val originalFilter = gpuImageView.filter
        if (originalFilter is GPUImageFilterGroup) {
            val gpuImageFilter = originalFilter.filters?.get(0)
            if (gpuImageFilter == null || gpuImageFilter.javaClass != filter.javaClass) {
                gpuImageView.filter =
                    GPUImageFilterGroup(getFilterList(filter, GPUImageAdjustFilterGroup()))
            }
        }
    }

    private fun startPhotoPicker() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, REQUEST_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            REQUEST_PICK_IMAGE -> {
                if (resultCode == RESULT_OK) {
                    gpuImageView.filter =
                        GPUImageFilterGroup(
                            getFilterList(
                                GPUImageFilter(),
                                GPUImageAdjustFilterGroup()
                            )
                        )
                    gpuImageView.setImage(data.data)
                } else {
                    finish()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onClick(v: View) {
        filterListView.visibility = View.GONE
        filterAdjust.visibility = View.GONE
        if (v.id == R.id.btn_filter) {
            filterListView.visibility = View.VISIBLE
        } else if (v.id == R.id.btn_adjust) {
            filterAdjust.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val REQUEST_PICK_IMAGE = 1
    }
}
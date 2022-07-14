package com.poney.gpuimage.activity

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poney.gpuimage.R
import com.poney.gpuimage.adapter.FilterAdapter
import com.poney.gpuimage.camera.Camera1Loader
import com.poney.gpuimage.camera.Camera2Loader
import com.poney.gpuimage.camera.CameraLoader
import com.poney.gpuimage.camera.doOnLayout
import com.poney.gpuimage.filter.base.FilterTypeList
import com.poney.gpuimage.filter.base.GPUImageFilter
import com.poney.gpuimage.filter.base.GPUImageFilterType
import com.poney.gpuimage.filter.base.GPUImageParams
import com.poney.gpuimage.filter.group.GPUImageAdjustFilterGroup
import com.poney.gpuimage.filter.group.GPUImageFilterGroup
import com.poney.gpuimage.utils.FilterTypeHelper
import com.poney.gpuimage.utils.Rotation
import com.poney.gpuimage.view.GPUImageView
import java.util.*

class CameraActivity : Activity(), View.OnClickListener {
    private lateinit var gpuImageAdjustFilterGroup: GPUImageAdjustFilterGroup
    private var mCheckedId: Int = 0
    private val gpuImageView: GPUImageView by lazy { findViewById(R.id.gpu_image) }
    private val filterListView: RecyclerView by lazy { findViewById(R.id.filter_listView) }
    private val fragmentAdjustRadioGroup: RadioGroup by lazy { findViewById(R.id.fragment_adjust_radiogroup) }
    private val btnAdjust: ImageView by lazy { findViewById(R.id.btn_adjust) }
    private val btnFilter: ImageView by lazy { findViewById(R.id.btn_filter) }
    private val btnCamera: ImageButton by lazy { findViewById(R.id.button_capture) }
    private val imgSwitchCamera: ImageView by lazy { findViewById(R.id.img_switch_camera) }
    private val filterAdjust: LinearLayout by lazy { findViewById(R.id.filter_adjust) }
    private val seekBar: SeekBar by lazy { findViewById(R.id.seekBar) }
    private val cameraLoader: CameraLoader by lazy {
        if (Build.VERSION.SDK_INT < 21) {
            Camera1Loader(this)
        } else {
            Camera2Loader(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        GPUImageParams.init(this)
        initAction()
        initFilterList()
        initCamera()
    }

    private fun initCamera() {
        cameraLoader.setOnPreviewFrameListener { data: ByteArray?, width: Int?, height: Int? ->
            gpuImageView.updatePreviewFrame(data, width!!, height!!)
        }
        gpuImageView.filter =
            GPUImageFilterGroup(getFilterList(GPUImageFilter(), GPUImageAdjustFilterGroup()))
        gpuImageView.setRotation(getRotation(cameraLoader.getCameraOrientation()))
        gpuImageView.setRenderMode(GPUImageView.RENDERMODE_CONTINUOUSLY)
    }

    private fun getRotation(cameraOrientation: Int): Rotation {
        return when (cameraOrientation) {
            90 -> Rotation.ROTATION_90
            180 -> Rotation.ROTATION_180
            270 -> Rotation.ROTATION_270
            else -> Rotation.NORMAL
        }
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
        fragmentAdjustRadioGroup.clearCheck()
        val originalFilter = gpuImageView.filter
        if (originalFilter is GPUImageFilterGroup) {
            val gpuImageFilter = originalFilter.filters?.get(0)
            if (gpuImageFilter == null || gpuImageFilter.javaClass != filter.javaClass) {
                gpuImageView.filter =
                    GPUImageFilterGroup(getFilterList(filter, GPUImageAdjustFilterGroup()))
            }
        }
    }

    private fun initAction() {
        btnAdjust.setOnClickListener(this)
        btnFilter.setOnClickListener(this)
        fragmentAdjustRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, checkedId ->
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
        btnCamera.setOnClickListener {
            saveSnapshot()
        }
        imgSwitchCamera.run {
            if (!cameraLoader.hasMultipleCamera()) {
                visibility = View.GONE
            }
            setOnClickListener {
                cameraLoader.switchCamera()
                gpuImageView.setRotation(getRotation(cameraLoader.getCameraOrientation()))
            }
        }
    }

    private fun getFilterList(
        originalFilter: GPUImageFilter?,
        gpuImageAdjustFilterGroup: GPUImageAdjustFilterGroup
    ): MutableList<GPUImageFilter> {
        val groupFilters: MutableList<GPUImageFilter> = ArrayList(2)
        groupFilters.add(originalFilter!!)
        groupFilters.add(gpuImageAdjustFilterGroup)
        return groupFilters
    }

    private fun saveSnapshot() {
        val folderName = "GPUImage"
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        gpuImageView.saveToPictures(folderName, fileName) {
            Toast.makeText(this, "$folderName/$fileName saved", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onClick(v: View) {
        filterListView.visibility = View.GONE
        filterAdjust.visibility = View.GONE
        when (v.id) {
            R.id.btn_filter -> {
                filterListView.visibility = View.VISIBLE
            }
            R.id.btn_adjust -> {
                filterAdjust.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        gpuImageView.doOnLayout {
            cameraLoader.onResume(it.width, it.height)
        }
    }

    override fun onPause() {
        cameraLoader.onPause()
        super.onPause()
    }
}
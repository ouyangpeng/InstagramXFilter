package com.poney.xfilter

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tbruyelle.rxpermissions2.RxPermissions
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.widget.Button
import com.poney.gpuimage.activity.GalleryActivity
import com.poney.gpuimage.activity.CameraActivity

class MainActivity : AppCompatActivity(),View.OnClickListener {
    private val buttonGallery: Button by lazy { findViewById(R.id.button_gallery) }
    private val buttonCamera: Button by lazy { findViewById(R.id.button_camera) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()

        buttonGallery.setOnClickListener(this)
        buttonCamera.setOnClickListener(this)
    }

    private fun requestPermissions() {
        val rxPermissions = RxPermissions(this)
        val disposable = rxPermissions.request(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
            .subscribe { permission: Boolean ->
                if (!permission) {
                    Toast.makeText(this@MainActivity, "请授予相关权限", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_gallery -> startActivity(Intent(this, GalleryActivity::class.java))
            R.id.button_camera -> startActivity(Intent(this, CameraActivity::class.java))
        }
    }
}
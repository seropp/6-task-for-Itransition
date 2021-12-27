package com.example.yourownmeme

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.MotionEvent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var btnOpenCamera: ImageButton
    private lateinit var imageText: TextView
    private lateinit var enterText: EditText
    private lateinit var btnOk: Button
    lateinit var root: LinearLayout



    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root = findViewById(R.id.main_layout)

        imageView = findViewById(R.id.image_view)
        imageText = findViewById(R.id.text_on_photo)
        imageText.setTextColor(Color.BLACK)
        btnOpenCamera = findViewById(R.id.btn_open_camera)
        enterText = findViewById(R.id.enter_text)
        btnOk = findViewById(R.id.confirm_button)



        btnOpenCamera.setOnClickListener { openCamera() }

        btnOk.setOnClickListener {
            val text = enterText.text.toString()
            imageText.text = text
        }

        imageText.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                view.x = (motionEvent.rawX - view.width / 2.0f)
                view.y = (motionEvent.rawY - 400.0f - view.height / 2.0f)
            }

            return@setOnTouchListener true
        }


    }


    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
        }

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null && data.extras != null) {
            val capturedImage = data.extras!!.get("data") as Bitmap
            imageView.setImageBitmap(capturedImage)
        }
    }
}

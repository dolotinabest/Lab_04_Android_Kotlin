package com.example.myapplication

import GyroSizer
import GyroView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class GyroViewDemo : AppCompatActivity() {

    private var gyroView: GyroView? = null
    private val imageUrl = "https://images.pexels.com/photos/47358/ford-classic-car-automobile-car-47358.jpeg?auto=compress&cs=tinysrgb&h=650&w=940"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gyro_view_demo)
        initView()
    }

    private fun initView() {
        gyroView = findViewById(R.id.head_image)
        gyroView?.post {
            val width = gyroView!!.width
            val height = gyroView!!.height

            Picasso.get()
                .load(imageUrl)
                .transform(GyroSizer(width, height))
                .into(gyroView!!)
        }
    }

    override fun onResume() {
        super.onResume()
        GyroListener.instance.attachListener(this)
    }

    override fun onPause() {
        super.onPause()
        GyroListener.instance.detachListener(this)
    }
}

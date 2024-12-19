package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var count = 0
    private var seekBarProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Restore saved state
        savedInstanceState?.let {
            count = it.getInt("COUNT_KEY", 0)
            seekBarProgress = it.getInt("SEEK_BAR_KEY", 0)
        }

        val textView = findViewById<TextView>(R.id.textView)
        textView.text = count.toString()

        val seekBar = findViewById<SeekBar>(R.id.seekBar2)
        val progressTextView = findViewById<TextView>(R.id.textView2)
        seekBar.progress = seekBarProgress
        progressTextView.text = seekBarProgress.toString()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBarProgress = progress
                progressTextView.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        findViewById<Button>(R.id.sendEmailButton).setOnClickListener {
            sendEmail()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("COUNT_KEY", count)
        outState.putInt("SEEK_BAR_KEY", seekBarProgress)
    }

    fun toastMe(view: View) {
        Toast.makeText(this, "Hello Toast!", Toast.LENGTH_SHORT).show()
    }

    fun countMe(view: View) {
        count++
        findViewById<TextView>(R.id.textView).text = count.toString()
    }

    fun randomMe(view: View) {
        val randomIntent = Intent(this, SecondActivity::class.java)
        randomIntent.putExtra(SecondActivity.TOTAL_COUNT, count)
        startActivity(randomIntent)
    }

    fun storage(view: View) {
        val storageIntent = Intent(this, StorageActivity::class.java)
        startActivity(storageIntent)
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Тема письма")
            putExtra(Intent.EXTRA_TEXT, "Текст письма")
        }

        val chooser = Intent.createChooser(intent, "Выберите приложение для отправки e-mail")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }
    }
}

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val sendEmailButton: Button = findViewById(R.id.sendEmailButton)
        sendEmailButton.setOnClickListener {
            sendEmail()
        }

        val seekBar = findViewById<SeekBar>(R.id.seekBar2)
        val progressTextView = findViewById<TextView>(R.id.textView2)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progressTextView.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }
    fun toastMe(view: View){
        val myToast = Toast.makeText(this,"Hello Toast!",Toast.LENGTH_SHORT)
        myToast.show()

    }
    fun countMe(view: View) {
        val textView = findViewById<TextView>(R.id.textView)
        var count: Int = Integer.parseInt(textView.text.toString())
        count++
        textView.text = count.toString()
    }
    fun randomMe(view: View) {
        val randomIntent = Intent(this, SecondActivity::class.java)
        val textView = findViewById<TextView>(R.id.textView)
        val countString = textView.text.toString()
        val count = Integer.parseInt(countString)
        randomIntent.putExtra(SecondActivity.TOTAL_COUNT, count)
        startActivity(randomIntent)

    }
    fun storage(view: View) {
        val storageIntent = Intent(this, StorageActivity::class.java)
        startActivity(storageIntent)
    }



        fun sendEmail() {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822" //
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
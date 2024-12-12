package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second2)
        showRandomNumber()

    }
    companion object {
        const val TOTAL_COUNT = "total_count"
    }
    fun showRandomNumber(){
        val count = intent.getIntExtra( TOTAL_COUNT,0)
        val random = Random(0)
        var randomInt = 0
        if (count>0){
            randomInt = random.nextInt(count + 1)

        }
        val textview_random = findViewById<TextView>(R.id.textView_random)
        val textview_label = findViewById<TextView>(R.id.textview_label)
        textview_random.text = Integer.toString(randomInt)
        textview_label.text = getString(R.string.random_heading,count)
    }
}

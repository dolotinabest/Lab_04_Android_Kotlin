package com.example.myapplication
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*


class StorageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        val fileName = findViewById<EditText>(R.id.editFile)
        val fileData = findViewById<EditText>(R.id.editFile2)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonView = findViewById<Button>(R.id.buttonView)

        buttonSave.setOnClickListener {
            val file = fileName.text.toString()
            val data = fileData.text.toString()

            if (file.isNotBlank() && data.isNotBlank()) {
                try {
                    openFileOutput(file, MODE_PRIVATE).use { fileOutputStream ->
                        fileOutputStream.write(data.toByteArray())
                    }

                    Toast.makeText(applicationContext, "Data saved", Toast.LENGTH_SHORT).show()
                    fileName.text.clear()
                    fileData.text.clear()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Error saving file", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "File name and data cannot be blank", Toast.LENGTH_SHORT).show()
            }
        }

        buttonView.setOnClickListener {
            val file = fileName.text.toString()

            if (file.isNotBlank()) {
                try {
                    openFileInput(file).use { fileInputStream ->
                        val inputStreamReader = InputStreamReader(fileInputStream)
                        val bufferedReader = BufferedReader(inputStreamReader)

                        val stringBuilder = StringBuilder()
                        var line: String?
                        while (bufferedReader.readLine().also { line = it } != null) {
                            stringBuilder.append(line).append("\n")
                        }

                        fileData.setText(stringBuilder.toString().trim())
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "File not found", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Error reading file", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "File name cannot be blank", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

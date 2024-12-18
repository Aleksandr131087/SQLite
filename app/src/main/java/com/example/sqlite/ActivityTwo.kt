package com.example.sqlite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityTwo : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var positionSpinner: Spinner
    private lateinit var phoneEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var getDataButton: Button
    private lateinit var deleteButton: Button
    private lateinit var outputTextView: TextView
    var databaseHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)

        nameEditText = findViewById(R.id.nameEditText)
        positionSpinner = findViewById(R.id.positionSpinner)
        phoneEditText = findViewById(R.id.phoneEditText)
        saveButton = findViewById(R.id.saveButton)
        getDataButton = findViewById(R.id.getDataButton)
        deleteButton = findViewById(R.id.deleteButton)
        outputTextView = findViewById(R.id.outputTextView)



        val positions = arrayOf("выберите профессию", "Менеджер", "Разработчик", "Дизайнер", "Энергетик", "Инженер")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, positions)
        positionSpinner.adapter = adapter

        saveButton.setOnClickListener { saveData() }
        getDataButton.setOnClickListener { getData() }
        deleteButton.setOnClickListener { deleteData() }
    }

    private fun saveData() {
        val name = nameEditText.text.toString()
        val position = positionSpinner.selectedItem.toString()
        val phone = phoneEditText.text.toString()

        if (databaseHelper.insertData(name, position, phone)) {
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Ошибка сохранения данных", Toast.LENGTH_SHORT).show()
        }

    nameEditText.text.clear()
    phoneEditText.text.clear()
       }
    @SuppressLint("Range")
    private fun getData() {
        val cursor = databaseHelper.getData()
        val stringBuilder = StringBuilder()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val position = cursor.getString(cursor.getColumnIndex("position"))
                val phone = cursor.getString(cursor.getColumnIndex("phone"))
                stringBuilder.append("ID: $id, Имя: $name, Должность: $position, Телефон: $phone\n")
            } while (cursor.moveToNext())
        }
        cursor.close()
        outputTextView.text = stringBuilder.toString()
    }

    private fun deleteData() {
databaseHelper.deleteData()
        outputTextView.text = ""

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}



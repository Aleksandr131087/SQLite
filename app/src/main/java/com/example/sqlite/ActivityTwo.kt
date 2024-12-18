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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityTwo : AppCompatActivity() {

    private lateinit var toolbar:Toolbar
private lateinit var nameET:EditText
private lateinit var phoneET:EditText
private lateinit var spinner:Spinner
private lateinit var saveBTN:Button
private lateinit var deleteBTN:Button
private lateinit var getBTN:Button
private lateinit var nameTV: TextView
private lateinit var phoneTV:TextView
 private lateinit var  professionTV: TextView

private var db = DBHelper(this, null)



    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        professionTV = findViewById(R.id.professionTV)
        phoneTV = findViewById(R.id.phoneTV)
        nameTV = findViewById(R.id.nameTV)
        getBTN = findViewById(R.id.getBTN)
        deleteBTN = findViewById(R.id.deleteBTN)
        saveBTN = findViewById(R.id.saveBTN)
        spinner = findViewById(R.id.spinner)
        phoneET = findViewById(R.id.phoneET)
        nameET = findViewById(R.id.nameET)


        val positions = arrayOf("Менеджер", "Разработчик", "Дизайнер")
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, positions)

        saveBTN.setOnClickListener {
            val name = nameET.text.toString()
            val phone = phoneET.text.toString()
        val profession = spinner.selectedItem.toString()
            db.addName(name,phone, profession)
            Toast.makeText(this, "$name $phone добавлены в базу данных", Toast.LENGTH_SHORT).show()
       nameET.text.clear()
            phoneET.text.clear()
        }

        getBTN.setOnClickListener {
            val cursor = db.getInfo()
            if (cursor!=null && cursor.moveToFirst()){
                cursor.moveToFirst()
                nameTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME))+"\n")
                phoneTV.append(cursor!!.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE))+"\n")
                professionTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PROFESSION))+"\n")
            }
            while (cursor!!.moveToNext()){
                nameTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME))+"\n")
                phoneTV.append(cursor!!.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE))+"\n")
                professionTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PROFESSION))+"\n")
            }
cursor.close()
        }

        deleteBTN.setOnClickListener {
db.removaAll()
            nameTV.text = ""
            phoneTV.text= " "

        }

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
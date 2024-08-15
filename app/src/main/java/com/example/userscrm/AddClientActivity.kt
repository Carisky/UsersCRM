package com.example.userscrm

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.userscrm.data.DatabaseHelper
import com.example.userscrm.data.models.Client

import java.util.Date

class AddClientActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_client)

        db = DatabaseHelper(this)

        val etClientSurname = findViewById<EditText>(R.id.etClientSurname)
        val etClientName = findViewById<EditText>(R.id.etClientName)
        val etClientPhone = findViewById<EditText>(R.id.etClientPhone)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val surname = etClientSurname.text.toString()
            val name = etClientName.text.toString()
            val phone = etClientPhone.text.toString()
            val date = Date()

            if (surname.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty()) {
                val client = Client(0, surname, name, phone, date)
                db.addClient(client)
                setResult(RESULT_OK)
                finish()
            } else {
                // Handle empty fields, maybe show a Toast
            }
        }
    }
}

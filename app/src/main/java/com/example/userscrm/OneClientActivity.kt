package com.example.userscrm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.userscrm.data.DatabaseHelper
import com.example.userscrm.data.models.Client

class OneClientActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_client)

        db = DatabaseHelper(this)

        val client = intent.getSerializableExtra("client") as Client

        val etClientSurname = findViewById<EditText>(R.id.etClientSurname)
        val etClientName = findViewById<EditText>(R.id.etClientName)
        val etClientPhone = findViewById<EditText>(R.id.etClientPhone)

        etClientSurname.setText(client.surname)
        etClientName.setText(client.name)
        etClientPhone.setText(client.phone)

        val btnClose = findViewById<Button>(R.id.btnClose)
        btnClose.setOnClickListener {
            finish()
        }
    }
}

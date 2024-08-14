package com.example.userscrm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userscrm.adapters.ClientAdapter
import com.example.userscrm.data.DatabaseHelper
import com.example.userscrm.data.models.Client
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var clientAdapter: ClientAdapter
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvClients)
        recyclerView.layoutManager = LinearLayoutManager(this)

        db = DatabaseHelper(this)

        // Add some dummy data if the table is empty (optional)
        if (db.getAllClients().isEmpty()) {
            db.addClient(Client(1, "Ivanov", "Ivan", "+380501234567", Date()))
            db.addClient(Client(2, "Petrov", "Petr", "+380501234568", Date()))
            db.addClient(Client(3, "Sidorov", "Sidr", "+380501234569", Date()))
        }

        val clients = db.getAllClients()
        clientAdapter = ClientAdapter(this, clients)
        recyclerView.adapter = clientAdapter
    }
}

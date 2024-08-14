package com.example.userscrm

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userscrm.adapters.ClientAdapter
import com.example.userscrm.data.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var clientAdapter: ClientAdapter
    private lateinit var db: DatabaseHelper
    private lateinit var addClientLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvClients)
        recyclerView.layoutManager = LinearLayoutManager(this)

        db = DatabaseHelper(this)
        val clients = db.getAllClients()

        clientAdapter = ClientAdapter(this, clients)
        recyclerView.adapter = clientAdapter

        addClientLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                clientAdapter.setClients(db.getAllClients())
                clientAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_client -> {
                val intent = Intent(this, AddClientActivity::class.java)
                addClientLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

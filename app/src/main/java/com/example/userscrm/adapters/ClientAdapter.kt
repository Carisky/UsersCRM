package com.example.userscrm.adapters
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.userscrm.OneClientActivity
import com.example.userscrm.R
import com.example.userscrm.data.DatabaseHelper
import com.example.userscrm.data.models.Client
import com.google.gson.Gson
import java.io.File

class ClientAdapter(private val context: Context, private var clients: List<Client>) :
    RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_client, parent, false)
        return ClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = clients[position]
        holder.tvClientName.text = "${client.surname} ${client.name}"
        holder.tvClientPhone.text = client.phone

        // Обробка кліку для відкриття екрану з деталями клієнта
        holder.itemView.setOnClickListener {
            val intent = Intent(context, OneClientActivity::class.java).apply {
                putExtra("client", client)
            }
            context.startActivity(intent)
        }

        // Довге натискання для відкриття PopupMenu з пунктом Delete
        holder.itemView.setOnLongClickListener {
            val popup = PopupMenu(context, holder.itemView)
            popup.inflate(R.menu.client_menu) // створіть XML файл меню з пунктом Delete
            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.action_delete -> {
                        // Видалення клієнта з бази даних
                        val dbHelper = DatabaseHelper(context)
                        dbHelper.deleteClient(client.id)

                        // Оновлення списку в RecyclerView
                        clients = clients.toMutableList().apply {
                            removeAt(position)
                        }
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, clients.size)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
            true
        }
    }

    override fun getItemCount(): Int = clients.size

    fun setClients(newClients: List<Client>) {
        this.clients = newClients
    }

    private fun exportClientToJson(context: Context, client: Client) {
        val gson = Gson()
        val clientJson = gson.toJson(client)
        val fileName = "${client.surname}_${client.name}.json"
        val file = File(context.getExternalFilesDir(null), fileName)

        file.writeText(clientJson)

        Toast.makeText(context, "Client exported to ${file.absolutePath}", Toast.LENGTH_LONG).show()
    }

    inner class ClientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvClientName: TextView = itemView.findViewById(R.id.tvClientName)
        val tvClientPhone: TextView = itemView.findViewById(R.id.tvClientPhone)
    }
}

package com.example.userscrm.adapters
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.userscrm.OneClientActivity
import com.example.userscrm.R
import com.example.userscrm.data.models.Client

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

        holder.itemView.setOnClickListener {
            val intent = Intent(context, OneClientActivity::class.java).apply {
                putExtra("client", client)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = clients.size

    fun setClients(newClients: List<Client>) {
        this.clients = newClients
    }

    inner class ClientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvClientName: TextView = itemView.findViewById(R.id.tvClientName)
        val tvClientPhone: TextView = itemView.findViewById(R.id.tvClientPhone)
    }
}

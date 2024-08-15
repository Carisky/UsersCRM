package com.example.userscrm.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.userscrm.data.models.Client
import java.util.Date

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "clients.db"
        private const val TABLE_CLIENTS = "clients"

        private const val COLUMN_ID = "id"
        private const val COLUMN_SURNAME = "surname"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_CLIENTS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_SURNAME TEXT, "
                + "$COLUMN_NAME TEXT, "
                + "$COLUMN_PHONE TEXT, "
                + "$COLUMN_DATE TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTS")
        onCreate(db)
    }

    fun addClient(client: Client) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_SURNAME, client.surname)
        contentValues.put(COLUMN_NAME, client.name)
        contentValues.put(COLUMN_PHONE, client.phone)
        contentValues.put(COLUMN_DATE, client.date.time)

        db.insert(TABLE_CLIENTS, null, contentValues)
        db.close()
    }

    fun getAllClients(): List<Client> {
        val clients = mutableListOf<Client>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CLIENTS", null)

        if (cursor.moveToFirst()) {
            do {
                val client = Client(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                    Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE)))
                )
                clients.add(client)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return clients
    }

    fun deleteClient(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_CLIENTS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

}

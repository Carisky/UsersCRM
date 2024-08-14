package com.example.userscrm.data.models

import java.io.Serializable
import java.util.Date

data class Client(
    val id: Int,
    val surname: String,
    val name: String,
    val phone: String,
    val date: Date
) : Serializable
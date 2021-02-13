package com.example.desafioandroidmobills.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Transaction(
    var id: String? = null,
    var value: Float = 0F,
    val description: String = "",
    var date: Date? = null,
    val paid: Boolean? = null
) : Parcelable {

    fun setValueNotEmpty(s: String) {
        if (s.isNotEmpty()) {
            value = s.toFloat()
        }
    }
}
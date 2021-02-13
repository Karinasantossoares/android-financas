package com.example.desafioandroidmobills.ui.component

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet
import java.text.SimpleDateFormat
import java.util.*


class   DateDialog(
    private val context: Context
) {
    private lateinit var datePickerDialog: DatePickerDialog

    fun showCalendar() {
        datePickerDialog.show()
    }

     fun bindCalendar(onSuccess: (Date) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        datePickerDialog = DatePickerDialog(context, { _, yearSelected, monthSelected, daySelected ->
            val calendarSelected = Calendar.getInstance(Locale.getDefault())
            calendarSelected.set(yearSelected, monthSelected, daySelected)
            onSuccess.invoke(calendarSelected.time)
        }, year, month, day)
    }
}
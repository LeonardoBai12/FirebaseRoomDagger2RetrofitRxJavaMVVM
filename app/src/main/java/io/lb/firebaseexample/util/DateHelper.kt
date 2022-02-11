package io.lb.firebaseexample.util

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun datePickerDialog(
        context: Context,
        typedDate: String,
        onDateSet: DatePickerDialog.OnDateSetListener
    ): DatePickerDialog {
        return if (typedDate.isEmpty()) {
            buildDatePickerDialog(context, onDateSet)
        } else {
            buildDatePickerDialog(context, typedDate, onDateSet)
        }
    }

    fun buildDatePickerDialog(
        context: Context,
        onDateSet: DatePickerDialog.OnDateSetListener
    ): DatePickerDialog {
        val calendar = Calendar.getInstance()
        return DatePickerDialog(
            context,
            onDateSet,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    fun buildDatePickerDialog(
        context: Context,
        dateText: String,
        onDateSet: DatePickerDialog.OnDateSetListener
    ): DatePickerDialog {
        val form = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val date = form.parse(dateText) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date

        return DatePickerDialog(
            context,
            onDateSet,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    fun dateToString(
        day: Int,
        month: Int,
        year: Int,
    ): String {
        val date = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            .parse("$day-${month + 1}-$year")!!
        return SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(date)
    }
}
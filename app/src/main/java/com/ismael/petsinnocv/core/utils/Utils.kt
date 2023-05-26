package com.ismael.petsinnocv.core.utils

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object Utils {
  fun convertDate(fromFormat: String, toFormat: String, dateToFormat: String): String {
    val inFormat = SimpleDateFormat(fromFormat)
    try {
      val date = inFormat.parse(dateToFormat)
      val outFormat = SimpleDateFormat(toFormat)
      if (date != null) return outFormat.format(date)
    } catch (e: ParseException) {
      e.printStackTrace()
    }
    return ""
  }


  fun customAlertDialog(
    context: Context,
    onPossitiveButtonClick : (() -> Unit)? = null,
    onNegativeButtonClick : (() -> Unit)? = null
  ) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Eliminar Usuario")
    builder.setMessage("¿Está seguro que desea eliminar este usuario?")

    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
      onPossitiveButtonClick?.invoke()
      dialog.dismiss()
    }

    builder.setNegativeButton(android.R.string.no) { dialog, which ->
      onNegativeButtonClick?.invoke()
      dialog.dismiss()
    }
    builder.show()
  }

  fun pickerDialog(
    context: Context,
    onDateSetListener : ((Calendar) -> Unit)? = null
  ) {
    val calendar = Calendar.getInstance()

    val dialog = DatePickerDialog(
      context,
      { view, year, month, day ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        onDateSetListener?.invoke(calendar)
      },
      calendar.get(Calendar.YEAR),
      calendar.get(Calendar.MONTH),
      calendar.get(Calendar.DAY_OF_MONTH)
    )
    dialog.datePicker.maxDate = Date().time
    dialog.show()
  }
}
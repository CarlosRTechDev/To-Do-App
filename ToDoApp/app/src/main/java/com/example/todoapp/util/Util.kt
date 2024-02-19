package com.example.todoapp.util

import android.content.Context
import android.content.Intent
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.R
import com.example.todoapp.ui.LoginActivity
import com.example.todoapp.ui.ToDoActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Util(private val context: Context) {


    var isPasswordVisible = false

        fun togglePasswordVisibility(editTextPassword: EditText, imgIconPassword: ImageView,) {
            isPasswordVisible = !isPasswordVisible

            if (isPasswordVisible) {
                editTextPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                imgIconPassword.setImageResource(R.drawable.ic_eye_open)
            } else {
                editTextPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                imgIconPassword.setImageResource(R.drawable.ic_eye_close)
            }
        }

    fun validateSession(activity: AppCompatActivity, sessionManager: SessionManager) {
        // Si ya está loggeado, muestra ToDoActivity
        if (activity is LoginActivity && sessionManager.isLoggedIn()) {
            startNewActivity(activity)
            activity.finish()
        } else if (activity is ToDoActivity && !sessionManager.isLoggedIn()) { // Si no, muestra LoginActivity
            startNewActivity(activity)
            activity.finish()
        }
    }

    /*fun startToDoActivity(activity: AppCompatActivity) {
        val intent = Intent(activity, ToDoActivity::class.java)
        activity.startActivity(intent)
    }*/

    // Método para lanzar un Activity
    fun startNewActivity(activity: AppCompatActivity) {
        val intent: Intent

        if (activity is LoginActivity) {
            intent = Intent(activity, ToDoActivity::class.java)
        } else {
            intent = Intent(activity, LoginActivity::class.java)
        }
        activity.startActivity(intent)
    }

    fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)
        return formattedDate
    }

}
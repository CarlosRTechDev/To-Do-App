package com.example.todoapp.util

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.todoapp.R
import com.example.todoapp.databinding.DialogAddTaskBinding
import com.example.todoapp.databinding.DialogUpdateTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.viewmodel.ToDoViewModel


class AlertUtil(private val context: Context, private val toDoViewModel: ToDoViewModel) {

    val util = Util(context)
    val sessionManager = SessionManager(context)

    fun showAddTaskDialog() {
        val dialogBuilder = AlertDialog.Builder(context)

        val dialogBinding: DialogAddTaskBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.dialog_add_task, null, false)

        dialogBuilder.setView(dialogBinding.getRoot())
        dialogBuilder.setCancelable(false)
        dialogBuilder.setTitle("Agregar Tarea")

        dialogBuilder.setPositiveButton("Agregar") { dialog: DialogInterface?, which: Int ->
            val title: String = dialogBinding.editTextTitle.getText().toString().trim()
            val description: String = dialogBinding.editTextDescription.getText().toString().trim()

            if (!title.isEmpty() && !description.isEmpty()) {
                val sharedPrefUserId = sessionManager.readIdUser()



                val newTask = Task(0, title, description, util.getCurrentDate(), sharedPrefUserId)
                Log.d("PRUEBA-CHA", "se insertó con ID: " +sharedPrefUserId)
                toDoViewModel.insertTask(newTask)
            } else {
                Toast.makeText(context, "Los campos no deben estar vacíos", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBuilder.setNegativeButton("Cancelar") { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    fun showUpdateTaskDialog(id: Int, title: String, description: String) {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogBinding: DialogUpdateTaskBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.dialog_update_task, null, false)

        dialogBuilder.setView(dialogBinding.getRoot())
        dialogBuilder.setCancelable(false)

        dialogBuilder.setTitle("Actualizar Tarea")
        dialogBinding.editTextTitle.setText(title)
        dialogBinding.editTextDescription.setText(description)

        dialogBuilder.setPositiveButton("Actualizar") { dialog: DialogInterface?, which: Int ->

            val titleToUpdate: String = dialogBinding.editTextTitle.getText().toString().trim()
            val descriptionToUpdate: String = dialogBinding.editTextDescription.getText().toString().trim()

            if (!titleToUpdate.isEmpty() && !descriptionToUpdate.isEmpty()) {

                val sharedPrefUserId = sessionManager.readIdUser()

                val updatedTask = Task(id, titleToUpdate, descriptionToUpdate, util.getCurrentDate(), sharedPrefUserId)
                toDoViewModel.updateTask(updatedTask)
                //CHA: tambien funciona con esta funcion
                //toDoViewModel.updateTaskById(id, titleToUpdate, descriptionToUpdate);
            } else {
                Toast.makeText(context, "Los campos no deben estar vacíos", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBuilder.setNegativeButton(
            "Cancelar") { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    fun showDeleteTaskDialog(task: Task) {
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setCancelable(false)
        dialogBuilder.setTitle("Eliminar Tarea")
        dialogBuilder.setMessage("¿Quieres eliminar esta tarea?")

        dialogBuilder.setPositiveButton("Aceptar") { dialog: DialogInterface?, which: Int ->
            toDoViewModel.deleteTask(task);
        }

        dialogBuilder.setNegativeButton("Cancelar") { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}
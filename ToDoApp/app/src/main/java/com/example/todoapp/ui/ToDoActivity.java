package com.example.todoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todoapp.util.AlertUtil;
import com.example.todoapp.R;
import com.example.todoapp.adapter.TaskAdapter;
import com.example.todoapp.databinding.ActivityToDoBinding;
import com.example.todoapp.model.Task;
import com.example.todoapp.util.SessionManager;
import com.example.todoapp.util.Util;
import com.example.todoapp.viewmodel.ToDoViewModel;

import java.util.List;

public class ToDoActivity extends AppCompatActivity {
    private ToDoViewModel toDoViewModel;
    private SessionManager sessionManager;
    private TaskAdapter adapter;
    private AlertUtil alertUtil;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Configura Binding
        ActivityToDoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_to_do);
        toDoViewModel = new ViewModelProvider(this).get(ToDoViewModel.class);
        binding.setViewModel(toDoViewModel);
        binding.setLifecycleOwner(this);

        alertUtil = new AlertUtil(ToDoActivity.this, toDoViewModel);
        util = new Util(ToDoActivity.this);
        sessionManager = new SessionManager(this);

        //validateSesion();
        util.validateSession(ToDoActivity.this, sessionManager);

        //Configura Adapter
        adapter = new TaskAdapter();
        binding.recyclerViewTasks.setAdapter(adapter);
        binding.recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));

        // Obtiene/muestra todas las tareas al iniciar el Activity
        toDoViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });

        adapter.setOnTaskLongClickListener(new TaskAdapter.OnTaskLongClickListener() {
            @Override
            public void onTaskLongClick(Task task) {
                // Lanza un evento cuando el elemento/item se deja presionado
                alertUtil.showDeleteTaskDialog(task);
            }
        });

        adapter.setOnTaskClickListener(new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(Task task) {
                //Toast.makeText(ToDoActivity.this, "posicion elemento: "+task.getId(), Toast.LENGTH_SHORT).show();
                alertUtil.showUpdateTaskDialog(task.getId(), task.getTitle(), task.getDescription());
            }
        });

        binding.buttonAddTask.setOnClickListener(view -> alertUtil.showAddTaskDialog());
        binding.tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Establecer la sesión como inactiva
                sessionManager.setLoggedIn(false);

                // Regresar a LoginActivity
                //startLoginActivity();
                util.startNewActivity(ToDoActivity.this);
                finish();
            }
        });
    }

    /*private void validateSesion(){
        // si ya no esta loggeado, muestra LoginActivity
        if (!sessionManager.isLoggedIn()) {
            util.startNewActivity(ToDoActivity.this);
            finish();
        }
    }*/

    // Método para iniciar LoginActivity
    /*private void startLoginActivity() {
        startActivity(new Intent(ToDoActivity.this, LoginActivity.class));
    }*/

}
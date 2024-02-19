package com.example.todoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.util.Util;
import com.example.todoapp.viewmodel.UserViewModel;
import com.example.todoapp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private UserViewModel usuarioViewModel;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        usuarioViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.setViewModel(usuarioViewModel);
        binding.setLifecycleOwner(this);

        util = new Util(RegisterActivity.this);

        binding.imgIconPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.togglePasswordVisibility(binding.editTextPassword, binding.imgIconPassword);
            }
        });

        usuarioViewModel.getRegisterSuccessLiveData().observe(this, registerSuccess -> {
            if (registerSuccess) {
                // Inicio de sesión exitoso
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            } else {
                // Credenciales vacias
                Toast.makeText(this, "Debes ingregar datos", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvExistingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
}
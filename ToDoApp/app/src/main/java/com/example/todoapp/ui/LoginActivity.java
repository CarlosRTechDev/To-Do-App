package com.example.todoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.util.SessionManager;
import com.example.todoapp.util.Util;
import com.example.todoapp.viewmodel.UserViewModel;
import com.example.todoapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private UserViewModel usuarioViewModel;
    private SessionManager sessionManager;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Configura Binding
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        usuarioViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.setViewModel(usuarioViewModel);
        binding.setLifecycleOwner(this);

        util = new Util(LoginActivity.this);
        sessionManager = new SessionManager(this);

        //validar sesion activa/inactiva
        util.validateSession(LoginActivity.this, sessionManager);

        binding.imgIconPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.togglePasswordVisibility(binding.editTextPassword, binding.imgIconPassword);
            }
        });

        //CHA: revisar esto para mejorar validacion
        usuarioViewModel.getLoginSuccessLiveData().observe(this, loginSuccess -> {
            if (loginSuccess) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                // Establecer la sesión como activa
                sessionManager.setLoggedIn(true);
                util.startNewActivity(LoginActivity.this);
                finish();
            } else {
                // Credenciales incorrectas
                Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Sí, olvidé mi contraseña! :(", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(LoginActivity.this, PruebaActivity.class));
            }
        });
    }


}
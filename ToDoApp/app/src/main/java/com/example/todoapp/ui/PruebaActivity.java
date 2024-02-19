package com.example.todoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.todoapp.R;
import com.example.todoapp.databinding.ActivityLoginBinding;
import com.example.todoapp.databinding.ActivityPruebaBinding;
import com.example.todoapp.viewmodel.UserViewModel;

public class PruebaActivity extends AppCompatActivity {

    private UserViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_prueba);

        //Configura Binding
        ActivityPruebaBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_prueba);
        usuarioViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.setViewModel(usuarioViewModel);
        binding.setLifecycleOwner(this);

    }
}
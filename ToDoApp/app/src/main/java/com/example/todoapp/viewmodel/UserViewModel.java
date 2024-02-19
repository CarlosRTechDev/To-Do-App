package com.example.todoapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.model.User;
import com.example.todoapp.dao.UserDao;
import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.util.SessionManager;

public class UserViewModel extends AndroidViewModel {
    private SessionManager sessionManager;

    private MutableLiveData<String> idUserLiveData = new MutableLiveData<>();
    private MutableLiveData<String> userLiveData = new MutableLiveData<>();
    private MutableLiveData<String> passwordTwoLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginSuccessLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> registerSuccessLiveData = new MutableLiveData<>();
    private UserDao userDao;

    public UserViewModel(@NonNull Application application) {
        super(application);

        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
    }

    public MutableLiveData<String> getIdUserLiveData() {
        return idUserLiveData;
    }
    public MutableLiveData<String> getUserLiveData() {
        return userLiveData;
    }
    public MutableLiveData<String> getPasswordTwoLiveData() {
        return passwordTwoLiveData;
    }
    public MutableLiveData<Boolean> getLoginSuccessLiveData() {
        return loginSuccessLiveData;
    }
    public MutableLiveData<Boolean> getRegisterSuccessLiveData() {
        return registerSuccessLiveData;
    }


    /*public User getUsuario(){
        String usuario = userLiveData.getValue();
        return userDao.obtenerUsuarioPorNombre(usuario);
        *//*Toast.makeText(getApplication().getApplicationContext(),
                "prueba: " +usuarioDao.obtenerUsuarioPorNombre(usuario), Toast.LENGTH_SHORT).show();*//*
    }

    public void getUsuario2(){
        String usuario = userLiveData.getValue();
        AsyncTask.execute(() -> {
            //usuarioDao.obtenerUsuarioPorNombre(usuario);
            Toast.makeText(getApplication().getApplicationContext(),
                    "prueba: " +userDao.obtenerUsuarioPorNombre(usuario), Toast.LENGTH_SHORT).show();
        });
    }*/

    public void getUser() {
        String user = userLiveData.getValue().trim();
        String password = passwordTwoLiveData.getValue().trim();

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)) {
            AsyncTask.execute(() -> {
                sessionManager = new SessionManager(getApplication().getApplicationContext());

                User userFound = userDao.getUserByNamePass(user, password);

               if (userFound == null || userFound.equals("")){
                    Log.d("ERROR-USUARIO", "no existe el usuario");
                    loginSuccessLiveData.postValue(false);
                } else {
                   Log.d("PRUEBA-CHA", "guardando ID... ");
                   sessionManager.saveIdUser(userFound.getId());

                   int sharedPrefUserId = sessionManager.readIdUser();
                   Log.d("PRUEBA-CHA", "leyendo ID... " +sharedPrefUserId);

                   loginSuccessLiveData.postValue(userFound != null);
               }
            });
        } else {
            //campos vacios envia el toast de "Credenciales incorrectas"
            Log.d("ERROR", "campos vacios, ingresa un usuario y contraseña.");
            loginSuccessLiveData.postValue(false);
        }
        //CHA: creo que faltaria el caso de cuando las cred. son incorrectas o no existen
    }

    //CHA:PRUEBA
    public void getUserById() {
        int idUser = Integer.parseInt(idUserLiveData.getValue());

        AsyncTask.execute(() -> {
            User userFounded = userDao.getUserById(idUser);
            Log.d("PRUEBA-CHAR", "USUARIO: "+userFounded.getUser());
        });
    }

    public void setUserAccount() {
        String user = userLiveData.getValue().trim();
        String passwordTwo = passwordTwoLiveData.getValue().trim();

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(passwordTwo)) {
            User newUser = new User();
            newUser.user = user;
            newUser.passwordTwo = passwordTwo;

            AsyncTask.execute(() -> {
                Log.d("REGISTRO", "registro exitoso!");
                userDao.insertUser(newUser);
                registerSuccessLiveData.postValue(true);
            });
        } else {
            Log.i("EMPTY", "campos vacios");
            registerSuccessLiveData.postValue(false);
        }
    }

}

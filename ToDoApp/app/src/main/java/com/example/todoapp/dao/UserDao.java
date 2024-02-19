package com.example.todoapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todoapp.model.User;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user_table WHERE user = :userName AND passwordtwo = :password LIMIT 1")
    User getUserByNamePass(String userName, String password);

    @Query("SELECT * FROM user_table WHERE id = :id")
    User getUserById(int id);
}

package com.svalero.toplaptop.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.toplaptop.domain.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id = :id")
    User getUserById(int id);

    @Query("SELECT * FROM user WHERE name LIKE :query")
    List<User> getByNameString(String query);

    @Query("SELECT * FROM user WHERE surname LIKE :query")
    List<User> getBySurnameString(String query);

    @Query("SELECT * FROM user WHERE dni LIKE :query")
    List<User> getByDniString(String query);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}

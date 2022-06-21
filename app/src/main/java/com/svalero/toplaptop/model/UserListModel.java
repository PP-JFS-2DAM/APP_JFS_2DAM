package com.svalero.toplaptop.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.toplaptop.contract.UserListContract;
import com.svalero.toplaptop.database.AppDatabase;
import com.svalero.toplaptop.domain.User;

import java.util.ArrayList;

public class UserListModel implements UserListContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "user").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public ArrayList<User> loadAllUsers() {
        return (ArrayList<User>) db.userDao().getAll();
    }

    @Override
    public ArrayList<User> loadUsersByName(String query) {
        return (ArrayList<User>) db.userDao().getByNameString(query);
    }

    @Override
    public ArrayList<User> loadUsersBySurname(String query) {
        return (ArrayList<User>) db.userDao().getBySurnameString(query);
    }

    @Override
    public ArrayList<User> loadUsersByDni(String query) {
        return (ArrayList<User>) db.userDao().getByDniString(query);
    }

    @Override
    public void deleteUser(User user) {
        db.userDao().delete(user);
    }
}

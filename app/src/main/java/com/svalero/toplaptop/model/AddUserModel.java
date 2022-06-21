package com.svalero.toplaptop.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.toplaptop.contract.AddUserContract;
import com.svalero.toplaptop.database.AppDatabase;
import com.svalero.toplaptop.domain.User;

public class AddUserModel implements AddUserContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "user").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void insertUser(User user) {
        db.userDao().insert(user);
    }

    @Override
    public void updateUser(User user) {
        db.userDao().update(user);
    }
}

package com.svalero.toplaptop.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.toplaptop.contract.AddComputerContract;
import com.svalero.toplaptop.database.AppDatabase;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;

import java.util.ArrayList;

public class AddComputerModel implements AddComputerContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "computer").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void insertComputer(Computer computer) {
        db.computerDao().insert(computer);
    }

    @Override
    public void updateComputer(Computer computer) {
        db.computerDao().update(computer);
    }

    @Override
    public ArrayList<User> loadAllUser(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "user").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return (ArrayList<User>) db.userDao().getAll();
    }
}

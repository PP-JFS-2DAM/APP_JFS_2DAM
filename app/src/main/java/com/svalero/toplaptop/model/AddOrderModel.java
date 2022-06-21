package com.svalero.toplaptop.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.toplaptop.contract.AddOrderContract;
import com.svalero.toplaptop.database.AppDatabase;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;

import java.util.ArrayList;

public class AddOrderModel implements AddOrderContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "order").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void insertOrder(Order order) {
        db.orderDao().insert(order);
    }

    @Override
    public void updateOrder(Order order) {
        db.orderDao().update(order);
    }

    @Override
    public ArrayList<Computer> userComputers(int userId, Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "computer").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        //return (ArrayList<Computer>) db.computerDao().getComputersByUserId(userId);
        return null;
    }

    @Override
    public ArrayList<User> users(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "user").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return (ArrayList<User>) db.userDao().getAll();
    }
}

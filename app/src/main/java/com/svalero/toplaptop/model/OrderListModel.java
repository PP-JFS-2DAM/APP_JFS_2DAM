package com.svalero.toplaptop.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.toplaptop.contract.OrderListContract;
import com.svalero.toplaptop.database.AppDatabase;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;

import java.util.ArrayList;

public class OrderListModel implements OrderListContract.Model {

    private Context context;
    private AppDatabase db;

    public OrderListModel(Context context) {
        this.context = context;
    }


    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "order").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public ArrayList<Order> loadAllOrders() {
        return (ArrayList<Order>) db.orderDao().getAll();
    }

    @Override
    public void deleteOrder(Order order) {
        db.orderDao().delete(order);
    }

    @Override
    public User loadUserById(int id) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "user").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();

        return db.userDao().getUserById(id);
    }

    @Override
    public Computer loadComputerById(int id) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "computer").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();

        return db.computerDao().getComputerById(id);
    }
}

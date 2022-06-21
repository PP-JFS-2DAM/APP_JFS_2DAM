package com.svalero.toplaptop.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.toplaptop.dao.ComputerDao;
import com.svalero.toplaptop.dao.UserDao;
import com.svalero.toplaptop.dao.OrderDao;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;

@Database(entities = {Computer.class, User.class, Order.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ComputerDao computerDao();

    public abstract UserDao userDao();

    public abstract OrderDao orderDao();

}

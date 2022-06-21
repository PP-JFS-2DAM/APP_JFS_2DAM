package com.svalero.toplaptop.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.toplaptop.domain.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM Order")
    List<Order> getAll();

    @Query("SELECT * FROM Order WHERE orderDate LIKE :query")
    List<Order> getByDate(String query);

    @Query("SELECT * FROM Order WHERE description LIKE :query")
    List<Order> getByDescriptionString(String query);

    @Insert
    void insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);
}

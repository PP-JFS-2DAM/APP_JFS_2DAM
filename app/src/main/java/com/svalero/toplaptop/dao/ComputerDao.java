package com.svalero.toplaptop.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.toplaptop.domain.Computer;

import java.util.List;

@Dao
public interface ComputerDao {

    @Query("SELECT * FROM computer")
    List<Computer> getAll();

    @Query("SELECT * FROM computer WHERE id = :id")
    Computer getComputerById(int id);

    @Query("SELECT * FROM computer WHERE brand LIKE :query")
    List<Computer> getByBrandString(String query);

    @Query("SELECT * FROM computer WHERE model LIKE :query")
    List<Computer> getByModelString(String query);

    @Query("SELECT * FROM computer WHERE licensePlate LIKE :query")
    List<Computer> getByLicensePlateString(String query);

    /*
    @Query("SELECT * FROM computer WHERE userId = :userId")
    List<Computer> getComputersByUserId(int userId);
    */

    @Insert
    void insert(Computer computer);

    @Update
    void update(Computer computer);

    @Delete
    void delete(Computer computer);
}

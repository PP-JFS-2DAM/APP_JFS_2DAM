package com.svalero.toplaptop.api;

import android.util.Log;

import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ToplaptopApiInterface {

    // Computers
    @GET("computers?all=true")
    Call<List<Computer>> getComputers();
    @GET("computers")
    Call<List<Computer>> getComputersByBrand(@Query("brand") String brand);
    @GET("computers")
    Call<List<Computer>> getComputersByModel(@Query("model") String model);
    @GET("computers")
    Call<List<Computer>> getComputersByRam(@Query("ram") String ram);
    @DELETE("computer/{id}")
    Call<Void> deleteComputer(@Path("id") long id);

// Users
    @GET("users?all=true")
    Call<List<User>> getUsers();
    @GET("users")
    Call<List<User>> getUsersByName(@Query("name") String name);
    @GET("users")
    Call<List<User>> getUsersBySurname(@Query("surname") String surname);
    @GET("users")
    Call<List<User>> getUsersByDni(@Query("dni") String dni);
    @DELETE("user/{id}")
    Call<Void> deleteUsers(@Path("id") long id);

}
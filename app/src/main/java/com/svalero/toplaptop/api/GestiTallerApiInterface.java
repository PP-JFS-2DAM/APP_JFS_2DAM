package com.svalero.toplaptop.api;

import com.svalero.toplaptop.domain.Computer;

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
    Call<List<Computer>> getComputersByLicense(@Query("license") String license);
    @DELETE("computer/{id}")
    Call<Void> deleteComputer(@Path("id") long id);
}

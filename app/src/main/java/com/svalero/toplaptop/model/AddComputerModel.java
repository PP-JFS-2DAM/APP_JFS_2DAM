package com.svalero.toplaptop.model;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.svalero.toplaptop.api.ToplaptopApi;
import com.svalero.toplaptop.api.ToplaptopApiInterface;
import com.svalero.toplaptop.contract.AddComputerContract;
import com.svalero.toplaptop.database.AppDatabase;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.dto.ComputerDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddComputerModel implements AddComputerContract.Model {

    private AppDatabase db;
    private ToplaptopApiInterface api;
    private List<User> users;

    @Override
    public void startDb(Context context) {
        users = new ArrayList<>();
        api = ToplaptopApi.buildInstance();
        db = Room.databaseBuilder(context,
                        AppDatabase.class, "computer").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void addComputer(OnAddComputerListener listener, Computer computer) {
        ComputerDTO computerDTO = new ComputerDTO();
        computerDTO.setBrand(computer.getBrand());
        computerDTO.setModel(computer.getModel());
        computerDTO.setRam(computer.getRam());
        computerDTO.setComputerImage(computer.getComputerImage());
        Log.i("userId", "iduser model" + computer.getUser().getId());
        computerDTO.setUser(computer.getUser().getId());
        Log.i("userId", "iduserdto" + computerDTO.getUser());

        Call<Computer> computerCall = api.addComputer(computerDTO);

        computerCall.enqueue(new Callback<Computer>() {
            @Override
            public void onResponse(Call<Computer> call, Response<Computer> response) {
                listener.onAddComputerSuccess("Ordenador añadido con éxito");
            }

            @Override
            public void onFailure(Call<Computer> call, Throwable t) {
                listener.onAddComputerError("No se ha podido añadir el ordenador");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void modifyComputer(OnModifyComputerListener listener, Computer computer) {
        ComputerDTO computerDTO = new ComputerDTO();
        computerDTO.setBrand(computer.getBrand());
        computerDTO.setModel(computer.getModel());
        computerDTO.setRam(computer.getRam());
        computerDTO.setComputerImage(computer.getComputerImage());
        Log.i("userId", "iduser model" + computer.getUser().getId());
        computerDTO.setUser(computer.getUser().getId());
        Log.i("userId", "iduserdto" + computerDTO.getUser());
        Call<Computer> computerCall = api.modifyComputer(computer.getId(), computerDTO);

        computerCall.enqueue(new Callback<Computer>() {
            @Override
            public void onResponse(Call<Computer> call, Response<Computer> response) {
                listener.onModifyComputerSuccess("Ordenador modificado con éxito");
            }

            @Override
            public void onFailure(Call<Computer> call, Throwable t) {
                listener.onModifyComputerError("No se ha podido modificar el ordenador");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadAllUser(OnLoadUsersListener listener) {
        users.clear();

        Call<List<User>> userCall = api.getUsers();

        userCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                users = response.body();
                listener.onLoadUsersSuccess(users);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                listener.onLoadUsersError("Se ha producido un error");
            }
        });
    }
}

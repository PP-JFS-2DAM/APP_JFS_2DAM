package com.svalero.toplaptop.model;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.svalero.toplaptop.api.ToplaptopApi;
import com.svalero.toplaptop.api.ToplaptopApiInterface;
import com.svalero.toplaptop.contract.AddUserContract;
import com.svalero.toplaptop.contract.UserListContract;
import com.svalero.toplaptop.database.AppDatabase;
import com.svalero.toplaptop.domain.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserModel implements AddUserContract.Model {

    private AppDatabase db;
    private ToplaptopApiInterface api;

    @Override
    public void startDb(Context context) {
        api = ToplaptopApi.buildInstance();
        db = Room.databaseBuilder(context,
                        AppDatabase.class, "user").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void addUser(OnAddUserListener onAddUserListener, User user) {

        Call<User> userCall = api.addUser(user);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                onAddUserListener.onAddUserSuccess("User added successfully");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onAddUserListener.onAddUserError("The user couldn't be added");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void modifyUser(OnModifyUserListener onModifyUserListener, User user) {

        Call<User> userCall = api.modifyUser(user.getId(), user);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                onModifyUserListener.onModifyUserSuccess("User modified successfully");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onModifyUserListener.onModifyUserError("The user couldn't be modified");
                t.printStackTrace();
            }
        });
    }
}

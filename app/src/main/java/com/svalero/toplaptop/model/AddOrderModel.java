package com.svalero.toplaptop.model;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.svalero.toplaptop.api.ToplaptopApi;
import com.svalero.toplaptop.api.ToplaptopApiInterface;
import com.svalero.toplaptop.contract.AddOrderContract;
import com.svalero.toplaptop.database.AppDatabase;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;
import com.svalero.toplaptop.domain.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOrderModel implements AddOrderContract.Model {

    private AppDatabase db;
    private ToplaptopApiInterface api;
    private List<User> users;
    private List<Computer> userComputer;

    @Override
    public void startDb(Context context) {
        users = new ArrayList<>();
        userComputer=new ArrayList<>();
        api = ToplaptopApi.buildInstance();
        db = Room.databaseBuilder(context,
                AppDatabase.class, "order").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
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

    @Override
    public void loadUserComputers(OnLoadComputersListener listener, User user) {
        userComputer.clear();
                Log.i("COMPUTERR", user.toString());
        Call<List<Computer>> userComputerCall = api.getComputersByUserId(user.getId());

        userComputerCall.enqueue(new Callback<List<Computer>>() {
            @Override
            public void onResponse(Call<List<Computer>> call, Response<List<Computer>> response) {
                userComputer = response.body();
                Log.i("COMPUTERR", userComputer.toString());
                listener.onLoadComputersSuccess(userComputer);
            }

            @Override
            public void onFailure(Call<List<Computer>> call, Throwable t) {
                listener.onLoadComputersError("Se ha producido un error");
            }
        });
    }

    @Override
    public void addOrder(OnAddOrderListener listener, Order order) {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setDescription(order.getDescription());
        orderDTO.setTechnical(1);
        orderDTO.setComputer(order.getComputer().getId());
        Log.i("COMPUTERR", "DTO" +orderDTO);
        Call<Order> orderCall = api.addOrder(orderDTO);

        orderCall.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                listener.onAddOrderSuccess("Orden añadida correctamente");
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                listener.onAddOrderError("Error al añadir la orden");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void modifyOrder(OnModifyOrderListener listener, Order order) {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setDescription(order.getDescription());
        orderDTO.setTechnical(1);
        orderDTO.setComputer(order.getComputer().getId());
        Log.i("COMPUTERR", "DTO" +orderDTO);
        Call<Order> orderCall = api.modifyOrder(order.getId(), orderDTO);

        orderCall.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                listener.onModifyOrderSuccess("Orden añadida correctamente");
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                listener.onModifyOrderSuccess("Error al añadir la orden");
                t.printStackTrace();
            }
        });
    }
}

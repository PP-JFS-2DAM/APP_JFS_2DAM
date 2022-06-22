package com.svalero.toplaptop.contract;

import android.content.Context;

import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;
import com.svalero.toplaptop.domain.dto.OrderDTOAdapter;

import java.util.ArrayList;

public interface OrderListContract {
    interface Model {
        void startDb(Context context);

        ArrayList<Order> loadAllOrders();

        void deleteOrder(Order order);

        User loadUserById(int id);

        Computer loadComputerById(int id);
    }

    interface View {
        void listUsers(ArrayList<OrderDTOAdapter> ordersDTOArrayList);
    }

    interface Presenter {
        void loadAllOrders();

        void deleteOrder(Order order);
    }
}

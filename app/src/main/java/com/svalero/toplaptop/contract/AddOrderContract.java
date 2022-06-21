package com.svalero.toplaptop.contract;

import android.content.Context;

import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;

import java.util.ArrayList;

public interface AddOrderContract {
    interface Model {
        void startDb(Context context);

        void insertOrder(Order order);

        void updateOrder(Order order);

        ArrayList<Computer> userComputers(int userId, Context context);

        ArrayList<User> users(Context context);
    }

    interface View {
        void fillComputerSpinner(ArrayList<Computer> computers);

        void fillUserSpinner(ArrayList<User> users);

        void cleanForm();
    }

    interface Presenter {
        void addOrder(Order order, Boolean modifyOrder);

        void fillComputerSpinner(int userId);

        void fillUserSpinner();
    }
}

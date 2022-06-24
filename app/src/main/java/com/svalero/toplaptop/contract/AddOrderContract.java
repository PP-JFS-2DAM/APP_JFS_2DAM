package com.svalero.toplaptop.contract;

import android.content.Context;

import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;

import java.util.ArrayList;
import java.util.List;

public interface AddOrderContract {
    interface Model {
        // USERS
        interface OnLoadUsersListener {
            void onLoadUsersSuccess(List<User> users);

            void onLoadUsersError(String message);
        }

        // COMPUTERS
        interface OnLoadComputersListener {
            void onLoadComputersSuccess(List<Computer> usersComputer);

            void onLoadComputersError(String message);
        }

        // ORDERS
        interface OnAddOrderListener {
            void onAddOrderSuccess(String message);

            void onAddOrderError(String message);
        }

        interface OnModifyOrderListener {
            void onModifyOrderSuccess(String message);

            void onModifyOrderError(String message);
        }

        void startDb(Context context);

        void loadAllUser(OnLoadUsersListener listener);

        void loadUserComputers(OnLoadComputersListener listener, User user);

        void addOrder(OnAddOrderListener listener, Order order);

        void modifyOrder(OnModifyOrderListener listener, Order order);
    }

    interface View {
        void loadUserSpinner(List<User> users);

        void loadUserComputerSpinner(List<Computer> computers);

        void addComputer(android.view.View view);

        void cleanForm();

        void showMessage(String message);
    }

    interface Presenter {
        void loadUsersSpinner();

        void loadUserComputerSpinner(User user);

        void addOrModifyOrder(Order order, Boolean modifyOrder);
    }
}

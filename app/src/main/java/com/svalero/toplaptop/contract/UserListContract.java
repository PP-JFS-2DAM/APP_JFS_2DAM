package com.svalero.toplaptop.contract;

import android.content.Context;

import com.svalero.toplaptop.domain.User;

import java.util.ArrayList;

public interface UserListContract {
    interface Model {
        void startDb(Context context);

        ArrayList<User> loadAllUsers();

        ArrayList<User> loadUsersByName(String query);

        ArrayList<User> loadUsersBySurname(String query);

        ArrayList<User> loadUsersByDni(String query);

        void deleteUser(User user);
    }

    interface View {
        void listUsers(ArrayList<User> users);
    }

    interface Presenter {
        void loadAllUsers();

        void loadUsersByName(String query);

        void loadUsersBySurname(String query);

        void loadUsersByDni(String query);

        void deleteUser(User user);
    }
}

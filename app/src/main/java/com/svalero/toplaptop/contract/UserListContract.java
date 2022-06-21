package com.svalero.toplaptop.contract;

import android.content.Context;

import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;

import java.util.ArrayList;
import java.util.List;

public interface UserListContract {
    interface Model {
        interface OnLoadUsersListener {
            void onLoadUsersSuccess(List<User> users);

            void onLoadUsersError(String message);
        }

        interface OnDeleteUserListener {
            void onDeleteUserSuccess(String message);

            void onDeleteUserError(String message);
        }

        void startDb(Context context);

        void loadAllUsers(OnLoadUsersListener listener);

        void loadUsersByName(OnLoadUsersListener listener, String query);

        void loadUsersBySurname(OnLoadUsersListener listener, String query);

        void loadUsersByDni(OnLoadUsersListener listener, String query);

        void deleteUser(OnDeleteUserListener listener, User user);
    }

    interface View {
        void listUsers(List<User> users);

        void showMessage(String message);
    }

    interface Presenter {
        void loadAllUsers();

        void loadUsersByName(String query);

        void loadUsersBySurname(String query);

        void loadUsersByDni(String query);

        void deleteUser(User user);
    }
}

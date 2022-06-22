package com.svalero.toplaptop.contract;

import android.content.Context;

import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;

import java.util.ArrayList;
import java.util.List;

public interface AddComputerContract {
    interface Model {
        // USERS
        interface OnLoadUsersListener {
            void onLoadUsersSuccess(List<User> users);

            void onLoadUsersError(String message);
        }

        // COMPUTERS
        interface OnAddComputerListener {
            void onAddComputerSuccess(String message);

            void onAddComputerError(String message);
        }

        interface OnModifyComputerListener {
            void onModifyComputerSuccess(String message);

            void onModifyComputerError(String message);
        }

        void startDb(Context context);

        void addComputer(OnAddComputerListener listener, Computer computer);

        void modifyComputer(OnModifyComputerListener listener, Computer computer);

        void loadAllUser(OnLoadUsersListener listener);
    }

    interface View {
        void loadUserSpinner(List<User> users);

        void addComputer(android.view.View view);

        void cleanForm();

        void showMessage(String message);
    }

    interface Presenter {
        void loadUsersSpinner();

        void addOrModifyComputer(Computer computer, Boolean modifyComputer);
    }
}

package com.svalero.toplaptop.contract;

import android.content.Context;

import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;

import java.util.ArrayList;

public interface AddComputerContract {
    interface Model {
        void startDb(Context context);

        void insertComputer(Computer computer);

        void updateComputer(Computer computer);

        ArrayList<User> loadAllUser(Context context);
    }

    interface View {
        void loadUserSpinner(ArrayList<User> users);

        void cleanForm();
    }

    interface Presenter {
        void loadUsersSpinner();

        void addComputer(Computer computer, Boolean modifyComputer);
    }
}

package com.svalero.toplaptop.contract;

import android.content.Context;

import com.svalero.toplaptop.domain.User;

public interface AddUserContract {
    interface Model {
        void startDb(Context context);

        void insertUser(User user);

        void updateUser(User user);
    }

    interface View {
        void cleanForm();
    }

    interface Presenter {
        void addUser(User user, Boolean modifyUser);
    }
}

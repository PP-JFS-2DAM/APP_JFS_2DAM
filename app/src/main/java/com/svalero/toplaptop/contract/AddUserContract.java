package com.svalero.toplaptop.contract;

import android.content.Context;

import com.svalero.toplaptop.domain.User;

public interface AddUserContract {
    interface Model {
        interface OnAddUserListener {
            void onAddUserSuccess(String message);

            void onAddUserError(String message);
        }

        interface OnModifyUserListener {
            void onModifyUserSuccess(String message);

            void onModifyUserError(String message);
        }

        void startDb(Context context);

        void addUser(OnAddUserListener onAddUserListener, User user);

        void modifyUser(OnModifyUserListener onModifyUserListener, User user);
    }

    interface View {
        void addUser(android.view.View view);

        void cleanForm();

        void showMessage(String message);
    }

    interface Presenter {
        void addOrModifyUser(User user, Boolean modifyUser);
    }
}

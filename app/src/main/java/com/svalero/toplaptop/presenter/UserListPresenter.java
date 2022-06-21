package com.svalero.toplaptop.presenter;

import com.svalero.toplaptop.contract.UserListContract;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.model.UserListModel;
import com.svalero.toplaptop.view.UserListView;

import java.util.List;

public class UserListPresenter implements UserListContract.Presenter, UserListContract.Model.OnLoadUsersListener, UserListContract.Model.OnDeleteUserListener {

    private UserListModel model;
    private UserListView view;

    public UserListPresenter(UserListView view) {
        model = new UserListModel();
        model.startDb(view.getApplicationContext());

        this.view = view;
    }

    @Override
    public void loadAllUsers() {
        model.loadAllUsers(this);
    }

    @Override
    public void loadUsersByName(String query) {
        model.loadUsersByName(this, query);
    }

    @Override
    public void loadUsersBySurname(String query) {
        model.loadUsersBySurname(this, query);
    }

    @Override
    public void loadUsersByDni(String query) {
        model.loadUsersByDni(this, query);
    }

    @Override   // OnLoadUsersListener SUCCESS
    public void onLoadUsersSuccess(List<User> users) {
        view.listUsers(users);
    }

    @Override      // OnLoadUsersListener ERROR
    public void onLoadUsersError(String message) {
        view.showMessage(message);
    }

    @Override
    public void deleteUser(User user) {
        model.deleteUser(this, user);
    }


    @Override   // OnDeleteUsersListener SUCCESS
    public void onDeleteUserSuccess(String message) {

    }

    @Override   // OnDeleteUsersListener ERROR
    public void onDeleteUserError(String message) {

    }
}

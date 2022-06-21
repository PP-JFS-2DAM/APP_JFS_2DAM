package com.svalero.toplaptop.presenter;

import com.svalero.toplaptop.contract.UserListContract;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.model.UserListModel;
import com.svalero.toplaptop.view.UserListView;

public class UserListPresenter implements UserListContract.Presenter {

    private UserListModel model;
    private UserListView view;

    public UserListPresenter(UserListView view) {
        model = new UserListModel();
        model.startDb(view.getApplicationContext());

        this.view = view;
    }

    @Override
    public void loadAllUsers() {
        view.listUsers(model.loadAllUsers());
    }

    @Override
    public void loadUsersByName(String query) {
        view.listUsers(model.loadUsersByName(query));
    }

    @Override
    public void loadUsersBySurname(String query) {
        view.listUsers(model.loadUsersBySurname(query));
    }

    @Override
    public void loadUsersByDni(String query) {
        view.listUsers(model.loadUsersByDni(query));
    }

    @Override
    public void deleteUser(User user) {
        model.deleteUser(user);
    }
}

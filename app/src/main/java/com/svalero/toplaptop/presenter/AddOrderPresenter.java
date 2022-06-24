package com.svalero.toplaptop.presenter;

import android.content.Context;
import android.widget.Toast;

import com.svalero.toplaptop.R;
import com.svalero.toplaptop.contract.AddOrderContract;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;
import com.svalero.toplaptop.model.AddOrderModel;
import com.svalero.toplaptop.view.AddOrderView;

import java.util.List;

public class AddOrderPresenter implements AddOrderContract.Presenter, AddOrderContract.Model.OnAddOrderListener, AddOrderContract.Model.OnModifyOrderListener, AddOrderContract.Model.OnLoadUsersListener, AddOrderContract.Model.OnLoadComputersListener {

    private AddOrderModel model;
    private AddOrderView view;
    private Context context;

    public AddOrderPresenter(AddOrderView view) {
        this.context = view.getApplicationContext();
        this.view = view;

        model = new AddOrderModel();
        model.startDb(context);
    }

    @Override
    public void loadUsersSpinner() {
        model.loadAllUser(this);
    }

    @Override
    public void loadUserComputerSpinner(User user) {
        model.loadUserComputers(this, user);
    }

    @Override
    public void addOrModifyOrder(Order order, Boolean modifyOrder) {
        model.startDb(view.getApplicationContext());

        if (view.getComputerSpinner().getCount() == 0) {
            // TODO view.showMessage(message);
        } else if ((order.getDescription().equals("")) || (order.getOrderDate() == null)) {
            view.showMessage("Completa all fields");
        } else {
            if (modifyOrder) {
                view.setModifyOrder(false);
                view.getAddButton().setText(R.string.add_button);
                model.modifyOrder(this, order);
            } else {
                order.setId(0);
                model.addOrder(this, order);
            }
        }
    }

    @Override
    public void onLoadUsersSuccess(List<User> users) {
        view.loadUserSpinner(users);
    }

    @Override
    public void onLoadUsersError(String message) {
        view.showMessage(message);
    }

    @Override
    public void onLoadComputersSuccess(List<Computer> usersComputer) {
        view.loadUserComputerSpinner(usersComputer);
    }

    @Override
    public void onLoadComputersError(String message) {
        view.showMessage(message);
    }

    @Override
    public void onAddOrderSuccess(String message) {
        view.showMessage(message);
        view.cleanForm();
    }

    @Override
    public void onAddOrderError(String message) {
        view.showMessage(message);
    }

    @Override
    public void onModifyOrderSuccess(String message) {
        view.showMessage(message);
        view.cleanForm();
    }

    @Override
    public void onModifyOrderError(String message) {
        view.showMessage(message);
    }
}

package com.svalero.toplaptop.presenter;

import android.content.Context;
import android.widget.Toast;

import com.svalero.toplaptop.R;
import com.svalero.toplaptop.contract.AddComputerContract;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.model.AddComputerModel;
import com.svalero.toplaptop.view.AddComputerView;

import java.util.List;

public class AddComputerPresenter implements AddComputerContract.Presenter, AddComputerContract.Model.OnLoadUsersListener, AddComputerContract.Model.OnAddComputerListener, AddComputerContract.Model.OnModifyComputerListener {

    private AddComputerModel model;
    private AddComputerView view;
    private Context context;

    public AddComputerPresenter(AddComputerView view) {
        this.context = view.getApplicationContext();
        this.view = view;

        model = new AddComputerModel();
        model.startDb(context);
    }

    @Override
    public void loadUsersSpinner() {
        model.loadAllUser(this);
    }

    @Override
    public void addOrModifyComputer(Computer computer, Boolean modifyComputer) {
        if ((computer.getBrand().equals("")) || (computer.getModel().equals("")) || (computer.getRam().equals(""))) {
            view.showMessage("Complete all the fields");
        } else {
            if (modifyComputer) {
                view.setModifyComputer(false);
                view.getAddButton().setText(R.string.add_button);
                model.modifyComputer(this, computer);
            } else {
                computer.setId(0);
                model.addComputer(this, computer);
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
    public void onAddComputerSuccess(String message) {
        view.showMessage(message);
        view.cleanForm();
    }

    @Override
    public void onAddComputerError(String message) {
        view.showMessage(message);
    }

    @Override
    public void onModifyComputerSuccess(String message) {
        view.showMessage(message);
        view.cleanForm();
    }

    @Override
    public void onModifyComputerError(String message) {
        view.showMessage(message);
    }
}

package com.svalero.toplaptop.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.svalero.toplaptop.R;
import com.svalero.toplaptop.contract.AddUserContract;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.model.AddUserModel;
import com.svalero.toplaptop.view.AddUserView;

public class AddUserPresenter implements AddUserContract.Presenter, AddUserContract.Model.OnAddUserListener, AddUserContract.Model.OnModifyUserListener {

    private AddUserModel model;
    private AddUserView view;
    private Context context;

    public AddUserPresenter(AddUserView view) {
        this.view = view;
        this.context = view.getApplicationContext();

        model = new AddUserModel();
        model.startDb(context);
    }

    @Override
    public void addOrModifyUser(User user, Boolean modifyUser) {
        if ((user.getName().equals("")) || (user.getSurname().equals("")) || (user.getDni().equals(""))) {
            view.showMessage("Completa todos los campos");
        } else if (user.getLatitude() == 0 && user.getLongitude() == 0) {
            view.showMessage("Selecciona una posición en el mapa");
        } else {

            if (modifyUser) {
                Log.i("userr", "modyfy");
                view.setModifyUser(false);
                view.getAddButton().setText(R.string.add_button);
                model.modifyUser(this, user);
            } else {
                Log.i("userr", "add");
                user.setId(0);
                model.addUser(this, user);
            }
        }
    }

    @Override
    public void onAddUserSuccess(String message) {
        view.showMessage(message);
        view.cleanForm();

    }

    @Override
    public void onAddUserError(String message) {
        view.showMessage(message);
    }

    @Override
    public void onModifyUserSuccess(String message) {
        view.showMessage(message);
        view.cleanForm();
    }

    @Override
    public void onModifyUserError(String message) {
        view.showMessage(message);
    }
}

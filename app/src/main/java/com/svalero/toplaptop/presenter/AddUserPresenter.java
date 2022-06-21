package com.svalero.toplaptop.presenter;

import android.content.Context;
import android.widget.Toast;

import com.svalero.toplaptop.R;
import com.svalero.toplaptop.contract.AddUserContract;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.model.AddUserModel;
import com.svalero.toplaptop.view.AddUserView;

public class AddUserPresenter implements AddUserContract.Presenter {

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
    public void addUser(User user, Boolean modifyUser) {
        if ((user.getName().equals("")) || (user.getSurname().equals("")) || (user.getDni().equals(""))) {
            Toast.makeText(context, R.string.complete_all_fields, Toast.LENGTH_SHORT).show();
        } else if (user.getLatitude() == 0 && user.getLongitude() == 0) {
            Toast.makeText(context, R.string.select_map_position, Toast.LENGTH_SHORT).show();
        } else {

            if (modifyUser) {
                view.setModifyUser(false);
                view.getAddButton().setText(R.string.add_button);
                model.updateUser(user);
                Toast.makeText(context, R.string.modified_user, Toast.LENGTH_SHORT).show();
            } else {
                user.setId(0);
                model.insertUser(user);
                Toast.makeText(context, R.string.added_user, Toast.LENGTH_SHORT).show();
            }
            view.cleanForm();
        }
    }
}

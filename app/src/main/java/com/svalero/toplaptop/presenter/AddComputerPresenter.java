package com.svalero.toplaptop.presenter;

import android.widget.Toast;

import com.svalero.toplaptop.R;
import com.svalero.toplaptop.contract.AddComputerContract;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.model.AddComputerModel;
import com.svalero.toplaptop.view.AddComputerView;

public class AddComputerPresenter implements AddComputerContract.Presenter {

    private AddComputerModel model;
    private AddComputerView view;

    public AddComputerPresenter(AddComputerView view) {
        model = new AddComputerModel();
        this.view = view;
    }

    @Override
    public void loadUsersSpinner() {
        view.loadUserSpinner(model.loadAllUser(view.getApplicationContext()));
    }

    @Override
    public void addComputer(Computer computer, Boolean modifyComputer) {

        model.startDb(view.getApplicationContext());

        if ((computer.getBrand().equals("")) || (computer.getModel().equals("")) || (computer.getLicensePlate().equals(""))) {
            Toast.makeText(view.getApplicationContext(), R.string.complete_all_fields, Toast.LENGTH_SHORT).show();
        } else {

            if (modifyComputer) {
                view.setModifyComputer(false);
                view.getAddButton().setText(R.string.add_button);
                model.updateComputer(computer);
                Toast.makeText(view.getApplicationContext(), R.string.modified_computer, Toast.LENGTH_SHORT).show();
            } else {
                computer.setId(0);
                model.insertComputer(computer);
                Toast.makeText(view.getApplicationContext(), R.string.added_computer, Toast.LENGTH_SHORT).show();
            }
            view.cleanForm();
        }
    }
}

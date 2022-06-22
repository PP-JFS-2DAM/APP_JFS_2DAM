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

public class AddOrderPresenter implements AddOrderContract.Presenter {

    private AddOrderModel model;
    private AddOrderView view;
    private Context context;

    public AddOrderPresenter(AddOrderView view) {
        this.context = view.getApplicationContext();
        this.view = view;

        model = new AddOrderModel();
    }

    @Override
    public void addOrder(Order order, Boolean modifyOrder) {

        Computer computer = new Computer();
        User user = new User();

        model.startDb(view.getApplicationContext());

        if (view.getComputerSpinner().getCount() == 0) {
            // TODO view.showMessage(message);
        } else if ((order.getDescription().equals("")) || (order.getOrderDate() == null)) {
            Toast.makeText(context, R.string.complete_all_fields, Toast.LENGTH_SHORT).show();
        } else {
            user.setId((int) view.getUsers().get(view.getUserSpinner().getSelectedItemPosition()).getId());
            // order.setUser(user);
            computer.setId(view.getComputers().get(view.getComputerSpinner().getSelectedItemPosition()).getId());
            order.setComputer(computer);

            if (modifyOrder) {
                view.setModifyOrder(false);
                view.getAddButton().setText(R.string.add_button);
                model.updateOrder(order);
                Toast.makeText(context, R.string.modified_order, Toast.LENGTH_SHORT).show();
            } else {
                order.setId(0);
                model.insertOrder(order);
                Toast.makeText(context, R.string.added_order, Toast.LENGTH_SHORT).show();
            }
            view.cleanForm();
        }
    }

    @Override
    public void fillComputerSpinner(int userId) {
        view.fillComputerSpinner(model.userComputers(userId, view.getApplicationContext()));
    }

    @Override
    public void fillUserSpinner() {
        view.fillUserSpinner(model.users(view.getApplicationContext()));
    }
}
